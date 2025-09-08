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
  `telNumber` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
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
INSERT INTO `Branch` VALUES (1,1,'02-1000-1000','을지로 더넥스트','서울 중구 을지로 170 을지트윈타워 2층'),(2,1,'0507-1475-1525','서초 더넥스트','서울 서초구 서초대로 286 서초프라자 2층'),(3,1,'0507-1466-4117','선릉 더넥스트','서울 강남구 테헤란로 401 1층'),(4,1,'0507-1482-3013','영등포 더넥스트','서울 영등포구 영등포동 4가 65-1 7층'),(5,1,'02-533-1111','방배서래','서울 서초구 방배로 268'),(6,9,'033-252-2255','컬처뱅크','강원특별자치 춘천시 시청길 3'),(7,6,'042-252-7800','50+컬처뱅크','대전 중구 대종로 481'),(8,10,'053-3000-1000','컬처뱅크','충남 천안시 동남구 버들로 17');
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
  `content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Curriculum_LessonGisu` (`lessonGisuId`),
  CONSTRAINT `fk_Curriculum_LessonGisu` FOREIGN KEY (`lessonGisuId`) REFERENCES `LessonGisu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Curriculum`
--

LOCK TABLES `Curriculum` WRITE;
/*!40000 ALTER TABLE `Curriculum` DISABLE KEYS */;
INSERT INTO `Curriculum` VALUES (NULL,1,1,'2025-09-05 17:15:34','사진에 대한 기본 이해'),(NULL,2,1,'2025-09-05 17:15:34','카메라 구조 '),(NULL,3,1,'2025-09-05 17:15:34','사진 구도에 대한 이해 '),(NULL,4,1,'2025-09-05 17:15:34','사진 잘 찍는 방법 전수'),(NULL,5,2,'2025-09-05 17:21:12','연극 기초'),(NULL,6,2,'2025-09-05 17:21:12','눈물 연기 해봐요'),(NULL,10,4,'2025-09-06 23:42:54','꽃꽂이 실습 1'),(NULL,11,4,'2025-09-06 23:42:54','꽃꽂이 실습 2'),(NULL,12,4,'2025-09-06 23:42:54','꽃꽂이 실습 3'),('2025-09-01 10:00:00',13,5,'2025-09-01 09:00:00','시집 읽기'),(NULL,14,6,'2025-09-07 15:41:51','퇴직연금 운용법'),(NULL,15,6,'2025-09-07 15:41:51','안전한 투자 방법'),(NULL,16,6,'2025-09-07 15:41:51','핫한 코인, 이게 맞을까?'),(NULL,17,6,'2025-09-07 15:41:51','1:1 금융 맞춤 솔루션 제공'),(NULL,18,7,'2025-09-07 16:09:46','와인의 역사 및 기본 예의'),(NULL,19,7,'2025-09-07 16:09:46','와인 시음 1'),(NULL,20,7,'2025-09-07 16:09:46','와인 시음 2'),(NULL,21,7,'2025-09-07 16:09:46','와인 시음 3'),(NULL,22,8,'2025-09-07 16:11:58','한강 계속 달리기 '),(NULL,24,10,'2025-09-07 16:43:58','스테이블 코인 1'),(NULL,25,10,'2025-09-07 16:43:58','스테이블 코인2'),(NULL,26,11,'2025-09-15 14:00:00','윤동주, 하늘과 바람과 별과 시'),(NULL,27,11,'2025-09-15 14:00:00','한강, 소년이 온다 읽기'),(NULL,28,11,'2025-09-15 14:00:00','한강, 채식주의자'),(NULL,29,11,'2025-09-15 14:00:00','파스칼, 사랑바다'),(NULL,30,11,'2025-09-15 14:00:00','김소월, 진달래꽃'),(NULL,31,15,'2025-09-08 13:20:15','배추김치 담그기'),(NULL,32,15,'2025-09-08 13:20:15','총각김치 담그기'),(NULL,37,18,'2025-09-08 14:22:25','노후 브랜딩이란? 시니어의 브랜딩 필요성'),(NULL,38,18,'2025-09-08 14:22:25','1:1 맞춤 노후 브랜딩 솔루션 제공');
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
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Facility_Branch` (`branchId`),
  CONSTRAINT `fk_Facility_Branch` FOREIGN KEY (`branchId`) REFERENCES `Branch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Facility`
--

LOCK TABLES `Facility` WRITE;
/*!40000 ALTER TABLE `Facility` DISABLE KEYS */;
INSERT INTO `Facility` VALUES (6,1,'시네마룸','편안하게 영화를 볼 수 있는 공간'),(6,2,'독서실','조용히 책 읽을 수 있는 공간'),(6,3,'크리에이터실','굉장히 빠른 컴퓨터'),(7,4,'음악감상실','우아하게 음악 감상'),(7,5,'라운지','고급스러운 인테리어'),(7,6,'세미나실','자유로운 회의 분위기'),(6,7,'세미나실','쾌적한 회의실'),(1,8,'을지로 라운지','누구나 편안하게 쉴 수 있는 공간'),(1,9,'더넥스트 상담창구','누구나 무료 상담! 예약제, 상품 권유 X'),(2,10,'더넥스트 라운지','하나은행 전문인력으로 은퇴 설계 시스템을 체계적으로!'),(2,11,'연금더드림 라운지','연금자산 종합 컨설팅');
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
  `facilityImage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FacilityImage_Facility` (`facilityId`),
  CONSTRAINT `fk_FacilityImage_Facility` FOREIGN KEY (`facilityId`) REFERENCES `Facility` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FacilityImage`
