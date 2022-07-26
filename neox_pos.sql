-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.26 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for neox_pos
DROP DATABASE IF EXISTS `neox_pos`;
CREATE DATABASE IF NOT EXISTS `neox_pos` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `neox_pos`;

-- Dumping structure for table neox_pos.brand
DROP TABLE IF EXISTS `brand`;
CREATE TABLE IF NOT EXISTS `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.brand: ~2 rows (approximately)
DELETE FROM `brand`;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` (`id`, `name`) VALUES
	(1, 'CBL'),
	(2, 'Ritsburry');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;

-- Dumping structure for table neox_pos.category
DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.category: ~2 rows (approximately)
DELETE FROM `category`;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`id`, `name`) VALUES
	(1, 'Biscuits'),
	(2, 'Chocolate');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- Dumping structure for table neox_pos.city
DROP TABLE IF EXISTS `city`;
CREATE TABLE IF NOT EXISTS `city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.city: ~0 rows (approximately)
DELETE FROM `city`;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` (`id`, `name`) VALUES
	(1, 'Kandy');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;

-- Dumping structure for table neox_pos.company
DROP TABLE IF EXISTS `company`;
CREATE TABLE IF NOT EXISTS `company` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `contact_number` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `contact_number_UNIQUE` (`contact_number`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.company: ~1 rows (approximately)
DELETE FROM `company`;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `name`, `contact_number`) VALUES
	(1, 'Ritsburry', '0767036495'),
	(2, 'CBL', '0768498448');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

-- Dumping structure for table neox_pos.company_branch
DROP TABLE IF EXISTS `company_branch`;
CREATE TABLE IF NOT EXISTS `company_branch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `branch_contact_number` varchar(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  `company_branch_address_id` int NOT NULL,
  `company_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_company_branch_company_branch_address1_idx` (`company_branch_address_id`),
  KEY `fk_company_branch_company1_idx` (`company_id`),
  CONSTRAINT `fk_company_branch_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_company_branch_company_branch_address1` FOREIGN KEY (`company_branch_address_id`) REFERENCES `company_branch_address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.company_branch: ~0 rows (approximately)
DELETE FROM `company_branch`;
/*!40000 ALTER TABLE `company_branch` DISABLE KEYS */;
INSERT INTO `company_branch` (`id`, `branch_contact_number`, `name`, `company_branch_address_id`, `company_id`) VALUES
	(1, '0812300885', 'Koswatta', 1, 1);
/*!40000 ALTER TABLE `company_branch` ENABLE KEYS */;

-- Dumping structure for table neox_pos.company_branch_address
DROP TABLE IF EXISTS `company_branch_address`;
CREATE TABLE IF NOT EXISTS `company_branch_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `line1` varchar(45) NOT NULL,
  `line2` varchar(45) NOT NULL,
  `city_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_company_branch_address_city1_idx` (`city_id`),
  CONSTRAINT `fk_company_branch_address_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.company_branch_address: ~0 rows (approximately)
DELETE FROM `company_branch_address`;
/*!40000 ALTER TABLE `company_branch_address` DISABLE KEYS */;
INSERT INTO `company_branch_address` (`id`, `line1`, `line2`, `city_id`) VALUES
	(1, 'No 79, Hapugoda', ',Ambatenna', 1);
/*!40000 ALTER TABLE `company_branch_address` ENABLE KEYS */;

