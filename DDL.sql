create table if not exists about
(
    id          int auto_increment
        primary key,
    content     text     null comment '内容',
    create_time datetime not null comment '创建时间',
    update_time datetime null comment '更新时间'
);

create table if not exists article
(
    id              int auto_increment
        primary key,
    user_id         int                  not null comment '作者',
    category_id     int                  null comment '文章分类',
    article_cover   varchar(1024)        null comment '文章缩略图',
    article_title   varchar(50)          not null comment '标题',
    article_content longtext             not null comment '内容',
    is_top          tinyint(1) default 0 not null comment '是否置顶 0否 1是',
    is_featured     tinyint(1) default 0 not null comment '是否推荐 0否 1是',
    is_delete       tinyint(1) default 0 not null comment '是否删除  0否 1是',
    status          tinyint(1) default 1 not null comment '状态值 1公开 2私密 3草稿',
    type            tinyint(1) default 1 not null comment '文章类型 1原创 2转载 3翻译',
    password        varchar(255)         null comment '访问密码',
    original_url    varchar(255)         null comment '原文链接',
    create_time     datetime             not null comment '发表时间',
    update_time     datetime             null comment '更新时间'
);

create table if not exists article_tag
(
    id         int auto_increment
        primary key,
    article_id int not null comment '文章id',
    tag_id     int not null comment '标签id'
);

create index fk_article_tag_1
    on article_tag (article_id);

create index fk_article_tag_2
    on article_tag (tag_id);

create table if not exists category
(
    id            int auto_increment
        primary key,
    category_name varchar(20) not null comment '分类名',
    create_time   datetime    not null comment '创建时间',
    update_time   datetime    null comment '更新时间'
);

create table if not exists comment
(
    id              int auto_increment comment '主键'
        primary key,
    user_id         int                  not null comment '评论用户Id',
    topic_id        int                  null comment '评论主题id',
    comment_content text                 not null comment '评论内容',
    reply_user_id   int                  null comment '回复用户id',
    parent_id       int                  null comment '父评论id',
    type            tinyint              not null comment '评论类型 1.文章 2.留言 3.关于我 4.友链 5.说说',
    is_delete       tinyint    default 0 not null comment '是否删除  0否 1是',
    is_review       tinyint(1) default 1 not null comment '是否审核',
    create_time     datetime             not null comment '评论时间',
    update_time     datetime             null comment '更新时间'
);

create index fk_comment_parent
    on comment (parent_id);

create index fk_comment_user
    on comment (user_id);

create table if not exists exception_log
(
    id             int auto_increment
        primary key,
    opt_uri        varchar(255)  not null comment '请求接口',
    opt_method     varchar(255)  not null comment '请求方式',
    request_method varchar(255)  null comment '请求方式',
    request_param  varchar(2000) null comment '请求参数',
    opt_desc       varchar(255)  null comment '操作描述',
    exception_info longtext      null comment '异常信息',
    ip_address     varchar(255)  null comment 'ip',
    ip_source      varchar(255)  null comment 'ip来源',
    create_time    datetime      not null comment '操作时间'
)
    collate = utf8mb4_general_ci;

create table if not exists file
(
    id          int auto_increment comment '唯一主键'
        primary key,
    file_name   varchar(255) default ''                not null comment '文件名称',
    create_time timestamp    default CURRENT_TIMESTAMP not null comment '文件创建时间',
    file_url    varchar(255) default ''                not null comment '文件地址',
    modify_time timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    file_size   int          default 0                 not null comment '记录文件大小,单位kb'
)
    comment '记录系统中上传的文件信息';

create table if not exists friend_link
(
    id           int auto_increment
        primary key,
    link_name    varchar(20)  not null comment '链接名',
    link_avatar  varchar(255) not null comment '链接头像',
    link_address varchar(50)  not null comment '链接地址',
    link_intro   varchar(100) not null comment '链接介绍',
    create_time  datetime     not null comment '创建时间',
    update_time  datetime     null comment '更新时间'
);

