package com.nature.qrcodelogin.vo;

/**
 * @author wangck
 * @date 2019/7/10
 */
public class AccessToken extends SundataObject {

    /**
     *
     */
    private static final long serialVersionUID = -5119046968646554604L;

    /**
     * 访问令牌初始化过期时间 2小时 = 2*3600
     */
    public static final int ACCESSTOKEN_EXPIREIN = 7200;
    /**
     * 访问授权令牌
     */
    private String accessToken;
    /**
     * 访问过期时间
     */
    private int expireIn;
    /**
     * 刷新token
     */
    private String refreshToken;
    /**
     * 用户Id
     */
    private String openId;

    /**
     * 账号状态 0 已禁用 1正常 2 未激活
     */
    private String state;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
