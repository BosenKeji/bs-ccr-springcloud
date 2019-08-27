CREATE TABLE product(
    id int (11) NOT NULL AUTO_INCREMENT,
    name varchar (24) NOT NULL DEFAULT '' ,
    version_name varchar (50) not null DEFAULT '' ,
    logo varchar (150) not null DEFAULT '' ,
    remark varchar (40) not null default '',
    status tinyint not null default 1 ,
    created_at timestamp not null,
    updated_at timestamp not null default '1980-01-01 00:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
