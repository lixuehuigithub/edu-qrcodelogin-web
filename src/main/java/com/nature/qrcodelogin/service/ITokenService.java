package com.nature.qrcodelogin.service;

import com.nature.qrcodelogin.vo.AccessToken;
import com.nature.qrcodelogin.vo.Response;

import java.io.Serializable;

/**
 * @author wangck
 * @date 2017年5月23日 下午2:42:01
 */
public interface ITokenService extends Serializable {

    /**
     * 生成访问令牌accessToken
     *
     * @param userId
     * @return
     */
    public Response<AccessToken> createAccessToken(String userId);


}
