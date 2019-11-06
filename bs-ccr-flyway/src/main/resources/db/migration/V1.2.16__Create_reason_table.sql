
CREATE TABLE `reason` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL default '',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `reason_type_id` int not null default 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `reason_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL default '',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `combo_day_by_admin_reason` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_product_combo_day_by_admin_id` int not null default 0,
  `reason_id` int not null default 0,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `reason_type` values (1,'combo_day_by_admin_reason',1,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );

insert into `reason` values (1,'活动奖励',1,1,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );
insert into `reason` values (2,'购买奖励',1,1,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );
insert into `reason` values (3,'续费',1,1,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );
insert into `reason` values (4,'其他',1,1,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );