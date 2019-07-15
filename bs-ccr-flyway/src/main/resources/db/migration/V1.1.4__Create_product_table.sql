CREATE TABLE product(
    id int (11) NOT NULL AUTO_INCREMENT,
    name varchar (24) NOT NULL DEFAULT '' ,
    version_name varchar (50) not null DEFAULT '' ,
    logo varchar (50) not null DEFAULT '' ,
    remark varchar (40) not null default '',
    status tinyint not null default 1 ,
    created_at timestamp not null,
    updated_at timestamp not null default '1980-01-01 00:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE product_combo(
    id int (11) NOT NULL AUTO_INCREMENT,
    product_id int (11) not null default 0,
    name varchar (24) NOT NULL DEFAULT '' ,
    time int  not null DEFAULT 0 ,
    price float  not null DEFAULT 0 ,
    remark varchar(40) not null default '',
    status tinyint not null default 1 ,
    created_at timestamp not null,
    updated_at timestamp not null default '1980-01-01 00:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user_product_combo(
    id int (11) NOT NULL AUTO_INCREMENT,
    user_id int(11) not null,
    order_number varchar (128) not null default '',
    product_combo_id int (11) not null,
    remark varchar(80) not null default '',
    status tinyint not null default 1 ,
    created_at timestamp not null,
    updated_at timestamp not null default '1980-01-01 00:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user_product_combo_day(
    id int (11) NOT NULL AUTO_INCREMENT,
    user_id int(11) not null,
    user_product_combo_id int (11) not null,
    type tinyint not null default 1,
    number int(11) not null default 0,
    status tinyint not null default 1 ,
    created_at timestamp not null,
    updated_at timestamp not null default '1980-01-01 00:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user_product_combo_day_by_admin(
    id int (11) NOT NULL AUTO_INCREMENT,
    admin_id int(11) not null,
    user_product_combo_day_id int (11) not null,
    status tinyint not null default 1 ,
    created_at timestamp not null,
    updated_at timestamp not null default '1980-01-01 00:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;