DROP PROCEDURE IF EXISTS schema_change;
DELIMITER //
CREATE PROCEDURE schema_change()
BEGIN
    DECLARE  CurrentDatabase VARCHAR(100);
    SELECT DATABASE() INTO CurrentDatabase;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin' AND COLUMN_NAME = 'created_at')
    then
        ALTER TABLE coin MODIFY	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair' AND COLUMN_NAME = 'created_at')
    then
        ALTER TABLE coin_pair MODIFY	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_sort' AND COLUMN_NAME = 'created_at')
    then
        ALTER TABLE coin_sort MODIFY	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_coin' AND COLUMN_NAME = 'created_at')
    then
        ALTER TABLE coin_pair_coin MODIFY	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'trade_platform' AND COLUMN_NAME = 'created_at')
    then
        ALTER TABLE trade_platform MODIFY created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'trade_platform_api' AND COLUMN_NAME = 'created_at')
    then
        ALTER TABLE trade_platform_api MODIFY	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'trade_platform_coin_pair' AND COLUMN_NAME = 'created_at')
    then
        ALTER TABLE trade_platform_coin_pair MODIFY	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_choice' AND COLUMN_NAME = 'created_at')
    then
        ALTER TABLE coin_pair_choice MODIFY	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_choice_attribute' AND COLUMN_NAME = 'created_at')
    then
        ALTER TABLE coin_pair_choice_attribute MODIFY	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_choice_attribute_custom' AND COLUMN_NAME = 'created_at')
    then
        ALTER TABLE coin_pair_choice_attribute_custom MODIFY	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
    END IF;
end //
DELIMITER ;
CALL schema_change();
DROP PROCEDURE IF EXISTS schema_change;