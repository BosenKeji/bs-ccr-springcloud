
CREATE TABLE trade_platform(
    id int (11) NOT NULL AUTO_INCREMENT,
    name varchar (50) NOT NULL DEFAULT '' ,
    logo varchar (80) NOT NULL DEFAULT '' ,
    status tinyint not null default 1,
    created_at timestamp not null  ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE trade_platform_coin_pair(
    id int (11) NOT NULL AUTO_INCREMENT,
    trade_platform_id int(11) NOT NULL ,
    coin_pair_id int(11) NOT NULL ,
    status tinyint not null default 1,
    created_at timestamp not null  ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;