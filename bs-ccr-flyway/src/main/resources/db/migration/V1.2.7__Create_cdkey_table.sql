CREATE TABLE `cd_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(64) NOT NULL COMMENT '激活码',
  `product_combo_id` int(11) NOT NULL COMMENT '产品套餐id',
  `username` varchar(16) COMMENT '用户名',
  `remark` varchar(255) NOT NULL COMMENT '备注',
  `status` tinyint not null default 1 ,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='激活码表';
