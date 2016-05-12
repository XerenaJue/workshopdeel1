-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema workshopdeel1
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema workshopdeel1
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `workshopdeel1` DEFAULT CHARACTER SET utf8 ;
USE `workshopdeel1` ;

-- -----------------------------------------------------
-- Table `workshopdeel1`.`klant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshopdeel1`.`klant` (
  `klant_id` INT(11) NOT NULL AUTO_INCREMENT,
  `voornaam` VARCHAR(50) NOT NULL,
  `achternaam` VARCHAR(51) NOT NULL,
  `tussenvoegsel` VARCHAR(12) NULL DEFAULT NULL,
  `email` VARCHAR(80) NULL DEFAULT NULL,
  `straatnaam` VARCHAR(26) NULL,
  `postcode` VARCHAR(6) NULL,
  `toevoeging` VARCHAR(6) NULL DEFAULT NULL,
  `huisnummer` INT(5) NOT NULL,
  `woonplaats` VARCHAR(26) NOT NULL,
  PRIMARY KEY (`klant_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `workshopdeel1`.`bestelling`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshopdeel1`.`bestelling` (
  `bestelling_id` INT(11) NOT NULL AUTO_INCREMENT,
  `klant_id` INT(11) NOT NULL,
  `artikel_id` INT(11) NULL DEFAULT NULL,
  `artikel_naam` VARCHAR(25) NULL DEFAULT NULL,
  `artikel_aantal` INT(11) NULL DEFAULT NULL,
  `artikel_prijs` INT(11) NULL DEFAULT NULL,
  `artikel2_id` INT(11) NULL DEFAULT NULL,
  `artikel2_naam` VARCHAR(25) NULL DEFAULT NULL,
  `artikel2_aantal` INT(11) NULL DEFAULT NULL,
  `artikel2_prijs` INT(11) NULL DEFAULT NULL,
  `artikel3_id` INT(11) NULL DEFAULT NULL,
  `artikel3_naam` VARCHAR(25) NULL DEFAULT NULL,
  `artikel3_aantal` INT(11) NULL DEFAULT NULL,
  `artikel3_prijs` INT(11) NULL DEFAULT NULL,
  INDEX `klant_id_idx` (`klant_id` ASC),
  PRIMARY KEY (`bestelling_id`),
  UNIQUE INDEX `bestelling_id_UNIQUE` (`bestelling_id` ASC),
  CONSTRAINT `klant_id`
    FOREIGN KEY (`klant_id`)
    REFERENCES `workshopdeel1`.`klant` (`klant_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE USER 'hallo' IDENTIFIED BY 'doei';

GRANT SELECT, INSERT, TRIGGER ON TABLE `mydb`.* TO 'hallo';
GRANT SELECT, INSERT, TRIGGER ON TABLE `workshopdeel1`.* TO 'hallo';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `mydb`.* TO 'hallo';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `workshopdeel1`.* TO 'hallo';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
