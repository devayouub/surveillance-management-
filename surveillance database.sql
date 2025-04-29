CREATE DATABASE  IF NOT EXISTS `surveillance_data_base` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `surveillance_data_base`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: surveillance_data_base
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `contain`
--

DROP TABLE IF EXISTS `contain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contain` (
  `num_niveau` int NOT NULL,
  `ID_spécialité` int NOT NULL,
  PRIMARY KEY (`num_niveau`,`ID_spécialité`),
  KEY `ID_spécialité` (`ID_spécialité`),
  CONSTRAINT `contain_ibfk_1` FOREIGN KEY (`num_niveau`) REFERENCES `niveau` (`num_niveau`),
  CONSTRAINT `contain_ibfk_2` FOREIGN KEY (`ID_spécialité`) REFERENCES `spécialité` (`ID_spécialité`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contain`
--

LOCK TABLES `contain` WRITE;
/*!40000 ALTER TABLE `contain` DISABLE KEYS */;
/*!40000 ALTER TABLE `contain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cycle`
--

DROP TABLE IF EXISTS `cycle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cycle` (
  `ID_cycle` int NOT NULL,
  `nom_cycle` varchar(20) DEFAULT NULL,
  `num_niveau` int DEFAULT NULL,
  PRIMARY KEY (`ID_cycle`),
  KEY `num_niveau` (`num_niveau`),
  CONSTRAINT `cycle_ibfk_1` FOREIGN KEY (`num_niveau`) REFERENCES `niveau` (`num_niveau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cycle`
--

LOCK TABLES `cycle` WRITE;
/*!40000 ALTER TABLE `cycle` DISABLE KEYS */;
/*!40000 ALTER TABLE `cycle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam` (
  `ID_exam` int NOT NULL,
  `exam_heure` time DEFAULT NULL,
  `exam_duré` time DEFAULT NULL,
  `exam_date` date DEFAULT NULL,
  `mnémonique` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID_exam`),
  KEY `mnémonique` (`mnémonique`),
  CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`mnémonique`) REFERENCES `module` (`mnémonique`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examiner`
--

DROP TABLE IF EXISTS `examiner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examiner` (
  `ID_exam` int NOT NULL,
  `nom_salle` varchar(20) NOT NULL,
  PRIMARY KEY (`ID_exam`,`nom_salle`),
  KEY `nom_salle` (`nom_salle`),
  CONSTRAINT `examiner_ibfk_1` FOREIGN KEY (`ID_exam`) REFERENCES `exam` (`ID_exam`),
  CONSTRAINT `examiner_ibfk_2` FOREIGN KEY (`nom_salle`) REFERENCES `salle` (`nom_salle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examiner`
--

LOCK TABLES `examiner` WRITE;
/*!40000 ALTER TABLE `examiner` DISABLE KEYS */;
/*!40000 ALTER TABLE `examiner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `module` (
  `mnémonique` varchar(10) NOT NULL,
  `nom_module` varchar(30) DEFAULT NULL,
  `num_semestre` int DEFAULT NULL,
  PRIMARY KEY (`mnémonique`),
  KEY `num_semestre` (`num_semestre`),
  CONSTRAINT `module_ibfk_1` FOREIGN KEY (`num_semestre`) REFERENCES `semestre` (`num_semestre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `niveau`
--

DROP TABLE IF EXISTS `niveau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `niveau` (
  `num_niveau` int NOT NULL,
  `nom_niveau` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`num_niveau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `niveau`
--

LOCK TABLES `niveau` WRITE;
/*!40000 ALTER TABLE `niveau` DISABLE KEYS */;
/*!40000 ALTER TABLE `niveau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor`
--

DROP TABLE IF EXISTS `professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor` (
  `ID_prof` int NOT NULL,
  `nom_prof` varchar(30) NOT NULL,
  `prenom_prof` varchar(30) NOT NULL,
  `email_prof` varchar(40) DEFAULT NULL,
  `surveillanced_heur` time DEFAULT NULL,
  PRIMARY KEY (`ID_prof`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor`
--

LOCK TABLES `professor` WRITE;
/*!40000 ALTER TABLE `professor` DISABLE KEYS */;
/*!40000 ALTER TABLE `professor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salle`
--

DROP TABLE IF EXISTS `salle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salle` (
  `nom_salle` varchar(5) NOT NULL,
  `nbr_surveillant` int DEFAULT NULL,
  `type_salle` int DEFAULT NULL,
  PRIMARY KEY (`nom_salle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salle`
--

LOCK TABLES `salle` WRITE;
/*!40000 ALTER TABLE `salle` DISABLE KEYS */;
/*!40000 ALTER TABLE `salle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semestre`
--

DROP TABLE IF EXISTS `semestre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `semestre` (
  `num_semestre` int NOT NULL,
  `ID_spécialité` int DEFAULT NULL,
  PRIMARY KEY (`num_semestre`),
  KEY `ID_spécialité` (`ID_spécialité`),
  CONSTRAINT `semestre_ibfk_1` FOREIGN KEY (`ID_spécialité`) REFERENCES `spécialité` (`ID_spécialité`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semestre`
--

LOCK TABLES `semestre` WRITE;
/*!40000 ALTER TABLE `semestre` DISABLE KEYS */;
/*!40000 ALTER TABLE `semestre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spécialité`
--

DROP TABLE IF EXISTS `spécialité`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spécialité` (
  `ID_spécialité` int NOT NULL,
  `nom_spécialité` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_spécialité`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spécialité`
--

LOCK TABLES `spécialité` WRITE;
/*!40000 ALTER TABLE `spécialité` DISABLE KEYS */;
/*!40000 ALTER TABLE `spécialité` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `surveillance`
--

DROP TABLE IF EXISTS `surveillance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `surveillance` (
  `ID_prof` int NOT NULL,
  `nom_salle` varchar(50) NOT NULL,
  `surveillance_heure` time DEFAULT NULL,
  PRIMARY KEY (`ID_prof`,`nom_salle`),
  KEY `nom_salle` (`nom_salle`),
  CONSTRAINT `surveillance_ibfk_1` FOREIGN KEY (`ID_prof`) REFERENCES `professor` (`ID_prof`),
  CONSTRAINT `surveillance_ibfk_2` FOREIGN KEY (`nom_salle`) REFERENCES `salle` (`nom_salle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `surveillance`
--

LOCK TABLES `surveillance` WRITE;
/*!40000 ALTER TABLE `surveillance` DISABLE KEYS */;
/*!40000 ALTER TABLE `surveillance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID_user` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `user_prenom` varchar(30) NOT NULL,
  `is_admin` tinyint(1) DEFAULT NULL,
  `remember_me` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-24 15:15:26
