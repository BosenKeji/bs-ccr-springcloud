DROP PROCEDURE IF EXISTS schema_change;
DELIMITER //
CREATE PROCEDURE schema_change()
BEGIN
    DECLARE  CurrentDatabase VARCHAR(100);
    SELECT DATABASE() INTO CurrentDatabase;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'trade_platform_api' AND COLUMN_NAME = 'access_key')
    then
        ALTER TABLE trade_platform_api drop column access_key;
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'trade_platform_api' AND COLUMN_NAME = 'secret_key')
    then
        ALTER TABLE trade_platform_api drop column secret_key;
    END IF;

    IF NOT EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'trade_platform_api' AND COLUMN_NAME = 'robot_id')
        then
            ALTER TABLE trade_platform_api add robot_id varchar(200) NOT NULL default '';
    END IF;
end //
DELIMITER ;
CALL schema_change();
DROP PROCEDURE IF EXISTS schema_change;