--

LOCK TABLES `FacilityImage` WRITE;
/*!40000 ALTER TABLE `FacilityImage` DISABLE KEYS */;
INSERT INTO `FacilityImage` VALUES (1,1,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/cinemaroom.png'),(2,2,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/library.jpg'),(3,3,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/createrroom.jpeg'),(4,4,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/musicroom.jpg'),(5,5,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/lounge.jpeg'),(6,6,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/seminarroom.jpg'),(7,7,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/seminarroom.jpg'),(8,8,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/lounge.jpeg'),(9,9,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/lounge2.jpeg'),(10,10,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/lounge2.jpeg'),(11,11,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/lounge.jpeg'),(1,12,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/cinemaroom2.jpeg'),(1,13,'https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/sinemaroom3.jpg');
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FacilityTime`
--

LOCK TABLES `FacilityTime` WRITE;
/*!40000 ALTER TABLE `FacilityTime` DISABLE KEYS */;
INSERT INTO `FacilityTime` VALUES ('2025-09-08 12:00:00',1,1,4,'2025-09-05 08:12:47','2025-09-08 10:00:00'),('2025-09-08 17:00:00',5,2,6,'2025-09-07 07:14:33','2025-09-08 15:00:00'),('2025-09-08 13:00:00',6,3,6,'2025-09-07 07:14:47','2025-09-08 12:00:00'),('2025-09-09 14:00:00',1,4,1,'2025-09-07 07:15:35','2025-09-09 12:00:00'),('2025-09-08 18:00:00',2,5,2,'2025-09-07 07:15:47','2025-09-08 16:00:00'),('2025-09-10 11:00:00',3,6,4,'2025-09-07 07:15:58','2025-09-10 09:00:00'),('2025-09-08 17:00:00',1,7,2,'2025-09-07 07:16:09','2025-09-08 15:00:00'),('2025-09-02 10:00:00',1,8,1,'2025-09-01 10:00:00','2025-09-02 09:00:00'),('2025-08-25 14:00:00',2,9,1,'2025-08-20 10:00:00','2025-08-25 13:00:00'),('2025-09-10 16:00:00',1,11,9,'2025-09-08 04:50:31','2025-09-10 14:00:00');
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
  `instructor` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lessonName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `instruction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `lessonImg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category` enum('CULTURE','DIGITAL','FINANCE','HEALTH','LANGUAGE','OTHERS','TREND') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Lesson_Branch` (`branchId`),
  KEY `fk_Lesson_Member` (`memberId`),
  CONSTRAINT `fk_Lesson_Branch` FOREIGN KEY (`branchId`) REFERENCES `Branch` (`id`),
  CONSTRAINT `fk_Lesson_Member` FOREIGN KEY (`memberId`) REFERENCES `Member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Lesson`
--

LOCK TABLES `Lesson` WRITE;
/*!40000 ALTER TABLE `Lesson` DISABLE KEYS */;
INSERT INTO `Lesson` VALUES (6,1,4,'2025-09-10 08:15:33','최은우','디지털 카메라 기초 완성','카메라와 사진 구도에 대해 이해하고, 더 좋은 사진을 찍기 위한 강의를 진행합니다.','한국대학교 사진학과 전공\r\n선데이 스튜디오 운영\r\n사진작가 경력 15년','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/031b90e8-9bce-41d0-9c72-8bd2d669ec8a.png','DIGITAL'),(6,2,4,'2025-09-05 08:21:12','최은우','연극 수업','다함께 연기 한 번 해봐요','연극 관련한 경력은 없지만 열심히 가르치겠습니다!','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/f38d925a-baa2-49d0-a0c9-0667ff9d0e6c.jpg','CULTURE'),(6,4,1,'2025-09-06 14:42:53','박서준','꽃꽂이','꽃꽂이를 하는 방법을 알려드리겠습니다','꽃집 운영 10년','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/5b566203-b11a-4b4f-b71f-bc8f8cff6d5b.jpg','CULTURE'),(6,5,7,'2025-09-01 09:00:00','이연자','독서 교실','독서를 통한 마음 기르기','한국대학교 국어국문학과 전공','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/library2.jpg','LANGUAGE'),(6,6,2,'2025-09-07 06:41:50','김지안','퇴직연금 운용방법','시니어의 퇴직연금을 어떻게 하면 효율적으로 운용할 수 있을지 강의해드리겠습니다.','금융 강의 경력 15년\r\n하나은행 근무 20년\r\n\"시니어 금융\" 책 출간','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/845195c3-e850-4c90-a466-0d9af61f2364.jpg','FINANCE'),(7,7,6,'2025-09-07 07:09:45','안대성','와인 소믈리에','와인에 대한 깊은 이해 및 와인을 어떻게 하면 더 풍미있게 즐길 수 있는지 알려드리는 수업입니다.','한국대학교 소믈리에 학과 전공\r\n소믈리에 경력 10년\r\n소믈리에 자격증 보유','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/885eb480-246d-4d44-a543-b913d421b398.png','CULTURE'),(7,8,6,'2025-09-07 07:11:58','강도윤','내 무릎 지키면서 러닝','뛰는 방법이 중요합니다. 어떻게 하면\r\n무릎 안아프게 뛰는지 알려드리겠습니다','러닝을 사랑하는 사람입니다\r\n우리 모두 다같이 뛰어보아요','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/7a51507d-8dcf-4feb-88cd-1f781abc1acf.jpg','HEALTH'),(6,10,3,'2025-09-07 07:43:58','박하나','스테이블 코인','요즘 핫한 스테이블 코인에 대해 설명해드리겠습니다','하나은행 전문 강사\r\n스테이블 코인 전문가','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/seminar.jpg','FINANCE'),(6,11,NULL,'2025-09-07 08:40:53','선복자','대화하는 방법','대화를 잘하는 것은 매우 중요합니다. 그걸 어떻게 하는지 이번 강의에서 가져가세요.','대화잘하는 방법 책 저자','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/gang2.jpg','OTHERS'),(6,12,NULL,'2025-09-07 08:40:53','이주은','DIY 비누만들기','천연 아로마로 건강하고 향기로운 비누 만들어봐요!','비누만들기 장인','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/soap.jpeg','CULTURE'),(6,13,NULL,'2025-09-07 08:40:53','강형욱','강아지와 함께 산책하기','강아지와 건강하게 산책하는 방법','자타공인 명실상부 강아지 전문가','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/dog.jpeg','HEALTH'),(6,14,9,'2025-09-08 04:20:15','김시영','맛있는 전라도식 김치 레시피 전수','배추김치, 총각김치를 어떻게 하면 맛있게 만들 수 있는지 자세히 알려드릴게요~','종갓집 며느리 40년\r\n사랑의 김장 봉사 10회 이상 참여','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/79100406-73ed-4335-9d45-2130ecd21e13.jpg','OTHERS'),(6,17,3,'2025-09-08 05:22:25','정은주','시니어 노후 브랜딩','38만 유튜버의 은퇴 후 삶을 위한 노후 브랜딩 노하우를 알려드리겠습니다. ','38만 유튜버\r\n유튜브 연 수익 1억 달성\r\n브랜딩 관련 강의 경험 다수','https://hanarowa-upload.s3.ap-northeast-2.amazonaws.com/uploads/9c281324-9dd9-49a0-a74d-3db0eca948f3.png','TREND');
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
  `duration` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `lessonState` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `fk_LessonGisu_Lesson` (`lessonId`),
  KEY `fk_LessonGisu_LessonRoom` (`lessonRoomId`),
  CONSTRAINT `fk_LessonGisu_Lesson` FOREIGN KEY (`lessonId`) REFERENCES `Lesson` (`id`),
  CONSTRAINT `fk_LessonGisu_LessonRoom` FOREIGN KEY (`lessonRoomId`) REFERENCES `LessonRoom` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LessonGisu`
