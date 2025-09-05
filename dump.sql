-- MySQL dump 10.13  Distrib 8.4.5, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hanarowa
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `Branch`
--

DROP TABLE IF EXISTS `Branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Branch` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `locationId` bigint NOT NULL,
  `telNumber` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKltpjifhco2l8sl8on5ngqtox6` (`telNumber`),
  KEY `fk_Branch_Location` (`locationId`),
  CONSTRAINT `fk_Branch_Location` FOREIGN KEY (`locationId`) REFERENCES `Location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Branch`
--

LOCK TABLES `Branch` WRITE;
/*!40000 ALTER TABLE `Branch` DISABLE KEYS */;
INSERT INTO `Branch` VALUES (1,1,'02-1000-1000','을지로 더넥스트','서울 중구 을지로 170 을지트윈타워 2층'),(2,1,'0507-1475-1525','서초 더넥스트','서울 서초구 서초대로 286 서초프라자 2층'),(3,1,'0507-1466-4117','선릉 더넥스트','서울 강남구 테헤란로 401 1층'),(4,1,'0507-1482-3013','영등포 더넥스트','서울 영등포구 영등포동 4가 65-1 7층'),(5,1,'02-533-1111','방배 더넥스트','서울 서초구 방배로 268'),(6,9,'033-252-2255','컬처뱅크','강원특별자치 춘천시 시청길 3'),(7,6,'042-252-7800','50+컬처뱅크','대전 중구 대종로 481'),(8,10,'053-3000-1000','컬처뱅크','충남 천안시 동남구 버들로 17');
/*!40000 ALTER TABLE `Branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Curriculum`
--

DROP TABLE IF EXISTS `Curriculum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Curriculum` (
  `endedAt` datetime DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lessonGisuId` bigint NOT NULL,
  `startedAt` datetime NOT NULL,
  `content` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Curriculum_LessonGisu` (`lessonGisuId`),
  CONSTRAINT `fk_Curriculum_LessonGisu` FOREIGN KEY (`lessonGisuId`) REFERENCES `LessonGisu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Curriculum`
--

LOCK TABLES `Curriculum` WRITE;
/*!40000 ALTER TABLE `Curriculum` DISABLE KEYS */;
INSERT INTO `Curriculum` VALUES (NULL,1,1,'2025-09-05 10:16:23','사진과 카메라의 역사'),(NULL,2,1,'2025-09-05 10:16:23','카메라 구조에 대한 기초적인 이해'),(NULL,3,1,'2025-09-05 10:16:23','풍경화 잘 찍는 방법'),(NULL,4,1,'2025-09-05 10:16:23','인물 잘 찍는 방법'),(NULL,5,2,'2025-09-05 10:50:34','꽃꽂이 1차'),(NULL,6,2,'2025-09-05 10:50:34','꽃꽂이 2차'),(NULL,7,2,'2025-09-05 10:50:34','꽃꽂이 3차'),(NULL,8,2,'2025-09-05 10:50:34','꽃꽂이 4차'),(NULL,9,3,'2025-09-05 11:05:26','연극1'),(NULL,10,3,'2025-09-05 11:05:26','연극2'),(NULL,11,3,'2025-09-05 11:05:26','연극3'),(NULL,12,3,'2025-09-05 11:05:26','연극4');
/*!40000 ALTER TABLE `Curriculum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Facility`
--

DROP TABLE IF EXISTS `Facility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Facility` (
  `branchId` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Facility_Branch` (`branchId`),
  CONSTRAINT `fk_Facility_Branch` FOREIGN KEY (`branchId`) REFERENCES `Branch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Facility`
--

