package com.athan.utils;

import cn.hutool.core.util.IdUtil;
import com.athan.constant.Constant;
import io.jsonwebtoken.*;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成token
 * @author : Kou Shenhai
 * @date : 2020-06-05 10:50
 */
@Data
public class TokenUtil {
    /**
     * 私钥
     */
    private static final String SECRET = "5201314QSH";
    /**
     * 超时时间
     */
    private static final Long EXPIRE = 1800L;

    public static Long getExpire() {
        return EXPIRE;
    }

    public static Map<String,Object> getClaims(Long userId,String username) {
        Map<String,Object> claims = new HashMap<>(2);
        claims.put(Constant.USER_KEY_HEAD, userId);
        claims.put(Constant.USERNAME_HEAD,username);
        return claims;
    }

    /**
     * 获取token
     * @param claims
     * @return
     */
    public static String getToken(Map<String,Object> claims){
        return Jwts.builder()
                //签发编号
                .setId(IdUtil.simpleUUID())
                //参数头
                .setHeaderParam("typ","JWT")
                //说明
                .setSubject("SSO")
                //签发信息
                .setIssuer("LAOKOU")
                //接收者
                .setAudience("CUSTOMER")
                .setClaims(claims)
                //签发时间
                .setIssuedAt(DateTime.now().toDate())
                //过期时间
                .setExpiration(DateTime.now().plusSeconds(EXPIRE.intValue()).toDate())
                //数据压缩方式
                .compressWith(CompressionCodecs.GZIP)
                //加密方式
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 验证是否过期
     * @param token
     * @return
     * @throws Exception
     */
    public static boolean isExpiration(String token) {
        try {
            Claims claimsBody = getClaimsBody(token);
            boolean beforeDateFlag = claimsBody.getExpiration().before(new Date());
            return beforeDateFlag;
        }catch (Exception e){
            return true;
        }
    }

    /**
     * 解析token信息
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token){
        return getJws(token).getBody();
    }

    public static Long getUserId(String token) {
        return getClaimsBody(token).get(Constant.USER_KEY_HEAD,Long.class);
    }

    public static String getUsername(String token) {
        return getClaimsBody(token).get(Constant.USERNAME_HEAD,String.class);
    }

    public static Jws<Claims> getJws(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
    }

}
