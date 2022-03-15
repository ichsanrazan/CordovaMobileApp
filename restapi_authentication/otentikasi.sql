-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 15, 2022 at 07:46 AM
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
-- Database: `otentikasi`
--

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `password_hash` varchar(256) NOT NULL,
  `salt` varchar(256) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`user_id`, `username`, `full_name`, `password_hash`, `salt`, `created_date`) VALUES
(1, 'admin', 'ichsan', '$2y$10$gHLKAaCoNWsJ1FdUIKfnFurvrEgyx0zjxywsMyjwERRUfzbJ8po2m', '4ec32442d76d3dc65c44ea9a32b8611d336de50529879e6ec3a2ad66ec8bfa55', '2022-02-25 21:26:24'),
(2, 'ichsanrazan', 'ichsan razan', '$2y$10$HBYkQdKseGkCEvvGr2pHCO6b5uTN6tVSVo9BO2BIIPwtQAtZhXr0e', '742a8d4cd1185dd78ca79364026824fc743204f221f4dd90b7a0e7814ef29574', '2022-03-01 10:01:47'),
(3, 'telkomsel', 'telkomsel', '$2y$10$dTxVscVQGaM3NTHo4eYEWeoav/C7nzwqG2tafYevq.FXXC/JKVIOK', '9423cbb79bc63121bd98cdcf89c4fd466033d7643ba48aeb4a488d4edf51463f', '2022-03-01 10:05:29');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `member`
--
ALTER TABLE `member`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
