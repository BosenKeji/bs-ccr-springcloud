CREATE TABLE strategy (
    id int(11) NOT NULL AUTO_INCREMENT ,
    name varchar(50) not null ,
    status tinyint not null default 1,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE strategy_attribute (
    id int(11) NOT NULL AUTO_INCREMENT ,
    name varchar(50) not null ,
		strategy_id int(11) NOT NULL,
		is_default tinyint NOT NULL DEFAULT 0,
		is_tip tinyint NOT NULL DEFAULT 0,
		lever int NOT NULL DEFAULT 1,
		strategy_sequence_id int(11) NOT NULL,
		rate int NOT NULL,
		stop_profit_ratio tinyint NOT NULL,
		is_stop_profit_trace tinyint NOT NULL DEFAULT 1,
		stop_profit_trace_trigger_rate tinyint NOT NULL,
		stop_profit_trace_drop_rate tinyint NOT NULL,
		is_stop_profit_money tinyint NOT NULL DEFAULT 0,
		is_stop_profit_grid tinyint NOT NULL DEFAULT 0,
		build_reference tinyint NOT NULL,
    status tinyint not null default 1,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE strategy_sequence (
    id int(11) NOT NULL AUTO_INCREMENT ,
    name varchar(50) not null ,
    status tinyint not null default 1,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE strategy_sequence_value (
    id int(11) NOT NULL AUTO_INCREMENT ,
    strategy_sequence_id int(10) NOT NULL,
		value VARCHAR(256) NOT NULL,
		sort_num int NOT NULL,
    status tinyint not null default 1,
    created_at timestamp not null ,
    updated_at timestamp not null default '1970-01-01 08:00:01',
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
