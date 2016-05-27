-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema workshopdeel1
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema workshopdeel1
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `workshopdeel1` DEFAULT CHARACTER SET utf8 ;
USE `workshopdeel1` ;

-- -----------------------------------------------------
-- Table `workshopdeel1`.`adres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshopdeel1`.`adres` (
  `adres_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `straatnaam` VARCHAR(26) NOT NULL,
  `postcode` VARCHAR(6) NULL DEFAULT NULL,
  `toevoeging` VARCHAR(6) NULL DEFAULT NULL,
  `huisnummer` INT(5) NOT NULL,
  `woonplaats` VARCHAR(26) NOT NULL,
  PRIMARY KEY (`adres_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `workshopdeel1`.`artikel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshopdeel1`.`artikel` (
  `artikel_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `artikel_naam` VARCHAR(25) NULL DEFAULT NULL,
  `artikel_prijs` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`artikel_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `workshopdeel1`.`klant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshopdeel1`.`klant` (
  `klant_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `voornaam` VARCHAR(50) NOT NULL,
  `achternaam` VARCHAR(51) NOT NULL,
  `tussenvoegsel` VARCHAR(12) NULL DEFAULT NULL,
  `email` VARCHAR(80) NULL DEFAULT NULL,
  PRIMARY KEY (`klant_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `workshopdeel1`.`bestelling`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshopdeel1`.`bestelling` (
  `bestelling_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `klant_klant_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`bestelling_id`),
  INDEX `fk_bestelling_1_idx` (`klant_klant_id` ASC),
  CONSTRAINT `fk_bestelling_1`
    FOREIGN KEY (`klant_klant_id`)
    REFERENCES `workshopdeel1`.`klant` (`klant_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `workshopdeel1`.`bestelling_has_artikel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshopdeel1`.`bestelling_has_artikel` (
  `bestelling_bestelling_id` INT(10) UNSIGNED NOT NULL,
  `artikel_artikel_id` INT(10) UNSIGNED NOT NULL,
  `aantal_artikelen` INT(3) NULL DEFAULT NULL,
  PRIMARY KEY (`bestelling_bestelling_id`, `artikel_artikel_id`),
  INDEX `fk_bestelling_has_artikel_artikel1_idx` (`artikel_artikel_id` ASC),
  INDEX `fk_bestelling_has_artikel_bestelling_idx` (`bestelling_bestelling_id` ASC),
  CONSTRAINT `fk_bestelling_has_artikel_artikel1`
    FOREIGN KEY (`artikel_artikel_id`)
    REFERENCES `workshopdeel1`.`artikel` (`artikel_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bestelling_has_artikel_bestelling`
    FOREIGN KEY (`bestelling_bestelling_id`)
    REFERENCES `workshopdeel1`.`bestelling` (`bestelling_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `workshopdeel1`.`klant_has_adres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshopdeel1`.`klant_has_adres` (
  `klant_klant_id` INT(10) UNSIGNED NOT NULL,
  `adres_adres_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`klant_klant_id`, `adres_adres_id`),
  INDEX `fk_klant_has_adres_adres1_idx` (`adres_adres_id` ASC),
  INDEX `fk_klant_has_adres_klant1_idx` (`klant_klant_id` ASC),
  CONSTRAINT `fk_klant_has_adres_adres1`
    FOREIGN KEY (`adres_adres_id`)
    REFERENCES `workshopdeel1`.`adres` (`adres_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_klant_has_adres_klant1`
    FOREIGN KEY (`klant_klant_id`)
    REFERENCES `workshopdeel1`.`klant` (`klant_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
