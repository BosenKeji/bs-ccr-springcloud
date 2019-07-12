CREATE TABLE coin (
    id int (11) NOT NULL AUTO_INCREMENT ,
    name varchar(50) not null ,
    status tinyint not null default 1,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coin_pair(
    id int (11) NOT NULL AUTO_INCREMENT,
    name varchar (50) NOT NULL DEFAULT '' ,
    is_popular tinyint  not null DEFAULT 0 ,
    is_official_set tinyint  not null DEFAULT 0 ,
    status tinyint not null default 1 ,
    created_at timestamp not null  ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coin_sort(
    id int (11) NOT NULL AUTO_INCREMENT,
    trade_platform_id int (11)  NOT NULL,
    coin_id int (11) NOT NULL ,
    type int (11) NOT NULL DEFAULT 1,
    sort_num int (11) NOT NULL DEFAULT 1,
    status tinyint not null default 1,
    created_at timestamp not null  ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coin_pair_coin(
    id int (11) NOT NULL AUTO_INCREMENT,
    coin_pair_id int(11) NOT NULL,
    coin_id int (11) NOT NULL,
    status tinyint not null default 1,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;