-- Dumping structure for table neox_pos.customer
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `contact_number` varchar(45) DEFAULT NULL,
  `city_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_city1_idx` (`city_id`),
  CONSTRAINT `fk_customer_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.customer: ~0 rows (approximately)
DELETE FROM `customer`;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `name`, `contact_number`, `city_id`) VALUES
	(1, 'sd', '3123123', 1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

-- Dumping structure for table neox_pos.grn
DROP TABLE IF EXISTS `grn`;
CREATE TABLE IF NOT EXISTS `grn` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` int NOT NULL,
  `date_time` datetime NOT NULL,
  `user_id` int NOT NULL,
  `unique_id` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `fk_grn_supplier1_idx` (`supplier_id`),
  KEY `fk_grn_user1_idx` (`user_id`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`),
  CONSTRAINT `fk_grn_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.grn: ~6 rows (approximately)
DELETE FROM `grn`;
/*!40000 ALTER TABLE `grn` DISABLE KEYS */;
INSERT INTO `grn` (`id`, `supplier_id`, `date_time`, `user_id`, `unique_id`) VALUES
	(1, 1, '2022-07-20 02:04:22', 1, '1658262862191-1'),
	(2, 1, '2022-07-20 02:07:13', 1, '1658263033212-1'),
	(3, 1, '2022-07-20 02:15:49', 1, '1658263549118-1'),
	(5, 1, '2022-07-20 02:25:47', 1, '1658264147895-1'),
	(7, 1, '2022-07-20 05:07:49', 1, '1658273869613-1'),
	(8, 2, '2022-07-20 05:10:08', 1, '1658274008866-1'),
	(9, 1, '2022-07-20 08:31:57', 1, '1658286117477-1');
/*!40000 ALTER TABLE `grn` ENABLE KEYS */;

-- Dumping structure for table neox_pos.grn_item
DROP TABLE IF EXISTS `grn_item`;
CREATE TABLE IF NOT EXISTS `grn_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `buying_price` double NOT NULL,
  `grn_id` int NOT NULL,
  `stock_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_item_grn1_idx` (`grn_id`),
  KEY `fk_grn_item_stock1_idx` (`stock_id`),
  CONSTRAINT `fk_grn_item_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`),
  CONSTRAINT `fk_grn_item_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.grn_item: ~6 rows (approximately)
DELETE FROM `grn_item`;
/*!40000 ALTER TABLE `grn_item` DISABLE KEYS */;
INSERT INTO `grn_item` (`id`, `quantity`, `buying_price`, `grn_id`, `stock_id`) VALUES
	(1, 10, 100, 1, 2),
	(2, 10, 120, 2, 3),
	(3, 15, 200, 3, 4),
	(4, 10, 150, 5, 5),
	(5, 12, 150, 7, 6),
	(6, 12, 150, 8, 7),
	(7, 10, 100, 9, 8);
/*!40000 ALTER TABLE `grn_item` ENABLE KEYS */;

-- Dumping structure for table neox_pos.grn_payment
DROP TABLE IF EXISTS `grn_payment`;
CREATE TABLE IF NOT EXISTS `grn_payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grn_id` int NOT NULL,
  `payment_type_id` int NOT NULL,
  `payment` double NOT NULL,
  `balance` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_payment_payment_type1_idx` (`payment_type_id`),
  KEY `fk_grn_payment_grn1_idx` (`grn_id`),
  CONSTRAINT `fk_grn_payment_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`),
  CONSTRAINT `fk_grn_payment_payment_type1` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.grn_payment: ~6 rows (approximately)
DELETE FROM `grn_payment`;
/*!40000 ALTER TABLE `grn_payment` DISABLE KEYS */;
INSERT INTO `grn_payment` (`id`, `grn_id`, `payment_type_id`, `payment`, `balance`) VALUES
	(1, 1, 1, 1000, 0),
	(2, 2, 1, 1200, 0),
	(3, 3, 1, 3000, 0),
	(4, 5, 1, 1500, 0),
	(5, 7, 1, 1800, 0),
	(6, 8, 2, 1800, 0),
	(7, 9, 1, 1000, 0);
/*!40000 ALTER TABLE `grn_payment` ENABLE KEYS */;

-- Dumping structure for table neox_pos.invoice
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `user_id` int NOT NULL,
  `unique_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_customer1_idx` (`customer_id`),
  KEY `fk_invoice_user1_idx` (`user_id`),
  CONSTRAINT `fk_invoice_customer1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_invoice_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.invoice: ~44 rows (approximately)
DELETE FROM `invoice`;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` (`id`, `customer_id`, `date_time`, `user_id`, `unique_id`) VALUES
	(1, 1, '2022-07-22 03:24:20', 1, NULL),
	(2, 1, '2022-07-22 03:25:33', 1, '1658440533039-1'),
	(3, 1, '2022-07-22 03:28:29', 1, '1658440709759-1'),
	(4, 1, '2022-07-22 03:30:26', 1, '1658440826135-1'),
	(5, 1, '2022-07-22 03:32:04', 1, '1658440924966-1'),
	(6, 1, '2022-07-22 03:34:26', 1, '1658441066823-1'),
	(7, 1, '2022-07-22 03:34:27', 1, '1658441067127-1'),
	(8, 1, '2022-07-22 03:34:27', 1, '1658441067335-1'),
	(9, 1, '2022-07-22 03:35:32', 1, '1658441132607-1'),
	(10, 1, '2022-07-22 06:03:38', 1, '1658450018949-1'),
	(11, 1, '2022-07-22 23:36:39', 1, '1658513199867-1'),
	(12, 1, '2022-07-23 01:19:43', 1, '1658519383597-1'),
	(13, 1, '2022-07-23 01:20:20', 1, '1658519420076-1'),
	(14, 1, '2022-07-23 01:22:34', 1, '1658519554817-1'),
	(15, 1, '2022-07-23 01:24:33', 1, '1658519673502-1'),
	(16, 1, '2022-07-24 00:58:37', 1, '1658604517449-1'),
	(17, 1, '2022-07-24 00:59:50', 1, '1658604590067-1'),
	(18, 1, '2022-07-24 01:02:46', 1, '1658604766095-1'),
	(20, 1, '2022-07-24 01:04:56', 1, '1658604896770-1'),
	(21, 1, '2022-07-24 01:06:06', 1, '1658604966107-1'),
	(22, 1, '2022-07-24 01:09:31', 1, '1658605171849-1'),
	(23, 1, '2022-07-24 01:10:24', 1, '1658605224179-1'),
	(24, 1, '2022-07-24 01:18:47', 1, '1658605727640-1'),
	(25, 1, '2022-07-24 01:28:59', 1, '1658606339863-1'),
	(26, 1, '2022-07-24 01:30:02', 1, '1658606402672-1'),
	(28, 1, '2022-07-24 02:27:09', 1, '1658609829118-1'),
	(30, 1, '2022-07-24 02:31:53', 1, '1658610113527-1'),
	(32, 1, '2022-07-24 02:36:51', 1, '1658610411423-1'),
	(33, 1, '2022-07-24 02:37:08', 1, '1658610428079-1'),
	(34, 1, '2022-07-25 00:55:06', 1, '1658690706189-1'),
	(35, 1, '2022-07-25 01:04:10', 1, '1658691250429-1'),
	(36, 1, '2022-07-25 01:06:07', 1, '1658691367894-1'),
	(37, 1, '2022-07-25 01:16:30', 1, '1658691990198-1'),
	(38, 1, '2022-07-25 01:19:59', 1, '1658692199942-1'),
	(39, 1, '2022-07-25 01:20:58', 1, '1658692258046-1'),
	(40, 1, '2022-07-25 01:33:51', 1, '1658693031727-1'),
	(41, 1, '2022-07-25 07:19:23', 1, '1658713763274-1'),
	(42, 1, '2022-07-25 07:23:10', 1, '1658713990987-1'),
	(43, 1, '2022-07-25 07:32:14', 1, '1658714534930-1'),
	(44, 1, '2022-07-25 07:33:38', 1, '1658714618907-1'),
	(45, 1, '2022-07-26 00:34:37', 1, '1658775877611-1'),
	(46, 1, '2022-07-26 00:35:41', 1, '1658775941836-1'),
	(47, 1, '2022-07-26 00:40:58', 1, '1658776258340-1'),
	(48, 1, '2022-07-26 00:49:29', 1, '1658776769005-1');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;

-- Dumping structure for table neox_pos.invoice_item
DROP TABLE IF EXISTS `invoice_item`;
CREATE TABLE IF NOT EXISTS `invoice_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_id` int NOT NULL,
  `qty` int DEFAULT NULL,
  `invoice_id` int NOT NULL,
  `discount` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_item_invoice1_idx` (`invoice_id`),
  KEY `fk_invoice_item_stock1_idx` (`stock_id`),
  CONSTRAINT `fk_invoice_item_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `fk_invoice_item_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.invoice_item: ~38 rows (approximately)
DELETE FROM `invoice_item`;
/*!40000 ALTER TABLE `invoice_item` DISABLE KEYS */;
INSERT INTO `invoice_item` (`id`, `stock_id`, `qty`, `invoice_id`, `discount`) VALUES
	(1, 6, 1, 9, 0),
	(2, 2, 1, 10, 0),
	(3, 2, 1, 11, 0),
	(4, 5, 2, 11, 0),
	(5, 4, 1, 11, 0),
	(6, 6, 1, 15, 5),
	(7, 7, 1, 16, 0),
	(8, 3, 1, 16, 0),
	(9, 2, 1, 17, 0),
	(10, 3, 1, 17, 0),
	(11, 7, 1, 18, 0),
	(12, 6, 1, 20, 0),
	(13, 2, 1, 21, 0),
	(14, 3, 1, 21, 0),
	(15, 7, 1, 22, 0),
	(16, 7, 1, 23, 0),
	(17, 7, 1, 24, 0),
	(18, 6, 1, 25, 0),
	(19, 2, 1, 26, 0),
	(20, 2, 1, 28, 0),
	(21, 2, 1, 30, 0),
	(22, 7, 1, 32, 0),
	(23, 7, 1, 33, 0),
	(24, 2, 1, 34, 0),
	(25, 2, 1, 35, 0),
	(26, 6, 1, 36, 0),
	(27, 2, 1, 37, 0),
	(28, 2, 1, 38, 0),
	(29, 8, 1, 39, 0),
	(30, 6, 1, 40, 0),
	(31, 7, 1, 41, 0),
	(32, 7, 1, 42, 0),
	(33, 6, 1, 43, 0),
	(34, 7, 1, 44, 0),
	(35, 7, 1, 45, 0),
	(36, 7, 1, 46, 0),
	(37, 3, 1, 47, 0),
	(38, 6, 1, 48, 15);
/*!40000 ALTER TABLE `invoice_item` ENABLE KEYS */;

-- Dumping structure for table neox_pos.invoice_payment
DROP TABLE IF EXISTS `invoice_payment`;
CREATE TABLE IF NOT EXISTS `invoice_payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `payment` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `invoice_id` int NOT NULL,
  `payment_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_payment_invoice1_idx` (`invoice_id`),
  KEY `fk_invoice_payment_payment_type1_idx` (`payment_type_id`) USING BTREE,
  CONSTRAINT `fk_invoice_payment_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `fk_invoice_payment_payment_type1` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.invoice_payment: ~42 rows (approximately)
DELETE FROM `invoice_payment`;
/*!40000 ALTER TABLE `invoice_payment` DISABLE KEYS */;
INSERT INTO `invoice_payment` (`id`, `payment`, `balance`, `invoice_id`, `payment_type_id`) VALUES
	(1, 1200, 0, 3, 1),
	(2, 1200, 0, 4, 1),
	(3, 1200, 0, 5, 1),
	(4, 1200, 0, 6, 1),
	(5, 1200, 0, 7, 1),
	(6, 1200, 0, 8, 1),
	(7, 1200, 0, 9, 1),
	(8, 80, 0, 10, 1),
	(9, 680, 0, 11, 1),
	(10, 190, 0, 12, 1),
	(11, 190, 0, 13, 1),
	(12, 190, 0, 14, 1),
	(13, 190, 0, 15, 1),
	(14, 350, 0, 16, 1),
	(15, 230, 0, 17, 1),
	(16, 200, 0, 18, 1),
	(17, 200, 0, 20, 1),
	(18, 230, 0, 21, 1),
	(19, 200, 0, 22, 1),
	(20, 200, 0, 23, 1),
	(21, 200, 0, 24, 1),
	(22, 200, 0, 25, 1),
	(23, 80, 0, 26, 1),
	(24, 80, 0, 28, 1),
	(25, 80, 0, 30, 1),
	(26, 200, 0, 32, 1),
	(27, 200, 0, 33, 1),
	(28, 80, 0, 34, 1),
	(29, 80, 0, 35, 1),
	(30, 200, 0, 36, 1),
	(31, 80, 0, 37, 1),
	(32, 80, 0, 38, 1),
	(33, 120, 0, 39, 1),
	(34, 200, 0, 40, 1),
	(35, 200, 0, 41, 1),
	(36, 200, 0, 42, 1),
	(37, 280, 0, 43, 1),
	(38, 200, 0, 44, 1),
	(39, 200, 0, 45, 1),
	(40, 200, 0, 46, 1),
	(41, 150, 0, 47, 1),
	(42, 238, 0, 48, 1);
/*!40000 ALTER TABLE `invoice_payment` ENABLE KEYS */;

-- Dumping structure for table neox_pos.payment_type
DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE IF NOT EXISTS `payment_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.payment_type: ~2 rows (approximately)
DELETE FROM `payment_type`;
/*!40000 ALTER TABLE `payment_type` DISABLE KEYS */;
INSERT INTO `payment_type` (`id`, `name`) VALUES
	(1, 'Cash '),
	(2, 'Debit Card'),
	(3, 'Credit Card');
/*!40000 ALTER TABLE `payment_type` ENABLE KEYS */;

-- Dumping structure for table neox_pos.product
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `barcode` varchar(45) DEFAULT NULL,
  `category_id` int NOT NULL,
  `brand_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_category1_idx` (`category_id`),
  KEY `fk_product_brand1_idx` (`brand_id`),
  CONSTRAINT `fk_product_brand1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `fk_product_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.product: ~3 rows (approximately)
DELETE FROM `product`;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`, `name`, `barcode`, `category_id`, `brand_id`) VALUES
	(1, 'Hawain Cookies', '13645683', 1, 1),
	(2, 'Cashew Nuts', '256887554', 2, 2),
	(3, 'Milk Chocolate', '12312341', 2, 2),
	(4, 'Maliban ', '151651', 1, 1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- Dumping structure for table neox_pos.refund
DROP TABLE IF EXISTS `refund`;
CREATE TABLE IF NOT EXISTS `refund` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `invoice_id` int NOT NULL,
  `stock_id` int NOT NULL,
  `user_id` int NOT NULL,
  `refund_reason_id` int NOT NULL,
  `refund_type_id` int NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `invoice_id` (`invoice_id`),
  KEY `stock_id` (`stock_id`),
  KEY `user_id` (`user_id`),
  KEY `refund_reason_id` (`refund_reason_id`),
  KEY `refund_type_id` (`refund_type_id`),
  CONSTRAINT `invoice_id` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `refund_reason_id` FOREIGN KEY (`refund_reason_id`) REFERENCES `refund_reason` (`id`),
  CONSTRAINT `refund_type_id` FOREIGN KEY (`refund_type_id`) REFERENCES `refund_type` (`id`),
  CONSTRAINT `stock_id` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.refund: ~1 rows (approximately)
DELETE FROM `refund`;
/*!40000 ALTER TABLE `refund` DISABLE KEYS */;
INSERT INTO `refund` (`id`, `product_id`, `invoice_id`, `stock_id`, `user_id`, `refund_reason_id`, `refund_type_id`, `date`) VALUES
	(1, 2, 36, 6, 1, 1, 3, '2022-07-25');
/*!40000 ALTER TABLE `refund` ENABLE KEYS */;

-- Dumping structure for table neox_pos.refund_reason
DROP TABLE IF EXISTS `refund_reason`;
CREATE TABLE IF NOT EXISTS `refund_reason` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reason` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.refund_reason: ~4 rows (approximately)
DELETE FROM `refund_reason`;
/*!40000 ALTER TABLE `refund_reason` DISABLE KEYS */;
INSERT INTO `refund_reason` (`id`, `reason`) VALUES
	(1, 'Damaged'),
	(2, 'Not Satisfied'),
	(3, 'Mistake'),
	(4, 'Other');
/*!40000 ALTER TABLE `refund_reason` ENABLE KEYS */;

-- Dumping structure for table neox_pos.refund_type
DROP TABLE IF EXISTS `refund_type`;
CREATE TABLE IF NOT EXISTS `refund_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.refund_type: ~2 rows (approximately)
DELETE FROM `refund_type`;
/*!40000 ALTER TABLE `refund_type` DISABLE KEYS */;
INSERT INTO `refund_type` (`id`, `type`) VALUES
	(1, 'Bank Transfer'),
	(2, 'Cash'),
	(3, 'Exchange');
/*!40000 ALTER TABLE `refund_type` ENABLE KEYS */;

-- Dumping structure for table neox_pos.stock
DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `selling_price` double DEFAULT NULL,
  `mfd` date DEFAULT NULL,
  `exd` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_product1_idx` (`product_id`),
  CONSTRAINT `fk_stock_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.stock: ~8 rows (approximately)
DELETE FROM `stock`;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` (`id`, `product_id`, `quantity`, `selling_price`, `mfd`, `exd`) VALUES
	(1, 1, 12, 1500, '2022-07-18', '2022-08-18'),
	(2, 2, -1, 80, '2022-07-04', '2022-07-25'),
	(3, 3, 6, 150, '2022-07-04', '2022-07-31'),
	(4, 1, 14, 200, '2022-07-11', '2022-07-26'),
	(5, 3, 8, 200, '2022-07-11', '2022-08-24'),
	(6, 2, 1, 280, '2022-07-03', '2022-07-30'),
	(7, 2, 0, 200, '2022-07-11', '2022-07-27'),
	(8, 1, 9, 120, '2022-07-04', '2022-07-25');
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;

-- Dumping structure for table neox_pos.supplier
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `contact_number` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `company_branch_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_company_branch1_idx` (`company_branch_id`),
  CONSTRAINT `fk_supplier_company_branch1` FOREIGN KEY (`company_branch_id`) REFERENCES `company_branch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.supplier: ~3 rows (approximately)
DELETE FROM `supplier`;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` (`id`, `name`, `contact_number`, `email`, `company_branch_id`) VALUES
	(1, 'Kasun', '0712300885', 'kasun@gmail.con', 1),
	(2, 'Ravis', '0724522115', 'rasuc@gmail.com', 1),
	(3, 'Danuka', '0764894818', 'danuka@gmail.com', 1);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;

-- Dumping structure for table neox_pos.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `contact_number` varchar(10) NOT NULL,
  `user_type_id` int NOT NULL,
  `user_status_id` int NOT NULL DEFAULT '1',
  `city_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_user_type_idx` (`user_type_id`),
  KEY `fk_user_user_status1_idx` (`user_status_id`),
  KEY `fk_user_city1_idx` (`city_id`),
  CONSTRAINT `fk_user_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `fk_user_user_status1` FOREIGN KEY (`user_status_id`) REFERENCES `user_status` (`id`),
  CONSTRAINT `fk_user_user_type` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.user: ~3 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `name`, `username`, `password`, `contact_number`, `user_type_id`, `user_status_id`, `city_id`) VALUES
	(1, 'Tharindu', 'wikum', 'wikum123', '0767036495', 1, 1, 1),
	(2, 'Kasun', 'kasun1', 'kasun123', '0767989848', 2, 1, 1),
	(3, 'Ravindu', 'ravi', 'ravindu123', '0767036495', 2, 2, 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table neox_pos.user_status
DROP TABLE IF EXISTS `user_status`;
CREATE TABLE IF NOT EXISTS `user_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.user_status: ~2 rows (approximately)
DELETE FROM `user_status`;
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
INSERT INTO `user_status` (`id`, `name`) VALUES
	(1, 'Active'),
	(2, 'Deactive');
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;

-- Dumping structure for table neox_pos.user_type
DROP TABLE IF EXISTS `user_type`;
CREATE TABLE IF NOT EXISTS `user_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table neox_pos.user_type: ~2 rows (approximately)
DELETE FROM `user_type`;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` (`id`, `name`) VALUES
	(1, 'Admin'),
	(2, 'Cashier');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
