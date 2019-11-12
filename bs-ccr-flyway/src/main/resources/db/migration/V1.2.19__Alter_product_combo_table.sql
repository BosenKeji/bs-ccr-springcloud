
# 用户
alter table `user` modify created_at timestamp not null default CURRENT_TIMESTAMP;
alter table `user` modify updated_at timestamp not null default CURRENT_TIMESTAMP;

#产品
alter table `product` modify created_at timestamp not null default CURRENT_TIMESTAMP;
alter table `product` modify updated_at timestamp not null default CURRENT_TIMESTAMP;

#套餐
alter table `product_combo` modify created_at timestamp not null default CURRENT_TIMESTAMP;
alter table `product_combo` modify updated_at timestamp not null default CURRENT_TIMESTAMP;
alter table `user_product_combo` modify created_at timestamp not null default CURRENT_TIMESTAMP;
alter table `user_product_combo` modify updated_at timestamp not null default CURRENT_TIMESTAMP;

#套餐时长
alter table `user_product_combo_day` modify created_at timestamp not null default CURRENT_TIMESTAMP;
alter table `user_product_combo_day` modify updated_at timestamp not null default CURRENT_TIMESTAMP;
alter table `user_product_combo_day_by_admin` modify created_at timestamp not null default CURRENT_TIMESTAMP;
alter table `user_product_combo_day_by_admin` modify updated_at timestamp not null default CURRENT_TIMESTAMP;

#api绑定机器人
alter table `trade_platform_api_bind_product_combo` modify created_at timestamp not null default CURRENT_TIMESTAMP;
alter table `trade_platform_api_bind_product_combo` modify updated_at timestamp not null default CURRENT_TIMESTAMP;

