CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '权限名称',
  `url` varchar(255) NOT NULL COMMENT '授权路径',
  `http_method` tinyint not null comment '请求方式，1：post 2:get 3:update 4:delete',
  `type` tinyint not null default 1 comment '1 允许权限 2 拒绝权限',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` tinyint not null default 1 ,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` tinyint not null default 1 ,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

CREATE TABLE `permission_group` (
  `id` int(11) not null auto_increment,
  `name` varchar(64) not null comment '权限组名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` tinyint not null default 1 ,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  primary key (`id`)
) engine = InnoDB DEFAULT CHARSET=utf8 comment='权限组表';

CREATE TABLE `role_permission_group` (
  `id` int(11) not null auto_increment,
  `role_id` int(11) not null comment '角色ID',
  `permission_group_id` int(11) not null comment '权限组id',
  `status` tinyint not null default 1 ,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  primary key (`id`)
) engine = InnoDB DEFAULT CHARSET=utf8 comment='角色权限组表';

CREATE TABLE `permission_group_permission` (
  `id` int(11) not null auto_increment,
  `permission_group_id` int(11) not null comment '权限组id',
  `permission_id` int(11) not null comment '权限id',
  `status` tinyint not null default 1 ,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  primary key (`id`)
) engine = InnoDB DEFAULT CHARSET=utf8 comment='权限组权限表';

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户 ID',
  `role_id` int(11) NOT NULL COMMENT '角色 ID',
  `user_type` tinyint not null comment '角色类型 1:admin 2:user',
  `status` tinyint not null default 1 ,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';