package com.nature.qrcodelogin.util;

import java.text.MessageFormat;

public class RedisKeyBuilder {

    /**
     * 登录二维码key
     */
    private static final String LOGINQRCODE_KEY = "qrcode:login:{0}";

    /**
     * 用户Id与accessToken关系
     */
    private static final String USERID_ACCESSTOKEN_KEY = "userId:at:{0}";

    /**
     * accessToken与userId关系
     */
    private static final String ACCESSTOKEN_USERID_KEY = "at:userId:{0}";


    /**
     * 二维码与用户管理Key
     *
     * @param qrcodeId
     * @return
     */
    public static String getLoginqrcodeKey(String qrcodeId) {
        return MessageFormat.format(LOGINQRCODE_KEY, qrcodeId);
    }


    /**
     * 登录成功 令牌与登录用户key
     *
     * @param userId
     * @return
     */
    public static String getUserIdAccessTokenKey(String userId) {
        return MessageFormat.format(USERID_ACCESSTOKEN_KEY, userId);
    }


    /**
     * 登录成功 令牌与登录用户key
     *
     * @param accessToken
     * @return
     */
    public static String getAccessTokenUserIdKey(String accessToken) {
        return MessageFormat.format(ACCESSTOKEN_USERID_KEY, accessToken);
    }

}
