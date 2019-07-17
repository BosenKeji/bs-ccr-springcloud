CREATE TABLE coin_pair_choic (
    id int (11) NOT NULL AUTO_INCREMENT ,
    coin_partner_id int(11) not null ,
    user_id int(11) not null ,
    is_start tinyint not null default 1 ,
    status tinyint not null default 1 ,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coin_pair_choic_attribute (
    id int (11) NOT NULL AUTO_INCREMENT ,
    coin_partner_choic_id int(11) not null ,
    expect_money int(11) not null default 0,
    strategy_id int(11) not null ,
    is_custom tinyint not null default 1 ,
    status tinyint not null default 1 ,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coin_pair_choic_attribute_custom (
    id int (11) NOT NULL AUTO_INCREMENT ,
    coin_partner_choic_id int(11) not null ,
    stop_profit_type tinyint not null default 1 ,
    stop_profit_money int (11) not null default 0 ,
    stop_profit_trace_trigger_rate float(11) not null default 0 ,
    stop_profit_trace_drop_rate float(11)  not null  default 0,
    stop_profit_fixed_rate float(11)  not null  default 0,
    status tinyint not null default 1 ,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coin_pair_deal (
    id int (11) NOT NULL AUTO_INCREMENT ,
    user_id int(11) not null ,
    coin_partner_choic_id int(11) not null ,
    type tinyint not null  default 1 ,
    quantity int(50) not null default 1 ,
    status tinyint not null default 1 ,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;