create index fk_friend_link_user
    on friend_link (link_name);

create table if not exists job
(
    id              int auto_increment comment '任务ID',
    job_name        varchar(64)  default ''        not null comment '任务名称',
    job_group       varchar(64)  default 'DEFAULT' not null comment '任务组名',
    invoke_target   varchar(500)                   not null comment '调用目标字符串',
    cron_expression varchar(255) default ''        null comment 'cron执行表达式',
    misfire_policy  tinyint(1)   default 3         null comment '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    concurrent      tinyint(1)   default 1         null comment '是否并发执行（0禁止 1允许）',
    status          tinyint(1)   default 0         null comment '状态（0暂停 1正常）',
    create_time     datetime                       not null comment '创建时间',
    update_time     datetime                       null comment '更新时间',
    remark          varchar(500) default ''        null comment '备注信息',
    primary key (id, job_name, job_group)
)
    comment '定时任务调度表' collate = utf8mb4_general_ci;

create table if not exists job_log
(
    id             int auto_increment comment '任务日志ID'
        primary key,
    job_id         int                  not null comment '任务ID',
    job_name       varchar(64)          not null comment '任务名称',
    job_group      varchar(64)          not null comment '任务组名',
    invoke_target  varchar(500)         not null comment '调用目标字符串',
    job_message    varchar(500)         null comment '日志信息',
    status         tinyint(1) default 0 null comment '执行状态（0正常 1失败）',
    exception_info longtext             null comment '异常信息',
    create_time    datetime             null comment '创建时间',
    start_time     datetime             null comment '开始时间',
    end_time       datetime             null comment '结束时间'
)
    comment '定时任务调度日志表' collate = utf8mb4_general_ci;

create table if not exists operation_log
(
    id             int auto_increment comment '主键id'
        primary key,
    opt_module     varchar(20)  not null comment '操作模块',
    opt_type       varchar(20)  not null comment '操作类型',
    opt_uri        varchar(255) not null comment '操作url',
    opt_method     varchar(255) not null comment '操作方法',
    opt_desc       varchar(255) not null comment '操作描述',
    request_param  longtext     not null comment '请求参数',
    request_method varchar(20)  not null comment '请求方式',
    response_data  longtext     not null comment '返回数据',
    user_id        int          not null comment '用户id',
    nickname       varchar(50)  not null comment '用户昵称',
    ip_address     varchar(255) not null comment '操作ip',
    ip_source      varchar(255) not null comment '操作地址',
    create_time    datetime     not null comment '创建时间',
    update_time    datetime     null comment '更新时间'
);

create table if not exists permission
(
    id               int auto_increment comment '主键'
        primary key,
    name             varchar(20)          not null comment '菜单名',
    path             varchar(50)          not null comment '菜单路径',
    component        varchar(50)          not null comment '组件',
    icon             varchar(50)          not null comment '菜单icon',
    create_time      datetime             not null comment '创建时间',
    update_time      datetime             null comment '更新时间',
    `rank`           tinyint(1)           not null comment '排序',
    parent_id        int                  null comment '父id',
    status           tinyint(1) default 0 not null comment '是否隐藏  0否1是',
    type             tinyint              null comment '类型',
    permission_value varchar(255)         null comment '权限值'
);

create table if not exists photo
(
    id          int auto_increment comment '主键'
        primary key,
    album_id    int                  not null comment '相册id',
    photo_name  varchar(20)          not null comment '照片名',
    photo_desc  varchar(50)          null comment '照片描述',
    photo_src   varchar(1000)        not null comment '照片地址',
    is_delete   tinyint(1) default 0 not null comment '是否删除',
    create_time datetime             not null comment '创建时间',
    update_time datetime             null comment '更新时间'
)
    comment '照片';

