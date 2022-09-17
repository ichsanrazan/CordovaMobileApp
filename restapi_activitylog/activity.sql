-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2022 at 08:53 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `activity`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity_list`
--

CREATE TABLE `activity_list` (
  `activity_id` int(11) NOT NULL,
  `crq_date` varchar(255) DEFAULT NULL,
  `crq_subject` varchar(20) DEFAULT NULL,
  `pic_reporter` varchar(50) DEFAULT NULL,
  `category` varchar(20) DEFAULT NULL,
  `crq_no` varchar(20) DEFAULT NULL,
  `crq_activity` text DEFAULT NULL,
  `crq_serviceimp` text DEFAULT NULL,
  `crq_pic` text DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `activity_list`
--

INSERT INTO `activity_list` (`activity_id`, `crq_date`, `crq_subject`, `pic_reporter`, `category`, `crq_no`, `crq_activity`, `crq_serviceimp`, `crq_pic`, `owner`) VALUES
(2, '01-01-2022', 'Core CS', 'Rahmat Jaelani', 'INC/IM Troubleshoot', 'INC#123457', 'Troubleshoot Title', NULL, '', ''),
(3, '01-01-2022', 'Core PS', 'Rahmat Jaelani', 'INC/IM Troubleshoot', 'INC#123454', 'Troubleshoot 2', NULL, '', ''),
(4, '01-01-2022', 'Core CS', 'Bruury SR Efendy', 'HQ Project', 'CRQ#123459', 'Name', NULL, '', ''),
(5, '02-01-2022', 'Security', 'Rahmat Jaelani', 'HQ Project', 'CRQ#121212', 'Security Activity', NULL, 'TEST|Nama Saya Budi|0811123456,TEST|Nama Saya Budi|0811123456,TEST|Nama Saya Ani|0811123321,TEST|Nama Saya Cita|0811123333,', ''),
(6, '02-01-2022', 'Datacomm', 'Mohamad Habibi', 'Add/Upgrade Resource', 'CRQ#212121', 'Datacomm Upgrade', NULL, 'TEST|Nama Saya Ani|0811123321,', ''),
(7, '03-01-2022', 'Core CS', 'Chandra Eka Kurniawan', 'Corrective/Preventiv', 'CRQ#123456', 'Preventive Activity', 'Description', 'TEST|Nama Saya Cita|0811123333,', ''),
(8, '21-03-2022', 'Core CS', 'Mohamad Habibi', 'INC/IM Troubleshoot', 'INC#124151', 'Troubleshooting', '', '', ''),
(9, '02-01-2022', 'Datacomm', 'Wahyu Sulaksono', 'INC/IM Troubleshoot', 'INC#5113151', 'Title', '', '', ''),
(11, '21-03-2022', 'Core PS', 'Mohamad Habibi', 'Reconfiguration', 'CRQ#124113', 'adawodn', 'adergg', '', ''),
(12, '21-03-2022', 'Core PS', 'Bruury SR Efendy', 'Corrective/Preventiv', 'CRQ#123411', 'fwefwef', '', '', ''),
(13, '21-03-2022', 'Core CS', 'Bruury SR Efendy', 'INC/IM Troubleshoot', 'INC#123413', 'afaw', '', '', ''),
(14, '21-03-2022', 'Datacomm', 'ichsan', 'Corrective/Preventiv', 'CRQ#123231', 'fqfqwdqwd', '', '', ''),
(15, '30-03-2022', 'Datacomm', 'ichsan', 'INC/IM Troubleshoot', 'INC#690690422313', 'TEST TROUBLESHOOT', '', '', 'ichsan'),
(25, '30-03-2022', 'Core PS', 'ichsan', 'HQ Project', 'CRQ#940579000000', 'TEST ACTIVITY 1', 'DESKRIPSI UPDATED LAGI', 'TEST|Nama Saya Budi|0811123456,TEST|Nama Saya Ani|0811123321,TEST|Nama Saya Cita|0811123333,TEST|Nama Saya Budi|0811123456,TESTING|Kaleb Lagi|922,', 'ichsan');

-- --------------------------------------------------------

--
-- Table structure for table `activity_pic`
--

CREATE TABLE `activity_pic` (
  `id` int(10) NOT NULL,
  `company` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `activity_pic`
--

INSERT INTO `activity_pic` (`id`, `company`, `full_name`, `phone_number`) VALUES
(1, 'TEST', 'Nama Saya Budi', '0811123456'),
(2, 'TEST', 'Nama Saya Ani', '0811123321'),
(3, 'TEST', 'Nama Saya Cita', '0811123333'),
(118, 'TELKOMSEL', 'Kaleb Juliu', '911'),
(119, 'TESTING', 'Kaleb Lagi', '922'),
(120, 'TELKOMSEL', 'John Hammond', '123'),
(121, 'Amazon', 'Jeremy Clarkson', '944'),
(122, 'TELKOMSEL', 'Jim Halpert', '01938540');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity_list`
--
ALTER TABLE `activity_list`
  ADD PRIMARY KEY (`activity_id`);

--
-- Indexes for table `activity_pic`
--
ALTER TABLE `activity_pic`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity_list`
--
ALTER TABLE `activity_list`
  MODIFY `activity_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `activity_pic`
--
ALTER TABLE `activity_pic`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=125;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
