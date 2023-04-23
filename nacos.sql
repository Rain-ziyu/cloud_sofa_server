-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: huayu.asia    Database: nacos
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `config_info`
--

DROP TABLE IF EXISTS `config_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info`
(
  `id`                 bigint                        NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id`            varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id`           varchar(128) COLLATE utf8_bin          DEFAULT NULL,
  `content`            longtext COLLATE utf8_bin     NOT NULL COMMENT 'content',
  `md5`                varchar(32) COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
  `gmt_create`         datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified`       datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user`           text COLLATE utf8_bin COMMENT 'source user',
  `src_ip`             varchar(50) COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
  `app_name`           varchar(128) COLLATE utf8_bin          DEFAULT NULL,
  `tenant_id`          varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
  `c_desc`             varchar(256) COLLATE utf8_bin          DEFAULT NULL,
  `c_use`              varchar(64) COLLATE utf8_bin           DEFAULT NULL,
  `effect`             varchar(64) COLLATE utf8_bin           DEFAULT NULL,
  `type`               varchar(64) COLLATE utf8_bin           DEFAULT NULL,
  `c_schema`           text COLLATE utf8_bin,
  `encrypted_data_key` text COLLATE utf8_bin         NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 57
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin COMMENT ='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info`
  DISABLE KEYS */;
INSERT INTO `config_info`
VALUES (6, 'cloud-sofa-server-security.properties', 'DEV_GROUP',
        'mybatis.mapper-locations=classpath*:mapper/*.xml\nmybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\n# ========================redis相关配置=====================\nspring.redis.cluster.max-redirects=1000\nspring.redis.cluster.nodes=huayu.asia:6379,huayu.asia:6380,huayu.asia:6381,huayu.asia:6382,huayu.asia:6383,huayu.asia:6384\nspring.redis.database=0\nspring.redis.port=6379\nspring.redis.password=redis\nspring.redis.lettuce.pool.max-active=8\nspring.redis.lettuce.pool.max-wait=-1ms\nspring.redis.lettuce.pool.max-idle=8\nspring.redis.lettuce.pool.min-idle=0\n#返回json的全局时间格式\nspring.jackson.date-format=yyyy-MM-dd HH:mm:ss\nspring.jackson.time-zone=GMT+8\n#=======================swagger配置=========================\nspring.mvc.pathmatch.matching-strategy=ant_path_matcher',
        'cdc51e19090eaa86b2049dc354c2f7ab', '2023-01-12 09:06:49', '2023-03-08 11:23:28', 'nacos', '10.0.0.2', '',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', 'security配置', '', '', 'properties', '', ''),
       (9, 'cloud-sofa-server-system-dev.properties', 'DEV_GROUP',
        'server.port=3377\r\nmybatis.mapper-locations=classpath*:mapper/*.xml', '615f62fe913b8c54313cdb1f64586bd7',
        '2023-01-30 10:26:23', '2023-01-30 10:26:23', NULL, '10.0.0.2', '', '7a31e530-8f89-4d83-acd5-f4a8d37f088e',
        '开发环境自测', NULL, NULL, 'properties', NULL, ''),
       (10, 'cloud-sofa-server-file-dev.properties', 'DEV_GROUP',
        'server.port=3378\nmybatis.mapper-locations=classpath*:mapper/*.xml\nminio.server-url=https://prod.huayu.asia\n# 只能是api端口号不能是web页面地址\nminio.port=9000 \nminio.access-key=wwlhuayu\nminio.secret-key=wwlhuayu\nminio.secure=true\nminio.bucket-name=sofa-server\nminio.config-dir=\nminio.domain-name=\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\n\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        '991d05d23b03a697d9e4f108d881723d', '2023-01-30 10:27:17', '2023-03-08 11:40:01', 'nacos', '10.0.0.2', '',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', '开发环境自测', '', '', 'properties', '', ''),
       (14, 'cloud-sofa-server-admin-dev.properties', 'DEV_GROUP',
        'server.servlet.context-path=/admin\nserver.port=3399\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\n#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\n\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN',
        'ec743246ad20ca58fb8bb80e8f2eb24b', '2023-02-08 10:18:04', '2023-03-22 14:13:53', 'nacos', '10.0.0.2', '',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', 'admin', '', '', 'properties', '', ''),
       (44, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP',
        'server.port=8080\nserver.servlet.context-path=/user\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\n#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nsecurity.permitAllUri=\"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.mode.type=Standalone\nspring.shardingsphere.datasource.names=master,slave\nspring.shardingsphere.props.sql-show=true\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        '40a2fe8d180723bf41bb15b2e465a085', '2023-03-08 11:35:21', '2023-03-27 18:02:42', 'nacos', '10.0.0.2', '',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', '', '', '', 'properties', '', '');
/*!40000 ALTER TABLE `config_info`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_aggr`
--

DROP TABLE IF EXISTS `config_info_aggr`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_aggr`
(
  `id`           bigint                        NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id`      varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id`     varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id`     varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content`      longtext COLLATE utf8_bin     NOT NULL COMMENT '内容',
  `gmt_modified` datetime                      NOT NULL COMMENT '修改时间',
  `app_name`     varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id`    varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`, `group_id`, `tenant_id`, `datum_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin COMMENT ='增加租户字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_aggr`
--

LOCK TABLES `config_info_aggr` WRITE;
/*!40000 ALTER TABLE `config_info_aggr`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_aggr`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_beta`
--

DROP TABLE IF EXISTS `config_info_beta`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_beta`
(
  `id`                 bigint                        NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id`            varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id`           varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name`           varchar(128) COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
  `content`            longtext COLLATE utf8_bin     NOT NULL COMMENT 'content',
  `beta_ips`           varchar(1024) COLLATE utf8_bin         DEFAULT NULL COMMENT 'betaIps',
  `md5`                varchar(32) COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
  `gmt_create`         datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified`       datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user`           text COLLATE utf8_bin COMMENT 'source user',
  `src_ip`             varchar(50) COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
  `tenant_id`          varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8_bin         NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin COMMENT ='config_info_beta';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_beta`
--

LOCK TABLES `config_info_beta` WRITE;
/*!40000 ALTER TABLE `config_info_beta`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_beta`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_tag`
--

DROP TABLE IF EXISTS `config_info_tag`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_tag`
(
  `id`           bigint                        NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id`      varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id`     varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id`    varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT 'tenant_id',
  `tag_id`       varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name`     varchar(128) COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
  `content`      longtext COLLATE utf8_bin     NOT NULL COMMENT 'content',
  `md5`          varchar(32) COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
  `gmt_create`   datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user`     text COLLATE utf8_bin COMMENT 'source user',
  `src_ip`       varchar(50) COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin COMMENT ='config_info_tag';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_tag`
--

LOCK TABLES `config_info_tag` WRITE;
/*!40000 ALTER TABLE `config_info_tag`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_tag`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_tags_relation`
--

DROP TABLE IF EXISTS `config_tags_relation`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_tags_relation`
(
  `id`        bigint                        NOT NULL COMMENT 'id',
  `tag_name`  varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type`  varchar(64) COLLATE utf8_bin  DEFAULT NULL COMMENT 'tag_type',
  `data_id`   varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id`  varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid`       bigint                        NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin COMMENT ='config_tag_relation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_tags_relation`
--

LOCK TABLES `config_tags_relation` WRITE;
/*!40000 ALTER TABLE `config_tags_relation`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `config_tags_relation`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_capacity`
--

DROP TABLE IF EXISTS `group_capacity`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_capacity`
(
  `id`                bigint unsigned               NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id`          varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota`             int unsigned                  NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage`             int unsigned                  NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size`          int unsigned                  NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count`    int unsigned                  NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size`     int unsigned                  NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned                  NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create`        datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified`      datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin COMMENT ='集群、各Group容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_capacity`
--

LOCK TABLES `group_capacity` WRITE;
/*!40000 ALTER TABLE `group_capacity`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `group_capacity`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `his_config_info`
--

DROP TABLE IF EXISTS `his_config_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `his_config_info`
(
  `id`                 bigint unsigned               NOT NULL,
  `nid`                bigint unsigned               NOT NULL AUTO_INCREMENT,
  `data_id`            varchar(255) COLLATE utf8_bin NOT NULL,
  `group_id`           varchar(128) COLLATE utf8_bin NOT NULL,
  `app_name`           varchar(128) COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
  `content`            longtext COLLATE utf8_bin     NOT NULL,
  `md5`                varchar(32) COLLATE utf8_bin           DEFAULT NULL,
  `gmt_create`         datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified`       datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user`           text COLLATE utf8_bin,
  `src_ip`             varchar(50) COLLATE utf8_bin           DEFAULT NULL,
  `op_type`            char(10) COLLATE utf8_bin              DEFAULT NULL,
  `tenant_id`          varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8_bin         NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 64
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin COMMENT ='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info`
  DISABLE KEYS */;
INSERT INTO `his_config_info`
VALUES (26, 40, 'cloud-sofa-server-blog-dev.yaml', 'DEV_GROUP', '',
        'server:\n  port: 8080\n\nspring:\n  rabbitmq:\n    host: prod.huayu.asia\n    port: 5672\n    username: guest\n    password: guest\n    listener:\n      simple:\n        retry:\n          enabled: true\n          max-attempts: 3\n          initial-interval: 3000\n  elasticsearch:\n    uris: xqyprod.huayu.asia:9200\n    password: c=cqm5pq-Fhq_mNVCexY\n    username: elastic\n  mail:\n    host: smtp.163.com\n    username: rainziyu@163.com\n    password: JDOUAHVZTRTICGUP\n    default-encoding: UTF-8\n    protocol: smtp\n    port: 465\n    properties:\n      mail:\n        smtp:\n          auth: true\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n            port: 465\n          ssl:\n            enable: true\n          starttls:\n            enable: true\n            required: true\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n# springdoc-openapi项目配置\nspringdoc:\n  swagger-ui:\n    path: /swagger-ui.html\n    tags-sorter: alpha\n    operations-sorter: alpha\n  api-docs:\n    path: /v3/api-docs\n  group-configs:\n    - group: \'default\'\n      paths-to-match: \'/**\'\n      packages-to-scan: asia.huayu.controller\n# knife4j的增强配置，不需要增强可以不配\nknife4j:\n  enable: true\n  setting:\n    language: zh_cn\nmybatis-plus:\n  mapper-locations: classpath*:mapper/*.xml\n  configuration:\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n    map-underscore-to-camel-case: true\n\nsearch:\n  mode: elasticsearch\n# 微服务形式  Feign调用file模块\nupload:\n  mode: minio\n  oss:\n    url: http://Bucket域名/\n    endpoint: OSS配置endpoint\n    accessKeyId: OSS配置accessKeyId\n    accessKeySecret: OSS配置accessKeySecret\n    bucketName: OSS配置bucketName\n  minio:\n    url: http://huayu.asia:9000\n    endpoint: http://huayu.asia:9000\n    accesskey: 用户名\n    secretKey: 密码\n    bucketName: 桶的名称\n\nwebsite:\n  url: https://prod.huayu.asia\n\nqq:\n  app-id: 1112200977\n  check-token-url: https://graph.qq.com/oauth2.0/me?access_token={access_token}\n  user-info-url: https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}',
        '176e38c0db41db5b0bff67673068f3dd', '2023-02-27 05:05:25', '2023-02-27 13:05:26', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (26, 41, 'cloud-sofa-server-blog-dev.yaml', 'DEV_GROUP', '',
        'server:\n  port: 8080\n  servlet:\n    context-path: /user\nspring:\n  rabbitmq:\n    host: prod.huayu.asia\n    port: 5672\n    username: guest\n    password: guest\n    listener:\n      simple:\n        retry:\n          enabled: true\n          max-attempts: 3\n          initial-interval: 3000\n  elasticsearch:\n    uris: xqyprod.huayu.asia:9200\n    password: c=cqm5pq-Fhq_mNVCexY\n    username: elastic\n  mail:\n    host: smtp.163.com\n    username: rainziyu@163.com\n    password: JDOUAHVZTRTICGUP\n    default-encoding: UTF-8\n    protocol: smtp\n    port: 465\n    properties:\n      mail:\n        smtp:\n          auth: true\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n            port: 465\n          ssl:\n            enable: true\n          starttls:\n            enable: true\n            required: true\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n# springdoc-openapi项目配置\nspringdoc:\n  swagger-ui:\n    path: /swagger-ui.html\n    tags-sorter: alpha\n    operations-sorter: alpha\n  api-docs:\n    path: /v3/api-docs\n  group-configs:\n    - group: \'default\'\n      paths-to-match: \'/**\'\n      packages-to-scan: asia.huayu.controller\n# knife4j的增强配置，不需要增强可以不配\nknife4j:\n  enable: true\n  setting:\n    language: zh_cn\nmybatis-plus:\n  mapper-locations: classpath*:mapper/*.xml\n  configuration:\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n    map-underscore-to-camel-case: true\n\nsearch:\n  mode: elasticsearch\n# 微服务形式  Feign调用file模块\nupload:\n  mode: minio\n  oss:\n    url: http://Bucket域名/\n    endpoint: OSS配置endpoint\n    accessKeyId: OSS配置accessKeyId\n    accessKeySecret: OSS配置accessKeySecret\n    bucketName: OSS配置bucketName\n  minio:\n    url: http://huayu.asia:9000\n    endpoint: http://huayu.asia:9000\n    accesskey: 用户名\n    secretKey: 密码\n    bucketName: 桶的名称\n\nwebsite:\n  url: https://prod.huayu.asia\n\nqq:\n  app-id: 1112200977\n  check-token-url: https://graph.qq.com/oauth2.0/me?access_token={access_token}\n  user-info-url: https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}',
        'fd00b16810d63aefd959588ae7e34467', '2023-02-27 06:07:28', '2023-02-27 14:07:28', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (26, 42, 'cloud-sofa-server-blog-dev.yaml', 'DEV_GROUP', '',
        'server:\n  port: 8080\n  servlet:\n    context-path: /user\nspring:\n  rabbitmq:\n    host: prod.huayu.asia\n    port: 5672\n    username: guest\n    password: guest\n    listener:\n      simple:\n        retry:\n          enabled: true\n          max-attempts: 3\n          initial-interval: 3000\n  elasticsearch:\n    uris: xqyprod.huayu.asia:9200\n    password: c=cqm5pq-Fhq_mNVCexY\n    username: elastic\n  mail:\n    host: smtp.163.com\n    username: rainziyu@163.com\n    password: JDOUAHVZTRTICGUP\n    default-encoding: UTF-8\n    protocol: smtp\n    port: 465\n    properties:\n      mail:\n        smtp:\n          auth: true\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n            port: 465\n          ssl:\n            enable: true\n          starttls:\n            enable: true\n            required: true\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n# springdoc-openapi项目配置\nspringdoc:\n  swagger-ui:\n    path: /swagger-ui.html\n    tags-sorter: alpha\n    operations-sorter: alpha\n  api-docs:\n    path: /v3/api-docs\n  group-configs:\n    - group: \'default\'\n      paths-to-match: \'/**\'\n      packages-to-scan: asia.huayu.controller\n# knife4j的增强配置，不需要增强可以不配\nknife4j:\n  enable: true\n  setting:\n    language: zh_cn\nmybatis-plus:\n  mapper-locations: classpath*:mapper/*.xml\n  configuration:\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n    map-underscore-to-camel-case: true\n\nsearch:\n  mode: elasticsearch\n# 微服务形式  Feign调用file模块\nupload:\n  mode: minio\n  oss:\n    url: http://Bucket域名/\n    endpoint: OSS配置endpoint\n    accessKeyId: OSS配置accessKeyId\n    accessKeySecret: OSS配置accessKeySecret\n    bucketName: OSS配置bucketName\n  minio:\n    url: http://huayu.asia:9000\n    endpoint: http://huayu.asia:9000\n    accesskey: 用户名\n    secretKey: 密码\n    bucketName: 桶的名称\n\nwebsite:\n  url: https://prod.huayu.asia\nsecurity:\n  permitAllUri: \"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq:\n  app-id: 1112200977\n  check-token-url: https://graph.qq.com/oauth2.0/me?access_token={access_token}\n  user-info-url: https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}',
        '5b80a5b38798e10699cb042f0f2bc1b9', '2023-02-27 09:38:30', '2023-02-27 17:38:30', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (26, 43, 'cloud-sofa-server-blog-dev.yaml', 'DEV_GROUP', '',
        'server:\n  port: 8080\n  servlet:\n    context-path: /user\nspring:\n  rabbitmq:\n    host: prod.huayu.asia\n    port: 5672\n    username: guest\n    password: guest\n    listener:\n      simple:\n        retry:\n          enabled: true\n          max-attempts: 3\n          initial-interval: 3000\n  elasticsearch:\n    uris: xqyprod.huayu.asia:9200\n    password: c=cqm5pq-Fhq_mNVCexY\n    username: elastic\n  mail:\n    host: smtp.163.com\n    username: rainziyu@163.com\n    password: JDOUAHVZTRTICGUP\n    default-encoding: UTF-8\n    protocol: smtp\n    port: 465\n    properties:\n      mail:\n        smtp:\n          auth: true\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n            port: 465\n          ssl:\n            enable: true\n          starttls:\n            enable: true\n            required: true\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n# springdoc-openapi项目配置\nspringdoc:\n  swagger-ui:\n    path: /swagger-ui.html\n    tags-sorter: alpha\n    operations-sorter: alpha\n  api-docs:\n    path: /v3/api-docs\n  group-configs:\n    - group: \'default\'\n      paths-to-match: \'/**\'\n      packages-to-scan: asia.huayu.controller\n# knife4j的增强配置，不需要增强可以不配\nknife4j:\n  enable: true\n  setting:\n    language: zh_cn\nmybatis-plus:\n  mapper-locations: classpath*:mapper/*.xml\n  configuration:\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n    map-underscore-to-camel-case: true\n\nsearch:\n  mode: elasticsearch\n# 微服务形式  Feign调用file模块\nupload:\n  mode: minio\n  oss:\n    url: http://Bucket域名/\n    endpoint: OSS配置endpoint\n    accessKeyId: OSS配置accessKeyId\n    accessKeySecret: OSS配置accessKeySecret\n    bucketName: OSS配置bucketName\n  minio:\n    url: http://huayu.asia:9000\n    endpoint: http://huayu.asia:9000\n    accesskey: 用户名\n    secretKey: 密码\n    bucketName: 桶的名称\n\nwebsite:\n  url: https://prod.huayu.asia\nsecurity:\n  permitAllUri: \"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq:\n  app-id: 1112200977\n  check-token-url: https://graph.qq.com/oauth2.0/me?access_token={access_token}\n  user-info-url: https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}',
        'ba03360a8f3ef97913f7f155a7c7b060', '2023-03-01 05:00:22', '2023-03-01 13:00:22', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (14, 44, 'cloud-sofa-server-admin-dev.properties', 'DEV_GROUP', '',
        'server.servlet.context-path=/admin\nserver.port=3399\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=xqyprod.huayu.asia:9200\nspring.elasticsearch.password=c=cqm5pq-Fhq_mNVCexY\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\nmybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n\n',
        '98cb835f1c14969f0f2b3f48b7437c93', '2023-03-01 05:01:07', '2023-03-01 13:01:07', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (6, 45, 'cloud-sofa-server-security.properties', 'DEV_GROUP', '',
        'mybatis.mapper-locations=classpath*:mapper/*.xml\r\nmybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\r\n\r\n# ========================alibaba.druid相关配置=====================\r\nspring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver\r\nspring.datasource.username=root\r\nspring.datasource.password=24681379\r\nspring.datasource.url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\r\n# ========================redis相关配置=====================\r\nspring.redis.cluster.max-redirects=1000\r\nspring.redis.cluster.nodes=huayu.asia:6379,huayu.asia:6380,huayu.asia:6381,huayu.asia:6382,huayu.asia:6383,huayu.asia:6384\r\nspring.redis.database=0\r\nspring.redis.port=6379\r\nspring.redis.password=redis\r\nspring.redis.lettuce.pool.max-active=8\r\nspring.redis.lettuce.pool.max-wait=-1ms\r\nspring.redis.lettuce.pool.max-idle=8\r\nspring.redis.lettuce.pool.min-idle=0\r\n#返回json的全局时间格式\r\nspring.jackson.date-format=yyyy-MM-dd HH:mm:ss\r\nspring.jackson.time-zone=GMT+8\r\n#=======================swagger配置=========================\r\nspring.mvc.pathmatch.matching-strategy=ant_path_matcher',
        '0a56c647624d44d8713aac74390af55c', '2023-03-07 08:28:12', '2023-03-07 16:28:13', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (14, 46, 'cloud-sofa-server-admin-dev.properties', 'DEV_GROUP', '',
        'server.servlet.context-path=/admin\nserver.port=3399\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\nmybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n\n',
        '320015c63145b5fabaf9f027a417f928', '2023-03-08 02:43:17', '2023-03-08 10:43:17', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (14, 47, 'cloud-sofa-server-admin-dev.properties', 'DEV_GROUP', '',
        'server.servlet.context-path=/admin\nserver.port=3399\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\nmybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\nspring.datasource.driver-class-name=org.apache.shardingsphere.driver.ShardingSpher\n',
        '62d3b268e9788634150b307b2489cb96', '2023-03-08 03:20:00', '2023-03-08 11:20:01', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (6, 48, 'cloud-sofa-server-security.properties', 'DEV_GROUP', '',
        'mybatis.mapper-locations=classpath*:mapper/*.xml\nmybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\n\n# ========================alibaba.druid相关配置=====================\nspring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.datasource.username=root\nspring.datasource.password=24681379\nspring.datasource.url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\n# ========================redis相关配置=====================\nspring.redis.cluster.max-redirects=1000\nspring.redis.cluster.nodes=huayu.asia:6379,huayu.asia:6380,huayu.asia:6381,huayu.asia:6382,huayu.asia:6383,huayu.asia:6384\nspring.redis.database=0\nspring.redis.port=6379\nspring.redis.password=redis\nspring.redis.lettuce.pool.max-active=8\nspring.redis.lettuce.pool.max-wait=-1ms\nspring.redis.lettuce.pool.max-idle=8\nspring.redis.lettuce.pool.min-idle=0\n#返回json的全局时间格式\nspring.jackson.date-format=yyyy-MM-dd HH:mm:ss\nspring.jackson.time-zone=GMT+8\n#=======================swagger配置=========================\nspring.mvc.pathmatch.matching-strategy=ant_path_matcher',
        '94cab6db6a47ed20058368ab707e29a1', '2023-03-08 03:23:28', '2023-03-08 11:23:28', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (14, 49, 'cloud-sofa-server-admin-dev.properties', 'DEV_GROUP', '',
        'server.servlet.context-path=/admin\nserver.port=3399\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\nmybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}',
        'abf1d60e0ea5ef7e2778ef82543a7c83', '2023-03-08 03:30:36', '2023-03-08 11:30:36', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (0, 50, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\r\nserver.servlet.context-path=/user\r\nspring.rabbitmq.host=prod.huayu.asia\r\nspring.rabbitmq.port=5672\r\nspring.rabbitmq.username=guest\r\nspring.rabbitmq.password=guest\r\nspring.rabbitmq.listener.simple.retry.enabled=true\r\nspring.rabbitmq.listener.simple.retry.max-attempts=3\r\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\r\nspring.elasticsearch.uris=prod.huayu.asia:9200\r\nspring.elasticsearch.password=wanwenlong521\r\nspring.elasticsearch.username=elastic\r\nspring.mail.host=smtp.163.com\r\nspring.mail.username=rainziyu@163.com\r\nspring.mail.password=JDOUAHVZTRTICGUP\r\nspring.mail.default-encoding=UTF-8\r\nspring.mail.protocol=smtp\r\nspring.mail.port=465\r\nspring.mail.properties.mail.smtp.auth=true\r\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\r\nspring.mail.properties.mail.smtp.socketFactory.port=465\r\nspring.mail.properties.mail.smtp.ssl.enable=true\r\nspring.mail.properties.mail.smtp.starttls.enable=true\r\nspring.mail.properties.mail.smtp.starttls.required=true\r\nspring.servlet.multipart.max-file-size=100MB\r\nspring.servlet.multipart.max-request-size=100MB\r\nspringdoc.swagger-ui.path=/swagger-ui.html\r\nspringdoc.swagger-ui.tags-sorter=alpha\r\nspringdoc.swagger-ui.operations-sorter=alpha\r\nspringdoc.api-docs.path=/v3/api-docs\r\nspringdoc.group-configs[0].group=default\r\nspringdoc.group-configs[0].paths-to-match=/**\r\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\r\nknife4j.enable=true\r\nknife4j.setting.language=zh_cn\r\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\r\nmybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\r\nmybatis-plus.configuration.map-underscore-to-camel-case=true\r\nsearch.mode=elasticsearch\r\nupload.mode=minio\r\nupload.oss.url=http://Bucket域名/\r\nupload.oss.endpoint=OSS配置endpoint\r\nupload.oss.accessKeyId=OSS配置accessKeyId\r\nupload.oss.accessKeySecret=OSS配置accessKeySecret\r\nupload.oss.bucketName=OSS配置bucketName\r\nupload.minio.url=http://huayu.asia:9000\r\nupload.minio.endpoint=http://huayu.asia:9000\r\nupload.minio.accesskey=用户名\r\nupload.minio.secretKey=密码\r\nupload.minio.bucketName=桶的名称\r\nwebsite.url=https://prod.huayu.asia\r\nsecurity.permitAllUri=/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\r\nqq.app-id=1112200977\r\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\r\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\r\n# Configure actual data sources\r\nspring.shardingsphere.datasource.names=master,slave\r\n\r\n# Configure the first data source\r\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\r\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\r\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\r\nspring.shardingsphere.datasource.master.username=root\r\nspring.shardingsphere.datasource.master.password=24681379\r\n\r\n# Configure the second data source\r\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\r\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\r\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\r\nspring.shardingsphere.datasource.slave.username=root\r\nspring.shardingsphere.datasource.slave.password=24681379\r\n\r\n# Configure sharding algorithm\r\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\r\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \r\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\r\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\r\n',
        '533c1c2a565cba561583f4143dc760d1', '2023-03-08 03:35:21', '2023-03-08 11:35:21', NULL, '10.0.0.2', 'I',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (26, 51, 'cloud-sofa-server-blog-dev.yaml', 'DEV_GROUP', '',
        'server:\n  port: 8080\n  servlet:\n    context-path: /user\nspring:\n  rabbitmq:\n    host: prod.huayu.asia\n    port: 5672\n    username: guest\n    password: guest\n    listener:\n      simple:\n        retry:\n          enabled: true\n          max-attempts: 3\n          initial-interval: 3000\n  elasticsearch:\n    uris: prod.huayu.asia:9200\n    password: wanwenlong521\n    username: elastic\n  mail:\n    host: smtp.163.com\n    username: rainziyu@163.com\n    password: JDOUAHVZTRTICGUP\n    default-encoding: UTF-8\n    protocol: smtp\n    port: 465\n    properties:\n      mail:\n        smtp:\n          auth: true\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n            port: 465\n          ssl:\n            enable: true\n          starttls:\n            enable: true\n            required: true\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n# springdoc-openapi项目配置\nspringdoc:\n  swagger-ui:\n    path: /swagger-ui.html\n    tags-sorter: alpha\n    operations-sorter: alpha\n  api-docs:\n    path: /v3/api-docs\n  group-configs:\n    - group: \'default\'\n      paths-to-match: \'/**\'\n      packages-to-scan: asia.huayu.controller\n# knife4j的增强配置，不需要增强可以不配\nknife4j:\n  enable: true\n  setting:\n    language: zh_cn\nmybatis-plus:\n  mapper-locations: classpath*:mapper/*.xml\n  configuration:\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n    map-underscore-to-camel-case: true\n\nsearch:\n  mode: elasticsearch\n# 微服务形式  Feign调用file模块\nupload:\n  mode: minio\n  oss:\n    url: http://Bucket域名/\n    endpoint: OSS配置endpoint\n    accessKeyId: OSS配置accessKeyId\n    accessKeySecret: OSS配置accessKeySecret\n    bucketName: OSS配置bucketName\n  minio:\n    url: http://huayu.asia:9000\n    endpoint: http://huayu.asia:9000\n    accesskey: 用户名\n    secretKey: 密码\n    bucketName: 桶的名称\n\nwebsite:\n  url: https://prod.huayu.asia\nsecurity:\n  permitAllUri: \"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq:\n  app-id: 1112200977\n  check-token-url: https://graph.qq.com/oauth2.0/me?access_token={access_token}\n  user-info-url: https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}',
        '22f62f636c288a9e6ac690f59c1d814d', '2023-03-08 03:39:33', '2023-03-08 11:39:34', NULL, '10.0.0.2', 'D',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (10, 52, 'cloud-sofa-server-file-dev.properties', 'DEV_GROUP', '',
        'server.port=3378\nmybatis.mapper-locations=classpath*:mapper/*.xml\nminio.server-url=https://prod.huayu.asia\n# 只能是api端口号不能是web页面地址\nminio.port=9000 \nminio.access-key=wwlhuayu\nminio.secret-key=wwlhuayu\nminio.secure=true\nminio.bucket-name=sofa-server\nminio.config-dir=\nminio.domain-name=\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn',
        '56d68b13aad8ce0240a63fa5f83064ef', '2023-03-08 03:40:00', '2023-03-08 11:40:01', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (44, 53, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\r\nserver.servlet.context-path=/user\r\nspring.rabbitmq.host=prod.huayu.asia\r\nspring.rabbitmq.port=5672\r\nspring.rabbitmq.username=guest\r\nspring.rabbitmq.password=guest\r\nspring.rabbitmq.listener.simple.retry.enabled=true\r\nspring.rabbitmq.listener.simple.retry.max-attempts=3\r\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\r\nspring.elasticsearch.uris=prod.huayu.asia:9200\r\nspring.elasticsearch.password=wanwenlong521\r\nspring.elasticsearch.username=elastic\r\nspring.mail.host=smtp.163.com\r\nspring.mail.username=rainziyu@163.com\r\nspring.mail.password=JDOUAHVZTRTICGUP\r\nspring.mail.default-encoding=UTF-8\r\nspring.mail.protocol=smtp\r\nspring.mail.port=465\r\nspring.mail.properties.mail.smtp.auth=true\r\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\r\nspring.mail.properties.mail.smtp.socketFactory.port=465\r\nspring.mail.properties.mail.smtp.ssl.enable=true\r\nspring.mail.properties.mail.smtp.starttls.enable=true\r\nspring.mail.properties.mail.smtp.starttls.required=true\r\nspring.servlet.multipart.max-file-size=100MB\r\nspring.servlet.multipart.max-request-size=100MB\r\nspringdoc.swagger-ui.path=/swagger-ui.html\r\nspringdoc.swagger-ui.tags-sorter=alpha\r\nspringdoc.swagger-ui.operations-sorter=alpha\r\nspringdoc.api-docs.path=/v3/api-docs\r\nspringdoc.group-configs[0].group=default\r\nspringdoc.group-configs[0].paths-to-match=/**\r\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\r\nknife4j.enable=true\r\nknife4j.setting.language=zh_cn\r\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\r\nmybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\r\nmybatis-plus.configuration.map-underscore-to-camel-case=true\r\nsearch.mode=elasticsearch\r\nupload.mode=minio\r\nupload.oss.url=http://Bucket域名/\r\nupload.oss.endpoint=OSS配置endpoint\r\nupload.oss.accessKeyId=OSS配置accessKeyId\r\nupload.oss.accessKeySecret=OSS配置accessKeySecret\r\nupload.oss.bucketName=OSS配置bucketName\r\nupload.minio.url=http://huayu.asia:9000\r\nupload.minio.endpoint=http://huayu.asia:9000\r\nupload.minio.accesskey=用户名\r\nupload.minio.secretKey=密码\r\nupload.minio.bucketName=桶的名称\r\nwebsite.url=https://prod.huayu.asia\r\nsecurity.permitAllUri=/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\r\nqq.app-id=1112200977\r\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\r\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\r\n# Configure actual data sources\r\nspring.shardingsphere.datasource.names=master,slave\r\n\r\n# Configure the first data source\r\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\r\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\r\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\r\nspring.shardingsphere.datasource.master.username=root\r\nspring.shardingsphere.datasource.master.password=24681379\r\n\r\n# Configure the second data source\r\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\r\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\r\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\r\nspring.shardingsphere.datasource.slave.username=root\r\nspring.shardingsphere.datasource.slave.password=24681379\r\n\r\n# Configure sharding algorithm\r\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\r\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \r\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\r\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\r\n',
        '533c1c2a565cba561583f4143dc760d1', '2023-03-08 08:13:37', '2023-03-08 16:13:37', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (44, 54, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\nserver.servlet.context-path=/user\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\nmybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nsecurity.permitAllUri=\"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\n\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        'f58504358d142f8c0afafa3372f44f0e', '2023-03-22 06:13:35', '2023-03-22 14:13:35', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (14, 55, 'cloud-sofa-server-admin-dev.properties', 'DEV_GROUP', '',
        'server.servlet.context-path=/admin\nserver.port=3399\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\nmybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\n\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN',
        '2fade85a9ac7e1586cc0465098a2fb00', '2023-03-22 06:13:53', '2023-03-22 14:13:53', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (44, 56, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\nserver.servlet.context-path=/user\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\n#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nsecurity.permitAllUri=\"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\n\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        '48491df27037f8a7cd4127dfe5b0aca3', '2023-03-27 08:20:17', '2023-03-27 16:20:18', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (44, 57, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\nserver.servlet.context-path=/user\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\n#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nsecurity.permitAllUri=\"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\n\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=org.apache.shardingsphere.driver.ShardingSphereDriver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=org.apache.shardingsphere.driver.ShardingSphereDriver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        '5b95027bf8696c0a09e3cbdde911e56b', '2023-03-27 09:29:35', '2023-03-27 17:29:35', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (44, 58, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\nserver.servlet.context-path=/user\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\n#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nsecurity.permitAllUri=\"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\nspring.shardingsphere.props.sql-show=true\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=org.apache.shardingsphere.driver.ShardingSphereDriver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=org.apache.shardingsphere.driver.ShardingSphereDriver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        '0d5904995f2bc1505cbe5872f1051e60', '2023-03-27 09:49:06', '2023-03-27 17:49:07', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (44, 59, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\nserver.servlet.context-path=/user\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\n#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nsecurity.permitAllUri=\"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\nspring.shardingsphere.props.sql-show=true\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.driver-class-name=org.apache.shardingsphere.driver.ShardingSphereDriver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.driver-class-name=org.apache.shardingsphere.driver.ShardingSphereDriver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        'd36cfbe2ff185dde54b27d1115799eac', '2023-03-27 09:51:38', '2023-03-27 17:51:38', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (44, 60, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\nserver.servlet.context-path=/user\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\n#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nsecurity.permitAllUri=\"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\nspring.shardingsphere.props.sql-show=true\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        '964db2604d73fb533dcde9437a4da0cd', '2023-03-27 09:53:55', '2023-03-27 17:53:55', NULL, '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (44, 61, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\nserver.servlet.context-path=/user\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\n#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nsecurity.permitAllUri=\"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\n\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        '48491df27037f8a7cd4127dfe5b0aca3', '2023-03-27 09:54:08', '2023-03-27 17:54:09', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (44, 62, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\nserver.servlet.context-path=/user\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\n#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nsecurity.permitAllUri=\"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\nspring.shardingsphere.props.sql-show=true\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        '964db2604d73fb533dcde9437a4da0cd', '2023-03-27 09:56:14', '2023-03-27 17:56:15', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', ''),
       (44, 63, 'cloud-sofa-server-blog-dev.properties', 'DEV_GROUP', '',
        'server.port=8080\nserver.servlet.context-path=/user\nspring.rabbitmq.host=prod.huayu.asia\nspring.rabbitmq.port=5672\nspring.rabbitmq.username=guest\nspring.rabbitmq.password=guest\nspring.rabbitmq.listener.simple.retry.enabled=true\nspring.rabbitmq.listener.simple.retry.max-attempts=3\nspring.rabbitmq.listener.simple.retry.initial-interval=3000\nspring.elasticsearch.uris=prod.huayu.asia:9200\nspring.elasticsearch.password=wanwenlong521\nspring.elasticsearch.username=elastic\nspring.mail.host=smtp.163.com\nspring.mail.username=rainziyu@163.com\nspring.mail.password=JDOUAHVZTRTICGUP\nspring.mail.default-encoding=UTF-8\nspring.mail.protocol=smtp\nspring.mail.port=465\nspring.mail.properties.mail.smtp.auth=true\nspring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory\nspring.mail.properties.mail.smtp.socketFactory.port=465\nspring.mail.properties.mail.smtp.ssl.enable=true\nspring.mail.properties.mail.smtp.starttls.enable=true\nspring.mail.properties.mail.smtp.starttls.required=true\nspring.servlet.multipart.max-file-size=100MB\nspring.servlet.multipart.max-request-size=100MB\nspringdoc.swagger-ui.path=/swagger-ui.html\nspringdoc.swagger-ui.tags-sorter=alpha\nspringdoc.swagger-ui.operations-sorter=alpha\nspringdoc.api-docs.path=/v3/api-docs\nspringdoc.group-configs[0].group=default\nspringdoc.group-configs[0].paths-to-match=/**\nspringdoc.group-configs[0].packages-to-scan=asia.huayu.controller\nknife4j.enable=true\nknife4j.setting.language=zh_cn\nmybatis-plus.mapper-locations=classpath*:mapper/*.xml\n#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl\nmybatis-plus.configuration.map-underscore-to-camel-case=true\nsearch.mode=elasticsearch\nupload.mode=minio\nupload.oss.url=http://Bucket域名/\nupload.oss.endpoint=OSS配置endpoint\nupload.oss.accessKeyId=OSS配置accessKeyId\nupload.oss.accessKeySecret=OSS配置accessKeySecret\nupload.oss.bucketName=OSS配置bucketName\nupload.minio.url=http://huayu.asia:9000\nupload.minio.endpoint=http://huayu.asia:9000\nupload.minio.accesskey=用户名\nupload.minio.secretKey=密码\nupload.minio.bucketName=桶的名称\nwebsite.url=https://prod.huayu.asia\nsecurity.permitAllUri=\"/articles/*,/archives/all,/report,/,/about,/categories/all,/comments,/comments/topSix,/comments/*/replies,/links,/photos/albums,/albums/*/photos,/tags/*,/talks,/talks/*,/users/code,/users/register,/users/info/*,/users/oauth/qq\"\nqq.app-id=1112200977\nqq.check-token-url=https://graph.qq.com/oauth2.0/me?access_token={access_token}\nqq.user-info-url=https://graph.qq.com/user/get_user_info?openid={openid}&access_token={access_token}&oauth_consumer_key={oauth_consumer_key}\n# Configure actual data sources\nspring.shardingsphere.datasource.names=master,slave\nspring.shardingsphere.props.sql-show=true\n# Configure the first data source\nspring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://huayu.asia:3306/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.master.username=root\nspring.shardingsphere.datasource.master.password=24681379\n\n# Configure the second data source\nspring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource\nspring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://huayu.asia:3307/cloud_sofa_server?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true\nspring.shardingsphere.datasource.slave.username=root\nspring.shardingsphere.datasource.slave.password=24681379\n\n# Configure sharding algorithm\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.load-balancer-name=round_robin\nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.write-data-source-name=master   \nspring.shardingsphere.rules.readwrite-splitting.data-sources.random.static-strategy.read-data-source-names=slave\nspring.shardingsphere.rules.readwrite-splitting.load-balancers.round_robin.type= ROUND_ROBIN\n',
        '964db2604d73fb533dcde9437a4da0cd', '2023-03-27 10:02:42', '2023-03-27 18:02:42', 'nacos', '10.0.0.2', 'U',
        '7a31e530-8f89-4d83-acd5-f4a8d37f088e', '');
/*!40000 ALTER TABLE `his_config_info`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions`
(
  `role`     varchar(50)  NOT NULL,
  `resource` varchar(255) NOT NULL,
  `action`   varchar(8)   NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles`
(
  `username` varchar(50) NOT NULL,
  `role`     varchar(50) NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`, `role`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles`
  DISABLE KEYS */;
INSERT INTO `roles`
VALUES ('nacos', 'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_capacity`
--

DROP TABLE IF EXISTS `tenant_capacity`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_capacity`
(
  `id`                bigint unsigned               NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id`         varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota`             int unsigned                  NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage`             int unsigned                  NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size`          int unsigned                  NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count`    int unsigned                  NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size`     int unsigned                  NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned                  NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create`        datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified`      datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin COMMENT ='租户容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_capacity`
--

LOCK TABLES `tenant_capacity` WRITE;
/*!40000 ALTER TABLE `tenant_capacity`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `tenant_capacity`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_info`
--

DROP TABLE IF EXISTS `tenant_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_info`
(
  `id`            bigint                        NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp`            varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id`     varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name`   varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc`   varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8_bin  DEFAULT NULL COMMENT 'create_source',
  `gmt_create`    bigint                        NOT NULL COMMENT '创建时间',
  `gmt_modified`  bigint                        NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`, `tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin COMMENT ='tenant_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_info`
--

LOCK TABLES `tenant_info` WRITE;
/*!40000 ALTER TABLE `tenant_info`
  DISABLE KEYS */;
INSERT INTO `tenant_info`
VALUES (1, '1', '7a31e530-8f89-4d83-acd5-f4a8d37f088e', 'cloud_sofa_server', 'cloud_sofa_server项目使用', 'nacos',
        1673403104803, 1673403104803);
/*!40000 ALTER TABLE `tenant_info`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users`
(
  `username` varchar(50)  NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled`  tinyint(1)   NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
  DISABLE KEYS */;
INSERT INTO `users`
VALUES ('ico', '$2a$10$V3ZQ4e164ZsWSvJo.g0Bj.wXJ46d2fsPGA7yGTM.QO2synN6R6lRe', 1),
       ('myworld9464', '$2a$10$s0KGKuia5LEXhwZ3kplSaOsca4/fWve226LwGioIJRDURMwHZZZTK', 1),
       ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);
/*!40000 ALTER TABLE `users`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2023-03-28 14:31:34
