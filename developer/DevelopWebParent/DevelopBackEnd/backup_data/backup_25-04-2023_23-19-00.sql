-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `test`
--

/*!40000 DROP DATABASE IF EXISTS `test`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `test` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `test`;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `country_id` int DEFAULT NULL,
  `first_name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `last_name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `phone_number` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `address_line_1` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `address_line_2` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `city` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `state` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `postal_code` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `default_address` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn3sth7s3kur1rafwbbrqqnswt` (`country_id`),
  KEY `FKhrpf5e8dwasvdc5cticysrt2k` (`customer_id`),
  CONSTRAINT `FKhrpf5e8dwasvdc5cticysrt2k` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FKn3sth7s3kur1rafwbbrqqnswt` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (9,17,1,'rtt','qưtqt','09897647845','12412','3245','3245','Bà Rịa – Vũng Tàu','5572727',_binary '\0'),(10,20,9,'Nguyen','Test','0874115112','123','okg','test','Amnat Charoen','87787',_binary '\0');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `backup`
--

DROP TABLE IF EXISTS `backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `backup` (
  `id` int NOT NULL AUTO_INCREMENT,
  `count_download` int DEFAULT NULL,
  `path_file` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `time_create` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backup`
--

LOCK TABLES `backup` WRITE;
/*!40000 ALTER TABLE `backup` DISABLE KEYS */;
INSERT INTO `backup` VALUES (1,1,'backup_data/backup_18-04-2023_08-29-07.sql','2023-04-18 08:29:07.910000'),(2,0,'backup_data/backup_18-04-2023_09-50-38.sql','2023-04-18 09:50:39.389000'),(3,1,'backup_data/backup_25-04-2023_22-20-00.sql','2023-04-25 22:20:00.264000');
/*!40000 ALTER TABLE `backup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `id` int NOT NULL AUTO_INCREMENT,
  `board_cate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `board_no` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `end_date` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `full_description` blob,
  `kind_content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `main_image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `start_date` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `title` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `work_user` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `number_views` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (28,NULL,'B0100001','2023-03-27 13:15:55.562000',_binary '','2023-03-29',_binary '<div><div style=\"text-align:center;\"><img src=\"https://file.hstatic.net/200000420363/file/z4171161878768_1b79b33e9a5cab2c2113ccddf0c9fce3_ffacada83c354519bb8ee6580b8cb654_grande.jpg\"></div><div style=\"text-align:center;\"><br><br><div class=\"videoEmbed\" style=\"position:relative;height:0;padding-bottom:56.25%\"><iframe src=\"https://www.youtube-nocookie.com/embed/ngBteN-KiSw?ecver=2\" width=\"640\" height=\"360\" frameborder=\"0\" style=\"position:absolute;width:100%;height:100%;left:0\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" allowfullscreen=\"\"></iframe></div><br><br><br></div></div>','B01','dell.png','2023-03-26','giảm giá','2023-03-27 13:16:28.010000','Nghia',8.00);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `logo` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_oce3937d2f4mpfqrycbr0l93m` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (2,'DELL','dell-png-logo-3741.png'),(4,'ASUS','logo-asus-png-7165.png'),(6,'LG ','lg-logo-14410.png'),(7,'SAMSUNG','samsung-logo-png-1286.png'),(8,'HUAWEI','huawei-logo-png-6978.png'),(9,'OPPO','oppo-logo-40747.png'),(10,'APPLE','spapple.png'),(13,'Test','camera.png'),(14,'123456','gigabyte.png');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands_categories`
--

DROP TABLE IF EXISTS `brands_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands_categories` (
  `brand_id` int NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`brand_id`,`category_id`),
  KEY `FK6x68tjj3eay19skqlhn7ls6ai` (`category_id`),
  CONSTRAINT `FK58ksmicdguvu4d7b6yglgqvxo` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FK6x68tjj3eay19skqlhn7ls6ai` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands_categories`
--

LOCK TABLES `brands_categories` WRITE;
/*!40000 ALTER TABLE `brands_categories` DISABLE KEYS */;
INSERT INTO `brands_categories` VALUES (6,1),(7,1),(8,1),(9,1),(10,1),(2,2),(4,2),(6,2),(7,2),(8,2),(10,2),(7,3),(2,4),(4,4),(6,4),(7,4),(10,4),(13,4),(14,4),(8,5),(7,6),(13,6),(4,7),(7,7),(10,8),(14,8),(2,9),(10,9),(14,9),(13,10),(13,11);
/*!40000 ALTER TABLE `brands_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdagcsk6v6x4n1kxw3rkp57921` (`customer_id`),
  KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKdagcsk6v6x4n1kxw3rkp57921` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (42,15,41,1),(49,17,41,1),(50,17,53,1),(51,17,32,1),(53,17,52,1);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alias` varchar(128) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `image` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `parent_id` int DEFAULT NULL,
  `all_parent_ids` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jx1ptm0r46dop8v0o7nmgfbeq` (`alias`),
  UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`),
  KEY `FKsaok720gsu4u2wrgbk10b5n8d` (`parent_id`),
  CONSTRAINT `FKsaok720gsu4u2wrgbk10b5n8d` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Dan may tinh',_binary '','icon may tinh.jpg','Dàn máy tính',NULL,NULL),(2,'laptop',_binary '','icon máy tinh.png','Laptop',NULL,NULL),(3,'samsung',_binary '','samsung-logo-png-1286.png','Samsung',1,'-1-'),(4,'pc',_binary '','pc.png','PC',NULL,NULL),(5,'xiaomi',_binary '','xiaomi.jpg','Xiaomi',1,'-1-'),(6,'123456',_binary '','dell.png','123456',6,'-6-'),(7,'asus',_binary '','logo-asus-png-7165.png','Asus ',4,'-4-'),(8,'iphone',_binary '','png-apple-logo-9711.png','Iphone',1,'-1-'),(9,'dell',_binary '','dell-png-logo-3741.png','Dell',27,'-27-'),(10,'tplink',_binary '','camera.png','Tplink',2,'-2-'),(11,'hkvision',_binary '','mayanh.jpg','HKVison',1,'-1-'),(27,'Giga',_binary '','gigabyte.png','Giga',NULL,NULL);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_info`
--

DROP TABLE IF EXISTS `client_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `browser_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `browser_version` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `device_infor` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ip_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `mac_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_info`
--

LOCK TABLES `client_info` WRITE;
/*!40000 ALTER TABLE `client_info` DISABLE KEYS */;
INSERT INTO `client_info` VALUES (2,'Chrome','112.0.0.0','Unknown','0:0:0:0:0:0:0:1','30-24-32-A7-BB-A6');
/*!40000 ALTER TABLE `client_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_replies`
--

DROP TABLE IF EXISTS `comment_replies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_replies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_time` datetime(6) DEFAULT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `work_user` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `comment_id` int DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrrv53wwt4b7plhna65ixeiiio` (`comment_id`),
  KEY `FKlkq2iw5fqt8gx5ubf645m6d8p` (`customer_id`),
  KEY `FK3me1ei56u3kwl3v43mfn815wb` (`product_id`),
  CONSTRAINT `FK3me1ei56u3kwl3v43mfn815wb` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKlkq2iw5fqt8gx5ubf645m6d8p` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FKrrv53wwt4b7plhna65ixeiiio` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_replies`
--

LOCK TABLES `comment_replies` WRITE;
/*!40000 ALTER TABLE `comment_replies` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_replies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_time` datetime(6) DEFAULT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `work_user` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKev1bd87g1c51ujncao608e6qa` (`customer_id`),
  KEY `FK6uv0qku8gsu6x1r2jkrtqwjtn` (`product_id`),
  CONSTRAINT `FK6uv0qku8gsu6x1r2jkrtqwjtn` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKev1bd87g1c51ujncao608e6qa` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `countries` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `code` varchar(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries`
--

LOCK TABLES `countries` WRITE;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` VALUES (1,'Việt Nam','VN'),(8,'Hàn Quốc','KR'),(9,'Thái Lan','THAI');
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currencies`
--

DROP TABLE IF EXISTS `currencies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currencies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `symbol` varchar(3) NOT NULL,
  `code` varchar(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currencies`
--

LOCK TABLES `currencies` WRITE;
/*!40000 ALTER TABLE `currencies` DISABLE KEYS */;
INSERT INTO `currencies` VALUES (1,'United States Dollar','$','USD'),(2,'British Pound','£','GPB'),(3,'Japanese Yen','¥','JPY'),(4,'Euro','€','EUR'),(5,'Russian Ruble','₽','RUB'),(6,'South Korean Won','₩','KRW'),(7,'Chinese Yuan','¥','CNY'),(8,'Brazilian Real','R$','BRL'),(9,'Australian Dollar','$','AUD'),(10,'Canadian Dollar','$','CAD'),(11,'Việt Nam đồng','đ','VND'),(12,'Indian Rupee','₹','INR');
/*!40000 ALTER TABLE `currencies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_activity`
--

DROP TABLE IF EXISTS `customer_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_activity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ativity` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `log_in` datetime(6) DEFAULT NULL,
  `log_out` datetime(6) DEFAULT NULL,
  `time_ativity` datetime(6) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `url_log` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdyksoexu86euux7t1cy57wrs` (`customer_id`),
  CONSTRAINT `FKdyksoexu86euux7t1cy57wrs` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_activity`
--

LOCK TABLES `customer_activity` WRITE;
/*!40000 ALTER TABLE `customer_activity` DISABLE KEYS */;
INSERT INTO `customer_activity` VALUES (12,'Thêm sản phẩm vào giỏ',NULL,NULL,'2023-04-11 21:38:11.099000',20,'p/Dell-Inspiron-14-5415-R7-5700U-(TX4H61)'),(13,'Đã cập nhật thông tin tài khoản',NULL,NULL,'2023-04-11 21:39:02.952000',20,'account_details'),(14,'Đã xóa sản phẩm trong giỏ',NULL,NULL,'2023-04-11 21:40:46.857000',20,''),(15,'Đã thêm địa chỉ nhận hàng',NULL,NULL,'2023-04-11 21:48:06.828000',20,'address_book/edit/10'),(16,'Thêm sản phẩm vào giỏ',NULL,NULL,'2023-04-17 21:25:40.243000',17,'p/Dell-Vostro-3510-i5');
/*!40000 ALTER TABLE `customer_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `address_line_1` varchar(64) NOT NULL,
  `address_line_2` varchar(64) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `country_id` int DEFAULT NULL,
  `postal_code` varchar(10) NOT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `verification_code` varchar(64) DEFAULT NULL,
  `authentication_type` varchar(10) DEFAULT NULL,
  `reset_password_token` varchar(30) DEFAULT NULL,
  `work_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rfbvkrffamfql7cjmen8v976v` (`email`),
  KEY `FK7b7p2myt0y31l4nyj1p7sk0b1` (`country_id`),
  CONSTRAINT `FK7b7p2myt0y31l4nyj1p7sk0b1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (15,'rinokumura615@yahoo.com','','Lê','Huỳnh Ý','098752145415','12','test','Test','Bình Dương',1,'12515','2023-03-26 15:43:53.805000',NULL,_binary '',NULL,'FACEBOOK',NULL,NULL),(17,'lehuyny2110@gmail.com','','Y','Le','098745612','12412','3245','3245','Bình Dương',1,'5572727','2023-03-27 13:01:11.426000',NULL,_binary '',NULL,'GOOGLE',NULL,NULL),(20,'lehuynhy2106@gmail.com','','1247_Lê','Huỳnh Ý','0985411111','158','lê hồng phong','dĩ an','Bình Dương',1,'7500000','2023-04-11 21:37:37.086000',NULL,_binary '',NULL,'GOOGLE',NULL,NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_time` datetime(6) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `end_date` date DEFAULT NULL,
  `full_description` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `number_views` decimal(19,2) DEFAULT NULL,
  `short_description` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `start_date` date DEFAULT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `work_user` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `poppup` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (9,'2023-03-27 13:10:44.399000',_binary '','2023-05-31','<p>xin nghỉ v&agrave;i ng&agrave;y</p>',NULL,'nghỉ khỏe','2023-03-26','2023-04-11 22:13:07.486000','HUYNH Y',_binary '\0');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_cost` float NOT NULL,
  `quantity` int NOT NULL,
  `shipping_cost` float NOT NULL,
  `subtotal` float NOT NULL,
  `unit_price` float NOT NULL,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`),
  CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (23,300,2,0,800,400,27,32),(24,50000,1,0,150000,150000,28,53);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_track`
--

DROP TABLE IF EXISTS `order_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_track` (
  `id` int NOT NULL AUTO_INCREMENT,
  `notes` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `status` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `work_user` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK31jv1s212kajfn3kk1ksmnyfl` (`order_id`),
  CONSTRAINT `FK31jv1s212kajfn3kk1ksmnyfl` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_track`
--

LOCK TABLES `order_track` WRITE;
/*!40000 ALTER TABLE `order_track` DISABLE KEYS */;
INSERT INTO `order_track` VALUES (4,'Đặt hàng bởi khách hàng','NEW','2023-04-04 11:40:23.000000',NULL,27),(5,'Đơn hàng đang được xử lý','PROCESSING','2023-04-04 11:42:27.000000',NULL,27),(6,'Đặt hàng bởi khách hàng','NEW','2023-04-11 21:40:46.833000',NULL,28);
/*!40000 ALTER TABLE `order_track` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address_line_1` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `address_line_2` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `city` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `country` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `deliver_date` datetime(6) DEFAULT NULL,
  `deliver_days` int NOT NULL,
  `first_name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `last_name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `order_time` datetime(6) DEFAULT NULL,
  `payment_method` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `phone_number` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `postal_code` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `product_cost` float NOT NULL,
  `shipping_cost` float NOT NULL,
  `state` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `subtotal` float NOT NULL,
  `tax` float NOT NULL,
  `total` float NOT NULL,
  `customer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpxtb8awmi0dk6smoh2vp1litg` (`customer_id`),
  CONSTRAINT `FKpxtb8awmi0dk6smoh2vp1litg` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (27,'12412','3245','3245','Việt Nam','2023-04-07 00:00:00.000000',3,'Y','Le','2023-04-04 11:40:23.713000','COD','098745612','5572727',300,0,'Bình Dương','NEW',800,0,800,17),(28,'158','lê hồng phong','dĩ an','Việt Nam','2023-04-14 21:40:46.833000',3,'1247_Lê','Huỳnh Ý','2023-04-11 21:40:46.833000','COD','0985411111','7500000',50000,0,'Bình Dương','NEW',150000,0,150000,20);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_details`
--

DROP TABLE IF EXISTS `product_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnfvvq3meg4ha3u1bju9k4is3r` (`product_id`),
  CONSTRAINT `FKnfvvq3meg4ha3u1bju9k4is3r` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_details`
--

LOCK TABLES `product_details` WRITE;
/*!40000 ALTER TABLE `product_details` DISABLE KEYS */;
INSERT INTO `product_details` VALUES (14,'Ram','16GB',55),(15,'Màu','đen',55);
/*!40000 ALTER TABLE `product_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqnq71xsohugpqwf3c9gxmsuy` (`product_id`),
  CONSTRAINT `FKqnq71xsohugpqwf3c9gxmsuy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (28,'vi-vn-dell-vostro-3510-i5-p112f002bbl-1.jpg',52),(30,'iPhone-14-thumb-do-600x600.jpg',55),(31,'iphone-13-pro-max-graphite-600x600.jpg',55),(32,'samsung-galaxy-s23-600x600.jpg',55);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `alias` varchar(256) NOT NULL,
  `short_description` varchar(512) NOT NULL,
  `full_description` blob NOT NULL,
  `main_image` varchar(255) DEFAULT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'1',
  `in_stock` bit(1) DEFAULT b'1',
  `cost` float NOT NULL DEFAULT '0',
  `price` float NOT NULL DEFAULT '0',
  `discount_percent` float DEFAULT '0',
  `lenght` float NOT NULL DEFAULT '0',
  `width` float NOT NULL DEFAULT '0',
  `height` float NOT NULL DEFAULT '0',
  `weight` float NOT NULL DEFAULT '0',
  `brand_id` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `fabric` varchar(255) DEFAULT NULL,
  `work_user` varchar(255) DEFAULT NULL,
  `number_views` decimal(19,2) DEFAULT NULL,
  `related_pdt` bit(1) NOT NULL,
  `qty` decimal(19,2) DEFAULT NULL,
  `kind` varchar(255) DEFAULT NULL,
  `pdt_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8qwq8q3hk7cxkp9gruxupnif5` (`alias`),
  UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`),
  KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  FULLTEXT KEY `products_FTS` (`name`,`short_description`),
  CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (32,'Camera IP Ngoài Trời 3MP TP-link Tapo C310','Camera-IP-Ngoài-Trời-3MP-TP-link-Tapo-C310','<div>ok</div>',_binary '<div><br></div>','camera-giam-sat-ngoai-troi-3mp-tp-link-tapo-c310-030822-045845-600x600.jpg','2023-01-31 23:09:26.678000','2023-03-27 14:07:31.776000',_binary '',_binary '',150000,400000,0,0,0,0,0,7,6,'S','Đen','Cotton','HUYNH Y',4.00,_binary '',1.00,'A01','A0100003'),(35,'HP AIO ProOne 240 G9 i5 1235U 23.8 inch (6M3V2PA)','HP-AIO-ProOne-240-G9-i5-1235U-23.8-inch-(6M3V2PA)','<div>OK</div>',_binary '<div><br></div>','hp-aio-proone-240-g9-i5-6m3v2pa-ab-thumb-600x600.jpg','2023-02-01 11:45:53.365000','2023-03-19 02:16:27.228000',_binary '',_binary '',150000,250000,0,0,0,0,0,7,4,'S','Đen','Cotton','Nghia',6.00,_binary '\0',4.00,'A01','A0100004'),(36,'iPhone 14','iPhone-14','<div>ok</div>',_binary '<div><br></div>','iPhone-14-thumb-do-600x600.jpg','2023-02-01 11:47:00.454000','2023-03-19 00:42:09.467000',_binary '',_binary '',90000,150000,0,0,0,0,0,10,1,'S','Đen','Cotton','Nghia',11.00,_binary '\0',3.00,'A01','A0100005'),(40,'iPhone 13 Pro Max 1TB','iPhone-13-Pro-Max-1TB','<div>ok</div>',_binary '<div><br></div>','iphone-13-pro-max-graphite-600x600.jpg','2023-02-01 12:12:12.203000','2023-03-19 02:16:31.895000',_binary '',_binary '',50000,60000,0,0,0,0,0,10,8,'S','Đen','Cotton','Nghia',17.00,_binary '\0',4.00,'A01','C0100002'),(41,'Laptop Dell XPS 17 9710 i7 11800H/16GB/1TB SSD/4GB','Laptop-Dell-XPS-17-9710-i7-11800H','<div>k</div>',_binary '<div>Sản phẩm đón chờ ngoài sự mong đợi</div>','dell-vostro-3510-i5-p112f002bbl-(14).jpg','2023-02-01 12:13:01.150000','2023-03-19 22:46:31.636000',_binary '',_binary '',12000,50000,10000,1,1,1,1,2,2,'S','Đen','Cotton','Nghia',9.00,_binary '\0',12.00,'A01','C0100002'),(42,'Mac mini M1 2020 Silver (MGNT3SA/A)','Mac-mini-M1-2020-Silver','<div>ol</div>',_binary '<div><br></div>','mac-mini-2020-m1-8-core-8gb-512gb-silver-mgnt3sa-a-600x600.jpg','2023-02-01 12:20:21.992000','2023-03-19 22:45:03.049000',_binary '',_binary '',50000,6000000,0,0,0,0,0,10,4,'S','Đen','Cotton','Nghia',11.00,_binary '\0',6.00,'A01','C0100002'),(43,'Samsung Galaxy S23 5G','Samsung-Galaxy-S23-5G','<div>k</div>',_binary '<div><br></div>','samsung-galaxy-s23-600x600.jpg','2023-02-01 12:20:53.169000','2023-03-19 02:16:03.074000',_binary '',_binary '',500000,1564500,154000,0,0,0,0,7,1,'S','Đen','Cotton','Nghia',1.00,_binary '\0',5.00,'A01','A0100006'),(46,'iPhone 14 Pro Max','iPhone-14-Pro-Max','<div>k</div>',_binary '<div><br></div>','iphone-14-pro-max-vang-thumb-600x600.jpg','2023-02-01 12:53:05.089000','2023-03-19 02:25:37.219000',_binary '',_binary '',8000000,9500000,100000,0,0,0,0,10,1,'S','Đen','Cotton','Nghia',3.00,_binary '\0',2.00,'A01','C0100002'),(47,'Camera IP 360 Độ 1536P TP-Link Tapo C210','Camera-IP-360-Độ-1536P-TP-Link-Tapo-C210','sản phầm bla nla.....',_binary '<div>cái gì đây ?&nbsp; &nbsp;&nbsp;<div style=\"text-align:center;\"><img src=\"https://product.hstatic.net/1000026716/product/dell_u2422h_gearvn_e8dfce5719b0455aa386bf41352592cd_large.jpg\"></div></div>','camera-ip-360-do-3mp-tp-link-tapo-c210-030822-051551-600x600.jpg','2023-02-01 12:59:14.842000','2023-03-27 14:06:47.759000',_binary '',_binary '\0',20000,500000,7,0,0,0,0,14,9,'S','Đen','Cotton','HUYNH Y',9.00,_binary '',0.00,'B01','B0100004'),(48,'Dell Vostro 3400 i7 1165G7 (V4I7015W1)','Dell-Vostro-3400-i7-1165G7-(V4I7015W1)','<div>k</div>',_binary '<div><br></div>','dell-vostro-3400-i7-1165g7-8gb-512gb-2gb-600x600.jpg','2023-02-01 13:07:28.767000','2023-03-19 00:36:57.690000',_binary '',_binary '\0',0,0,0,0,0,0,0,2,2,'S','Đen','Cotton','Nghia',10.00,_binary '\0',0.00,'D01','D0100001'),(51,'Laptop Dell Vostro 5620 i5 1240P/8GB/256GB/OfficeHS/Win11','Laptop-Dell-Vostro-5620-i5-1240P','<div>i</div>',_binary '<div><br></div>','dell-vostro-3510-i5-p112f002bbl-(14).jpg','2023-02-01 13:11:48.542000','2023-03-19 22:45:47.240000',_binary '',_binary '',8000000,9990000,1000000,0,0,0,0,2,9,'S','Đen','Cotton','Nghia',33.00,_binary '\0',3.00,'A01','A0100007'),(52,'Dell Vostro 3510 i5','Dell-Vostro-3510-i5','Sản phẩm tiêu biểu của năm',_binary '<div><h3 style=\"margin: 20px 0px 15px; padding: 0px; font-variant-numeric: normal; font-variant-east-asian: normal; font-variant-alternates: normal; font-kerning: auto; font-optical-sizing: auto; font-feature-settings: normal; font-variation-settings: normal; font-weight: bold; font-stretch: normal; font-size: 20px; line-height: 28px; font-family: Arial, Helvetica, sans-serif; color: rgb(51, 51, 51); outline: none;\"><a href=\"https://www.thegioididong.com/laptop/dell-vostro-3510-i5-p112f002bbl\" target=\"_blank\" title=\"Dell Vostro 3510 i5 (P112F002BBL) đang bán tại dienmayxanh.com\" style=\"margin: 0px; padding: 0px; transition: all 0.2s ease 0s; color: rgb(47, 128, 237); font-variant-numeric: normal; font-variant-east-asian: normal; font-variant-alternates: normal; font-kerning: auto; font-optical-sizing: auto; font-feature-settings: normal; font-variation-settings: normal; font-stretch: normal; line-height: 18px; outline: none;\">Laptop Dell Vostro 3510 i5 (P112F002BBL)</a>&nbsp;sở hữu cấu hình mạnh mẽ, đa nhiệm mượt mà trên sức mạnh của bộ vi xử lý Intel thế hệ 11, cùng một thiết kế đơn giản mà sang đẹp, sẽ là lựa chọn đắt giá đáp ứng nhu cầu học tập, làm việc hay giải trí của bạn.</h3><h3 style=\"margin: 20px 0px 15px; padding: 0px; font-variant-numeric: normal; font-variant-east-asian: normal; font-variant-alternates: normal; font-kerning: auto; font-optical-sizing: auto; font-feature-settings: normal; font-variation-settings: normal; font-weight: bold; font-stretch: normal; font-size: 20px; line-height: 28px; font-family: Arial, Helvetica, sans-serif; color: rgb(51, 51, 51); outline: none;\">Hiệu năng mạnh mẽ, ổn định</h3><div>Được trang bị dòng chip&nbsp;<strong style=\"margin: 0px; padding: 0px;\">Intel Core i5&nbsp;Tiger Lake 1135G7</strong>&nbsp;mạnh mẽ, hoạt động trên xung nhịp cơ bản&nbsp;<strong style=\"margin: 0px; padding: 0px;\">2.4 GHz</strong>&nbsp;và ép xung lên đến&nbsp;<strong style=\"margin: 0px; padding: 0px;\">4.2 GHz</strong>&nbsp;ở chế độ Turbo Boost, Dell Vostro 3510 dễ dàng giải quyết mượt mà các tác vụ như soạn thảo hợp đồng trên Word, nhập liệu và thống kê qua Excel, tạo bảng thuyết trình cùng PowerPoint,... hay thiết kế đồ hoạ cơ bản trên các ứng dụng nhà Adobe cũng hiệu quả không kém.</div><div>Sự hỗ trợ của bộ nhớ&nbsp;<strong style=\"margin: 0px; padding: 0px;\">RAM 8 GB</strong>&nbsp;chuẩn<strong style=\"margin: 0px; padding: 0px;\">&nbsp;DDR4 2 khe</strong>&nbsp;(1 khe&nbsp;<strong style=\"margin: 0px; padding: 0px;\">8 GB</strong>&nbsp;+ 1 khe rời) với tốc độ Bus RAM lên đến<strong style=\"margin: 0px; padding: 0px;\">&nbsp;3200 MHz</strong>&nbsp;cho quá trình đa nhiệm trên&nbsp;<a href=\"https://www.dienmayxanh.com/laptop\" target=\"_blank\" title=\"Xem thêm các mẫu Laptop hiện đang kinh doanh tại dienmayxanh.com\" style=\"margin: 0px; padding: 0px; transition: all 0.2s ease 0s; color: rgb(47, 128, 237);\">laptop</a>&nbsp;càng trơn tru, ổn định hơn, không gặp tình trạng giật lag máy, đơ màn hình. Máy cho phép nâng cấp RAM lên đến&nbsp;<strong style=\"margin: 0px; padding: 0px;\">16 GB</strong>&nbsp;để tăng sức mạnh cấu hình cho những tác vụ nặng.</div><div>Bên cạnh đó, sức mạnh đồ họa từ card đồ họa rời&nbsp;<strong style=\"margin: 0px; padding: 0px;\">NVIDIA GeForce MX350 2 GB&nbsp;</strong>cải tiến khả năng xử lý đồ họa thiết kế sản phẩm poster, banner,... ấn tượng cũng hiệu render video ngắn hiệu quả, từ đó giúp tăng trải nghiệm của người dùng, bạn cũng dễ dàng thử các tựa game nhẹ nhàng thịnh hành một cách mượt mà.</div><div><a href=\"https://www.thegioididong.com/laptop?g=ssd-512-gb\" target=\"_blank\" title=\"Xem thêm một số sản phẩm laptop có ổ cứng SSD 512 Gb đang kinh doanh tại Thegioididong.com\" style=\"margin: 0px; padding: 0px; transition: all 0.2s ease 0s; color: rgb(47, 128, 237);\">Laptop SSD 512 GB</a><strong style=\"margin: 0px; padding: 0px;\">&nbsp;NVMe PCIe</strong>&nbsp;(Có thể tháo ra, lắp thanh khác tối đa&nbsp;<strong style=\"margin: 0px; padding: 0px;\">2 TB&nbsp;(2280) / 1 TB (2230)</strong>) cho phép lưu trữ khá thoải mái dữ liệu cá nhân cho công việc, giải trí, hỗ trợ khởi động máy nhanh chóng, truy xuất dữ liệu ổn định. Khi cần mở rộng không gian lưu trữ, bạn cũng có thể dùng&nbsp;<strong style=\"margin: 0px; padding: 0px;\">khe cắm HDD SATA&nbsp;</strong>(nâng cấp tối đa&nbsp;<strong style=\"margin: 0px; padding: 0px;\">2 TB</strong>).</div><div><div style=\"text-align:center;\"><img src=\"https://cdn.tgdd.vn/Products/Images/44/266280/dell-vostro-3510-i5-p112f002bbl-5-1.jpg\"></div><h3 style=\"margin: 20px 0px 15px; padding: 0px; font-variant-numeric: normal; font-variant-east-asian: normal; font-variant-alternates: normal; font-kerning: auto; font-optical-sizing: auto; font-feature-settings: normal; font-variation-settings: normal; font-weight: bold; font-stretch: normal; font-size: 20px; line-height: 28px; font-family: Arial, Helvetica, sans-serif; color: rgb(51, 51, 51); outline: none;\">Thiết kế thanh lịch, sang đẹp</h3><div><a href=\"https://www.thegioididong.com/laptop-dell-vostro\" target=\"_blank\" title=\"Một số laptop Dell Vostro đang kinh doanh tại thegioididong.com\" style=\"margin: 0px; padding: 0px; transition: all 0.2s ease 0s; color: rgb(47, 128, 237);\">Laptop Dell Vostro</a>&nbsp;có lớp vỏ ngoài bằng nhựa màu đen hiện đại, tổng thể thiết kế toát lên sự chắc chắn, sang trọng mà rất thanh lịch, các chi tiết tinh tế, đẹp mắt. Máy có khối lượng&nbsp;<strong style=\"margin: 0px; padding: 0px;\">1.69 kg</strong>, độ dày&nbsp;<strong style=\"margin: 0px; padding: 0px;\">18.9 mm</strong>&nbsp;dễ dàng để mang theo trong hành trang thường nhật cho học tập, làm việc.</div></div><div><div style=\"text-align:center;\"><img src=\"https://cdn.tgdd.vn/Products/Images/44/266280/dell-vostro-3510-i5-p112f002bbl-2-1.jpg\"></div><br></div><div><a class=\"preventdefault\" href=\"https://cdn.tgdd.vn/Products/Images/44/266280/dell-vostro-3510-i5-p112f002bbl-5-1.jpg\" style=\"margin: 0px; padding: 0px; transition: all 0.2s ease 0s; color: rgb(47, 128, 237); cursor: default;\"></a></div></div>','dell-vostro-3510-i5-p112f002bbl-(14).jpg','2023-02-01 13:12:55.488000','2023-03-19 02:16:22.610000',_binary '',_binary '',3000000,5000000,100000,0,0,0,0,2,2,'S','Đen','Cotton','Nghia',17.00,_binary '\0',5.00,'B01','B0100005'),(53,'Dell Inspiron 14 5415 R7 5700U (TX4H61)','Dell-Inspiron-14-5415-R7-5700U-(TX4H61)','sản phẩm của năm',_binary '<div>sản phẩm của năm<br></div>','dell-inspiron-14-5415-r7-5700u-8gb-512gb-office-h-s-win11-tx4h61-600x600.jpg','2023-02-01 18:19:43.278000','2023-03-27 14:07:42.826000',_binary '',_binary '',50000,150000,0,0,0,0,0,2,2,'S','Đen','Cotton','HUYNH Y',16.00,_binary '',11.00,'D01','A0100008'),(55,'Test002','Test002','<div>Test</div>',_binary '<div><h3 style=\"margin: 20px 0px 15px; padding: 0px; font-variant-numeric: normal; font-variant-east-asian: normal; font-variant-alternates: normal; font-kerning: auto; font-optical-sizing: auto; font-feature-settings: normal; font-variation-settings: normal; font-weight: bold; font-stretch: normal; font-size: 20px; line-height: 28px; font-family: Arial, Helvetica, sans-serif; color: rgb(51, 51, 51); outline: none; text-align: justify;\"><a href=\"https://www.thegioididong.com/dtdd/samsung-galaxy-s23-5g\" target=\"_blank\" title=\"Tham khảo Samsung Galaxy S23 5G tại Thế Giới Di Động\" style=\"margin: 0px; padding: 0px; transition: all 0.2s ease 0s; color: rgb(47, 128, 237); font-variant-numeric: normal; font-variant-east-asian: normal; font-variant-alternates: normal; font-kerning: auto; font-optical-sizing: auto; font-feature-settings: normal; font-variation-settings: normal; font-stretch: normal; line-height: 18px; outline: none;\">Samsung Galaxy S23</a>&nbsp;có thể xem là cái tên mở màn cho dòng điện thoại cao cấp được nhà&nbsp;<a href=\"https://www.thegioididong.com/dtdd-samsung\" target=\"_blank\" title=\"Tham khảo điện thoại Samsung kinh doanh tại Thế Giới Di Động\" style=\"margin: 0px; padding: 0px; transition: all 0.2s ease 0s; color: rgb(47, 128, 237); font-variant-numeric: normal; font-variant-east-asian: normal; font-variant-alternates: normal; font-kerning: auto; font-optical-sizing: auto; font-feature-settings: normal; font-variation-settings: normal; font-stretch: normal; line-height: 18px; outline: none;\">Samsung</a>&nbsp;giới thiệu vào dịp đầu năm 2023, kể từ lúc chính thức ra mắt máy nhận được khá nhiều sự quan tâm với hàng loạt thông tin cấu hình hết sức nổi bật như con chip hàng đầu Qualcomm cùng bộ camera siêu chất lượng.</h3><h3 style=\"margin: 20px 0px 15px; padding: 0px; font-variant-numeric: normal; font-variant-east-asian: normal; font-variant-alternates: normal; font-kerning: auto; font-optical-sizing: auto; font-feature-settings: normal; font-variation-settings: normal; font-weight: bold; font-stretch: normal; font-size: 20px; line-height: 28px; font-family: Arial, Helvetica, sans-serif; color: rgb(51, 51, 51); outline: none; text-align: justify;\">Sở hữu lối thiết kế sang trọng</h3><div>Ở phiên bản năm nay Galaxy S23 vẫn sẽ thừa hưởng kiểu dáng quen thuộc đến từ thế hệ cũ, vẫn là mặt lưng kính được bao bọc bởi một bộ khung kim loại và bo tròn ở các góc, đi kèm với đó là cách tạo hình phẳng ở các mặt làm cho máy trở nên sang trọng và cực kỳ hiện đại.</div></div><div><div style=\"text-align:center;\"><img src=\"https://cdn.tgdd.vn/Products/Images/42/264060/samsung-galaxy-s23-5g-020223-023936.jpg\"></div>OKay</div><div><br><br><div class=\"videoEmbed\" style=\"position:relative;height:0;padding-bottom:56.25%\"><iframe src=\"https://www.youtube-nocookie.com/embed/LkiRVnco_us?ecver=2\" width=\"640\" height=\"360\" frameborder=\"0\" style=\"position:absolute;width:100%;height:100%;left:0\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" allowfullscreen=\"\"></iframe></div><br><br><br></div>','iphone-14-pro-max-vang-thumb-600x600.jpg','2023-03-19 23:18:20.780000','2023-03-19 23:18:20.780000',_binary '',_binary '',50000,80000,3000,1,1,1,1,13,6,'S','Đen','Cotton','Nghia',2.00,_binary '\0',15.00,'A01','A0100003'),(56,'Main B760M GiGa ','Main-B760M-GiGa-','<div><br></div>',_binary '<div><br></div>','maingiga3.png','2023-04-18 08:59:29.293000','2023-04-18 09:08:41.845000',_binary '',_binary '',5000000,6000000,0,0,0,0,0,13,4,'S','Đen','Cotton','HUYNH Y',3.00,_binary '\0',10.00,'B01','B0100006');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(150) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'manage everything','Admin'),(2,'manage product price, customer, shipping, order and sales report','Salesperson'),(3,'manage categories, brandsproducts, articles and menus','Editor'),(4,'View products, view orders and update orders status','Shipper'),(5,'manage questions and review','Assistant');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
  `key` varchar(128) NOT NULL,
  `value` varchar(1024) NOT NULL,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES ('ADDRESS_COMPANY','93 Đường D1, khu dân cư Đông an, Dĩ An, Bình Dương','GENERAL'),('BACKUP_YN','Y','GENERAL'),('COPYRIGHT','Copyright © 2022 ❤️ by huynh Y','GENERAL'),('CURRENCY_ID','1','CURRENCY'),('CURRENCY_SYMBOL','$','CURRENCY'),('CURRENCY_SYMBOL_POSITION','After price','CURRENCY'),('CUSTOMER_VERIFY_CONTENT','COMMA','MAIL_TEMPLATES'),('CUSTOMER_VERIFY_SUBJECT','Xác nhận mật khẩu','MAIL_TEMPLATES'),('DECIMAL_DIGITS','2','CURRENCY'),('DECIMAL_POINT_TYPE','POINT','CURRENCY'),('EMAIL_COMPANY','nghia.nguyen1622@gmail.com','GENERAL'),('LINK_CONNECT','https://www.datxanh.vn/','GENERAL'),('LINK_FB','https://www.facebook.com/nguyenvannghia1622it/','GENERAL'),('LINK_YT','https://www.youtube.com/@it-developerwebsitejava2352','GENERAL'),('LINK_ZALO','https://zalo.me/0368023380','GENERAL'),('MAIL_FROM','vannguyen002st@gmail.com','MAIL_SERVER'),('MAIL_HOST','smtp.gmail.com','MAIL_SERVER'),('MAIL_PASSWORD','tyyagefvmjplwclw','MAIL_SERVER'),('MAIL_PORT','587','MAIL_SERVER'),('MAIL_SENDER_NAME','Shop Team1','MAIL_SERVER'),('MAIL_USERNAME','vannguyen002st@gmail.com','MAIL_SERVER'),('ORDER_CONFIRMATION_CONTENT','Dear [[name]], this email is to confirm that...','MAIL_TEMPLATES'),('ORDER_CONFIRMATION_SUBJECT','Confirmation of your order ID #[[orderId]]','MAIL_TEMPLATES'),('PAYPAL_API_BASE_URL','https://api-m.sandbox.paypal.com','PAYMENT'),('PAYPAL_API_CLIENT_ID','PAYPAL_CLIENT_ID','PAYMENT'),('PAYPAL_API_CLIENT_SECRET','PAYPAL_CLIENT_SECRET','PAYMENT'),('PHONE','0368023380','GENERAL'),('SITE_LOGO','/site-logo/logo.png','GENERAL'),('SITE_NAME','Nghia IT','GENERAL'),('SMTP_AUTH','true','MAIL_SERVER'),('SMTP_SECURED','true','MAIL_SERVER'),('TAX_CODE','0266485482','GENERAL'),('TEL','02845445454','GENERAL'),('THOUSANDS_POINT_TYPE','POINT','CURRENCY'),('TIMES','9h - 21h','GENERAL');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_rates`
--

DROP TABLE IF EXISTS `shipping_rates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_rates` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cod_supported` bit(1) DEFAULT NULL,
  `days` int NOT NULL,
  `rate` float NOT NULL,
  `state` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `country_id` int DEFAULT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `work_user` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKef7sfgeybt3xn13nlt2j6sljw` (`country_id`),
  CONSTRAINT `FKef7sfgeybt3xn13nlt2j6sljw` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_rates`
--

LOCK TABLES `shipping_rates` WRITE;
/*!40000 ALTER TABLE `shipping_rates` DISABLE KEYS */;
INSERT INTO `shipping_rates` VALUES (10,_binary '',3,10000,'Bình Dương',1,'2023-03-26 16:05:15.507000','2023-03-26 16:05:15.507000','Nghia');
/*!40000 ALTER TABLE `shipping_rates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `states`
--

DROP TABLE IF EXISTS `states`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `states` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `country_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKskkdphjml9vjlrqn4m5hi251y` (`country_id`),
  CONSTRAINT `FKskkdphjml9vjlrqn4m5hi251y` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `states`
--

LOCK TABLES `states` WRITE;
/*!40000 ALTER TABLE `states` DISABLE KEYS */;
INSERT INTO `states` VALUES (21,'An Giang',1),(22,'Bà Rịa – Vũng Tàu',1),(23,'Bắc Giang',1),(24,'Bắc Kạn',1),(25,'Bạc Liêu',1),(26,'Bắc Ninh',1),(27,'Bến Tre',1),(28,'Bình Định',1),(29,'Bình Dương',1),(30,'Bình Phước',1),(31,'Bình Thuận',1),(32,'Cà Mau',1),(33,'Cần Thơ',1),(34,'Cao Bằng',1),(35,'Đà Nẵng',1),(36,'Đắk Lắk',1),(37,'Đắk Nông',1),(38,'Điện Biên',1),(39,'Đồng Nai',1),(40,'Đồng Tháp',1),(41,'Gia Lai',1),(42,'Hà Giang',1),(43,'Hà Nam',1),(44,'Hà Nội',1),(45,'Hà Tĩnh',1),(46,'Hải Dương',1),(47,'Hải Phòng',1),(48,'Hậu Giang',1),(49,'Hòa Bình',1),(50,'Hưng Yên',1),(51,'Khánh Hòa',1),(52,'Kiên Giang',1),(53,'Kon Tum',1),(54,'Lai Châu',1),(55,'Lâm Đồng',1),(56,'Lạng Sơn',1),(57,'Lào Cai',1),(58,'Long An',1),(59,'Nam Định',1),(60,'Nghệ An',1),(61,'Ninh Bình',1),(62,'Ninh Thuận',1),(63,'Phú Thọ',1),(64,'Phú Yên',1),(65,'Quảng Bình',1),(66,'Quảng Nam',1),(67,'Quảng Ngãi',1),(68,'Quảng Ninh',1),(69,'Quảng Trị',1),(70,'Sóc Trăng',1),(71,'Sơn La',1),(72,'Tây Ninh',1),(73,'Thái Bình',1),(74,'Thái Nguyên',1),(75,'Thanh Hóa',1),(76,'Thừa Thiên Huế',1),(77,'Tiền Giang',1),(78,'TP Hồ Chí Minh',1),(79,'Trà Vinh',1),(80,'Tuyên Quang',1),(81,'Vĩnh Long',1),(82,'Vĩnh Phúc',1),(83,'Yên Bái',1),(84,'Chiang Mai',9),(85,'Chiang Rai',9),(86,'Kamphaeng Phet',9),(87,'Lampang',9),(88,'Lamphun',9),(89,'Mae Hong Son',9),(90,'Nakhon Sawan',9),(91,'Nan',9),(92,'Phayao',9),(93,'Phetchabun',9),(94,'Phichit',9),(95,'Phitsanulok',9),(96,'Phrae',9),(97,'Sukhothai',9),(98,'Tak',9),(99,'Uthai Thani',9),(100,'Uttaradit',9),(101,'Amnat Charoen',9),(102,'Buri Ram',9),(103,'Chaiyaphum',9),(104,'Kalasin',9),(105,'Khon Kaen',9),(106,'Loei',9),(107,'Maha Sarakham',9),(108,'Mukdahan',9),(109,'Nakhon Phanom',9),(110,'Nakhon Ratchasima',9),(111,'Nong Bua Lamphu',9),(112,'Nong Khai',9),(113,'Roi Et',9),(114,'Sakon Nakhon',9),(115,'Si Sa Ket',9),(116,'Surin',9),(117,'Ubon Ratchathani',9),(118,'Udon Thani',9),(119,'Yasothon',9),(120,'Bung Kan',9);
/*!40000 ALTER TABLE `states` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `adress` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `birth_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `date_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enabled` bit(1) NOT NULL,
  `full_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `identity_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `issued_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `log_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `photos` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `work_user` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `degrees` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `maritals` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `sexs` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (48,'Đăk lăk','Đăk lăk','2022-07-12','2022-07-12','nguyen1623@gmail.com',_binary '','nghia','546124','Đăk Lăk',NULL,'$2a$10$8Q9XZN.CP9zKq2cmcGzzBesgIi7H45iJOdefz32KzF9.5pL0NRnva','0368023380','user.png','2023-02-01 12:20:21.992000','2023-03-19 01:16:46.956000','Nghia','E03','U03','S02'),(52,'Hà Nội','Đăk lăk','2022-06-27','2022-07-04','admin@admin.com',_binary '','HUYNH Y','123456478','Đăk Lăk',NULL,'$2a$10$ophEGSQuL7UUq1nREvKoMuB5hDpuFRVj6dzSBhparCOCUn3dQfaEe','0368023380','vga galax.png','2022-08-12 15:01:43.211000','2023-03-27 13:32:17.694000','HUYNH Y','E01','U01','S01'),(53,'Đăk lăk','Đăk lăk','2022-07-11','2022-06-26','edit@edit.com',_binary '','Edit','123456','Đăk Lăk',NULL,'$2a$10$9EA8ecAnwk1s.cEvulPLz.aDfHNZFVvLw/T4IK9e4GkRWFNcMq1hO','123456','user.png','2022-08-19 14:49:22.329000','2023-03-19 01:16:28.429000','Nghia','E01','U01','S01'),(56,'Hà Nội','Đăk lăk','2022-06-26','2022-06-26','assistant@assistant.com',_binary '','Assistant','12456','Đăk Lăk',NULL,'$2a$10$JAg1dDWCkd0HtY2iUX1S5Oa8QptKhU/U.3jlNbiUI9M/NsOcUd7n2','123456','user.png','2022-08-22 09:22:12.806000','2023-03-27 14:31:27.017000','HUYNH Y','E01','U01','S01'),(59,'Bình Dương','Bình Dương','2023-02-08','2000-02-09','salesperson@salesperson.com',_binary '','Sale','02348416269','Bình Dương',NULL,'$2a$10$BfgdrUkOxyki2CdtON6aR.nXPiZGpaZJKMw7Rhii8zV/88JIGB9bu','0368023380','user.png','2023-02-17 00:34:00.856000','2023-03-19 01:17:18.951000','Nghia','E01','U01','S01'),(61,'Bình Dương','Bình Dương','2023-03-22','2023-03-06','ship@admin.com',_binary '','Shiper','1254316567','Bình Dương',NULL,'$2a$10$JyiPvSmFKH5fNy4bnfAy3eYkfBZ5RmeKx2b6aQVVJ5Qo/fqQmMIyG','0368023380','user.png','2023-03-19 23:09:06.547000','2023-03-19 23:09:06.547000','Nghia','E01','U01','S01'),(62,'Bình Dương','Bình Dương','2023-03-06','2023-03-10','test@admin.com',_binary '','Test','1234679','Bình Dương',NULL,'$2a$10$8PDHf/eN1VLiSWu1tWJXq.IfCNpBsxcTu6iHT1LbCRGlSjT8LnRsq','0368023380','user.png','2023-03-19 23:13:50.943000','2023-03-19 23:13:50.943000','Nghia','E01','U01','S01');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (52,1),(59,2),(48,3),(53,3),(61,4),(62,4),(56,5);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-25 23:19:00
