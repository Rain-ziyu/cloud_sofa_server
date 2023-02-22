FROM swr.cn-east-3.myhuaweicloud.com/huayu/openjdk:17-jdk
MAINTAINER ZiYu
WORKDIR /
#时区
ENV TZ Asia/Shanghai
#端口号
ADD  ./server_blog/target/server_blog-0.0.1-SNAPSHOT.jar  blog.jar
ENTRYPOINT ["java","-jar","/blog.jar"]