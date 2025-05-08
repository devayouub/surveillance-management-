-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: surveillance_data_base
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `cycle`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cycle` (
  `ID_cycle` int NOT NULL,
  `nom_cycle` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID_cycle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cycle`
--

LOCK TABLES `cycle` WRITE;
/*!40000 ALTER TABLE `cycle` DISABLE KEYS */;
INSERT INTO `cycle` VALUES (1,'LMD'),(2,'ING');
/*!40000 ALTER TABLE `cycle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam` (
  `ID_exam` int NOT NULL AUTO_INCREMENT,
  `exam_heure` varchar(20) DEFAULT NULL,
  `exam_date` date DEFAULT NULL,
  `mnémonique` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID_exam`),
  KEY `mnémonique` (`mnémonique`),
  CONSTRAINT `fk_mnemonic` FOREIGN KEY (`mnémonique`) REFERENCES `module` (`mnémonique`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` VALUES (1,'08:00-9:30','2025-05-12','POO');
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examen_salle`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examen_salle` (
  `id_exam` int DEFAULT NULL,
  `nom_salle` varchar(50) DEFAULT NULL,
  KEY `fk_examen` (`id_exam`),
  KEY `fk_salle` (`nom_salle`),
  CONSTRAINT `fk_examen` FOREIGN KEY (`id_exam`) REFERENCES `exam` (`ID_exam`),
  CONSTRAINT `fk_salle` FOREIGN KEY (`nom_salle`) REFERENCES `salle` (`nom_salle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examen_salle`
--

LOCK TABLES `examen_salle` WRITE;
/*!40000 ALTER TABLE `examen_salle` DISABLE KEYS */;
/*!40000 ALTER TABLE `examen_salle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examiner`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examiner` (
  `ID_exam` int DEFAULT NULL,
  `ID_prof` int DEFAULT NULL,
  `is_present` tinyint(1) DEFAULT NULL,
  KEY `ID_prof` (`ID_prof`),
  KEY `fk_exam_id` (`ID_exam`),
  CONSTRAINT `examiner_ibfk_3` FOREIGN KEY (`ID_prof`) REFERENCES `professor` (`ID_prof`),
  CONSTRAINT `fk_exam_id` FOREIGN KEY (`ID_exam`) REFERENCES `exam` (`ID_exam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examiner`
--

LOCK TABLES `examiner` WRITE;
/*!40000 ALTER TABLE `examiner` DISABLE KEYS */;
INSERT INTO `examiner` VALUES (1,7,NULL),(1,7,NULL);
/*!40000 ALTER TABLE `examiner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `module` (
  `mnémonique` varchar(10) NOT NULL,
  PRIMARY KEY (`mnémonique`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES ('ANGLAIS'),('BDD'),('POO'),('SI'),('THG'),('THL');
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor` (
  `ID_prof` int NOT NULL AUTO_INCREMENT,
  `nom_prof` varchar(30) NOT NULL,
  `prenom_prof` varchar(30) NOT NULL,
  `email_prof` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID_prof`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor`
--

LOCK TABLES `professor` WRITE;
/*!40000 ALTER TABLE `professor` DISABLE KEYS */;
INSERT INTO `professor` VALUES (7,'Medjahed','Ayoub',''),(8,'MAROUF','FAWZI',''),(9,'KHAROURI','ZAKI',''),(10,'AKACEM','AYMEN',''),(11,'AEIMOUR','RAHIM',''),(12,'AMMARI','BASSET',''),(14,'9OL HAH','AYOUB',''),(15,'9OL HAH','fawzi','');
/*!40000 ALTER TABLE `professor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salle`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salle` (
  `nom_salle` varchar(5) NOT NULL,
  `minProf` int DEFAULT NULL,
  `maxProf` int DEFAULT NULL,
  PRIMARY KEY (`nom_salle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salle`
--

LOCK TABLES `salle` WRITE;
/*!40000 ALTER TABLE `salle` DISABLE KEYS */;
INSERT INTO `salle` VALUES ('A3',NULL,NULL);
/*!40000 ALTER TABLE `salle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semester_module`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `semester_module` (
  `domain_id` int NOT NULL,
  `semester_no` int NOT NULL,
  `module_id` varchar(50) NOT NULL,
  PRIMARY KEY (`domain_id`,`semester_no`,`module_id`),
  KEY `module_id` (`module_id`),
  CONSTRAINT `semester_module_ibfk_1` FOREIGN KEY (`domain_id`) REFERENCES `spécialité` (`ID_spécialité`),
  CONSTRAINT `semester_module_ibfk_2` FOREIGN KEY (`module_id`) REFERENCES `module` (`mnémonique`),
  CONSTRAINT `semester_module_chk_1` CHECK ((`semester_no` in (1,2)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semester_module`
--

LOCK TABLES `semester_module` WRITE;
/*!40000 ALTER TABLE `semester_module` DISABLE KEYS */;
INSERT INTO `semester_module` VALUES (10,2,'ANGLAIS'),(10,2,'BDD'),(10,2,'POO'),(10,2,'SI'),(10,2,'THG'),(10,2,'THL');
/*!40000 ALTER TABLE `semester_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spécialité`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spécialité` (
  `ID_spécialité` int NOT NULL AUTO_INCREMENT,
  `nom_spécialité` varchar(50) DEFAULT NULL,
  `cycle_id` int NOT NULL,
  PRIMARY KEY (`ID_spécialité`),
  KEY `fk_cycle_id` (`cycle_id`),
  CONSTRAINT `fk_cycle_id` FOREIGN KEY (`cycle_id`) REFERENCES `cycle` (`ID_cycle`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spécialité`
--

LOCK TABLES `spécialité` WRITE;
/*!40000 ALTER TABLE `spécialité` DISABLE KEYS */;
INSERT INTO `spécialité` VALUES (9,'ING1',2),(10,'ING2',2),(11,'L1',1),(14,'L3-SI',1);
/*!40000 ALTER TABLE `spécialité` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `surveillance`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `surveillance` (
  `ID_prof` int NOT NULL,
  `nom_salle` varchar(50) NOT NULL,
  `exam` int DEFAULT NULL,
  PRIMARY KEY (`ID_prof`,`nom_salle`),
  KEY `nom_salle` (`nom_salle`),
  KEY `fk_exam` (`exam`),
  CONSTRAINT `fk_exam` FOREIGN KEY (`exam`) REFERENCES `exam` (`ID_exam`),
  CONSTRAINT `fk_ExamenSalle` FOREIGN KEY (`nom_salle`) REFERENCES `examen_salle` (`nom_salle`),
  CONSTRAINT `fk_prof` FOREIGN KEY (`ID_prof`) REFERENCES `examiner` (`ID_prof`)
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

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID_user` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `is_admin` tinyint(1) DEFAULT NULL,
  `remember_me` tinyint(1) DEFAULT NULL,
  `user_password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_user`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (4,'Ayoub',1,1,'Ayoub2005');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'surveillance_data_base'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-07 23:26:56
