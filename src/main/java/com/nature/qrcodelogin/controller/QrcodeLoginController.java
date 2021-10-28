package com.nature.qrcodelogin.controller;

import com.nature.qrcodelogin.service.ILoginQrcodeService;
import com.nature.qrcodelogin.vo.AccessToken;
import com.nature.qrcodelogin.vo.LoginQrcodeVO;
import com.nature.qrcodelogin.vo.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangck
 * @date 2019/7/9
 */
@Controller
@RequestMapping("/login/qrcode")
public class QrcodeLoginController {
    private final static Logger logger = LoggerFactory.getLogger(QrcodeLoginController.class);

    @Autowired
    private ILoginQrcodeService loginQrcodeService;


    /**
     * 生成登录的二维码
     *
     * @return
     */
    @PostMapping("create")
    @ResponseBody
    public Response<LoginQrcodeVO> createLoginQrcode() {
        return loginQrcodeService.createLoginQrcode();
    }

    /**
     * 扫码登录
     * 1、获取登录用户信息
     * 2、拼接登录二维码
     *
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public Response<Boolean> qrcodeLogin(String qrcodeId, String userId) {
        if (StringUtils.isBlank(qrcodeId)) {
            logger.error("qrcodeId参数不能为空");
            return Response.failResult("二维码Id不能为空");
        }
        if (StringUtils.isBlank(userId)) {
            logger.error("userId参数不能为空");
            return Response.failResult("用户Id不能为空");
        }
        return loginQrcodeService.qrcodeLogin(qrcodeId, userId);
    }

    /**
     * 验证二维码是否已登录
     *
     * @return
     */
    @PostMapping("isLogined")
    @ResponseBody
    public Response<AccessToken> checkQrcodeIsLogined(String qrcodeId) {
        if (StringUtils.isBlank(qrcodeId)) {
            logger.error("qrcodeId参数不能为空");
            return Response.failResult("二维码Id不能为空");
        }
        return loginQrcodeService.getLoginQrcodeStatus(qrcodeId);
    }
}