--

LOCK TABLES `LessonGisu` WRITE;
/*!40000 ALTER TABLE `LessonGisu` DISABLE KEYS */;
INSERT INTO `LessonGisu` VALUES (5,50000,NULL,1,1,1,'2025-09-10 17:15:34','2025-09-10~2025-09-15 tue-thu 10:00-11:00','APPROVED'),(10,10000,NULL,2,2,1,'2025-09-05 17:21:12','2025-09-20~2025-09-24 mon-wed 09:00-10:00','REJECTED'),(20,60000,NULL,4,4,1,'2025-09-06 23:42:54','2025-09-07~2025-09-13 mon-wed-fri 10:00-11:00','REJECTED'),(12,35000,'2025-09-03 10:00:00',5,5,1,'2025-09-01 08:00:00','2025-09-01~2025-09-03 mon-wed 09:00-10:00','APPROVED'),(12,80000,NULL,6,6,1,'2025-09-07 15:41:51','2025-09-08~2025-09-17 mon-wed 13:00-14:00','APPROVED'),(8,100000,NULL,7,7,4,'2025-09-07 16:09:46','2025-09-13~2025-10-04 tue-thu 16:00-17:00','APPROVED'),(10,10000,NULL,8,8,4,'2025-09-07 16:11:58','2025-09-14~2025-10-26 weekend 09:00-10:00','APPROVED'),(20,20000,NULL,10,10,1,'2025-09-07 16:43:58','2025-09-22~2025-09-26 mon-fri 10:00-11:00','APPROVED'),(10,35000,'2025-09-19 14:00:00',11,5,2,'2025-09-15 14:00:00','2025-09-15~2025-09-19 mon-fri 14:00-15:00','APPROVED'),(30,10000,'2025-10-19 14:00:00',12,11,2,'2025-10-15 14:00:00','2025-10-15~2025-10-19 mon-fri 14:00-15:00','APPROVED'),(7,50000,'2025-10-12 14:00:00',13,12,2,'2025-10-08 14:00:00','2025-10-08~2025-10-12 mon-fri 14:00-15:00','APPROVED'),(5,100000,'2025-10-12 14:00:00',14,13,1,'2025-10-08 14:00:00','2025-10-08~2025-10-12 mon-fri 14:00-15:00','APPROVED'),(6,20000,NULL,15,14,1,'2025-09-08 13:20:15','2025-09-15~2025-09-19 tue-thu 11:00-12:00','APPROVED'),(10,10000,NULL,18,17,3,'2025-09-08 14:22:25','2025-09-23~2025-09-25 tue-thu 15:00-16:00','APPROVED');
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
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_LessonRoom_Branch` (`branchId`),
  CONSTRAINT `fk_LessonRoom_Branch` FOREIGN KEY (`branchId`) REFERENCES `Branch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LessonRoom`
--

LOCK TABLES `LessonRoom` WRITE;
/*!40000 ALTER TABLE `LessonRoom` DISABLE KEYS */;
INSERT INTO `LessonRoom` VALUES (6,1,'101호'),(6,2,'202호'),(6,3,'컴퓨터실1'),(7,4,'101호'),(7,5,'102호'),(7,6,'103호'),(1,7,'101호'),(1,8,'202호'),(2,9,'101호'),(2,10,'202호'),(3,11,'101호'),(3,12,'202호'),(4,13,'101호'),(4,14,'202호'),(5,15,'101호'),(5,16,'202호'),(8,17,'101호'),(8,18,'202호');
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
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
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
  `provider` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phoneNumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `providerId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `refreshToken` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` enum('ADMIN','USERS') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Member_Branch` (`branchId`),
  CONSTRAINT `fk_Member_Branch` FOREIGN KEY (`branchId`) REFERENCES `Branch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Member`
