#  0正常 1暂停买入 2忘记订单
alter table `coin_pair_choice` add column `order_status` TINYINT NOT NULL DEFAULT 0 COMMENT ' 0 默认 1暂停买入 2忘记订单';