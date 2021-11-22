ALTER TABLE `honey_production`
DROP COLUMN `production_status_id`,
DROP INDEX `fk_harvest_production_status1_idx` ;

DROP TABLE `production_status`;