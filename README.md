# edu-qrcodelogin-web

#### 介绍
    基于现有工作，将已上线运行的二维码扫码登录功能抽取出来分享给大家参考！

#### 软件架构
    1、项目搭建基于springboot2.1.6
    2、本项目运行依赖于redis 和nginx（作为图片服务器，显示二维码图片），
    3、本项目日志采用slf4j，日志实现使用log4j2，增加日志拦截器，详细输出请求日志信息

#### 安装教程
    1、下载工程到本地，使用IntelliJ IDEA导入项目源代码
    2、项目运行前置条件：搭建本地的redis环境和nginx服务 
    nginx增加如下配置： 
    
    http {
        include       mime.types;
        default_type  application/octet-stream;
        sendfile        on;   
        keepalive_timeout  65;
        server {
            listen       80;
            server_name  10.0.20.48;
            charset utf-8;
            location / {
                root   F://qrcode;     
            }      
        }
    }

    3、修改edu-qrcodelogin-web的application.properites配置文件
        修改redis配置信息包括redis地址、库、链接账号密码
        spring.redis.database=0
        spring.redis.host=localhost
        spring.redis.port=6379
        spring.redis.password=root

        修改图片服务器地址（nginx配置）
        img.server.url = http://localhost:80

        修改二维码存储地址（与nginx配置中的root路径保持一致，不然二维码图片无法访问到）
        qrcode.store.root.path=F:\\qrcode
    
    4、运行QrcodeloginWebApplication启动类（main方法），启动工程

#### 使用说明
    工程启动后，打开浏览器输入http://locahost:7010 打开页面切换到二维码登录即可, 
       
    使用过程中如有疑问可查看我的博客或联系我：
    博客地址：https://www.jianshu.com/p/cad255284c13
    qq:      313393310
   


