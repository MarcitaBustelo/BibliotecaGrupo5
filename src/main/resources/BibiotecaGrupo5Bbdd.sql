CREATE DATABASE  IF NOT EXISTS `biblioteca5` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `biblioteca5`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: biblioteca5
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_available` bit(1) NOT NULL,
  `title` varchar(255) NOT NULL,
  `year_published` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKg0286ag1dlt4473st1ugemd0m` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,' Antoine de Saint-Exupéry','Fantasy','el_principito.jpg',_binary '','El principito','1943-04-19'),(2,'Joana Marcus','Romance','etereo.jpg',_binary '','Etéreo','2024-11-21'),(3,'Freida McFadden','Mystery','La_asistenta.png',_binary '\0','La asistenta','2023-10-05'),(4,'Joana Marcus','Romance','antes_de_diciembre.jpg',_binary '','Antes de diciembre','2021-11-11'),(5,'Shari Lapena','Murder','la_pareja_de_al_lado.jpg',_binary '\0','La pareja de al lado','2016-07-14'),(6,'John Boyne','Non-Fiction','boywiththestriped.jpg',_binary '','El niño del pijama de rayas ','2006-01-05'),(7,'Anna Todd','Romance','after.jpg',_binary '','After','2014-10-21'),(8,'Juan Ramon Jimenez','Fiction','Platero-y-yo.jpg',_binary '','Platero y yo','1914-01-01'),(9,'Sarah J.Mass','Fantasy','una_corte_de_rosas_y_espinas.jpg',_binary '','Una corte de rosas y espinas','2024-03-06'),(10,'Mercedes Ron','Romance','marfil.jpg',_binary '\0','Marfil','2021-03-01');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan` (
  `id` bigint NOT NULL,
  `deleted` bit(1) NOT NULL,
  `due_date` date DEFAULT NULL,
  `initial_date` date DEFAULT NULL,
  `id_book` bigint NOT NULL,
  `id_user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK10ffxmthqlnpchvthtivt9jp2` (`id_book`),
  KEY `FKnx6y4sq2u7xecyn0yqiwm05br` (`id_user`),
  CONSTRAINT `FK10ffxmthqlnpchvthtivt9jp2` FOREIGN KEY (`id_book`) REFERENCES `book` (`id`),
  CONSTRAINT `FKnx6y4sq2u7xecyn0yqiwm05br` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES (1,_binary '\0','2025-02-02','2025-01-19',10,2),(2,_binary '','2025-02-02','2025-01-19',3,2),(3,_binary '','2025-02-02','2025-01-19',1,3),(52,_binary '','2025-02-02','2025-01-19',1,3),(102,_binary '','2025-02-02','2025-01-19',1,2),(152,_binary '\0','2025-02-02','2025-01-19',5,3),(153,_binary '\0','2025-02-02','2025-01-19',3,3);
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_seq`
--

DROP TABLE IF EXISTS `loan_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_seq`
--

LOCK TABLES `loan_seq` WRITE;
/*!40000 ALTER TABLE `loan_seq` DISABLE KEYS */;
INSERT INTO `loan_seq` VALUES (251);
/*!40000 ALTER TABLE `loan_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reservation` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `id_book` bigint NOT NULL,
  `id_user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaj8oy1p3oq7x9c8genjbv1bwd` (`id_book`),
  KEY `FKb3e2n5wgmx0lvpg0dvxjf7n1l` (`id_user`),
  CONSTRAINT `FKaj8oy1p3oq7x9c8genjbv1bwd` FOREIGN KEY (`id_book`) REFERENCES `book` (`id`),
  CONSTRAINT `FKb3e2n5wgmx0lvpg0dvxjf7n1l` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,'2025-01-19','pending',1,2);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `activated` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,_binary '','admin@admin.com','admin','admin','$2a$10$XDFx8DvPvuSJDDEupg8Ej.1d.SRXV.QqmEgePp86qgi19W3mD305O','ROLE_ADMIN'),(2,_binary '','anaprat26@gmail.com','Prat','Ana','$2a$10$qTVG.9RhCIRqkhyWSV6VsuY6j1CKQmp21r8W7zwKMlab/Env7TxHq','ROLE_USER'),(3,_binary '','marcitabuxtelo@gmail.com','Bustelo','Marcita','$2a$10$O4UFFOXO3o4fLzKzlREMdeHlt3hy0jYrK62AGqNYPjgj3tUkmlqFC','ROLE_USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_seq`
--

DROP TABLE IF EXISTS `user_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_seq`
--

LOCK TABLES `user_seq` WRITE;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` VALUES (101);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-19 20:06:15
