-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: libreriadb
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `autorelibri`
--

DROP TABLE IF EXISTS `autorelibri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `autorelibri` (
  `id` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `Cognome` varchar(45) DEFAULT NULL,
  `NazioneResidenza` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autorelibri`
--

LOCK TABLES `autorelibri` WRITE;
/*!40000 ALTER TABLE `autorelibri` DISABLE KEYS */;
INSERT INTO `autorelibri` VALUES (4,'Luca','Grandi','Italia'),(5,'Gianni','Barbarossa','Spagna'),(9,'Jake','McAllen','Italia'),(14,'Pippoo','Baudo','Italia');
/*!40000 ALTER TABLE `autorelibri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorialibri`
--

DROP TABLE IF EXISTS `categorialibri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorialibri` (
  `categoria` varchar(45) NOT NULL,
  PRIMARY KEY (`categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorialibri`
--

LOCK TABLES `categorialibri` WRITE;
/*!40000 ALTER TABLE `categorialibri` DISABLE KEYS */;
INSERT INTO `categorialibri` VALUES ('Azione'),('Divertente'),('Fantasy');
/*!40000 ALTER TABLE `categorialibri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libri`
--

DROP TABLE IF EXISTS `libri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libri` (
  `isbn` int NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(45) DEFAULT NULL,
  `Descrizione` varchar(45) DEFAULT NULL,
  `Categoria` varchar(45) DEFAULT NULL,
  `Prezzo` int DEFAULT NULL,
  `nCopie` int DEFAULT NULL,
  `idAutore` int NOT NULL,
  PRIMARY KEY (`isbn`),
  KEY `fk_categoriaLibro_idx` (`Categoria`),
  CONSTRAINT `fk_categoriaLibro` FOREIGN KEY (`Categoria`) REFERENCES `categorialibri` (`categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libri`
--

LOCK TABLES `libri` WRITE;
/*!40000 ALTER TABLE `libri` DISABLE KEYS */;
INSERT INTO `libri` VALUES (1,'ForrestGump','Bello','Divertente',11,430,5),(7,'HarryPotter','Per bambini','Fantasy',550,50,9),(18,'It','Pauroso','Fantasy',11,40,9),(22,'WebDesign','Interessante','Fantasy',14,20,4),(26,'LaBestia','Pauroso','Fantasy',11,40,9),(27,'LaBestia','Pauroso','Fantasy',11,40,9),(28,'LaBestia','Pauroso','Fantasy',11,40,9),(29,'Il codice ','Bello bello','Fantasy',40,40,4),(30,'Il codice ','Fantasy','Fantasy',40,40,4),(31,'In fonndo al mar','Per bambini','Fantasy',40,0,9),(32,'HarryPotter','Per bambini','Fantasy',50,0,9),(34,'HarryPotter','Per bambini','Fantasy',550,0,9),(35,'HarryPotter','Per bambini','Fantasy',550,0,9);
/*!40000 ALTER TABLE `libri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logweboperation`
--

DROP TABLE IF EXISTS `logweboperation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logweboperation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dataOra` date DEFAULT NULL,
  `URLRequest` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logweboperation`
--

LOCK TABLES `logweboperation` WRITE;
/*!40000 ALTER TABLE `logweboperation` DISABLE KEYS */;
/*!40000 ALTER TABLE `logweboperation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `magazzino`
--

DROP TABLE IF EXISTS `magazzino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `magazzino` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantita` int DEFAULT NULL,
  `statoStock` varchar(45) DEFAULT NULL,
  `isbn` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Magazzino_Libri_idx` (`isbn`),
  CONSTRAINT `fk_Magazzino_Libri` FOREIGN KEY (`isbn`) REFERENCES `libri` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazzino`
--

LOCK TABLES `magazzino` WRITE;
/*!40000 ALTER TABLE `magazzino` DISABLE KEYS */;
INSERT INTO `magazzino` VALUES (11,70,'richiesto',18),(14,860,'richiesto',22),(30,50,'richiesto',35);
/*!40000 ALTER TABLE `magazzino` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-04 11:24:24
