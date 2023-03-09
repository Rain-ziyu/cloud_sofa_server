package asia.huayu.auth.config;

/**
 * @author RainZiYu
 * @Date 2023/3/7
 */

import asia.huayu.auth.entity.ResourceRole;
import asia.huayu.auth.mapper.RoleMapper;
import asia.huayu.security.util.SystemValue;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 采用读写锁来控制 保证load之间相互冲突 load与clear之间相互冲突 但是clear之间不冲突 保证如果正在load的时候
     */
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 方法loadResourceRoleList作用为：
     * 初始化将数据库中的角色权限信息加载到redis中   来解决微服务时不同服务更新了接口权限 其他微服务如何读取
     *
     * @param
     * @return void
     * @throws
     * @author RainZiYu
     */
    @PostConstruct
    private void loadResourceRoleList() {
        List<ResourceRole> resourceRoleList = roleMapper.listResourceRoles();
        HashMap<Integer, ResourceRole> resourceRoleMap = new HashMap();
        resourceRoleList.stream().forEach(resourceRole ->
                {
                    resourceRoleMap.put(resourceRole.getId(), resourceRole);
                }
        );
        redisTemplate.delete(SystemValue.ROLE_AUTH);
        redisTemplate.opsForHash().putAll(SystemValue.ROLE_AUTH, resourceRoleMap);
    }

    public void clearDataSource() {
        readWriteLock.readLock().lock();
        try {
            redisTemplate.delete(SystemValue.ROLE_AUTH);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 方法getAttributes作用为：
     * 使用redis中的角色接口权限缓存
     *
     * @param object
     * @return java.util.Collection<org.springframework.security.access.ConfigAttribute>
     * @throws
     * @author RainZiYu
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HashMap<Integer, ResourceRole> resourceRoleMap = (HashMap<Integer, ResourceRole>) redisTemplate.opsForHash().entries(SystemValue.ROLE_AUTH);
        // TODO: 采用双端检锁防止缓存击穿  实际上微服务中应该引入分布式锁
        if (ObjectUtil.isNull(resourceRoleMap) || resourceRoleMap.size() == 0) {
            readWriteLock.writeLock().lock();
            try {
                if (ObjectUtil.isNull(resourceRoleMap) || resourceRoleMap.size() == 0) {
                    loadResourceRoleList();
                }
                // load完之后加载一次
                resourceRoleMap = (HashMap<Integer, ResourceRole>) redisTemplate.opsForHash().entries(SystemValue.ROLE_AUTH);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }
        Collection<ResourceRole> resourceRoleList = resourceRoleMap.values();
        FilterInvocation fi = (FilterInvocation) object;
        String method = fi.getRequest().getMethod();
        String url = fi.getRequest().getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 这里只能是遍历进行 match  使用map也无法绕过需要遍历match
        for (
                ResourceRole resourceRole : resourceRoleList) {
            if (antPathMatcher.match(resourceRole.getUrl(), url) && resourceRole.getRequestMethod().equals(method)) {
                List<String> roleList = resourceRole.getRoleList();
                if (CollectionUtils.isEmpty(roleList)) {
                    return SecurityConfig.createList("disable");
                }
                return SecurityConfig.createList(roleList.toArray(new String[]{}));
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
