-- MySQL dump 10.13  Distrib 9.3.0, for macos15.2 (arm64)
--
-- Host: 127.0.0.1    Database: hanarowa
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `Advice`
--

DROP TABLE IF EXISTS `Advice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Advice` (
  `category` tinyint NOT NULL,
  `endedAt` datetime DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `memberId` bigint NOT NULL,
  `startedAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Advice_Member` (`memberId`),
  CONSTRAINT `fk_Advice_Member` FOREIGN KEY (`memberId`) REFERENCES `Member` (`id`),
  CONSTRAINT `advice_chk_1` CHECK ((`category` between 0 and 3))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Advice`
--

LOCK TABLES `Advice` WRITE;
/*!40000 ALTER TABLE `Advice` DISABLE KEYS */;
/*!40000 ALTER TABLE `Advice` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Branch`
--

LOCK TABLES `Branch` WRITE;
/*!40000 ALTER TABLE `Branch` DISABLE KEYS */;
INSERT INTO `Branch` VALUES (1,1,'02-1000-1000','을지로','서울 중구 을지로 170 을지트윈타워 2층'),(2,1,'0507-1475-1525','서초동','서울 서초구 서초대로 286 서초프라자 2층'),(3,1,'0507-1466-4117','선릉역','서울 강남구 테헤란로 401 1층'),(4,1,'0507-1482-3013','영등포','서울 영등포구 영등포동 4가 65-1 7층'),(5,10,'053-3000-1000','천안','충남 천안시 동남구 버들로 17'),(6,9,'033-252-2255','춘천','강원특별자치 춘천시 시청길 3'),(7,6,'042-252-7800','대전','대전 중구 대종로 481'),(8,1,'02-533-1111','방배서래','서울 서초구 방배로 268');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Curriculum`
--

LOCK TABLES `Curriculum` WRITE;
/*!40000 ALTER TABLE `Curriculum` DISABLE KEYS */;
INSERT INTO `Curriculum` VALUES ('2025-08-28 11:35:27',1,1,'2025-08-28 11:35:27','스마트폰 기본기 다지기'),('2025-08-28 11:35:27',2,1,'2025-08-28 11:35:27','앱 설치와 활용'),('2025-08-28 11:35:27',3,2,'2025-08-28 11:35:27','인사말 배우기'),('2025-08-28 11:35:27',4,2,'2025-08-28 11:35:27','여행 영어'),('2025-08-28 11:35:27',5,3,'2025-08-28 11:35:27','주식 용어 기초'),('2025-08-28 11:35:27',6,3,'2025-08-28 11:35:27','투자 전략 세우기'),('2025-08-28 11:35:27',7,4,'2025-08-28 11:35:27','기초 요가 자세'),('2025-08-28 11:35:27',8,4,'2025-08-28 11:35:27','호흡과 명상'),('2025-08-28 11:35:27',9,5,'2025-08-28 11:35:27','조선시대 역사'),('2025-08-28 11:35:27',10,5,'2025-08-28 11:35:27','근현대사 이해');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Facility`
--

LOCK TABLES `Facility` WRITE;
/*!40000 ALTER TABLE `Facility` DISABLE KEYS */;
INSERT INTO `Facility` VALUES (1,1,'헬스장','헬스할 수 있는 공간'),(1,2,'독서실','조용히 책 읽을 수 있는 공간'),(2,3,'컴퓨터실','굉장히 빠른 컴퓨터'),(2,4,'댄스연습실','댄스댄스~!'),(3,5,'탁구장','즐거운 탁구'),(4,6,'노래방','노래로 스트레스 풀기'),(5,7,'작은도서관','책으로 교양 쌓기'),(6,8,'세미나룸','진지한 대화'),(7,9,'피트니스룸','운동으로 땀 흘리기'),(8,10,'강당','강당에서 모여봐요');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FacilityImage`
--

LOCK TABLES `FacilityImage` WRITE;
/*!40000 ALTER TABLE `FacilityImage` DISABLE KEYS */;
INSERT INTO `FacilityImage` VALUES (1,1,'gym1.png'),(2,2,'studyroom1.png'),(3,3,'pcroom1.png'),(4,4,'dance1.png'),(5,5,'pingpong1.png'),(6,6,'karaoke1.png'),(7,7,'library1.png'),(8,8,'seminar1.png'),(9,9,'fitness1.png'),(10,10,'auditorium1.png');
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
  `startedAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FacilityTime_Facility` (`facilityId`),
  KEY `fk_FacilityTime_Member` (`memberId`),
  CONSTRAINT `fk_FacilityTime_Facility` FOREIGN KEY (`facilityId`) REFERENCES `Facility` (`id`),
  CONSTRAINT `fk_FacilityTime_Member` FOREIGN KEY (`memberId`) REFERENCES `Member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FacilityTime`
--

LOCK TABLES `FacilityTime` WRITE;
/*!40000 ALTER TABLE `FacilityTime` DISABLE KEYS */;
INSERT INTO `FacilityTime` VALUES ('2025-08-29 14:00:00',1,1,1,'2025-08-29 13:00:00'),('2025-08-28 02:00:00',1,2,2,'2025-08-28 01:00:00'),('2025-08-29 12:00:00',1,3,3,'2025-08-29 11:00:00'),('2025-08-30 13:00:00',1,4,4,'2025-08-30 11:00:00'),('2025-08-27 01:13:58',1,5,5,'2025-08-27 01:13:58'),('2025-08-31 01:13:58',1,6,6,'2025-08-31 01:13:58'),('2025-08-28 01:13:58',7,7,7,'2025-08-28 01:13:58'),('2025-08-28 01:13:58',8,8,8,'2025-08-28 01:13:58'),('2025-08-28 01:13:58',9,9,9,'2025-08-28 01:13:58'),('2025-08-28 01:13:58',10,10,10,'2025-08-28 01:13:58');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Lesson`
--

LOCK TABLES `Lesson` WRITE;
/*!40000 ALTER TABLE `Lesson` DISABLE KEYS */;
INSERT INTO `Lesson` VALUES (1,1,1,'김강사','스마트폰 기초','시니어 대상 스마트폰 기초 수업','스마트폰 사용법 배우기','lesson1.png','DIGITAL'),(2,2,2,'이강사','영어회화','기초 영어회화 학습','영어 말하기','lesson2.png','LANGUAGE'),(3,3,3,'박강사','주식투자','금융 초보자를 위한 주식 강의','투자 기초','lesson3.png','FINANCE'),(4,4,4,'정강사','요가','건강을 위한 요가','체형 교정','lesson4.png','HEALTH'),(5,5,5,'최강사','한국사','문화 이해를 위한 한국사','역사 이야기','lesson5.png','CULTURE'),(6,6,6,'조강사','트렌드 읽기','사회 트렌드 분석','최신 유행','lesson6.png','TREND'),(7,7,7,'한강사','금융 문해력','시니어 금융 이해 교육','가계 재무 관리','lesson7.png','FINANCE'),(8,8,8,'김댄스','실버 댄스','즐겁게 배우는 댄스','댄스 배우기','lesson8.png','HEALTH'),(8,9,8,'이중국','중국어 기초','중국어 왕초보 과정','중국어 입문','lesson9.png','LANGUAGE'),(8,10,8,'박사진','사진 촬영','취미로 배우는 사진 강좌','카메라 사용법','lesson10.png','OTHERS');
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
  `duration` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lessonState` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `fk_LessonGisu_Lesson` (`lessonId`),
  KEY `fk_LessonGisu_LessonRoom` (`lessonRoomId`),
  CONSTRAINT `fk_LessonGisu_Lesson` FOREIGN KEY (`lessonId`) REFERENCES `Lesson` (`id`),
  CONSTRAINT `fk_LessonGisu_LessonRoom` FOREIGN KEY (`lessonRoomId`) REFERENCES `LessonRoom` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LessonGisu`
--

LOCK TABLES `LessonGisu` WRITE;
/*!40000 ALTER TABLE `LessonGisu` DISABLE KEYS */;
INSERT INTO `LessonGisu` VALUES (20,50000,NULL,1,1,1,'2025-08-28 11:35:27','2025-01-01~2025-02-28','PENDING'),(15,60000,NULL,2,2,2,'2025-08-28 11:35:27','2025-01-05~2025-03-05','PENDING'),(25,70000,NULL,3,3,3,'2025-08-28 11:35:27','2025-02-01~2025-03-31','PENDING'),(30,40000,NULL,4,4,4,'2025-08-28 11:35:27','2025-03-01~2025-04-30','PENDING'),(10,30000,NULL,5,5,5,'2025-08-28 11:35:27','2025-03-15~2025-04-15','PENDING'),(12,20000,NULL,6,6,6,'2025-08-28 11:35:27','2025-04-01~2025-05-30','PENDING'),(18,45000,NULL,7,7,7,'2025-08-28 11:35:27','2025-04-10~2025-06-10','PENDING'),(22,35000,NULL,8,8,8,'2025-08-28 11:35:27','2025-05-01~2025-06-30','PENDING'),(16,55000,NULL,9,9,9,'2025-08-28 11:35:27','2025-05-15~2025-07-15','PENDING'),(20,50000,NULL,10,10,10,'2025-08-28 11:35:27','2025-06-01~2025-07-31','PENDING');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LessonRoom`
--

LOCK TABLES `LessonRoom` WRITE;
/*!40000 ALTER TABLE `LessonRoom` DISABLE KEYS */;
INSERT INTO `LessonRoom` VALUES (1,1,'101호'),(1,2,'202호'),(2,3,'컴퓨터실1'),(2,4,'댄스홀'),(3,5,'강의실A'),(4,6,'강의실B'),(5,7,'대강당'),(6,8,'스터디룸'),(7,9,'체육관'),(8,10,'세미나실');
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Member`
--

LOCK TABLES `Member` WRITE;
/*!40000 ALTER TABLE `Member` DISABLE KEYS */;
INSERT INTO `Member` VALUES ('1998-01-01',1,NULL,1,NULL,'siyoung@hana.com','김시영','$2a$10$NtzZVS1HLn/HondkNYJ5Q.IlE3iaAs5cbt4NALTMBocpLuTuPUPnq','010-1111-1111',NULL,NULL,'USERS'),('1997-02-02',2,NULL,2,NULL,'soeun@hana.com','정소은','$2a$10$NtzZVS1HLn/HondkNYJ5Q.IlE3iaAs5cbt4NALTMBocpLuTuPUPnq','010-2222-2222',NULL,NULL,'USERS'),('1996-03-03',2,NULL,3,NULL,'youngkyun@hana.com','박영균','$2a$10$NtzZVS1HLn/HondkNYJ5Q.IlE3iaAs5cbt4NALTMBocpLuTuPUPnq','010-3333-3333',NULL,'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJwYXNzd29yZCI6IiIsInJvbGUiOiJBRE1JTiIsImVtYWlsIjoieW91bmdreXVuQGhhbmEuY29tIiwiaWF0IjoxNzU2MzgwOTQzLCJleHAiOjE3NTY0NjczNDN9.Y1nEyHVr4B3gdMOuLk_u54E6TTEvn3N-4ea-4iL14HoaXuUZr1tdpeUwGqYZsWxf','ADMIN'),('1995-04-04',3,NULL,4,NULL,'yumin@hana.com','이유민','$2a$10$NtzZVS1HLn/HondkNYJ5Q.IlE3iaAs5cbt4NALTMBocpLuTuPUPnq','010-4444-4444',NULL,NULL,'USERS'),('1999-05-05',3,NULL,5,NULL,'chaeyoung@hana.com','채영','$2a$10$NtzZVS1HLn/HondkNYJ5Q.IlE3iaAs5cbt4NALTMBocpLuTuPUPnq','010-5555-5555',NULL,NULL,'USERS'),('1998-06-06',4,NULL,6,NULL,'jaeyoon@hana.com','재윤','$2a$10$NtzZVS1HLn/HondkNYJ5Q.IlE3iaAs5cbt4NALTMBocpLuTuPUPnq','010-6666-6666',NULL,NULL,'USERS'),('1985-07-07',5,NULL,7,NULL,'gildong@hana.com','홍길동','$2a$10$NtzZVS1HLn/HondkNYJ5Q.IlE3iaAs5cbt4NALTMBocpLuTuPUPnq','010-7777-7777',NULL,NULL,'USERS'),('1980-08-08',6,NULL,8,NULL,'gamchan@hana.com','강감찬','$2a$10$NtzZVS1HLn/HondkNYJ5Q.IlE3iaAs5cbt4NALTMBocpLuTuPUPnq','010-8888-8888',NULL,NULL,'ADMIN'),('1975-09-09',7,NULL,9,NULL,'soonshin@hana.com','이순신','$2a$10$NtzZVS1HLn/HondkNYJ5Q.IlE3iaAs5cbt4NALTMBocpLuTuPUPnq','010-9999-9999',NULL,NULL,'USERS'),('1970-10-10',8,NULL,10,NULL,'sejong@hana.com','세종대왕','$2a$10$NtzZVS1HLn/HondkNYJ5Q.IlE3iaAs5cbt4NALTMBocpLuTuPUPnq','010-1010-1010',NULL,NULL,'USERS'),(NULL,NULL,NULL,11,NULL,'admin@gmail.com','admin','$2a$10$gpMtAfvGO1gMSkSRrQCoJukFGMc8ottyvfLJJQ7d62TILRovky8WO',NULL,NULL,NULL,'ADMIN');
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
  PRIMARY KEY (`id`),
  KEY `fk_MyLesson_LessonGisu` (`lessonGisuId`),
  KEY `fk_MyLesson_Member` (`memberId`),
  CONSTRAINT `fk_MyLesson_LessonGisu` FOREIGN KEY (`lessonGisuId`) REFERENCES `LessonGisu` (`id`),
  CONSTRAINT `fk_MyLesson_Member` FOREIGN KEY (`memberId`) REFERENCES `Member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MyLesson`
--

LOCK TABLES `MyLesson` WRITE;
/*!40000 ALTER TABLE `MyLesson` DISABLE KEYS */;
INSERT INTO `MyLesson` VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4),(5,5,5),(6,6,6),(7,7,7),(8,8,8),(9,9,9),(10,10,10);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Review`
--

LOCK TABLES `Review` WRITE;
/*!40000 ALTER TABLE `Review` DISABLE KEYS */;
INSERT INTO `Review` VALUES (5,1,1,1,'아주 유익했어요!'),(4,2,2,2,'강사님이 친절했어요.'),(5,3,3,3,'많이 배웠습니다.'),(3,4,4,4,'조금 어려웠어요.'),(4,5,5,5,'좋은 강의였습니다.'),(5,6,6,6,'추천합니다.'),(4,7,7,7,'재밌었습니다.'),(5,8,8,8,'유익했어요.'),(3,9,9,9,'좀 지루했어요.'),(5,10,10,10,'최고의 강의!');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RoomTime`
--

LOCK TABLES `RoomTime` WRITE;
/*!40000 ALTER TABLE `RoomTime` DISABLE KEYS */;
INSERT INTO `RoomTime` VALUES ('2025-08-28 11:35:27',1,1,'2025-08-28 11:35:27'),('2025-08-28 11:35:27',2,2,'2025-08-28 11:35:27'),('2025-08-28 11:35:27',3,3,'2025-08-28 11:35:27'),('2025-08-28 11:35:27',4,4,'2025-08-28 11:35:27'),('2025-08-28 11:35:27',5,5,'2025-08-28 11:35:27'),('2025-08-28 11:35:27',6,6,'2025-08-28 11:35:27'),('2025-08-28 11:35:27',7,7,'2025-08-28 11:35:27'),('2025-08-28 11:35:27',8,8,'2025-08-28 11:35:27'),('2025-08-28 11:35:27',9,9,'2025-08-28 11:35:27'),('2025-08-28 11:35:27',10,10,'2025-08-28 11:35:27');
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

-- Dump completed on 2025-08-29 13:26:40