--

LOCK TABLES `Member` WRITE;
/*!40000 ALTER TABLE `Member` DISABLE KEYS */;
INSERT INTO `Member` VALUES ('1958-05-18',6,NULL,1,NULL,'hana1@hana.com','조준식','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-3456-7890',NULL,NULL,'USERS'),('1962-11-23',6,NULL,2,NULL,'hana2@hana.com','이옥순','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-9876-5432',NULL,NULL,'USERS'),('2001-08-04',6,NULL,3,NULL,'admin@hana.com','이지은','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-2233-4455',NULL,'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6ImFkbWluQGhhbmEuY29tIiwiaWF0IjoxNzU3MzEzMzkyLCJleHAiOjE3NTczOTk3OTJ9.6BkpZyLd-SrT9JgxOV8Zp56tIsasApgE1uBWwHVSxB6vqar8zhmLGoXGM1uGukyP','ADMIN'),('1970-02-11',6,NULL,4,NULL,'hana4@hana.com','김순자','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-6677-8899',NULL,'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6ImhhbmE0QGhhbmEuY29tIiwiaWF0IjoxNzU3MjMyNTcwLCJleHAiOjE3NTczMTg5NzB9.4cfTX8ae0y8-F3yECWGQyhzzsQSWWww8XpSmK8klKAN7R3BEXSNVxQnhrPUHL8jj','USERS'),('1980-01-30',7,NULL,5,NULL,'hana5@hana.com','정춘배','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-1100-2211',NULL,NULL,'USERS'),('1978-09-15',7,NULL,6,NULL,'hana6@hana.com','이병찬','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-3344-5566',NULL,NULL,'USERS'),('1960-07-22',7,NULL,7,NULL,'hana7@hana.com','이연자','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-7788-9900',NULL,NULL,'USERS'),('1965-12-01',7,NULL,8,NULL,'hana8@hana.com','임환철','$2a$10$xyPNpTClLg6TyThLu4Y7UO30xs8/7dl59R/pOCzlgOLUPucONsHDO','010-1230-4567',NULL,NULL,'USERS'),('1975-08-02',6,NULL,9,'naver','tp1ksy@naver.com','김시영',NULL,'010-1234-1234','8bEkAwyn8omiLV6rVcGPtSx3vgEikZ_TaKuIP1S-qLk','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InRwMWtzeUBuYXZlci5jb20iLCJpYXQiOjE3NTczMTM2NTksImV4cCI6MTc1NzQwMDA1OX0.YvI-fS2MgaivDvpYoOfiwe5r1JBl7pbkxxmFZbhCVkCXTh7VzHfzktr8f9hkH_2o','USERS');
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MyLesson`
--

LOCK TABLES `MyLesson` WRITE;
/*!40000 ALTER TABLE `MyLesson` DISABLE KEYS */;
INSERT INTO `MyLesson` VALUES (1,1,1,'2025-09-06 15:32:05'),(2,5,1,'2025-09-06 16:03:57'),(3,5,2,'2025-09-07 06:43:53'),(4,1,2,'2025-09-07 06:44:10'),(5,10,4,'2025-09-07 07:47:09'),(6,1,4,'2025-09-07 07:47:21'),(7,6,4,'2025-09-07 07:47:29'),(8,6,2,'2025-09-07 07:48:35'),(9,7,2,'2025-09-07 07:49:30'),(10,8,2,'2025-09-07 07:49:35'),(11,5,4,'2025-09-01 14:00:00'),(12,11,9,'2025-09-08 04:56:27'),(13,8,8,'2025-09-08 06:08:26'),(14,18,8,'2025-09-08 06:08:35'),(15,11,8,'2025-09-08 06:08:49'),(16,1,8,'2025-09-08 06:09:35'),(17,6,8,'2025-09-08 06:10:00'),(18,8,7,'2025-09-08 06:10:19'),(19,1,7,'2025-09-08 06:10:31'),(20,18,7,'2025-09-08 06:10:51'),(21,6,7,'2025-09-08 06:11:08');
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
  `reviewTxt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Review_LessonGisu` (`lessonGisuId`),
  KEY `fk_Review_Member` (`memberId`),
  CONSTRAINT `fk_Review_LessonGisu` FOREIGN KEY (`lessonGisuId`) REFERENCES `LessonGisu` (`id`),
  CONSTRAINT `fk_Review_Member` FOREIGN KEY (`memberId`) REFERENCES `Member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Review`
--

LOCK TABLES `Review` WRITE;
/*!40000 ALTER TABLE `Review` DISABLE KEYS */;
INSERT INTO `Review` VALUES (5,1,5,2,'독서를 통한 마음 함양 수업,,, 너무 좋았습니다,,,^^'),(4,2,5,4,'괜찮습니다. 그런데 책 종류가 조금 마음에 안들어요.');
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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RoomTime`
--

