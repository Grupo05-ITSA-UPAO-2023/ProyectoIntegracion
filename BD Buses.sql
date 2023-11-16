SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Table `Pasajero`
CREATE TABLE IF NOT EXISTS `Pasajero` (
  `Id_Pasajero` INT NOT NULL AUTO_INCREMENT,
  `Dni` INT(8) NOT NULL,
  `Nombre` VARCHAR(40) NOT NULL,
  `ApellidoPaterno` VARCHAR(45) NOT NULL,
  `ApellidoMaterno` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id_Pasajero`)
) ENGINE = InnoDB;

CREATE UNIQUE INDEX `Dni_UNIQUE` ON `Pasajero` (`Dni` ASC) VISIBLE;

-- Table `Bus`
CREATE TABLE IF NOT EXISTS `Bus` (
  `Id_Bus` INT NOT NULL AUTO_INCREMENT,
  `Num_Placa` VARCHAR(6) NOT NULL,
  `Marca` VARCHAR(20) NOT NULL,
  `Capacidad` INT(2) NOT NULL,
  `Pisos` INT(1) NOT NULL,
  PRIMARY KEY (`Id_Bus`)
) ENGINE = InnoDB;

CREATE UNIQUE INDEX `Num_Placa_UNIQUE` ON `Bus` (`Num_Placa` ASC) VISIBLE;

-- Table `Asiento`
CREATE TABLE IF NOT EXISTS `Asiento` (
  `Id_Asiento` INT NOT NULL AUTO_INCREMENT,
  `Asiento` INT(2) NOT NULL,
  `Estado` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Id_Asiento`)
) ENGINE = InnoDB;

