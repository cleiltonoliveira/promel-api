create view vw_honey_production_hive as

select hp.id as honey_production_id, q.hive_id

from honey_production hp

inner join (select h.id as hive_id, uc.association_id from hive h

inner join production_unit pu on h.production_unit_id = pu.id

inner join user_account uc on uc.id = pu.user_account_id ) q on q.association_id = hp.association_id;