LOCK TABLES `RoomTime` WRITE;
/*!40000 ALTER TABLE `RoomTime` DISABLE KEYS */;
INSERT INTO `RoomTime` VALUES ('2025-09-09 11:00:00',1,1,'2025-09-09 10:00:00'),('2025-09-10 11:00:00',2,1,'2025-09-10 10:00:00'),('2025-09-11 11:00:00',3,1,'2025-09-11 10:00:00'),('2025-09-22 10:00:00',4,1,'2025-09-22 09:00:00'),('2025-09-23 10:00:00',5,1,'2025-09-23 09:00:00'),('2025-09-24 10:00:00',6,1,'2025-09-24 09:00:00'),('2025-09-08 14:00:00',7,1,'2025-09-08 13:00:00'),('2025-09-09 14:00:00',8,1,'2025-09-09 13:00:00'),('2025-09-10 14:00:00',9,1,'2025-09-10 13:00:00'),('2025-09-15 14:00:00',10,1,'2025-09-15 13:00:00'),('2025-09-16 14:00:00',11,1,'2025-09-16 13:00:00'),('2025-09-17 14:00:00',12,1,'2025-09-17 13:00:00'),('2025-09-13 17:00:00',13,4,'2025-09-13 16:00:00'),('2025-09-20 17:00:00',14,4,'2025-09-20 16:00:00'),('2025-09-27 17:00:00',15,4,'2025-09-27 16:00:00'),('2025-10-04 17:00:00',16,4,'2025-10-04 16:00:00'),('2025-09-15 10:00:00',17,4,'2025-09-15 09:00:00'),('2025-09-22 10:00:00',18,4,'2025-09-22 09:00:00'),('2025-09-29 10:00:00',19,4,'2025-09-29 09:00:00'),('2025-10-06 10:00:00',20,4,'2025-10-06 09:00:00'),('2025-10-13 10:00:00',21,4,'2025-10-13 09:00:00'),('2025-10-20 10:00:00',22,4,'2025-10-20 09:00:00'),('2025-09-08 11:00:00',23,7,'2025-09-08 10:00:00'),('2025-09-09 11:00:00',24,7,'2025-09-09 10:00:00'),('2025-09-10 11:00:00',25,7,'2025-09-10 10:00:00'),('2025-09-15 11:00:00',26,7,'2025-09-15 10:00:00'),('2025-09-16 11:00:00',27,7,'2025-09-16 10:00:00'),('2025-09-17 11:00:00',28,7,'2025-09-17 10:00:00'),('2025-09-22 11:00:00',29,1,'2025-09-22 10:00:00'),('2025-09-23 11:00:00',30,1,'2025-09-23 10:00:00'),('2025-09-24 11:00:00',31,1,'2025-09-24 10:00:00'),('2025-09-25 11:00:00',32,1,'2025-09-25 10:00:00'),('2025-09-26 11:00:00',33,1,'2025-09-26 10:00:00'),('2025-09-16 12:00:00',34,1,'2025-09-16 11:00:00'),('2025-09-17 12:00:00',35,1,'2025-09-17 11:00:00'),('2025-09-18 12:00:00',36,1,'2025-09-18 11:00:00'),('2025-09-23 16:00:00',37,1,'2025-09-23 15:00:00'),('2025-09-24 16:00:00',38,1,'2025-09-24 15:00:00'),('2025-09-25 16:00:00',39,1,'2025-09-25 15:00:00'),('2025-09-23 16:00:00',40,2,'2025-09-23 15:00:00'),('2025-09-24 16:00:00',41,2,'2025-09-24 15:00:00'),('2025-09-25 16:00:00',42,2,'2025-09-25 15:00:00'),('2025-09-23 16:00:00',43,3,'2025-09-23 15:00:00'),('2025-09-24 16:00:00',44,3,'2025-09-24 15:00:00'),('2025-09-25 16:00:00',45,3,'2025-09-25 15:00:00');
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

-- Dump completed on 2025-09-08 16:08:31