LOCK TABLES `Facility` WRITE;
/*!40000 ALTER TABLE `Facility` DISABLE KEYS */;
INSERT INTO `Facility` VALUES (6,1,'시네마룸','편안하게 영화를 볼 수 있는 공간'),(6,2,'독서실','조용히 책 읽을 수 있는 공간'),(6,3,'크리에이터실','굉장히 빠른 컴퓨터'),(7,4,'음악감상실','우아하게 음악 감상'),(7,5,'라운지','고급스러운 인테리어'),(7,6,'세미나실','자유로운 회의 분위기');
/*!40000 ALTER TABLE `Facility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FacilityImage`
--

DROP TABLE IF EXISTS `FacilityImage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FacilityImage` (
  `facilityId` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `facilityImage` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FacilityImage_Facility` (`facilityId`),
  CONSTRAINT `fk_FacilityImage_Facility` FOREIGN KEY (`facilityId`) REFERENCES `Facility` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FacilityImage`
--

LOCK TABLES `FacilityImage` WRITE;
/*!40000 ALTER TABLE `FacilityImage` DISABLE KEYS */;
INSERT INTO `FacilityImage` VALUES (1,1,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/cinemaroom.png'),(2,2,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/library.jpg'),(3,3,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/createrroom.jpeg'),(4,4,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/musicroom.jpg'),(5,5,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/cinemaroom.png'),(6,6,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/lounge.jpeg');
/*!40000 ALTER TABLE `FacilityImage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FacilityTime`
--

DROP TABLE IF EXISTS `FacilityTime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FacilityTime` (
  `endedAt` datetime DEFAULT NULL,
  `facilityId` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `memberId` bigint NOT NULL,
  `reservedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `startedAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FacilityTime_Facility` (`facilityId`),
  KEY `fk_FacilityTime_Member` (`memberId`),
  CONSTRAINT `fk_FacilityTime_Facility` FOREIGN KEY (`facilityId`) REFERENCES `Facility` (`id`),
  CONSTRAINT `fk_FacilityTime_Member` FOREIGN KEY (`memberId`) REFERENCES `Member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FacilityTime`
--

LOCK TABLES `FacilityTime` WRITE;
/*!40000 ALTER TABLE `FacilityTime` DISABLE KEYS */;
INSERT INTO `FacilityTime` VALUES ('2025-09-08 12:00:00',1,1,4,'2025-09-05 00:25:45','2025-09-08 11:00:00'),('2025-09-07 11:00:00',2,2,4,'2025-09-05 00:27:17','2025-09-07 09:00:00'),('2025-09-06 10:00:00',3,3,1,'2025-09-05 00:34:03','2025-09-06 09:00:00');
/*!40000 ALTER TABLE `FacilityTime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Lesson`
--

DROP TABLE IF EXISTS `Lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Lesson` (
  `branchId` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `memberId` bigint DEFAULT NULL,
  `openedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `instructor` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lessonName` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `instruction` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lessonImg` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category` enum('CULTURE','DIGITAL','FINANCE','HEALTH','LANGUAGE','OTHERS','TREND') COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Lesson_Branch` (`branchId`),
  KEY `fk_Lesson_Member` (`memberId`),
  CONSTRAINT `fk_Lesson_Branch` FOREIGN KEY (`branchId`) REFERENCES `Branch` (`id`),
  CONSTRAINT `fk_Lesson_Member` FOREIGN KEY (`memberId`) REFERENCES `Member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Lesson`
--

LOCK TABLES `Lesson` WRITE;
/*!40000 ALTER TABLE `Lesson` DISABLE KEYS */;
INSERT INTO `Lesson` VALUES (6,1,2,'2025-09-05 01:16:23','김지안','디지털 카메라 기초 완성','카메라의 구조에 대해 열심히 공부해봐요~!','한국대학교 사진학과 전공\r\n사랑스튜디오 운영\r\n사진경력 15년','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/9023db41-c228-4128-a8da-8b277fac5e08.png','DIGITAL'),(6,2,4,'2025-09-05 01:50:34','최은우','꽃꽂이 강좌','꽃꽂이를 하면서 우리 마음을 치유해봅시다','플로리스트 경력 10년\r\n아름다운 꽃집 운영중\r\n전국 꽃꽂이 대회 은상 수상','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/e39bc20f-e914-454b-9c51-a3264871a814.jpg','CULTURE'),(6,3,4,'2025-09-05 02:05:25','최은우','연극','연극에 대해 배워보고 우리의 표정을 배워봐요...','연극 경력 20년','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/728a1a9a-990e-435e-9e6a-820e70914544.jpg','CULTURE');
/*!40000 ALTER TABLE `Lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LessonGisu`
--

DROP TABLE IF EXISTS `LessonGisu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LessonGisu` (
  `capacity` int NOT NULL,
  `lessonFee` int NOT NULL,
  `endedAt` datetime DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lessonId` bigint NOT NULL,
  `lessonRoomId` bigint NOT NULL,
  `startedAt` datetime NOT NULL,
  `duration` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lessonState` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `fk_LessonGisu_Lesson` (`lessonId`),
  KEY `fk_LessonGisu_LessonRoom` (`lessonRoomId`),
  CONSTRAINT `fk_LessonGisu_Lesson` FOREIGN KEY (`lessonId`) REFERENCES `Lesson` (`id`),
  CONSTRAINT `fk_LessonGisu_LessonRoom` FOREIGN KEY (`lessonRoomId`) REFERENCES `LessonRoom` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LessonGisu`
--

LOCK TABLES `LessonGisu` WRITE;
/*!40000 ALTER TABLE `LessonGisu` DISABLE KEYS */;
INSERT INTO `LessonGisu` VALUES (10,0,NULL,1,1,1,'2025-09-05 10:16:23','2025-09-08~2025-09-15 tue-thu 09:00-10:00','REJECTED'),(7,200000,NULL,2,2,1,'2025-09-05 10:50:34','2025-09-06~2025-09-13 weekend 09:00-10:00','REJECTED'),(15,50000,NULL,3,3,1,'2025-09-05 11:05:26','2025-09-08~2025-09-15 tue-thu 11:00-13:00','APPROVED');
/*!40000 ALTER TABLE `LessonGisu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LessonRoom`
--

DROP TABLE IF EXISTS `LessonRoom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LessonRoom` (
  `branchId` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_LessonRoom_Branch` (`branchId`),
  CONSTRAINT `fk_LessonRoom_Branch` FOREIGN KEY (`branchId`) REFERENCES `Branch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LessonRoom`
--

LOCK TABLES `LessonRoom` WRITE;
/*!40000 ALTER TABLE `LessonRoom` DISABLE KEYS */;
INSERT INTO `LessonRoom` VALUES (6,1,'101호'),(6,2,'202호'),(6,3,'컴퓨터실1'),(7,4,'댄스홀'),(7,5,'강의실A'),(7,6,'강의실B');
/*!40000 ALTER TABLE `LessonRoom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Location`
--

LOCK TABLES `Location` WRITE;
/*!40000 ALTER TABLE `Location` DISABLE KEYS */;
INSERT INTO `Location` VALUES (1,'서울'),(2,'부산'),(3,'대구'),(4,'인천'),(5,'광주'),(6,'대전'),(7,'울산'),(8,'세종'),(9,'춘천'),(10,'천안');
/*!40000 ALTER TABLE `Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Member`
--

DROP TABLE IF EXISTS `Member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Member` (
  `birth` date DEFAULT NULL,
  `branchId` bigint DEFAULT NULL,
  `deletedAt` datetime DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `provider` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phoneNumber` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `providerId` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `refreshToken` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` enum('ADMIN','USERS') COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Member_Branch` (`branchId`),
  CONSTRAINT `fk_Member_Branch` FOREIGN KEY (`branchId`) REFERENCES `Branch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Member`
--

LOCK TABLES `Member` WRITE;
/*!40000 ALTER TABLE `Member` DISABLE KEYS */;
INSERT INTO `Member` VALUES ('1992-05-18',6,NULL,1,NULL,'hana1@hana.com','박서준','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-3456-7890',NULL,NULL,'USERS'),('1999-11-23',6,NULL,2,NULL,'hana2@hana.com','김지안','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-9876-5432',NULL,NULL,'USERS'),('2001-08-04',6,NULL,3,NULL,'admin@hana.com','이하윤','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-2233-4455',NULL,'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6ImFkbWluQGhhbmEuY29tIiwiaWF0IjoxNzU3MDM1MTExLCJleHAiOjE3NTcxMjE1MTF9.e-mRAineSlfJXRLGSSzmn1zxodcb38IremDWdo70dJSa91Y-uXqJPtUcpjB8aoDf','ADMIN'),('1997-02-11',6,NULL,4,NULL,'hana4@hana.com','최은우','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-6677-8899',NULL,'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6ImhhbmE0QGhhbmEuY29tIiwiaWF0IjoxNzU3MDM2ODc0LCJleHAiOjE3NTcxMjMyNzR9.ZojvNXGFaxwy5xTTTaWrwp31FnB_Mz8WByzR6vEZaibwT7jxUoyD0_erwJ4IDukV','USERS'),('2003-01-30',7,NULL,5,NULL,'hana5@hana.com','정서아','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-1100-2211',NULL,NULL,'USERS'),('1994-09-15',7,NULL,6,NULL,'hana6@hana.com','강도윤','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-3344-5566',NULL,NULL,'USERS'),('1998-07-22',7,NULL,7,NULL,'hana7@hana.com','윤지우','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-7788-9900',NULL,NULL,'USERS'),('2000-12-01',7,NULL,8,NULL,'hana8@hana.com','임시원','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-1230-4567',NULL,NULL,'USERS');
/*!40000 ALTER TABLE `Member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MyLesson`
--

DROP TABLE IF EXISTS `MyLesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MyLesson` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lessonGisuId` bigint NOT NULL,
  `memberId` bigint NOT NULL,
  `openedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_MyLesson_LessonGisu` (`lessonGisuId`),
  KEY `fk_MyLesson_Member` (`memberId`),
  CONSTRAINT `fk_MyLesson_LessonGisu` FOREIGN KEY (`lessonGisuId`) REFERENCES `LessonGisu` (`id`),
  CONSTRAINT `fk_MyLesson_Member` FOREIGN KEY (`memberId`) REFERENCES `Member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MyLesson`
--

LOCK TABLES `MyLesson` WRITE;
/*!40000 ALTER TABLE `MyLesson` DISABLE KEYS */;
/*!40000 ALTER TABLE `MyLesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Review`
--

DROP TABLE IF EXISTS `Review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Review` (
  `rating` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lessonGisuId` bigint NOT NULL,
  `memberId` bigint NOT NULL,
  `reviewTxt` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Review_LessonGisu` (`lessonGisuId`),
  KEY `fk_Review_Member` (`memberId`),
  CONSTRAINT `fk_Review_LessonGisu` FOREIGN KEY (`lessonGisuId`) REFERENCES `LessonGisu` (`id`),
  CONSTRAINT `fk_Review_Member` FOREIGN KEY (`memberId`) REFERENCES `Member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Review`
--

LOCK TABLES `Review` WRITE;
/*!40000 ALTER TABLE `Review` DISABLE KEYS */;
/*!40000 ALTER TABLE `Review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RoomTime`
--

DROP TABLE IF EXISTS `RoomTime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RoomTime` (
  `endedAt` datetime DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lessonRoomId` bigint NOT NULL,
  `startedAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_RoomTime_LessonRoom` (`lessonRoomId`),
  CONSTRAINT `fk_RoomTime_LessonRoom` FOREIGN KEY (`lessonRoomId`) REFERENCES `LessonRoom` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RoomTime`
--

LOCK TABLES `RoomTime` WRITE;
/*!40000 ALTER TABLE `RoomTime` DISABLE KEYS */;
INSERT INTO `RoomTime` VALUES ('2025-09-09 10:00:00',1,1,'2025-09-09 09:00:00'),('2025-09-10 10:00:00',2,1,'2025-09-10 09:00:00'),('2025-09-11 10:00:00',3,1,'2025-09-11 09:00:00'),('2025-09-08 10:00:00',4,1,'2025-09-08 09:00:00'),('2025-09-09 13:00:00',5,1,'2025-09-09 11:00:00'),('2025-09-10 13:00:00',6,1,'2025-09-10 11:00:00'),('2025-09-11 13:00:00',7,1,'2025-09-11 11:00:00');
/*!40000 ALTER TABLE `RoomTime` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-05 11:22:22
