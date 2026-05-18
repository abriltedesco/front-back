-- MySQL dump 10.13  Distrib 8.0.45, for Linux (x86_64)
--
-- Host: localhost    Database: ComidaApp
-- ------------------------------------------------------
-- Server version	8.0.45-0ubuntu0.24.04.1

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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `tipo` varchar(31) NOT NULL,
  `dni` int NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `contrasenia` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` int DEFAULT NULL,
  `ciudad` varchar(255) DEFAULT NULL,
  `dir_entrega` varchar(255) DEFAULT NULL,
  `disponible` bit(1) DEFAULT NULL,
  PRIMARY KEY (`dni`),
  CONSTRAINT `usuario_chk_1` CHECK ((`tipo` in (_utf8mb4'Usuario',_utf8mb4'ADMIN',_utf8mb4'CLIENTE',_utf8mb4'REPARTIDOR'))),
  CONSTRAINT `usuario_chk_2` CHECK (((`tipo` <> _utf8mb4'ADMIN') or (`telefono` is not null))),
  CONSTRAINT `usuario_chk_3` CHECK (((`tipo` <> _utf8mb4'CLIENTE') or (`telefono` is not null))),
  CONSTRAINT `usuario_chk_4` CHECK (((`tipo` <> _utf8mb4'REPARTIDOR') or ((`telefono` is not null) and (`disponible` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('CLIENTE',47865498,'Lesserteseur','$2a$10$iK4YhpCZm.b2HMJ0BC39wOB3FY.aCH5Oi6ptdDjbONKv.oBDTp.HG','eugeee@gmail.com','Euge',1123456789,NULL,NULL,NULL),('CLIENTE',98765432,'tedesco','$2a$10$QqBwRSux2vB3WbCy8eXbAOtVlEL9GQcRAkC5qwTdUz0qMDmNofLjS','abyby@gmail.com','abril',1165249869,NULL,NULL,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-18  9:12:57
