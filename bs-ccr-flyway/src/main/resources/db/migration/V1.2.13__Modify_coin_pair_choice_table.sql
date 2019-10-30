DROP PROCEDURE IF EXISTS schema_change;
DELIMITER //
CREATE PROCEDURE schema_change()
begin
    declare  CurrentDatabase varchar(100);
    select DATABASE() into CurrentDatabase;
    if not exists(select 1 from information_schema.COLUMNS where TABLE_SCHEMA = CurrentDatabase and TABLE_NAME = 'coin_pair_choice' and COLUMN_NAME = 'trade_platform_api_bind_product_combo_id')
    then
        alter table coin_pair_choice add trade_platform_api_bind_product_combo_id int (11) NOT NULL default 0;
    end if;

end //
DELIMITER ;
CALL schema_change();
DROP PROCEDURE IF EXISTS schema_change;