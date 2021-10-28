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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wangck
 * @date 2019/7/9
 */
@Controller
@RequestMapping("/user")
public class UserIndexController {
    private final static Logger logger = LoggerFactory.getLogger(UserIndexController.class);


    @GetMapping("/index")
    public ModelAndView userIndex() {
        ModelAndView view = new ModelAndView();
        view.setViewName("userIndex");
        return view;
    }


}
