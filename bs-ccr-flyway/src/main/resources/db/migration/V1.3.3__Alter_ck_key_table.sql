# 增加 user_id user_product_combo_id 字段
DROP PROCEDURE IF EXISTS schema_change;
DELIMITER //
CREATE PROCEDURE schema_change()
BEGIN
    DECLARE  CurrentDatabase VARCHAR(100);
    SELECT DATABASE() INTO CurrentDatabase;

    IF NOT EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'cd_key' AND COLUMN_NAME = 'user_id')
    then
        alter table `cd_key` add column `user_id` tinyint not null default 0 comment '激活用户';
    END IF;

    IF NOT EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'cd_key' AND COLUMN_NAME = 'user_product_combo_id')
    then
        alter table `cd_key` add column `user_product_combo_id` tinyint not null default 0 comment '被激活的机器人id';
    END IF;

end //
DELIMITER ;
CALL schema_change();
DROP PROCEDURE IF EXISTS schema_change;