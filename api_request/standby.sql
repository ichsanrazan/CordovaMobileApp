-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 17, 2022 at 04:39 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.3.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `standby`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `division` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `name`, `division`) VALUES
(1, 'Wahyu Sulaksono', 'Datacomm'),
(2, 'Bruury SR Effendy', 'Datacomm'),
(3, 'Hasian SM Hutagalung', 'Datacomm'),
(4, 'Erlangga Ervansyah', 'Datacomm'),
(5, 'Gunawan', 'Datacomm'),
(6, 'Rahmat Jaelani', 'Core'),
(7, 'Mohamad Habibi', 'Core'),
(8, 'Chandra Eka Kurniawan', 'Core'),
(9, 'Wildan Khalidy', 'Core');

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `date_from` date NOT NULL,
  `pic_from` varchar(150) NOT NULL,
  `date_to` date NOT NULL,
  `pic_to` varchar(150) NOT NULL,
  `status` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `request`
--

INSERT INTO `request` (`id`, `date_from`, `pic_from`, `date_to`, `pic_to`, `status`) VALUES
(1, '2022-05-15', 'Muhammad Ichsan Razan', '2022-05-20', 'Kaleb Juliu', '0'),
(2, '2022-05-16', 'Daffa Tsany', '2022-05-21', 'Muhammad Aldhi', '0'),
(4, '2022-05-16', 'Jokowi', '2022-05-17', 'Prabowo', '0'),
(5, '2022-05-13', 'Kaleb Juliu', '2022-05-14', 'Prabowo', '0'),
(6, '2022-05-13', 'Muhammad Ichsan', '2022-05-14', 'Ikram', '0');

-- --------------------------------------------------------

--
-- Table structure for table `schedule`
--

CREATE TABLE `schedule` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `pic` varchar(150) NOT NULL,
  `division` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `schedule`
--

INSERT INTO `schedule` (`id`, `date`, `pic`, `division`) VALUES
(1, '2022-04-01', 'Rahmat Jaelani', 'Core'),
(2, '2022-04-02', 'Mohamad Habibi', 'Core'),
(3, '2022-04-03', 'Chandra Eka Kurniawan', 'Core'),
(4, '2022-04-04', 'Wildan Khalidy', 'Core'),
(5, '2022-04-05', 'Rahmat Jaelani', 'Core'),
(6, '2022-04-06', 'Mohamad Habibi', 'Core'),
(7, '2022-04-07', 'Chandra Eka Kurniawan', 'Core'),
(8, '2022-04-08', 'Wildan Khalidy', 'Core'),
(9, '2022-04-09', 'Rahmat Jaelani', 'Core'),
(10, '2022-04-10', 'Mohamad Habibi', 'Core'),
(11, '2022-04-11', 'Chandra Eka Kurniawan', 'Core'),
(12, '2022-04-12', 'Wildan Khalidy', 'Core'),
(13, '2022-04-13', 'Rahmat Jaelani', 'Core'),
(14, '2022-04-14', 'Mohamad Habibi', 'Core'),
(15, '2022-04-15', 'Chandra Eka Kurniawan', 'Core'),
(16, '2022-04-16', 'Wildan Khalidy', 'Core'),
(17, '2022-04-17', 'Rahmat Jaelani', 'Core'),
(18, '2022-04-18', 'Mohamad Habibi', 'Core'),
(19, '2022-04-19', 'Chandra Eka Kurniawan', 'Core'),
(20, '2022-04-20', 'Wildan Khalidy', 'Core'),
(21, '2022-04-21', 'Rahmat Jaelani', 'Core'),
(22, '2022-04-22', 'Mohamad Habibi', 'Core'),
(23, '2022-04-23', 'Chandra Eka Kurniawan', 'Core'),
(24, '2022-04-24', 'Wildan Khalidy', 'Core'),
(25, '2022-04-25', 'Rahmat Jaelani', 'Core'),
(26, '2022-04-26', 'Mohamad Habibi', 'Core'),
(27, '2022-04-27', 'Chandra Eka Kurniawan', 'Core'),
(28, '2022-04-28', 'Wildan Khalidy', 'Core'),
(29, '2022-04-29', 'Rahmat Jaelani', 'Core'),
(30, '2022-04-30', 'Mohamad Habibi', 'Core'),
(31, '2022-04-01', 'Wahyu Sulaksono', 'Datacomm'),
(32, '2022-04-02', 'Bruury SR Effendy', 'Datacomm'),
(33, '2022-04-03', 'Hasian SM Hutagalung', 'Datacomm'),
(34, '2022-04-04', 'Erlangga Ervansyah', 'Datacomm'),
(35, '2022-04-05', 'Gunawan', 'Datacomm'),
(36, '2022-04-06', 'Wahyu Sulaksono', 'Datacomm'),
(37, '2022-04-07', 'Bruury SR Effendy', 'Datacomm'),
(38, '2022-04-08', 'Hasian SM Hutagalung', 'Datacomm'),
(39, '2022-04-09', 'Erlangga Ervansyah', 'Datacomm'),
(40, '2022-04-10', 'Gunawan', 'Datacomm'),
(41, '2022-04-11', 'Wahyu Sulaksono', 'Datacomm'),
(42, '2022-04-12', 'Bruury SR Effendy', 'Datacomm'),
(43, '2022-04-13', 'Hasian SM Hutagalung', 'Datacomm'),
(44, '2022-04-14', 'Erlangga Ervansyah', 'Datacomm'),
(45, '2022-04-15', 'Gunawan', 'Datacomm'),
(46, '2022-04-16', 'Wahyu Sulaksono', 'Datacomm'),
(47, '2022-04-17', 'Bruury SR Effendy', 'Datacomm'),
(48, '2022-04-18', 'Hasian SM Hutagalung', 'Datacomm'),
(49, '2022-04-19', 'Erlangga Ervansyah', 'Datacomm'),
(50, '2022-04-20', 'Gunawan', 'Datacomm'),
(51, '2022-04-21', 'Wahyu Sulaksono', 'Datacomm'),
(52, '2022-04-22', 'Bruury SR Effendy', 'Datacomm'),
(53, '2022-04-23', 'Hasian SM Hutagalung', 'Datacomm'),
(54, '2022-04-24', 'Erlangga Ervansyah', 'Datacomm'),
(55, '2022-04-25', 'Gunawan', 'Datacomm'),
(56, '2022-04-26', 'Wahyu Sulaksono', 'Datacomm'),
(57, '2022-04-27', 'Bruury SR Effendy', 'Datacomm'),
(58, '2022-04-28', 'Hasian SM Hutagalung', 'Datacomm'),
(59, '2022-04-29', 'Erlangga Ervansyah', 'Datacomm'),
(60, '2022-04-30', 'Gunawan', 'Datacomm');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `request`
--
ALTER TABLE `request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `schedule`
--
ALTER TABLE `schedule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
