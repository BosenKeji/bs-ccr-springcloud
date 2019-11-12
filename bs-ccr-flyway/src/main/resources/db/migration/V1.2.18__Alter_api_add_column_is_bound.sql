# 添加 是否被绑定 列  1未绑定(默认) 2 已绑定
alter table `trade_platform_api` add column `is_bound` TINYINT NOT NULL DEFAULT 1 COMMENT '是否被绑定';

#根据 绑定表 更新 api的绑定状态
UPDATE trade_platform_api
    set is_bound = 2
    WHERE
    id in
    (select trade_platform_api_id
    FROM trade_platform_api_bind_product_combo
    GROUP BY trade_platform_api_id);