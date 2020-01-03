ALTER TABLE `user` ADD UNIQUE uk_username(`username`(20));
ALTER TABLE `user` ADD UNIQUE uk_tel(`tel`(11));
ALTER TABLE `user_product_combo` ADD INDEX idk_user_id(`user_id`);
ALTER TABLE `user_product_combo_day` ADD INDEX idk_user_id(`user_id`);
ALTER TABLE `user_product_combo_day` ADD INDEX idk_combo_id(`user_product_combo_id`);

ALTER TABLE `user_product_combo_day_by_admin` ADD INDEX idk_combo_day_id(`user_product_combo_day_id`);
ALTER TABLE `trade_platform_api_bind_product_combo` ADD UNIQUE uk_user_product_combo(`user_product_combo_id`);