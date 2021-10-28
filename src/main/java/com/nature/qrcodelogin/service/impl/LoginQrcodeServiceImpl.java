package com.nature.qrcodelogin.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.nature.qrcodelogin.service.ILoginQrcodeService;
import com.nature.qrcodelogin.service.ITokenService;
import com.nature.qrcodelogin.util.DateUtil;
import com.nature.qrcodelogin.util.FileUtil;
import com.nature.qrcodelogin.util.MatrixToImageWriter;
import com.nature.qrcodelogin.util.RedisKeyBuilder;
import com.nature.qrcodelogin.vo.AccessToken;
import com.nature.qrcodelogin.vo.LoginQrcodeVO;
import com.nature.qrcodelogin.vo.Response;
import com.nature.qrcodelogin.vo.ResponseCode;
import com.xuanner.seq.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author wangck
 * @date 2019/7/10
 */
@Service("LoginQrcodeService")
public class LoginQrcodeServiceImpl implements ILoginQrcodeService {

    private final static Logger logger = LoggerFactory.getLogger(LoginQrcodeServiceImpl.class);


    public static final Integer QRCODE_WIDTH = 230;
    public static final Integer QRCODE_HEIGHT = 230;

    @Autowired
    private RedisService redisService;
    @Autowired
    private ITokenService tokenService;

    @Value("${img.server.url}")
    private String imgServerUrl;
    @Value("${qrcode.login.url}")
    private String qrcodeLoginUrl;
    @Value("${qrcode.store.root.path}")
    private String qrcodeStoreRootPath;


    @Override
    public Response<LoginQrcodeVO> createLoginQrcode() {
        String qrcodeFormat = "png";
        String qrcodeId = UUIDUtils.uuid();
        // 1.创建二维码图片的存储目录以及二维码图片存储路径 /qrcode/201907/11/12
        String dateDir = DateUtil.getDayMonth() + "/" + DateUtil.getSimpleDay() + "/" + DateUtil.getSimpleHour();
        String qrcodeParentDir = qrcodeStoreRootPath + "/" + dateDir;
        FileUtil.createDir(qrcodeParentDir);


        String qrcodeName = qrcodeId + "." + qrcodeFormat;
        String qrcodeRelativePath = dateDir + "/" + qrcodeName;
        //二维码图片的存储路径
        String qrcodeStorePath = qrcodeStoreRootPath + "/" + qrcodeRelativePath;
        //二维码图片的访问路径
        String qrcodeImgUrl = imgServerUrl + "/" + qrcodeRelativePath;
        File qrcodeFile = new File(qrcodeStorePath);


        //3、生成二维码图片
        String qrcodeContent = qrcodeLoginUrl + "?qrcodeId=" + qrcodeId;
        HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>(1);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrcodeContent, BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_HEIGHT, hints);
            BufferedImage image = new BufferedImage(QRCODE_WIDTH, QRCODE_HEIGHT, BufferedImage.TYPE_INT_RGB);
            ImageIO.write(image, qrcodeFormat, qrcodeFile);
            MatrixToImageWriter.writeToFile(bitMatrix, qrcodeFormat, qrcodeFile);


            LoginQrcodeVO loginQrcode = new LoginQrcodeVO();
            loginQrcode.setQrcodeId(qrcodeId);
            loginQrcode.setQrcodeImgUrl(qrcodeImgUrl);
            // 4.写入Redis 缓存
            String loginQrcodeKey = RedisKeyBuilder.getLoginqrcodeKey(qrcodeId);
            // 设置登录二维码的value为空表示没有登录，3分钟有效;
            redisService.cacheValue(loginQrcodeKey, "", 180);
            return Response.successResult("生成二维码成功", loginQrcode);
        } catch (Exception e) {
            logger.error("生成二维码发生异常，异常信息：{}", e);
            return Response.failResult("生成二维码失败");
        }
    }

    @Override
    public Response<Boolean> qrcodeLogin(String qrcodeId, String userId) {
        String loginQrcodeKey = RedisKeyBuilder.getLoginqrcodeKey(qrcodeId);
        if (redisService.containsValueKey(loginQrcodeKey)) {
            boolean flag = redisService.cacheValue(loginQrcodeKey, userId, 180);
            if (flag) {
                return Response.successResult("二维码登录成功", flag);
            } else {
                logger.info("二维码登录失败，qrcodeId={}", qrcodeId);
                return Response.successResult("二维码登录失败", flag);
            }
        } else {
            logger.info("二维码已不存在，qrcodeId={}", qrcodeId);
            return Response.failResult("二维码登录失败", false);
        }
    }

    @Override
    public Response<AccessToken> getLoginQrcodeStatus(String qrcodeId) {
        String loginQrcodeKey = RedisKeyBuilder.getLoginqrcodeKey(qrcodeId);
        String userId = redisService.getValue(loginQrcodeKey);
        if (StringUtils.isBlank(userId)) {
            logger.info("二维码还未登录,qrcodeId={}", qrcodeId);
            return Response.failResult("二维码还未登录");
        }
        // 认证通过后，生成访问令牌
        Response<AccessToken> response = tokenService.createAccessToken(userId);
        if (ResponseCode.SUCCESS.equals(response.getCode())) {
            AccessToken accessTokenObj = response.getData();
            return Response.successResult("二维码已登录", accessTokenObj);
        } else {
            logger.error("二维码已登录，但生成访问令牌失败");
            return Response.failResult("二维码已登录，但是生成访问令牌失败");
        }
    }
}