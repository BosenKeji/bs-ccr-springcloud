CREATE TABLE trade_platform_api(
    id int (11) NOT NULL AUTO_INCREMENT,
    user_id int(11) NOT NULL ,
    trade_platform_id int (11) NOT NULL ,
    sign varchar (50) DEFAULT NULL,
    access_key varchar (50) NOT NULL DEFAULT '',
    secret_key varchar (50) NOT NULL DEFAULT '',
    nickname varchar (50) NOT NULL DEFAULT '',
    status tinyint not null default 1,
    created_at timestamp not null  ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;