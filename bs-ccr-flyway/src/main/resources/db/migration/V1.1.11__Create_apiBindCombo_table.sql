CREATE TABLE trade_platform_api_bind_product_combo (
    id int (11) NOT NULL AUTO_INCREMENT ,
    user_id int(11) not null default 0,
    trade_platform_api_id int(11) not null default 0,
    user_product_combo_id int(11) not null default 0,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;