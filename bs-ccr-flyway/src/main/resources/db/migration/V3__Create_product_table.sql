CREATE TABLE product(
    id int (11) NOT NULL AUTO_INCREMENT,
    name varchar (50) NOT NULL DEFAULT '' ,
    version_name varchar (50) not null DEFAULT '' ,
    logo varchar (50) not null DEFAULT '' ,
    remark varchar (50) not null default '',
    status tinyint not null default 1 ,
    created_at timestamp not null default '1980-01-01 00:00:01',
    updated_at timestamp not null default '1980-01-01 00:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE product_combo(
    id int (11) NOT NULL AUTO_INCREMENT,
    product_id int (11) not null default 0,
    name varchar (50) NOT NULL DEFAULT '' ,
    time float  not null DEFAULT 0 ,
    price float  not null DEFAULT 0 ,
    remark varchar(50) not null default '',
    status tinyint not null default 1 ,
    created_at timestamp not null default '1980-01-01 00:00:01',
    updated_at timestamp not null default '1980-01-01 00:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;