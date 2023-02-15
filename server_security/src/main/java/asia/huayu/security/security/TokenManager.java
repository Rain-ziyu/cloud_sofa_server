package asia.huayu.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    // 1 使用jwt根据用户名生成token
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenEcpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
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
        String userinfo = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return userinfo;
    }

    // 3 删除token
    public void removeToken(String token) {
    }
}
