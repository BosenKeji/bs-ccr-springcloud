#0 已删除 1 正常
alter table `trade_platform_api_bind_product_combo` add column `status` TINYINT NOT NULL DEFAULT 1 COMMENT '绑定状态';

# 把 未绑定的 记录 状态改成 已删除
UPDATE trade_platform_api_bind_product_combo
    set status = 0
    WHERE
    trade_platform_api_id = 0;