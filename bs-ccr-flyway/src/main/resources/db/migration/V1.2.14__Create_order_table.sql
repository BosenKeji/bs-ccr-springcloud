drop procedure if exists schema_change;
delimiter //
create procedure schema_change()
begin
    DECLARE  CurrentDatabase VARCHAR(100);
    SELECT DATABASE() INTO CurrentDatabase;

    IF NOT EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'order_group')
    then
        create table order_group(
            id int (11) NOT NULL AUTO_INCREMENT ,
            name varchar(50) not null default'',
            end_profit_ratio int(11) not null  comment '结单收益比',
            is_end tinyint not null default 0 comment '是否结单',
            end_type tinyint not null default 0 comment '结单方式',
            status tinyint not null default 1 ,
            created_at timestamp not null default current_timestamp comment '订单组创建时间',
            updated_at timestamp not null default current_timestamp comment '订单组更新时间',
            PRIMARY KEY (id)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    end if ;

    IF NOT EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'order')
    then
        create table trade_order(
            id int (11) NOT NULL AUTO_INCREMENT ,
            order_group_id int(11) not null ,
            coin_pair_choice_id int(11) not null ,
            trade_average_price int(11) not null comment '交易均价',
            trade_numbers int(11) not null comment '交易数量' ,
            trade_cost int(11) not null comment '交易费用',
            shell_profit int(11) not null comment '卖出收益',
            trade_type tinyint not null default 1 comment '交易方式',
            status tinyint not null default 1 ,
            created_at timestamp not null default current_timestamp comment '订单创建时间',
            updated_at timestamp not null default current_timestamp comment '订单更新时间',
            PRIMARY KEY (id)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    end if ;
end //
delimiter ;
call schema_change();
drop procedure if exists schema_change;
