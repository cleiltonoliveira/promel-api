ALTER TABLE `promel`.`user_auth`
DROP FOREIGN KEY `fk_user_auth_role1`;
ALTER TABLE `promel`.`user_auth`
DROP COLUMN `role_id`,
DROP INDEX `fk_user_auth_role1_idx` ;
;

CREATE TABLE IF NOT EXISTS `promel`.`user_auth_has_role` (
    `user_auth_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`user_auth_id`, `role_id`),
    INDEX `fk_user_auth_has_role_role1_idx` (`role_id` ASC) VISIBLE,
    INDEX `fk_user_auth_has_role_user_auth1_idx` (`user_auth_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_auth_has_role_user_auth1`
    FOREIGN KEY (`user_auth_id`)
    REFERENCES `promel`.`user_auth` (`id`),
    CONSTRAINT `fk_user_auth_has_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `promel`.`role` (`id`)
    )
    ENGINE = InnoDB;