CREATE TABLE product(
    id int (11) NOT NULL AUTO_INCREMENT,
    name varchar (50) NOT NULL DEFAULT '' ,
    version_name varchar (50) not null DEFAULT '' ,
    is_official_set tinyint  not null DEFAULT 0 ,
    status tinyint not null default 1 ,
    created_at timestamp not null ,
    updated_at timestamp not null ,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE product_combo(
    id int (11) NOT NULL AUTO_INCREMENT,
    name varchar (50) NOT NULL DEFAULT '' ,
    is_popular tinyint  not null DEFAULT 0 ,
    is_official_set tinyint  not null DEFAULT 0 ,
    status tinyint not null default 1 ,
    created_at timestamp not null ,
    updated_at timestamp not null ,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;