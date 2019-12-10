DROP PROCEDURE IF EXISTS schema_change;
DELIMITER //
CREATE PROCEDURE schema_change()
BEGIN
    DECLARE  CurrentDatabase VARCHAR(100);
    SELECT DATABASE() INTO CurrentDatabase;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_choice' AND COLUMN_NAME = 'coin_partner_id')
    then
        ALTER TABLE coin_pair_choice change coin_partner_id coin_pair_id int(11) not null;
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_choice' AND COLUMN_NAME = 'user_id')
    then
        ALTER TABLE coin_pair_choice DROP user_id;
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_choice_attribute' AND COLUMN_NAME = 'coin_partner_choice_id')
    then
        ALTER TABLE coin_pair_choice_attribute change coin_partner_choice_id coin_pair_choice_id int(11) not null;
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_choice_attribute_custom' AND COLUMN_NAME = 'coin_partner_choice_id')
    then
        ALTER TABLE coin_pair_choice_attribute_custom change coin_partner_choice_id coin_pair_choice_id int(11) not null;
    END IF;

    IF  EXISTS(SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'trade_order' AND COLUMN_NAME = 'shell_profit')
    then
        ALTER TABLE trade_order change shell_profit sell_profit int(11) not null comment '卖出收益';
    END IF;

end //
DELIMITER ;
CALL schema_change();
DROP PROCEDURE IF EXISTS schema_change;