DROP PROCEDURE IF EXISTS schema_change;
DELIMITER //
CREATE PROCEDURE schema_change()
BEGIN
    DECLARE  CurrentDatabase VARCHAR(100);
    SELECT DATABASE() INTO CurrentDatabase;

    IF NOT	EXISTS(SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'user_product_combo_day_by_admin' AND COLUMN_NAME = 'order_number')
    then
				alter table user_product_combo_day_by_admin add `order_number` varchar(128) NOT NULL DEFAULT '';
    END IF;

    IF NOT EXISTS(SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'user_product_combo_day_by_admin' AND COLUMN_NAME = 'remark')
    then
        alter table user_product_combo_day_by_admin add  `remark` varchar(80) NOT NULL DEFAULT '';
    END IF;

    IF NOT EXISTS(SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'user_product_combo_day_by_admin' AND COLUMN_NAME = 'combo_day_reason_id')
        then
            alter table user_product_combo_day_by_admin add `combo_day_reason_id` int(11) NOT NULL DEFAULT 1;
    END IF;
end //
DELIMITER ;
CALL schema_change();
DROP PROCEDURE IF EXISTS schema_change;
