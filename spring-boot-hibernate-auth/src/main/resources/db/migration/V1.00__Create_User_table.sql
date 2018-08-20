CREATE TABLE `myauthdb`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `user_status` VARCHAR(45) NOT NULL,
  `last_login` DATETIME NULL,
  `wrong_password_attempt` INT NULL,
  `last_wrong_password_attempt` DATETIME NULL,
  `creation_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE);
