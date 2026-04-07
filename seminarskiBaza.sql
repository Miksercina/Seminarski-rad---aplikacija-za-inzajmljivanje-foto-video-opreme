/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 10.4.28-MariaDB : Database - seminarski_mihajlo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`seminarski_mihajlo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `seminarski_mihajlo`;

/*Table structure for table `brend` */

DROP TABLE IF EXISTS `brend`;

CREATE TABLE `brend` (
  `idBrend` bigint(255) unsigned NOT NULL AUTO_INCREMENT,
  `nazivOsiguravajuceKuce` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `brojTelefona` varchar(100) NOT NULL,
  PRIMARY KEY (`idBrend`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `brend` */

insert  into `brend`(`idBrend`,`nazivOsiguravajuceKuce`,`email`,`brojTelefona`) values 
(1,'Dunav osiguranje','dunavo@gmail.com','0600000000'),
(2,'Kasko','kasko@gmail.com','0611111111'),
(3,'Grawe','grawe@gmail.com','0622222222'),
(4,'MLEKO','mleko@gmail.com','0694444444');

/*Table structure for table `grad` */

DROP TABLE IF EXISTS `grad`;

CREATE TABLE `grad` (
  `postanskiBroj` bigint(20) unsigned NOT NULL,
  `naziv` varchar(100) NOT NULL,
  `opstina` varchar(100) NOT NULL,
  PRIMARY KEY (`postanskiBroj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `grad` */

insert  into `grad`(`postanskiBroj`,`naziv`,`opstina`) values 
(11000,'Beograd','Beograd'),
(18000,'Niš','Niš'),
(21000,'Novi Sad','Novi Sad'),
(26000,'Pančevo','Pančevo'),
(34000,'Kragujevac','Kragujevac');

/*Table structure for table `oprema` */

DROP TABLE IF EXISTS `oprema`;

