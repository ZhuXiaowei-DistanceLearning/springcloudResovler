package com.zxw.gateway.filter;

import com.zxw.pojo.JWTResult;
import io.jsonwebtoken.*;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author zxw
 * @date 2020/6/27 11:57
 */
public class JwUtil {
    private static RSAPrivateKey privateKey;
    private static RSAPublicKey publicKey;

    public static String generateToken(String user) {
        HashMap<String, Object> map = new HashMap<>();
        JWTResult jwtResult = new JWTResult();
        String token = Jwts.builder()
                .setSubject("user info").setClaims(map)
                .signWith(SignatureAlgorithm.HS512, publicKey)
                .compact();
        return token;
    }

    public static String getToken(String userId, int exp) {
        return Jwts.builder().setSubject(userId).setExpiration(new Date()).signWith(SignatureAlgorithm.RS512, publicKey).compact();
    }

    public static Map<String, String> validateToken(String token) {
        try {
            HashMap<String, String> map = new HashMap<>();
            Claims body = Jwts.parser()
                    .setSigningKey("")
                    .parseClaimsJws(token)
                    .getBody();
            Object user = body.get("user");
            return map;
        } catch (ExpiredJwtException e) {
            // token已经过期
            throw new RuntimeException("token已过期");
        } catch (SignatureException e) {
            // 秘钥不正确
            throw new RuntimeException("非法请求");
        }
    }
}
