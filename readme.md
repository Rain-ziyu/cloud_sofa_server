# 鸣谢：

本项目基于 [Aurora---前后端分离博客](https://github.com/linhaojun857/aurora)项目进行的二次开发

感谢[jetbrains](https://jb.gg/OpenSourceSupport)提供的开源开发许可证

![IntelliJ IDEA logo](https://resources.jetbrains.com/storage/products/company/brand/logos/IntelliJ_IDEA_icon.svg)

# 待办事项：

1.server_system使用feign调用server_file (已完成)

2.管理员后端 重写[pure-admin-backend](https://gitee.com/huayu_rain/pure-admin-backend)
后端服务，借助[vue-pure-admin](https://gitee.com/huayu_rain/vue-pure-admin)的前端实现（放弃）

3.整合[Aurora博客系统](https://github.com/linhaojun857/aurora)将其修改为为服务模式（已完成）

4.完善代码逻辑，修改并适配部分依赖的版本，以及数据库切换至读写分离（已完成）

5.增加备忘录功能

6.增加视频功能(服务器带宽不足)

7.增强接口管理：控制接口所需对应的角色(原作者已实现)

8.引入自己的图形验证码(放弃)

9.开放用户发帖(已完成)

**欢迎大家提出新的想法和issue**

首页展示
![](https://prod.huayu.asia:9000/picgo/202304071706754.png)
# 项目说明

本项目主要涉及的技术栈以及使用的主要依赖

| 技术或框架                                        | 版本         | 用途                    |
|:---------------------------------------------|------------|-----------------------|
| spring-boot                                  | 2.7.8      | 基础Java框架，提供IOC容器、AOP等 |
| spring-cloud                                 | 2021.0.5   | Spring微服务框架           |
| spring-cloud-starter-alibaba-nacos           | 2021.0.4.0 | 阿里的微服务框架主要使用Nacos     |
| mybatis-plus-boot-starter                    | 3.5.3      | 半ORM框架、数据库操作          |
| mysql-connector-j                            | 8.0.31     | 数据库驱动                 |
| hutool-all                                   | 5.8.11     | 工具类                   |
| minio（java）                                  | 8.4.5      | 连接minio               |
| knife4j-openapi3-spring-boot-starter         | 4.0.0      | 接口文档                  |
| shardingsphere-jdbc-core-spring-boot-starter | 5.2.1      | 读写分离、分库分表             |
| redisson                                     | 3.17.7     | redis连接、主要使用分布式锁      |
| fastjson2                                    | 2.0.22     | 阿里的json序列化            |
| spring-boot-starter-data-redis               | 2.7.8      | redis客户端工具            |
| ip2region                                    | 1.7.2      | ip解析归属地               |
| spring-boot-starter-thymeleaf                | 2.7.8      | 邮件模板                  |
| spring-boot-starter-mail                     | 2.7.8      | 邮件发送                  |
| spring-boot-starter-amqp                     | 2.7.8      | 消息中间件                 |
| spring-boot-starter-security                 | 2.7.8      | 鉴权框架                  |
| spring-cloud-starter-openfeign               | 3.1.5      | 微服务间调用                |
| maxwell                                      | 1.39.6     | 监听mysqlbinlog日志进行数据同步 |
| minio                                        |            | 对象存储，存储图片导入文件等        |
| rabbitmq                                     | management | 消息中间件，消息推送与订阅         |
| mysql                                        | 8.0.25     | 数据库、一主一从              |
| redis                                        | 6.2.6      | 内存中间件                 |
| elasticsearch                                | 8.4.3      | 搜索引擎                  |
| frp                                          | 0.47.0     | 内网穿透                  |
|                                              |            |                       |

# 前置条件

你已经部署了自己的nacos注册中心，并且自己部署了mysql与es存储引擎

# 后端服务部署（微服务+DOCKER）

将原有单机版项目重构为微服务形式，并使用Docker Service进行部署

## 部署后端BLOG与ADMIN服务

我使用的是华为云的流水线进行的自动化部署，这里假设使用的是纯手动

首先修改项目中的配置文件，主要修改nacos的配置为你的nacos注册中心和配置中心

首先将整个项目进行打包，执行

```shell
 mvn clean package -Dmaven.test.skip=true -U -e -X -B  
```

编译打包之后blog服务与admin服务的jar包应该已经成功生成，将其上传至服务器。

### 构建镜像

准备好jar包之后将对应服务代码库中的Dockerfile文件进行修改

以下为Blog服务的Dockerfile

```shell
#jdk镜像
FROM swr.cn-east-3.myhuaweicloud.com/huayu/openjdk:17-jdk
MAINTAINER ZiYu
WORKDIR /
#时区
ENV TZ Asia/Shanghai
#添加对应的jar文件到镜像中  前边地址为服务器中对应服务的jar文件地址
ADD  ./server_admin/target/server_admin-0.0.1-SNAPSHOT.jar  blog.jar
#执行启动命令
ENTRYPOINT ["java","-jar","/blog.jar"]
```

将以上dockerfile构建为镜像

### 启动服务

使用docker-compose-blog.yml进行服务启动

其中image:后的镜像为你自己生成的镜像名称与tag

```yml
version: '3.7'

services:
  blog:
    hostname: blog
    image: swr.cn-east-3.myhuaweicloud.com/huayu/sofa-server-blog:latest
    # volumes:
    #   - /opt/server_blog-0.0.1-SNAPSHOT.jar:/blog.jar
    labels:
      "type": "1"
    networks:
      - ali
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
  file:
    hostname: file
    image: swr.cn-east-3.myhuaweicloud.com/huayu/sofa-server-file:latest
    labels:
      "type": "1"
    networks:
      - ali
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
  admin:
    hostname: admin
    image: swr.cn-east-3.myhuaweicloud.com/huayu/sofa-server-admin:latest
    labels:
      "type": "1"
    networks:
      - ali
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
networks:
  ali:
    external: true
    name: huayu
```

开启服务

```
docker stack rm sofa_server

docker stack deploy -c docker-compose-blog.yml sofa_server
```

启动成功之后可以使用

docker service ls查看服务状态

docker logs -f 服务名 来查看对应服务的详细日志

## 部署nginx进行反向代理

为了将前端与后端处于同一端口下，需要使用nginx进行反向代理，将服务分别代理至对应域名的端口下

我这里是https，所以是443端口，并且我是容器启动的nginx，因此直接使用docker服务名即可访问对应服务

注意证书需要修改为你自己的ssl证书

```shell
server {
    listen       443;
    listen  [::]:443;
    server_name  prod.huayu.asia;
    charset utf-8;
    add_header X-Xss-Protection 1;
    ssl on;
    ssl_certificate /etc/nginx/cert/prod.huayu.asia_bundle.pem;
    ssl_certificate_key /etc/nginx/cert/prod.huayu.asia.key;
    ssl_session_timeout 5m;
    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    ssl_prefer_server_ciphers on;
    #access_log  /var/log/nginx/host.access.log  main;
    location / {		
        root   /usr/share/nginx/html/blog;
        index  index.html index.htm; 
        try_files $uri $uri/ /index.html;	
    }
    location /user {		
        proxy_pass http://blog:8080;
	    proxy_set_header   Host             $host;
        proxy_set_header   X-Real-IP        $remote_addr;						
        proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
    }
    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}
server {
    listen       443;
    listen  [::]:443;
    server_name  admin.huayu.asia;
    charset utf-8;
    add_header X-Xss-Protection 1;
    ssl on;
    ssl_certificate /etc/nginx/cert/admin.huayu.asia_bundle.pem;
    ssl_certificate_key /etc/nginx/cert/admin.huayu.asia.key;
    ssl_session_timeout 5m;
    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    ssl_prefer_server_ciphers on;
    #access_log  /var/log/nginx/host.access.log  main;
        location / {		
            root   /usr/share/nginx/html/admin;
            index  index.html index.htm; 
            try_files $uri $uri/ /index.html;	
        }
        location /admin {		
            proxy_pass http://admin:3399;
	        proxy_set_header   Host             $host;
            proxy_set_header   X-Real-IP        $remote_addr;						
            proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
        }
    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}
```

## 部署Minio进行存储管理

minio是一个对象存储服务，可以很方便的进行文件的上传预览等。

这里将通过docker容器的方式进行启动minio

直接使用官方镜像，并配置默认的用户名与密码

使用以下yml文件进行启动

因为需要使用nginx进行minio的https访问，因此nginx的容器编排也在这里

#### Nginx与Minio的编排文件

```yml
version: '3.7'

# Settings and configurations that are common for all containers
x-minio-common: &minio-common
 # image: minio:1.0
  image: quay.io/minio/minio
  command: server --console-address ":9001" http://minio/data{1...2}
  environment:
    MINIO_ROOT_USER: wwlhuayu
    MINIO_ROOT_PASSWORD: wwlhuayu
  healthcheck:
    test: ["CMD", "curl", "-f", "http://localhost:9000/minio/health/live"]
    interval: 30s
    timeout: 20s
    retries: 3

# starts 4 docker containers running minio server instances.
# using nginx reverse proxy, load balancing, you can access
# it through port 9000.
services:
  minio:
    <<: *minio-common
    hostname: minio
    volumes:
      - minio-data1-1:/data1
      - minio-data1-2:/data2
    # ports:
    #   - "9000:9000"
    #   - "9001:9001"
    labels:
      "type": "1"
    networks:
      - ali
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure      
  nginx:
    image: nginx:latest
    hostname: nginx
    ports:
    # 开启host模式，使用宿主机的网络，用于记录真实ip
      - target: 80
        published: 80
        mode: host
      - target: 443
        published: 443
        mode: host
      - target: 9000
        published: 9000
        mode: host
      - target: 9001
        published: 9001
        mode: host
    labels:
      "type": "1"
    networks:
      - ali
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
    volumes:
      - /opt/nginx/conf:/etc/nginx/
      - /opt/nginx/html:/usr/share/nginx/html
volumes:
  minio-data1-1:
  minio-data1-2:
networks:
  ali:
    external: true
    name: huayu
```

#### minio的https访问需要在nginx中的配置

如下：

```shell
server {
    listen       9000;
    listen  [::]:9000;
    server_name  localhost;
    charset utf-8;
    add_header X-Xss-Protection 1;
    ignore_invalid_headers off;
    client_max_body_size 0;
    proxy_buffering off;
    proxy_request_buffering off;
    ssl on;
    ssl_certificate /etc/nginx/cert/prod.huayu.asia_bundle.pem;
    ssl_certificate_key /etc/nginx/cert/prod.huayu.asia.key;
    ssl_session_timeout 5m;
    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    ssl_prefer_server_ciphers on;
    #access_log  /var/log/nginx/host.access.log  main;

    location / {
        proxy_set_header Host $http_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        proxy_connect_timeout 300;
        proxy_http_version 1.1;
        proxy_set_header Connection "";
        chunked_transfer_encoding off;
        proxy_pass http://minio:9000;
    }
    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }

}
server {
    listen       9001;
    listen  [::]:9001;
    server_name  localhost;
    ignore_invalid_headers off;
    client_max_body_size 0;
    proxy_buffering off;
    proxy_request_buffering off;
    charset utf-8;
    add_header X-Xss-Protection 1;
    ssl on;
    ssl_certificate /etc/nginx/cert/prod.huayu.asia_bundle.pem;
    ssl_certificate_key /etc/nginx/cert/prod.huayu.asia.key;
    ssl_session_timeout 5m;
    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    ssl_prefer_server_ciphers on;
    #access_log  /var/log/nginx/host.access.log  main;

    location / {
        proxy_set_header Host $http_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header X-NginX-Proxy true;

        real_ip_header X-Real-IP;

        proxy_connect_timeout 300;
        
        proxy_http_version 1.1;
        # 开启升级请求
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        
        chunked_transfer_encoding off;
        proxy_pass http://minio:9001;
    }
    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }

}
```

## 部署消息中间件进行mysql数据同步至es

为了实现使用es进行数据检索，因此当数据库产生新的数据时，需要利用mysql的binlog进行数据同步至es

这里选用rabbitmq作为消息中间件

maxwell作为mysql数据检测，并推送至mq

### 部署Maxwell

直接使用官方镜像，并修改容器启动命令

MaxWell的启动命令分析

```sh
 bin/maxwell  --user='数据库用户名' --password='数据库密码'  --host='数据库IP地址'  --producer=rabbitmq --rabbitmq_user='MQ用户名' --rabbitmq_pass='MQ密码' --rabbitmq_host='IP地址' --rabbitmq_port='5672' --rabbitmq_exchange='maxwell_exchange'  --rabbitmq_exchange_type='fanout' --rabbitmq_exchange_durable='true' --filter='exclude: *.*, include: aurora.t_article.article_title = *, include: aurora.t_article.article_content = *, include: aurora.t_article.is_delete = *, include: aurora.t_article.status = *' //运行MaxWell
```

修改command中的主要参数为你自己的

```yml
version: '3.7'

services:
  maxwell:
    hostname: maxwell
    image: zendesk/maxwell
    command: "bin/maxwell  --user='root' --password=''  --host='huayu.asia'  --producer=rabbitmq --rabbitmq_user='guest' --rabbitmq_pass='guest' --rabbitmq_host='prod.huayu.asia' --rabbitmq_port='5672' --rabbitmq_exchange='maxwell_exchange'  --rabbitmq_exchange_type='fanout' --rabbitmq_exchange_durable='true' --filter='exclude: *.*, include: cloud_sofa_server.article.article_title = *, include: cloud_sofa_server.article.article_content = *, include: cloud_sofa_server.article.is_delete = *, include: cloud_sofa_server.article.status = *'"
    labels:
      "type": "1"
    networks:
      - ali
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
networks:
  ali:
    # external: true
    name: huayu
```

如果mysql已有数据需要进行初始化，可以参考该文章https://prod.huayu.asia/articles/186进行数据初始化

### 部署rabbitMQ

直接使用官方镜像 默认账户密码均为guest

```yml
version: '3.7'

services:
  rabbitmq:
    hostname: rabbitmq
    image: rabbitmq:management
    ports:
      - 15672:15672
      - 5672:5672
    labels:
      "type": "1"
    networks:
      - ali
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
networks:
  ali:
    external: true
    name: huayu
```

到此后端服务已基本部署完毕

# 前端页面部署

前端页面的部署直接将前端的两个项目都进行打包即可

前端仓库地址：https://github.com/Rain-ziyu/aurora-vue.git

以下命令将项目进行build并压缩为tar.gz形式

```shell
cd aurora-admin
npm install --verbose
#默认构建
npm run build
# 压缩生成的dist
tar -zcvf admin.tar.gz ./dist

cd ..
cd aurora-blog
npm install --verbose
#默认构建
npm run build
tar -zcvf blog.tar.gz ./dist
```

将两个压缩包放置nginx镜像映射出来的目录中，上方后端部署nginx时已经映射了nginx中的html文件夹，也在nginx.conf中指定了静态资源的位置

将两个压缩包解压放到指定位置即可

```shell
cd /opt/nginx/html
rm -rf admin blog dist 
tar -zxvf admin.tar.gz 
mv dist/ admin/
tar -zxvf blog.tar.gz 
mv dist/ blog/
```

此时整个项目的前端与后端均部署完毕

项目所需sql等文件请看仓库根目录

DDL仅有数据库表的创建语句

cloud_sofa_server.sql中则有完整的初始数据

nacos.sql中则存储的是我的nacos注册中心的数据库备份，版本为（2.2.0）

