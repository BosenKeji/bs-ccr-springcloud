DROP PROCEDURE IF EXISTS schema_change;
DELIMITER //
CREATE PROCEDURE schema_change()
BEGIN
    DECLARE  CurrentDatabase VARCHAR(100);
    SELECT DATABASE() INTO CurrentDatabase;

    IF NOT	EXISTS(SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'user_product_combo' AND COLUMN_NAME = 'redis_key')
    then
				alter table user_product_combo add `redis_key` varchar (30) not null default '';
    END IF;

end //
DELIMITER ;
CALL schema_change();
DROP PROCEDURE IF EXISTS schema_change;