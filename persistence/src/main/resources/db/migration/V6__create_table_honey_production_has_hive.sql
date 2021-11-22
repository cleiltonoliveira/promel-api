CREATE TABLE IF NOT EXISTS `honey_production_has_hive` (
   `honey_production_id` INT NOT NULL,
   `hive_id` INT NOT NULL,
   `number_of_extracted_frames` INT NOT NULL,
   `parcial_production` DECIMAL(6,4) NOT NULL DEFAULT 0.0,
    PRIMARY KEY (`honey_production_id`, `hive_id`),
    INDEX `fk_honey_production_has_hive_hive1_idx` (`hive_id` ASC) VISIBLE,
    INDEX `fk_honey_production_has_hive_honey_production1_idx` (`honey_production_id` ASC) VISIBLE,
    CONSTRAINT `fk_honey_production_has_hive_honey_production1`
        FOREIGN KEY (`honey_production_id`)
        REFERENCES `honey_production` (`id`)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    CONSTRAINT `fk_honey_production_has_hive_hive1`
        FOREIGN KEY (`hive_id`)
        REFERENCES `hive` (`id`)
        ON DELETE CASCADE
        ON UPDATE NO ACTION)
ENGINE = InnoDB;
