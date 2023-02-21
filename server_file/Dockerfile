FROM openjdk:17-jdk
MAINTAINER ZiYu
WORKDIR /
#时区
ENV TZ Asia/Shanghai
#端口号
ADD  ./server_file/target/server_file-0.0.1-SNAPSHOT.jar  file.jar
ENTRYPOINT ["java","-jar","/file.jar"]