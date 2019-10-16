/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.ray.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/**
 * TODO
 *
 * @author liufeng
 * @version V1.0
 * @since 2019-10-15 17:54
 */
public class JwtUtil {
    public static final String SECRET = "poy7go/IVV7+rly0uJY9Vw==";

    // 过期时间30分钟
    public static final long EXPIRE_TIME = 30 * 60;

    /** token 过期时间: 60小时 */
    public static final int CALENDARFIELD = Calendar.MINUTE;
    public static final int CALENDARINTERVAL = 30;

    /**
     * JWT生成Token.<br/>
     *
     * JWT构成: header, payload, signature
     *
     * @param userId
     *            登录成功后用户userId, 参数userId不可传空
     */
    public static String createToken(String userId) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(CALENDARFIELD, CALENDARINTERVAL);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        /**
         * build token
         * param backups {iss:Service, aud:APP}
         *
         * withHeader : header
         * withClaim : payload
         * withIssuedAt : sign time
         * withExpiresAt : expire time
         * sign :signature
         */
        return JWT.create().withHeader(map)
            .withClaim("iss", "Service")
            .withClaim("aud", "APP")
            .withClaim("user_id", StringUtils.isBlank(userId) ? null : userId)
            .withIssuedAt(iatDate)
            //                .withExpiresAt(expiresDate)
            .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) throws Exception{
        DecodedJWT jwt = null;
        Map<String, Claim> claims = null;

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        jwt = verifier.verify(token);
        claims = jwt.getClaims();
        Optional.ofNullable(claims).orElseThrow(IllegalArgumentException::new);
        //        } catch (Exception e) {
        //            // e.printStackTrace();
        //            // token 校验失败, 抛出Token验证非法异常
        //            System.out.println("IllegalArgumentException : token 校验失败");
        //        }
        return claims;
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static String getAppUID(String token) throws Exception{
        Map<String, Claim> claims = verifyToken(token);
        Claim userIdClaim = claims.get("user_id");
        if (null == userIdClaim || StringUtils.isEmpty(userIdClaim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }
        return userIdClaim.asString();
    }
    public static void main(String[] args){
        try {
            String token = createToken("jt501");
            System.out.println("token ======== " + token);
            //            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJ1c2VyX2lkIjoianQ1MDEiLCJpc3MiOiJTZXJ2aWNlIiwiZXhwIjoxNTcwNDEwNTMzLCJpYXQiOjE1NzA0MTA0NzN9.RlaXGa9Fy4foYNPjtSxpc9sDK531A6vNg3ouxSZHPVM";

            Map<String, Claim> claimMap = verifyToken(token);
            System.out.println("claimMap ======== " + claimMap);

            System.out.println(getAppUID(token));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
