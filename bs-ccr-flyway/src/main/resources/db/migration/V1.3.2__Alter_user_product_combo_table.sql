# 用户套餐运行状态  1运行中(默认) 2 已暂停 3已封禁
alter table `user_product_combo` add column `run_status` TINYINT NOT NULL DEFAULT 1 COMMENT '运行状态 1运行中 2已暂停 3已封禁';