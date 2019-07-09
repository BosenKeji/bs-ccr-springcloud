CREATE TABLE coin (
    id int auto_increment primary key not null ,
    name varchar(50) not null ,
    status tinyint not null default 1,
    created_at timestamp not null ,
    updated_at timestamp not null

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coin_pairs(
id int (11) NOT NULL AUTO_INCREMENT,
name varchar (255) DEFAULT NULL,
is_popular varchar (255) DEFAULT NULL,
is_official_set varchar (255) DEFAULT NULL,
status varchar (255) DEFAULT NULL.DEFAULT,
create_at datetime DEFAULT NULL ,
update_at datetime DEFAULT NULL ,
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coin_sorts(
id int (11) NOT NULL AUTO_INCREMENT,
trade_platform_id int (11)  DEFAULT NULL,
coin_id int (11) DEFAULT NULL,
type int (11) DEFAULT NULL,
sort_num int (11) DEFAULT NULL ,
status int (11) DEFAULT NULL,
create_at datetime DEFAULT NULL ,
update_at datetime DEFAULT NULL ,
PRIMARY KEY (id),
CONSTRAINT fk_coins_coins_sorts FOREIGN KEY (coin_id) references coins (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coin_pair_coins(
id int (11) NOT NULL AUTO_INCREMENT,
coin_pair_id int(11) DEFAULT NULL,
coin_id int (11) DEFAULT NULL,
status int (11) DEFAULT NULL,
create_at datetime DEFAULT NULL ,
update_at datetime DEFAULT NULL ,
PRIMARY KEY (id) ,
CONSTRAINT fk_coins_coins_pair_coins FOREIGN KEY (coin_id) references coins (id),
CONSTRAINT fk_coins_pair_coins_pair_coins FOREIGN KEY (coin_pair_id) references coins_pairs (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;