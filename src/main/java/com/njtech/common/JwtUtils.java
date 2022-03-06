package com.njtech.common;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.njtech.entity.UserInfo;
import com.njtech.exception.CustomException;

import java.util.Calendar;
import java.util.Date;

public class JwtUtils {
    private static final String MYSECRET = "Hello NJU";

    /**
     * 签发对象：这个用户的id
     * 签发时间：现在
     * 有效时间：24小时
     * 载荷内容：暂时设计为：这个人的名字，这个人的昵称
     * 加密密钥：这个人的id加上一串字符串
     */
    public static String createToken(UserInfo user) {
        String userId = user.getUserId();
        String userName = user.getName();
        Integer identity = user.getIdentity();

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.HOUR, 24);
        Date expiresDate = nowTime.getTime();

        return JWT.create().withAudience(userId)   //签发对象
                .withIssuedAt(new Date())    //发行时间
                .withExpiresAt(expiresDate)  //有效时间
                .withClaim("userName", userName)  //载荷
                .withClaim("userId", userId)
                .withClaim("identity", identity)
                .sign(Algorithm.HMAC256(userId + MYSECRET));   //加密
    }

    /**
     * 检验合法性
     *
     * @param token
     * @throws CustomException
     */
    public static void verifyToken(String token, String userId) throws CustomException {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(userId + MYSECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //校验失败
            throw new CustomException("2003", "认证失败");
        }
    }

    /**
     * 获取签发对象
     */
    public static String getAudience(String token) throws CustomException {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            //这里是token解析失败
            throw new CustomException("4001", "token解析失败");
        }
        return audience;
    }

    /**
     * 通过载荷名字获取载荷的值
     */
    public static Claim getClaimByString(String token, String name) {
        return JWT.decode(token).getClaim(name);
    }
}