-- Table `Ruta`
CREATE TABLE IF NOT EXISTS `Ruta` (
  `Id_Ruta` INT NOT NULL AUTO_INCREMENT,
  `Origen` VARCHAR(45) NOT NULL,
  `Destino` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id_Ruta`)
) ENGINE = InnoDB;

-- Table `Terminales`
CREATE TABLE IF NOT EXISTS `Terminales` (
  `Id_Terminales` INT NOT NULL AUTO_INCREMENT,
  `Embarque` VARCHAR(50) NOT NULL,
  `Desembarque` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`Id_Terminales`)
) ENGINE = InnoDB;

-- Table `Programacion`
CREATE TABLE IF NOT EXISTS `Programacion` (
  `Id_Programacion` INT NOT NULL AUTO_INCREMENT,
  `Hora_Salida` TIME NOT NULL,
  `Terminal` VARCHAR(45) NOT NULL,
  `Servicio` VARCHAR(45) NOT NULL,
  `Precio` DECIMAL(5,2) NOT NULL,
  `Ruta_Id_Ruta` INT NOT NULL,
  `Bus_Id_Bus` INT NOT NULL,
  `Terminales_Id_Terminales` INT NOT NULL,
  PRIMARY KEY (`Id_Programacion`, `Ruta_Id_Ruta`, `Bus_Id_Bus`, `Terminales_Id_Terminales`),
  CONSTRAINT `fk_Programacion_Ruta1`
    FOREIGN KEY (`Ruta_Id_Ruta`)
    REFERENCES `Ruta` (`Id_Ruta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Programacion_Bus1`
    FOREIGN KEY (`Bus_Id_Bus`)
    REFERENCES `Bus` (`Id_Bus`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Programacion_Terminales1`
    FOREIGN KEY (`Terminales_Id_Terminales`)
    REFERENCES `Terminales` (`Id_Terminales`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `fk_Programacion_Ruta1_idx` ON `Programacion` (`Ruta_Id_Ruta` ASC) VISIBLE;
CREATE INDEX `fk_Programacion_Bus1_idx` ON `Programacion` (`Bus_Id_Bus` ASC) VISIBLE;
CREATE INDEX `fk_Programacion_Terminales1_idx` ON `Programacion` (`Terminales_Id_Terminales` ASC) VISIBLE;

-- Table `Reserva`
CREATE TABLE IF NOT EXISTS `Reserva` (
  `Id_Reserva` INT NOT NULL AUTO_INCREMENT,
  `Fecha_Ida` DATE NOT NULL,
  `Fecha_Vuelta` DATE NULL,
  `Asiento_Id_Asiento` INT NOT NULL,
  `Pasajero_Id_Pasajero` INT NOT NULL,
  `Programacion_Id_Programacion` INT NOT NULL,
  PRIMARY KEY (`Id_Reserva`, `Asiento_Id_Asiento`, `Pasajero_Id_Pasajero`, `Programacion_Id_Programacion`),
  CONSTRAINT `fk_Reserva_Asiento`
    FOREIGN KEY (`Asiento_Id_Asiento`)
    REFERENCES `Asiento` (`Id_Asiento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reserva_Pasajero1`
    FOREIGN KEY (`Pasajero_Id_Pasajero`)
    REFERENCES `Pasajero` (`Id_Pasajero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reserva_Programacion1`
    FOREIGN KEY (`Programacion_Id_Programacion`)
    REFERENCES `Programacion` (`Id_Programacion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `fk_Reserva_Asiento_idx` ON `Reserva` (`Asiento_Id_Asiento` ASC) VISIBLE;
CREATE INDEX `fk_Reserva_Pasajero1_idx` ON `Reserva` (`Pasajero_Id_Pasajero` ASC) VISIBLE;
CREATE INDEX `fk_Reserva_Programacion1_idx` ON `Reserva` (`Programacion_Id_Programacion` ASC) VISIBLE;

-- Table `Pago`
CREATE TABLE IF NOT EXISTS `Pago` (
  `Id_Pago` INT NOT NULL AUTO_INCREMENT,
  `Fecha_Pago` DATE NOT NULL,
  `Monto_Total` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`Id_Pago`)
) ENGINE = InnoDB;

-- Table `Venta_Boleto`
CREATE TABLE IF NOT EXISTS `Venta_Boleto` (
  `Id_VentaBoleto` INT NOT NULL AUTO_INCREMENT,
  `Fecha_Emision` DATE NOT NULL,
  `Hora_Emision` TIME NOT NULL,
  `Pago_Id_Pago` INT NOT NULL,
  PRIMARY KEY (`Id_VentaBoleto`, `Pago_Id_Pago`),
  CONSTRAINT `fk_Venta_Boleto_Pago1`
    FOREIGN KEY (`Pago_Id_Pago`)
    REFERENCES `Pago` (`Id_Pago`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `fk_Venta_Boleto_Pago1_idx` ON `Venta_Boleto` (`Pago_Id_Pago` ASC) VISIBLE;

-- Table `Detalle_Pago`
CREATE TABLE IF NOT EXISTS `Detalle_Pago` (
  `Id_DetallePago` INT NOT NULL AUTO_INCREMENT,
  `Tipo_Pago` VARCHAR(20) NOT NULL,
  `IGV` DECIMAL(4,2) NOT NULL,
  `SubTotal` DECIMAL(4,2) NOT NULL,
  `Reserva_Id_Reserva` INT NOT NULL,
  `Reserva_Asiento_Id_Asiento` INT NOT NULL,
  `Reserva_Pasajero_Id_Pasajero` INT NOT NULL,
  `Reserva_Programacion_Id_Programacion` INT NOT NULL,
  `Pago_Id_Pago` INT NOT NULL,
  PRIMARY KEY (`Id_DetallePago`, `Reserva_Id_Reserva`, `Reserva_Asiento_Id_Asiento`, `Reserva_Pasajero_Id_Pasajero`, `Reserva_Programacion_Id_Programacion`, `Pago_Id_Pago`),
  CONSTRAINT `fk_Detalle_Pago_Reserva1`
    FOREIGN KEY (`Reserva_Id_Reserva`, `Reserva_Asiento_Id_Asiento`, `Reserva_Pasajero_Id_Pasajero`, `Reserva_Programacion_Id_Programacion`)
    REFERENCES `Reserva` (`Id_Reserva`, `Asiento_Id_Asiento`, `Pasajero_Id_Pasajero`, `Programacion_Id_Programacion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Detalle_Pago_Pago1`
    FOREIGN KEY (`Pago_Id_Pago`)
    REFERENCES `Pago` (`Id_Pago`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `fk_Detalle_Pago_Reserva1_idx` ON `Detalle_Pago` (`Reserva_Id_Reserva` ASC, `Reserva_Asiento_Id_Asiento` ASC, `Reserva_Pasajero_Id_Pasajero` ASC, `Reserva_Programacion_Id_Programacion` ASC) VISIBLE;
CREATE INDEX `fk_Detalle_Pago_Pago1_idx` ON `Detalle_Pago` (`Pago_Id_Pago` ASC) VISIBLE;