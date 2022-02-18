/*
SQLyog Ultimate v12.5.1 (64 bit)
MySQL - 10.4.11-MariaDB : Database - activity
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`activity` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `activity`;

/*Table structure for table `activity_list` */

DROP TABLE IF EXISTS `activity_list`;

CREATE TABLE `activity_list` (
  `crq_date` varchar(255) DEFAULT NULL,
  `crq_subject` varchar(20) DEFAULT NULL,
  `pic_reporter` varchar(50) DEFAULT NULL,
  `category` varchar(20) DEFAULT NULL,
  `crq_no` varchar(20) DEFAULT NULL,
  `crq_activity` text DEFAULT NULL,
  `crq_serviceimp` text DEFAULT NULL,
  `crq_pic` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `activity_list` */

insert  into `activity_list`(`crq_date`,`crq_subject`,`pic_reporter`,`category`,`crq_no`,`crq_activity`,`crq_serviceimp`,`crq_pic`) values 
('2021-01-11','datacomm','Hasian','PROJECT','CRQ#708338','Cat-tbs_Reengineering_Migration_Deployment Day 3','null',''),
('2021-01-11','datacomm','Hasian','CORRECTIVE_PREVENTIV','CRQ#708470','Air Filter Cleaning sr-meruya.1&2','null',''),
('2021-01-11','datacomm','Hasian','PROJECT','CRQ#709209','Migration ACS to ISE F5 Site BRN and TBS','null','');

/*Table structure for table `activity_pic` */

DROP TABLE IF EXISTS `activity_pic`;

CREATE TABLE `activity_pic` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `company` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=latin1;

/*Data for the table `activity_pic` */

insert  into `activity_pic`(`id`,`company`,`full_name`,`phone_number`) values 
(1,'TEST','Nama Saya Budi','0811123456'),
(2,'TEST','Nama Saya Ani','0811123321'),
(3,'TEST','Nama Saya Cita','0811123333');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
