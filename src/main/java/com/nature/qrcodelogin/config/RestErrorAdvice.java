package com.nature.qrcodelogin.config;

import com.nature.qrcodelogin.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wangck
 * @date 2019/7/9
 */
@RestControllerAdvice(annotations = RestController.class)
public class RestErrorAdvice {
    private final static Logger log = LoggerFactory.getLogger(RestErrorAdvice.class);

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public Response<String> handle(RuntimeException e) {
        log.error(e.getMessage(), e);
        return Response.failResult("2001","请求响应异常");
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response<String> handle(Exception e) {
        log.error(e.getMessage(), e);
        return Response.failResult("2001","请求响应异常");
    }

}