CREATE TABLE `oprema` (
  `idOprema` bigint(255) unsigned NOT NULL AUTO_INCREMENT,
  `nazivProizvodjaca` varchar(100) NOT NULL,
  `nazivProizvoda` varchar(255) NOT NULL,
  `kategorijaOpreme` enum('TELO_KAMERE','OBJEKTIV','STATIV','RASVETA','GIMBAL') NOT NULL,
  `cenaPoDanu` decimal(10,2) NOT NULL,
  `ukupnaKolicina` bigint(50) NOT NULL,
  `dostupnaKolicina` bigint(50) NOT NULL,
  PRIMARY KEY (`idOprema`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `oprema` */

insert  into `oprema`(`idOprema`,`nazivProizvodjaca`,`nazivProizvoda`,`kategorijaOpreme`,`cenaPoDanu`,`ukupnaKolicina`,`dostupnaKolicina`) values 
(1,'Sony','A7 IV','TELO_KAMERE',500.00,1,-1),
(2,'Sony','A6000','TELO_KAMERE',250.00,2,-1),
(4,'Canon','R7','TELO_KAMERE',600.00,1,-1),
(5,'Sigma','24-70mm','OBJEKTIV',350.00,5,0),
(6,'Peak Design','112233','STATIV',100.00,4,0),
(7,'Sony','a6700','TELO_KAMERE',450.00,6,-3),
(8,'Godox','flash2000','RASVETA',200.00,9,7),
(9,'Godox','flash1000','RASVETA',99.00,5,4),
(10,'Nikon','d750','TELO_KAMERE',650.00,8,4);

/*Table structure for table `stavkanajma` */

DROP TABLE IF EXISTS `stavkanajma`;

CREATE TABLE `stavkanajma` (
  `idUgovorONajmu` bigint(255) unsigned NOT NULL,
  `rb` bigint(255) unsigned NOT NULL,
  `idOprema` bigint(255) unsigned NOT NULL,
  `vremeIzdavanja` date NOT NULL,
  `vremeVracanja` date NOT NULL,
  `kolicina` bigint(100) unsigned NOT NULL,
  `cena` decimal(10,2) unsigned NOT NULL,
  PRIMARY KEY (`idUgovorONajmu`,`rb`),
  KEY `idOprema` (`idOprema`),
  CONSTRAINT `stavkanajma_ibfk_1` FOREIGN KEY (`idOprema`) REFERENCES `oprema` (`idOprema`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `stavkanajma` */

insert  into `stavkanajma`(`idUgovorONajmu`,`rb`,`idOprema`,`vremeIzdavanja`,`vremeVracanja`,`kolicina`,`cena`) values 
(1,1,1,'2026-03-13','2026-04-13',1,500.00),
(1,2,6,'2026-03-20','2026-04-20',2,3100.00),
(2,1,4,'2026-04-13','2026-04-16',1,1800.00),
(3,1,5,'2026-11-20','2026-11-25',1,1750.00),
(3,2,1,'2026-05-20','2026-05-25',1,2500.00),
(5,1,7,'2026-04-20','2026-05-20',2,13500.00),
(5,2,2,'2026-06-20','2027-07-20',1,98750.00),
(5,3,7,'2026-06-10','2026-07-10',2,13500.00);

/*Table structure for table `ugovoronajmu` */

DROP TABLE IF EXISTS `ugovoronajmu`;

CREATE TABLE `ugovoronajmu` (
  `idUgovorONajmu` bigint(255) unsigned NOT NULL AUTO_INCREMENT,
  `ukupnaCena` decimal(10,2) unsigned NOT NULL,
  `idZakupodavac` bigint(255) unsigned NOT NULL,
  `idZakupac` bigint(255) unsigned NOT NULL,
  PRIMARY KEY (`idUgovorONajmu`),
  KEY `idZakupodavac` (`idZakupodavac`),
  KEY `idZakupac` (`idZakupac`),
  CONSTRAINT `ugovoronajmu_ibfk_1` FOREIGN KEY (`idZakupodavac`) REFERENCES `zakupodavac` (`idZakupodavac`),
  CONSTRAINT `ugovoronajmu_ibfk_2` FOREIGN KEY (`idZakupac`) REFERENCES `zakupac` (`idZakupac`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `ugovoronajmu` */

insert  into `ugovoronajmu`(`idUgovorONajmu`,`ukupnaCena`,`idZakupodavac`,`idZakupac`) values 
(1,3600.00,1,3),
(2,1800.00,1,4),
(3,4250.00,1,4),
(4,200.00,1,4),
(5,125750.00,1,1);

/*Table structure for table `zabr` */

DROP TABLE IF EXISTS `zabr`;

CREATE TABLE `zabr` (
  `idZakupodavac` bigint(255) unsigned NOT NULL,
  `idBrend` bigint(255) unsigned NOT NULL,
  `datumPocetkaOsiguranja` date NOT NULL,
  `datumKrajaOsiguranja` date NOT NULL,
  `cena` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idZakupodavac`,`idBrend`),
  KEY `idBrend` (`idBrend`),
  CONSTRAINT `zabr_ibfk_1` FOREIGN KEY (`idZakupodavac`) REFERENCES `zakupodavac` (`idZakupodavac`),
  CONSTRAINT `zabr_ibfk_2` FOREIGN KEY (`idBrend`) REFERENCES `brend` (`idBrend`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `zabr` */

insert  into `zabr`(`idZakupodavac`,`idBrend`,`datumPocetkaOsiguranja`,`datumKrajaOsiguranja`,`cena`) values 
(1,1,'2026-03-13','2027-03-13',1000.00);

/*Table structure for table `zakupac` */

DROP TABLE IF EXISTS `zakupac`;

CREATE TABLE `zakupac` (
  `idZakupac` bigint(255) unsigned NOT NULL AUTO_INCREMENT,
  `ime` varchar(100) NOT NULL,
  `prezime` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `postanskiBroj` bigint(20) unsigned NOT NULL,
  `adresa` varchar(100) NOT NULL,
  `brojTelefona` varchar(100) NOT NULL,
  PRIMARY KEY (`idZakupac`),
  KEY `postanskiBroj` (`postanskiBroj`),
  CONSTRAINT `zakupac_ibfk_1` FOREIGN KEY (`postanskiBroj`) REFERENCES `grad` (`postanskiBroj`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `zakupac` */

insert  into `zakupac`(`idZakupac`,`ime`,`prezime`,`email`,`postanskiBroj`,`adresa`,`brojTelefona`) values 
(1,'Mara','Maric','marija@gmail.com',11000,'Savska 2','0601231231'),
(3,'Mileva','maric','mileva@gmail.com',26000,'Zetska 12','0691234567'),
(4,'Veljko','Veljkovic','veljko@gmail.com',21000,'Zarka Zrenjanina 111','0633333333'),
(7,'Milenko','Tepic','milenko@gmail.com',18000,'Savska 98','0655555555'),
(8,'Stojan','Stojanovic','stojan@gmail.com',34000,'Sibirska 123','0611112223');

/*Table structure for table `zakupodavac` */

DROP TABLE IF EXISTS `zakupodavac`;

CREATE TABLE `zakupodavac` (
  `idZakupodavac` bigint(255) unsigned NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  `adresa` varchar(100) NOT NULL,
  `korisnickoIme` varchar(100) NOT NULL,
  `sifra` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`idZakupodavac`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `zakupodavac` */

insert  into `zakupodavac`(`idZakupodavac`,`naziv`,`adresa`,`korisnickoIme`,`sifra`,`email`) values 
(1,'Mihajlo','duvanska 8','mixa','mixa','mixa@gmail.com'),
(2,'Marko','mirka klisure 1','mare','mare','mare@gmail.com'),
(3,'FotoDiskont','Carlija Caplina 16','milos','milos','milos@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
