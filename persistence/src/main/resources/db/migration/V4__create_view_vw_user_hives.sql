create view vw_user_hives as select hive.*, unit.user_account_id from production_unit unit
inner join hive hive on unit.id = hive.production_unit_id;