create table if not exists photo_album
(
    id          int auto_increment comment '主键'
        primary key,
    album_name  varchar(20)          not null comment '相册名',
    album_desc  varchar(50)          not null comment '相册描述',
    album_cover varchar(1000)        not null comment '相册封面',
    is_delete   tinyint(1) default 0 not null comment '是否删除',
    status      tinyint(1) default 1 not null comment '状态值 1公开 2私密',
    create_time datetime             not null comment '创建时间',
    update_time datetime             null comment '更新时间'
)
    comment '相册';

create table if not exists resource
(
    id             int auto_increment comment '主键'
        primary key,
    resource_name  varchar(50)          not null comment '资源名',
    url            varchar(255)         null comment '权限路径',
    request_method varchar(10)          null comment '请求方式',
    parent_id      int                  null comment '父模块id',
    is_anonymous   tinyint(1) default 0 not null comment '是否匿名访问 0否 1是',
    create_time    datetime             not null comment '创建时间',
    update_time    datetime             null comment '修改时间'
);

create table if not exists role
(
    id          int auto_increment comment '角色id'
        primary key,
    role_name   varchar(20)      default ''  not null comment '角色名称',
    role_code   varchar(20)                  null comment '角色编码',
    remark      varchar(255)                 null comment '备注',
    is_disable  tinyint unsigned default '0' not null comment '逻辑删除 1（true）已删除， 0（false）未删除',
    create_time timestamp                    not null comment '创建时间',
    update_time datetime                     not null comment '更新时间'
)
    charset = utf8;

create table if not exists role_permission
(
    id            int auto_increment comment '唯一主键',
    role_id       int                          not null,
    permission_id int                          not null,
    is_deleted    tinyint unsigned default '0' not null comment '逻辑删除 1（true）已删除， 0（false）未删除',
    create_time   datetime                     not null comment '创建时间',
    update_time   datetime                     not null comment '更新时间',
    constraint role_permission_pk
        unique (id)
)
    comment '角色权限' charset = utf8;

create index idx_permission_id
    on role_permission (permission_id);

create index idx_role_id
    on role_permission (role_id);

create table if not exists role_resource
(
    id          int auto_increment
        primary key,
    role_id     int null comment '角色id',
    resource_id int null comment '权限id'
);

create table if not exists tag
(
    id          int auto_increment
        primary key,
    tag_name    varchar(20) not null comment '标签名',
    create_time datetime    not null comment '创建时间',
    update_time datetime    null comment '更新时间'
);

create table if not exists talk
(
    id          int auto_increment comment '说说id'
        primary key,
    user_id     int                  not null comment '用户id',
    content     varchar(2000)        not null comment '说说内容',
    images      varchar(2500)        null comment '图片',
    is_top      tinyint(1) default 0 not null comment '是否置顶',
    status      tinyint(1) default 1 not null comment '状态 1.公开 2.私密',
    create_time datetime             not null comment '创建时间',
    update_time datetime             null comment '更新时间'
);

create table if not exists temp_article
(
    id           bigint auto_increment comment '临时文章的唯一id'
        primary key,
    article_id   int          null comment '实际文章的id',
    tmp_user_env varchar(255) null comment '临时用户的登陆环境'
)
    comment '用于存储临时的未登录用户的发布文章';

create table if not exists unique_view
(
    id          int auto_increment
        primary key,
    views_count int      not null comment '访问量',
    create_time datetime not null comment '创建时间',
    update_time datetime null comment '更新时间'
);

create table if not exists user
(
    id            int auto_increment comment '用户id'
        primary key,
    username      varchar(20)      default ''                not null comment '用户登录账号',
    password      varchar(32)      default ''                not null comment '密码',
    is_disable    tinyint unsigned default '0'               not null comment '逻辑删除 1（true）已删除， 0（false）未删除',
    create_time   timestamp        default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   timestamp        default CURRENT_TIMESTAMP not null comment '更新时间',
    register_type int              default 0                 not null comment '注册类型   用于区分不同方式注册',
    constraint uk_username
        unique (username)
)
    comment '用户表 仅保存基础的用户名与密码 详细信息查询 user_info';

