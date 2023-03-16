package asia.huayu.service.impl;

import asia.huayu.auth.entity.UserRole;
import asia.huayu.auth.mapper.UserRoleMapper;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.constant.CommonConstant;
import asia.huayu.constant.RabbitMQConstant;
import asia.huayu.constant.RedisConstant;
import asia.huayu.entity.User;
import asia.huayu.entity.UserInfo;
import asia.huayu.enums.LoginTypeEnum;
import asia.huayu.enums.RoleEnum;
import asia.huayu.mapper.UserInfoMapper;
import asia.huayu.mapper.UserMapper;
import asia.huayu.model.dto.EmailDTO;
import asia.huayu.model.dto.UserDetailsDTO;
import asia.huayu.model.vo.QQLoginVO;
import asia.huayu.model.vo.UserVO;
import asia.huayu.service.AuroraInfoService;
import asia.huayu.service.RedisService;
import asia.huayu.service.UserService;
import asia.huayu.strategy.context.SocialLoginStrategyContext;
import asia.huayu.util.UserUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static asia.huayu.util.CommonUtil.checkEmail;
import static asia.huayu.util.CommonUtil.getRandomCode;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AuroraInfoService auroraInfoService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SocialLoginStrategyContext socialLoginStrategyContext;

    @Override
    public User getUserByUsername(String userName) {
        return userMapper.getUserByUsername(userName);
    }

    ;

    @Override
    public void sendCode(String userEmail) {
        if (!checkEmail(userEmail)) {
            throw new ServiceProcessException("请输入正确邮箱");
        }
        String code = getRandomCode();
        Map<String, Object> map = new HashMap<>();
        map.put("content", "您的验证码为 " + code + " 有效期15分钟，请不要告诉他人哦！");
        EmailDTO emailDTO = EmailDTO.builder()
                .email(userEmail)
                .subject(CommonConstant.CAPTCHA)
                .template("common.html")
                .commentMap(map)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConstant.EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        redisService.set(RedisConstant.USER_CODE_KEY + userEmail, code, RedisConstant.CODE_EXPIRE_TIME);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserVO userVO) {
        if (!checkEmail(userVO.getUsername())) {
            throw new ServiceProcessException("邮箱格式不对!");
        }
        if (checkUser(userVO)) {
            throw new ServiceProcessException("邮箱已被注册！");
        }
        UserInfo userInfo = UserInfo.builder()
                .email(userVO.getUsername())
                .nickname(CommonConstant.DEFAULNICKNAME + IdWorker.getId())
                .avatar(auroraInfoService.getWebsiteConfig().getUserAvatar())
                .build();
        userInfoMapper.insert(userInfo);
        UserRole userRole = UserRole.builder()
                .userId(String.valueOf(userInfo.getId()))
                .roleId(String.valueOf(RoleEnum.USER.getRoleId()))
                .build();
        userRoleMapper.insert(userRole);
        User user = User.builder()
                .id(userInfo.getId())
                .username(userVO.getUsername())
                .password(passwordEncoder.encode(userVO.getPassword()))
                .build();
        userMapper.insert(user);
    }

    @Override
    public void updatePassword(UserVO userVO) {
        if (!checkUser(userVO)) {
            throw new ServiceProcessException("邮箱尚未注册！");
        }
        // 从header中获取用户名
        Authentication authentication = UserUtil.getAuthentication();
        String name = authentication.getName();
        userMapper.update(new User(), new LambdaUpdateWrapper<User>()
                .set(User::getPassword, passwordEncoder.encode(userVO.getPassword()))
                .eq(User::getUsername, name));
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserDetailsDTO qqLogin(QQLoginVO qqLoginVO) {
        return socialLoginStrategyContext.executeLoginStrategy(JSON.toJSONString(qqLoginVO), LoginTypeEnum.QQ);
    }

    private Boolean checkUser(UserVO userVO) {
        if (!userVO.getCode().equals(redisService.get(RedisConstant.USER_CODE_KEY + userVO.getUsername()))) {
            throw new ServiceProcessException("验证码错误！");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getUsername)
                .eq(User::getUsername, userVO.getUsername()));
        return Objects.nonNull(user);
    }

}
