package com.nature.qrcodelogin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class QrcodeloginWebApplication extends SpringBootServletInitializer {
	
	private static Logger logger = LoggerFactory.getLogger(QrcodeloginWebApplication.class);
	
	@Autowired
	private RestTemplateBuilder builder;

	@Bean
	public RestTemplate restTemplate() {
		return builder.build();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(QrcodeloginWebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		logger.info("二维码登录demo启动开始");
		SpringApplication.run(QrcodeloginWebApplication.class, args);
		logger.info("二维码登录demo启动结束");
	}

}