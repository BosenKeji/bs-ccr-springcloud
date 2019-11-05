DROP PROCEDURE IF EXISTS schema_change;
DELIMITER //
CREATE PROCEDURE schema_change()
BEGIN
    DECLARE CurrentDatabase VARCHAR(100);
    SELECT DATABASE() INTO CurrentDatabase;

#     货币模块索引
#     coin货币表
    IF NOT EXISTS(SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin' AND INDEX_NAME = 'index_coin_name')
    then
        ALTER TABLE coin ADD INDEX index_coin_name(name);
    END IF;

#     coin_pair货币对表
    IF NOT EXISTS(SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair' AND INDEX_NAME = 'index_coin_pair_name')
    then
        ALTER TABLE coin_pair ADD INDEX index_coin_pair_name(name);
    END IF;

#     coin_pair_coin货币对货币表
    IF NOT EXISTS(SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_coin' AND INDEX_NAME = 'index_coin_pair_coin_id')
    then
        ALTER TABLE coin_pair_coin ADD INDEX index_coin_pair_coin_id(coin_id,coin_pair_id);
    END IF;

#     cort_sort货币排序表
    IF NOT EXISTS(SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_sort' AND INDEX_NAME = 'index_coin_sort_platform_id_coin_id')
    then
        ALTER TABLE coin_sort ADD INDEX index_coin_sort_platform_id_coin_id(trade_platform_id,coin_id);
    END IF;

#     交易平台模块索引
#     trade_platform交易平台表
    IF NOT EXISTS(SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'trade_platform' AND INDEX_NAME = 'index_trade_platform_name')
    then
        ALTER TABLE trade_platform ADD INDEX index_trade_platform_name(name);
    END IF;

#     trade_platform_api交易平台api表
    IF NOT EXISTS(SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'trade_platform_api' AND INDEX_NAME = 'index_trade_platform_api_user_platform_id')
    then
        ALTER TABLE trade_platform_api ADD INDEX index_trade_platform_api_user_platform_id(user_id,trade_platform_id);
    END IF;

#     trade_platform_coin_pair交易平台对货币对表
    IF NOT EXISTS(SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'trade_platform_coin_pair' AND INDEX_NAME = 'index_trade_platform_coin_pair_id')
    then
        ALTER TABLE trade_platform_coin_pair ADD INDEX index_trade_platform_coin_pair_id(trade_platform_id,coin_pair_id);
    END IF;

#     自选币模块
    IF NOT EXISTS(SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_choice' AND INDEX_NAME = 'index_trade_platform_api_bind_product_combo_id')
    then
        ALTER TABLE coin_pair_choice ADD INDEX index_trade_platform_api_bind_product_combo_id(trade_platform_api_bind_product_combo_id);
    END IF;

    IF NOT EXISTS(SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_choice_attribute' AND INDEX_NAME = 'index_attribute_coin_pair_choice_id')
    then
        ALTER TABLE coin_pair_choice_attribute ADD INDEX index_attribute_coin_pair_choice_id(coin_partner_choice_id);
    END IF;

    IF NOT EXISTS(SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = CurrentDatabase AND TABLE_NAME = 'coin_pair_choice_attribute_custom' AND INDEX_NAME = 'index_attribute_custom_coin_pair_choice_id')
    then
        ALTER TABLE coin_pair_choice_attribute_custom ADD INDEX index_attribute_custom_coin_pair_choice_id(coin_partner_choice_id);
    END IF;
end //
DELIMITER ;
CALL schema_change();
DROP PROCEDURE IF EXISTS schema_change;