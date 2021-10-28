package com.nature.qrcodelogin.service.impl;

import com.nature.qrcodelogin.service.ITokenService;
import com.nature.qrcodelogin.util.RedisKeyBuilder;
import com.nature.qrcodelogin.vo.AccessToken;
import com.nature.qrcodelogin.vo.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 访问令牌管理类
 *
 * @author wangck
 * @date 2017年5月24日 下午2:08:34
 */
@Service("tokenService")
public class RedisTokenServiceImpl implements ITokenService {

    private final Logger logger = LoggerFactory.getLogger(RedisTokenServiceImpl.class);
    @Autowired
    private RedisService redisService;

    @Override
    public Response<AccessToken> createAccessToken(String userId) {
        if (StringUtils.isBlank(userId)) {
            logger.error("userId参数不能为空");
            return Response.failResult("userId参数不能为空");
        }
        AccessToken accessTokenObj = new AccessToken();
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        accessTokenObj.setAccessToken(accessToken);
        accessTokenObj.setExpireIn(AccessToken.ACCESSTOKEN_EXPIREIN);
        accessTokenObj.setRefreshToken(refreshToken);
        accessTokenObj.setOpenId(userId);

        //缓存token与userId的关联关系
        String userIdAccessTokenKey = RedisKeyBuilder.getUserIdAccessTokenKey(userId);
        redisService.cacheValue(userIdAccessTokenKey, accessToken, AccessToken.ACCESSTOKEN_EXPIREIN);
        String accessTokenUserIdKey = RedisKeyBuilder.getAccessTokenUserIdKey(accessToken);
        redisService.cacheValue(accessTokenUserIdKey, userId, AccessToken.ACCESSTOKEN_EXPIREIN);
        return Response.successResult("生成访问令牌成功", accessTokenObj);
    }


}
