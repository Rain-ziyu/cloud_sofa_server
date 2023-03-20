package asia.huayu.service.impl;

import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.common.util.RequestUtil;
import asia.huayu.constant.RedisConstant;
import asia.huayu.entity.User;
import asia.huayu.entity.UserInfo;
import asia.huayu.entity.UserLoginInfo;
import asia.huayu.enums.FilePathEnum;
import asia.huayu.mapper.UserInfoMapper;
import asia.huayu.mapper.UserLoginInfoMapper;
import asia.huayu.mapper.UserMapper;
import asia.huayu.model.dto.UserInfoDTO;
import asia.huayu.model.vo.EmailVO;
import asia.huayu.model.vo.SubscribeVO;
import asia.huayu.model.vo.UserInfoVO;
import asia.huayu.service.RedisService;
import asia.huayu.service.UserInfoService;
import asia.huayu.service.UserService;
import asia.huayu.strategy.context.UploadStrategyContext;
import asia.huayu.util.UserUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private UserLoginInfoMapper userLoginInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) {
        String name = UserUtil.getAuthentication().getName();
        User user = userMapper.getUserByUsername(name);
        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .nickname(userInfoVO.getNickname())
                .intro(userInfoVO.getIntro())
                .website(userInfoVO.getWebsite())
                .build();
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public String updateUserAvatar(MultipartFile file) {
        String avatar = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath());
        String name = UserUtil.getAuthentication().getName();
        User user = userMapper.getUserByUsername(name);
        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .avatar(avatar)
                .build();
        userInfoMapper.updateById(userInfo);
        return avatar;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserEmail(EmailVO emailVO) {
        if (Objects.isNull(redisService.get(RedisConstant.USER_CODE_KEY + emailVO.getEmail()))) {
            throw new ServiceProcessException("验证码错误");
        }
        if (!emailVO.getCode().equals(redisService.get(RedisConstant.USER_CODE_KEY + emailVO.getEmail()).toString())) {
            throw new ServiceProcessException("验证码错误！");
        }
        String name = UserUtil.getAuthentication().getName();
        User user = userMapper.getUserByUsername(name);
        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .email(emailVO.getEmail())
                .build();
        userInfoMapper.updateById(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserSubscribe(SubscribeVO subscribeVO) {
        UserInfo temp = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, subscribeVO.getUserId()));
        if (StringUtils.isEmpty(temp.getEmail())) {
            throw new ServiceProcessException("邮箱未绑定！");
        }
        UserInfo userInfo = UserInfo.builder()
                .id(subscribeVO.getUserId())
                .isSubscribe(subscribeVO.getIsSubscribe())
                .build();
        userInfoMapper.updateById(userInfo);
    }


    @Override
    public UserInfoDTO getUserInfoById(Integer id) {
        UserInfo userInfo = userInfoMapper.selectById(id);
        User user = userMapper.selectById(userInfo.getId());
        UserLoginInfo userLoginInfo = userLoginInfoMapper.selectLastLoginInfo(id);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(userInfo.getId());
        userInfoDTO.setEmail(userInfo.getEmail());
        userInfoDTO.setLoginType(userLoginInfo.getLoginType());
        userInfoDTO.setUsername(user.getUsername());
        userInfoDTO.setNickname(userInfo.getNickname());
        userInfoDTO.setAvatar(userInfo.getAvatar());
        userInfoDTO.setIntro(userInfo.getIntro());
        userInfoDTO.setWebsite(userInfo.getWebsite());
        userInfoDTO.setIsSubscribe(userInfo.getIsSubscribe());
        userInfoDTO.setIpAddress(userLoginInfo.getIpAddress());
        userInfoDTO.setIpSource(userLoginInfo.getIpSource());
        userInfoDTO.setLastLoginTime(DateUtil.toLocalDateTime(userLoginInfo.getLoginTime()));
        return userInfoDTO;
    }

    @Override
    public UserInfo getUserInfoByName(String username) {
        User user = userMapper.getUserByUsername(username);
        UserInfo userInfo = userInfoMapper.selectById(user.getId());
        return userInfo;
    }

    @Override
    public UserInfoDTO getUserInfo() {
        User userByUsername = userMapper.getUserByUsername(UserUtil.getAuthentication().getName());
        UserInfoDTO userInfo = userInfoMapper.selectDTOById(userByUsername.getId());
        // 返回用户名
        HttpServletRequest request = RequestUtil.getRequest();
        userInfo.setUsername(UserUtil.getAuthentication().getName());
        userInfo.setToken(request.getParameter("token"));
        userInfo.setRefreshToken(request.getParameter("refreshToken"));
        return userInfo;
    }

}
