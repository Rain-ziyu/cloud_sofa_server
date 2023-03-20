package asia.huayu.security.security;

import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.security.entity.OnlineUser;
import asia.huayu.security.util.SystemValue;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenManager {
    // token有效时长
    private final long tokenEcpiration = 24 * 60 * 60 * 1000;
    // refreshToken有效时长
    private final long refreshTokenEcpiration = 24 * 60 * 60 * 1000 * 10;
    // 编码秘钥
    private final String tokenSignKey = "123456";
    private final String refreshTokenSignKey = "24681379";
    @Autowired
    private RedisTemplate redisTemplate;

    // 1 使用jwt根据用户名生成token
    public String createToken(String username, OnlineUser onlineUser) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenEcpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        // 统一在redis中放入生成的token
        onlineUser.setToken(token);
        redisTemplate.opsForHash().put(SystemValue.LOGIN_USER, token, onlineUser);
/*         // TODO: 将token放入redis，value暂时为其本身 仅用于验证当前token 是否被注销  修复用户多次登录 一次注销全部token失效
        redisTemplate.opsForValue().set(SystemValue.TOKEN_PREFIX+token,token,tokenEcpiration, TimeUnit.MILLISECONDS);
        // TODO: 每创建一个token，再redis中按照用户名进行存储该用户的所有token  当用户注销时，获取该list 查询所有token是否还存在 全部不存在则再在线用户中移除该用户
        redisTemplate.opsForSet().add(SystemValue.TOKEN_PREFIX+username,token); */
        return token;
    }

    // 1 使用jwt根据用户名生成refreshToken
    public String createRefreshToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenEcpiration))
                .signWith(SignatureAlgorithm.HS512, refreshTokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    // 2 根据refreshToken字符串得到用户信息
    public String getUserInfoFromRefreshToken(String token) {
        String userinfo = Jwts.parser().setSigningKey(refreshTokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return userinfo;
    }

    // 2 根据token字符串得到用户信息
    public String getUserInfoFromToken(String token) {
        if (redisTemplate.opsForHash().hasKey(SystemValue.LOGIN_USER, token)) {
            String userinfo = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
            return userinfo;
        }
        throw new ServiceProcessException("无效的token");
    }

    // 3 删除token
    public void removeToken(String token) {
        // 从redis中移除token记录
        redisTemplate.opsForHash().delete(SystemValue.LOGIN_USER, token);
/*         redisTemplate.delete(SystemValue.TOKEN_PREFIX + token);
        redisTemplate.opsForSet().remove(SystemValue.TOKEN_PREFIX+getUserInfoFromToken(token),token); */
    }
}
