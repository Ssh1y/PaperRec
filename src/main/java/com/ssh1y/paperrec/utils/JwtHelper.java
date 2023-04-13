package com.ssh1y.paperrec.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author chenweihong
 */
public class JwtHelper {
    private static final long TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;
    private static final String TOKEN_SIGN_KEY = "This_is_a_secret_created_by_Ssh1y";

    /**
     * 生成token
     *
     * @param userId   用户id
     * @param userType 用户类型
     * @return token
     */
    public static String createToken(Long userId, String userType) {
        return Jwts.builder()

                .setSubject("YYGH-USER")

                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))

                .claim("userId", userId).claim("userType", userType)

                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGN_KEY).compressWith(CompressionCodecs.GZIP).compact();
    }

    /**
     * 从token字符串获取userId
     *
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    /**
     * 从token字符串获取userType
     *
     * @param token
     * @return
     */
    public static String getUserType(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) (claims.get("userType"));
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        try {
            //没有过期，有效，返回false
            return Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            //过期出现异常，返回true
            return true;
        }
    }


    /**
     * 刷新Token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token).getBody();
            refreshedToken = JwtHelper.createToken(getUserId(token), getUserType(token));
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
}
