package asia.huayu.service.impl;

import asia.huayu.auth.config.FilterInvocationSecurityMetadataSourceImpl;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.config.RestConfig;
import asia.huayu.constant.CommonConstant;
import asia.huayu.entity.Resource;
import asia.huayu.entity.RoleResource;
import asia.huayu.mapper.ResourceMapper;
import asia.huayu.mapper.RoleResourceMapper;
import asia.huayu.model.dto.LabelOptionDTO;
import asia.huayu.model.dto.ResourceDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.ResourceVO;
import asia.huayu.security.util.SystemValue;
import asia.huayu.service.RedisService;
import asia.huayu.service.ResourceService;
import asia.huayu.util.BeanCopyUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author User
 * 接口权限服务类
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    private RestConfig.RestService restService;

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @SuppressWarnings("all")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Resource> importSwagger(String targetUrl, String urlPrefix) {
        // this.remove(null);   不删除所有
        // roleResourceMapper.delete(null);
        List<Resource> resources = this.list(new LambdaQueryWrapper<Resource>().isNull(Resource::getParentId));
        Map<String, Object> data = restService.restTemplate.getForObject(targetUrl, Map.class);
        Map<String, Integer> resourcesMap = resources.stream()
                .collect(Collectors.toMap(Resource::getResourceName, Resource::getId));
        resources.clear();
        Map<String, Map<String, Map<String, Object>>> path = (Map<String, Map<String, Map<String, Object>>>) data.get("paths");
        path.forEach((url, value) -> value.forEach((requestMethod, info) -> {
            String permissionName = info.getOrDefault("summary", "").toString();
            List<String> tag = (List<String>) info.get("tags");
            Integer parentId = resourcesMap.get(tag.get(0));
            // 如果以前不存在这个tag则新增
            if (ObjectUtil.isNull(parentId)) {
                Resource resource = Resource.builder()
                        .resourceName(tag.get(0))
                        .isAnonymous(CommonConstant.FALSE)
                        .createTime(LocalDateTime.now())
                        .build();
                this.save(resource);
                parentId = resource.getId();
                resourcesMap.put(resource.getResourceName(), resource.getId());
            }
            Resource resource = Resource.builder()
                    .resourceName(permissionName)
                    .url(urlPrefix + url.replaceAll("\\{[^}]*\\}", "*"))
                    .parentId(parentId)
                    .requestMethod(requestMethod.toUpperCase())
                    .isAnonymous(CommonConstant.FALSE)
                    .createTime(LocalDateTime.now())
                    .build();
            Long aLong = resourceMapper.selectCount(new LambdaQueryWrapper<Resource>()
                    .eq(Resource::getUrl, resource.getUrl()).eq(Resource::getRequestMethod, resource.getRequestMethod()));
            if (aLong == 0) {
                this.save(resource);
                // 原来不存在则加入保存列表
                resources.add(resource);
            }
        }));

        return resources;
    }

    @Override
    public void saveOrUpdateResource(ResourceVO resourceVO) {
        Resource resource = BeanCopyUtil.copyObject(resourceVO, Resource.class);
        this.saveOrUpdate(resource);
        // 同步更新redis中的资源   如果存在或者接口权限是从允许匿名被修改为不允许则清空redis  没有的话先不管 因为该资源暂时没有对应的角色 不会影响鉴权
        if (redisService.hHasKey(SystemValue.ROLE_AUTH, resource.getId()) || resource.getIsAnonymous() == 0) {
            filterInvocationSecurityMetadataSource.clearDataSource();
        }

    }

    @Override
    public void deleteResource(Integer resourceId) {
        Long count = roleResourceMapper.selectCount(new LambdaQueryWrapper<RoleResource>()
                .eq(RoleResource::getResourceId, resourceId));
        if (count > 0) {
            throw new ServiceProcessException("该资源下存在角色");
        }
        List<Integer> resourceIds = resourceMapper.selectList(new LambdaQueryWrapper<Resource>()
                        .select(Resource::getId).
                        eq(Resource::getParentId, resourceId))
                .stream()
                .map(Resource::getId)
                .collect(Collectors.toList());
        resourceIds.add(resourceId);
        resourceMapper.deleteBatchIds(resourceIds);
    }

    @Override
    public List<ResourceDTO> listResources(ConditionVO conditionVO) {
        List<Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<Resource>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Resource::getResourceName, conditionVO.getKeywords()));
        List<Resource> parents = listResourceModule(resources);
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resources);
        List<ResourceDTO> resourceDTOs = parents.stream().map(item -> {
            ResourceDTO resourceDTO = BeanCopyUtil.copyObject(item, ResourceDTO.class);
            List<ResourceDTO> child = BeanCopyUtil.copyList(childrenMap.get(item.getId()), ResourceDTO.class);
            resourceDTO.setChildren(child);
            childrenMap.remove(item.getId());
            return resourceDTO;
        }).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Resource> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<ResourceDTO> childrenDTOs = childrenList.stream()
                    .map(item -> BeanCopyUtil.copyObject(item, ResourceDTO.class))
                    .collect(Collectors.toList());
            resourceDTOs.addAll(childrenDTOs);
        }
        return resourceDTOs;
    }

    @Override
    public List<LabelOptionDTO> listResourceOption() {
        List<Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getId, Resource::getResourceName, Resource::getParentId)
                .eq(Resource::getIsAnonymous, CommonConstant.FALSE));
        List<Resource> parents = listResourceModule(resources);
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resources);
        return parents.stream().map(item -> {
            List<LabelOptionDTO> list = new ArrayList<>();
            List<Resource> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .map(resource -> LabelOptionDTO.builder()
                                .id(resource.getId())
                                .label(resource.getResourceName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getResourceName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }


    private List<Resource> listResourceModule(List<Resource> resourceList) {
        return resourceList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<Resource>> listResourceChildren(List<Resource> resourceList) {
        return resourceList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Resource::getParentId));
    }

}