create table if not exists user_collection
(
    id                 int auto_increment comment '唯一标识'
        primary key,
    user_id            int not null comment '收藏者id',
    collection_content int not null comment '收藏内容，记录收藏的文章商品之类的id',
    collection_type    int not null comment '收藏类型，用于分辨去哪个表查询'
)
    comment '用户收藏';

create table if not exists user_info
(
    id           int auto_increment comment '用户ID'
        primary key,
    email        varchar(50)              null comment '邮箱号',
    nickname     varchar(50)              not null comment '用户昵称',
    avatar       varchar(1024) default '' not null comment '用户头像',
    intro        varchar(255)             null comment '用户简介',
    website      varchar(255)             null comment '个人网站',
    is_subscribe tinyint(1)               null comment '是否订阅',
    create_time  datetime                 not null comment '创建时间',
    update_time  datetime                 null comment '更新时间'
);

create table if not exists user_login_info
(
    id         int auto_increment comment '唯一主键'
        primary key,
    user_id    int                                 not null comment '登录用户id',
    login_time timestamp default CURRENT_TIMESTAMP not null comment '登陆时间',
    login_type int       default 0                 not null comment '登陆类型  即：网页登陆 单点登录',
    ip_address varchar(20)                         null comment '登陆的ip地址',
    ip_source  varchar(20)                         null comment 'ip所在地',
    browser    varchar(255)                        null comment '用户登陆的浏览器类型',
    os         varchar(255)                        null comment '用户登录的操作系统'
)
    comment '用户登录信息';

create index user_login_info_user_id_login_time_index
    on user_login_info (user_id, login_time)
    comment '为时间建立索引  方便查询最后一次登录';

create table if not exists user_role
(
    id          int auto_increment comment '主键id'
        primary key,
    role_id     char(19)         default '0'               not null comment '角色id',
    user_id     char(19)         default '0'               not null comment '用户id',
    is_deleted  tinyint unsigned default '0'               not null comment '逻辑删除 1（true）已删除， 0（false）未删除',
    create_time timestamp        default CURRENT_TIMESTAMP null comment '创建时间',
    update_time timestamp        default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    charset = utf8;

create index idx_role_id
    on user_role (role_id);

create index idx_user_id
    on user_role (user_id);

create table if not exists website_config
(
    id          int auto_increment
        primary key,
    config      varchar(2000) null comment '配置信息',
    create_time datetime      not null comment '创建时间',
    update_time datetime      null comment '更新时间'
);

create or replace definer = root@`%` view last_login_info as
select `cloud_sofa_server`.`user_login_info`.`id`         AS `id`,
       `cloud_sofa_server`.`user_login_info`.`user_id`    AS `user_id`,
       `cloud_sofa_server`.`user_login_info`.`login_time` AS `login_time`,
       `cloud_sofa_server`.`user_login_info`.`login_type` AS `login_type`,
       `cloud_sofa_server`.`user_login_info`.`ip_address` AS `ip_address`,
       `cloud_sofa_server`.`user_login_info`.`ip_source`  AS `ip_source`
from ((select `cloud_sofa_server`.`user_login_info`.`user_id`         AS `user_id`,
              max(`cloud_sofa_server`.`user_login_info`.`login_time`) AS `login_time`
       from `cloud_sofa_server`.`user_login_info`
       group by `cloud_sofa_server`.`user_login_info`.`user_id`) `b` left join `cloud_sofa_server`.`user_login_info`
      on (((`cloud_sofa_server`.`user_login_info`.`user_id` = `b`.`user_id`) and
           (`cloud_sofa_server`.`user_login_info`.`login_time` = `b`.`login_time`))));

-- comment on column last_login_info.id not supported: 唯一主键

-- comment on column last_login_info.user_id not supported: 登录用户id

-- comment on column last_login_info.login_time not supported: 登陆时间

-- comment on column last_login_info.login_type not supported: 登陆类型  即：网页登陆 单点登录

-- comment on column last_login_info.ip_address not supported: 登陆的ip地址

-- comment on column last_login_info.ip_source not supported: ip所在地

