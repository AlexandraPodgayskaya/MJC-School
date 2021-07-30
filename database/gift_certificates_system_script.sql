-- MySQL Script generated by MySQL Workbench
-- Sat Jun 26 09:42:29 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gift_certificates_system
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gift_certificates_system
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gift_certificates_system` DEFAULT CHARACTER SET utf8 ;
USE `gift_certificates_system` ;

-- -----------------------------------------------------
-- Table `gift_certificates_system`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`gift_certificate` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `duration` INT NOT NULL,
  `create_date` DATETIME NOT NULL,
  `last_update_date` DATETIME NOT NULL,
  `deleted` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificates_system`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `deleted` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificates_system`.`gift_certificate_tag_connection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`gift_certificate_tag_connection` (
  `gift_certificate_id` BIGINT NOT NULL,
  `tag_id` BIGINT NOT NULL,
  PRIMARY KEY (`gift_certificate_id`, `tag_id`),
  INDEX `fk_gift_certificate_has_tag_tag1_idx` (`tag_id` ASC) VISIBLE,
  INDEX `fk_gift_certificate_has_tag_gift_certificate_idx` (`gift_certificate_id` ASC) VISIBLE,
  CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate`
    FOREIGN KEY (`gift_certificate_id`)
    REFERENCES `gift_certificates_system`.`gift_certificate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gift_certificate_has_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `gift_certificates_system`.`tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificates_system`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(45) NULL,
  `password` VARCHAR(60) NULL,
  `role` ENUM('USER', 'ADMIN') NULL,
  `deleted` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `gift_certificates_system`.`item_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`item_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cost` DECIMAL(10,2) NOT NULL,
  `user_id` BIGINT NOT NULL,
  `create_date` DATETIME NOT NULL,
  `deleted` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id`),
  INDEX `fk_order_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `gift_certificates_system`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificates_system`.`ordered_gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`ordered_gift_certificate` (
  `order_id` BIGINT NOT NULL,
  `gift_certificate_id` BIGINT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `duration` INT NOT NULL,
  `create_date` DATETIME NOT NULL,
  `last_update_date` DATETIME NOT NULL,
  `number` INT NOT NULL,
  PRIMARY KEY (`order_id`, `gift_certificate_id`),
  INDEX `fk_gift_certificate_has_order_order1_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_gift_certificate_has_order_gift_certificate1_idx` (`gift_certificate_id` ASC) VISIBLE,
  CONSTRAINT `fk_gift_certificate_has_order_gift_certificate1`
    FOREIGN KEY (`gift_certificate_id`)
    REFERENCES `gift_certificates_system`.`gift_certificate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gift_certificate_has_order_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `gift_certificates_system`.`item_order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `gift_certificates_system`.`gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('1', 'Excursion', 'Excursion immersion in the gentry culture with a lunch of national dishes', '2999.99', '5', '2018-11-18T17:33:42.156.', '2018-11-20T17:33:42.156.');
INSERT INTO `gift_certificates_system`.`gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('2', 'Diving with dolphins', 'Enjoy the underwater world and its inhabitants', '1500', '1', '2020-03-20 16:34:49', '2020-05-20 16:34:49');
INSERT INTO `gift_certificates_system`.`gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('3', 'Horse ride', 'Horseback ride for lovers - a Hollywood-style date', '500', '2', '2017-05-22 12:46:31', '2020-03-20 16:34:49');
INSERT INTO `gift_certificates_system`.`tag` (`id`, `name`) VALUES ('1', 'travel');
INSERT INTO `gift_certificates_system`.`tag` (`id`, `name`) VALUES ('2', 'gift');
INSERT INTO `gift_certificates_system`.`gift_certificate_tag_connection` (`gift_certificate_id`, `tag_id`) VALUES ('1', '1');
INSERT INTO `gift_certificates_system`.`gift_certificate_tag_connection` (`gift_certificate_id`, `tag_id`) VALUES ('1', '2');
INSERT INTO `gift_certificates_system`.`gift_certificate_tag_connection` (`gift_certificate_id`, `tag_id`) VALUES ('2', '2');

INSERT INTO `gift_certificates_system`.`user` (`id`, `name`) VALUES ('1', 'Alex');
INSERT INTO `gift_certificates_system`.`user` (`id`, `name`) VALUES ('2', 'Olga');
INSERT INTO `gift_certificates_system`.`user` (`id`, `name`) VALUES ('3', 'Masha');
INSERT INTO `gift_certificates_system`.`user` (`id`, `name`) VALUES ('4', 'Pavel');