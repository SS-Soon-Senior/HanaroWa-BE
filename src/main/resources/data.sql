INSERT INTO location (id, name) VALUES
                                    (1, '서울'),
                                    (2, '부산'),
                                    (3, '대구'),
                                    (4, '인천'),
                                    (5, '광주'),
                                    (6, '대전'),
                                    (7, '울산'),
                                    (8, '세종'),
                                    (9, '춘천'),
                                    (10, '천안');

INSERT INTO branch (id, name, address, telNumber, locationId) VALUES
                                                                  (1, '을지로', '서울 중구 을지로 170 을지트윈타워 2층', '02-1000-1000', 1),
                                                                  (2, '서초동', '서울 서초구 서초대로 286 서초프라자 2층', '0507-1475-1525', 1),
                                                                  (3, '선릉역', '서울 강남구 테헤란로 401 1층', '0507-1466-4117', 1),
                                                                  (4, '영등포', '서울 영등포구 영등포동 4가 65-1 7층', '0507-1482-3013', 1),
                                                                  (5, '천안', '충남 천안시 동남구 버들로 17', '053-3000-1000', 10),
                                                                  (6, '춘천', '강원특별자치 춘천시 시청길 3', '033-252-2255', 9),
                                                                  (7, '대전', '대전 중구 대종로 481', '042-252-7800', 6),
                                                                  (8, '방배서래', '서울 서초구 방배로 268', '02-533-1111', 1);

INSERT INTO facility (id, name, description, branchId) VALUES
                                                           (1, '헬스장', '헬스할 수 있는 공간', 1),
                                                           (2, '독서실', '조용히 책 읽을 수 있는 공간', 1),
                                                           (3, '컴퓨터실', '굉장히 빠른 컴퓨터', 2),
                                                           (4, '댄스연습실', '댄스댄스~!', 2),
                                                           (5, '탁구장', '즐거운 탁구', 3),
                                                           (6, '노래방', '노래로 스트레스 풀기', 4),
                                                           (7, '작은도서관', '책으로 교양 쌓기', 5),
                                                           (8, '세미나룸', '진지한 대화', 6),
                                                           (9, '피트니스룸', '운동으로 땀 흘리기', 7),
                                                           (10, '강당', '강당에서 모여봐요', 8);

INSERT INTO facilityImage (id, facilityImage, facilityId) VALUES
                                                              (1, 'gym1.png', 1),
                                                              (2, 'studyroom1.png', 2),
                                                              (3, 'pcroom1.png', 3),
                                                              (4, 'dance1.png', 4),
                                                              (5, 'pingpong1.png', 5),
                                                              (6, 'karaoke1.png', 6),
                                                              (7, 'library1.png', 7),
                                                              (8, 'seminar1.png', 8),
                                                              (9, 'fitness1.png', 9),
                                                              (10, 'auditorium1.png', 10);

INSERT INTO member (id, name, email, birth, password, phoneNumber, deletedAt, role, branchId) VALUES
                                                                                                  (1, '김시영', 'siyoung@hana.com', '1998-01-01', 'pw1', '010-1111-1111', NULL, 'USERS', 1),
                                                                                                  (2, '정소은', 'soeun@hana.com', '1997-02-02', 'pw2', '010-2222-2222', NULL, 'USERS', 2),
                                                                                                  (3, '박영균', 'youngkyun@hana.com', '1996-03-03', 'pw3', '010-3333-3333', NULL, 'ADMIN', 2),
                                                                                                  (4, '이유민', 'yumin@hana.com', '1995-04-04', 'pw4', '010-4444-4444', NULL, 'USERS', 3),
                                                                                                  (5, '채영', 'chaeyoung@hana.com', '1999-05-05', 'pw5', '010-5555-5555', NULL, 'USERS', 3),
                                                                                                  (6, '재윤', 'jaeyoon@hana.com', '1998-06-06', 'pw6', '010-6666-6666', NULL, 'USERS', 4),
                                                                                                  (7, '홍길동', 'gildong@hana.com', '1985-07-07', 'pw7', '010-7777-7777', NULL, 'USERS', 5),
                                                                                                  (8, '강감찬', 'gamchan@hana.com', '1980-08-08', 'pw8', '010-8888-8888', NULL, 'ADMIN', 6),
                                                                                                  (9, '이순신', 'soonshin@hana.com', '1975-09-09', 'pw9', '010-9999-9999', NULL, 'USERS', 7),
                                                                                                  (10, '세종대왕', 'sejong@hana.com', '1970-10-10', 'pw10', '010-1010-1010', NULL, 'USERS', 8);

INSERT INTO lessonRoom (id, name, branchId) VALUES
                                                (1, '101호', 1),
                                                (2, '202호', 1),
                                                (3, '컴퓨터실1', 2),
                                                (4, '댄스홀', 2),
                                                (5, '강의실A', 3),
                                                (6, '강의실B', 4),
                                                (7, '대강당', 5),
                                                (8, '스터디룸', 6),
                                                (9, '체육관', 7),
                                                (10, '세미나실', 8);

INSERT INTO lesson (id, lessonName, instructor, instruction, description, category, lessonImg, branchId, memberId) VALUES
                                                                                                                       (1, '스마트폰 기초', '김강사', '스마트폰 사용법 배우기', '시니어 대상 스마트폰 기초 수업', 'DIGITAL', 'lesson1.png', 1, 1),
                                                                                                                       (2, '영어회화', '이강사', '영어 말하기', '기초 영어회화 학습', 'LANGUAGE', 'lesson2.png', 2, 2),
                                                                                                                       (3, '주식투자', '박강사', '투자 기초', '금융 초보자를 위한 주식 강의', 'FINANCE', 'lesson3.png', 3, 3),
                                                                                                                       (4, '요가', '정강사', '체형 교정', '건강을 위한 요가', 'HEALTH', 'lesson4.png', 4, 4),
                                                                                                                       (5, '한국사', '최강사', '역사 이야기', '문화 이해를 위한 한국사', 'CULTURE', 'lesson5.png', 5, 5),
                                                                                                                       (6, '트렌드 읽기', '조강사', '최신 유행', '사회 트렌드 분석', 'TREND', 'lesson6.png', 6, 6),
                                                                                                                       (7, '금융 문해력', '한강사', '가계 재무 관리', '시니어 금융 이해 교육', 'FINANCE', 'lesson7.png', 7, 7),
                                                                                                                       (8, '실버 댄스', '김댄스', '댄스 배우기', '즐겁게 배우는 댄스', 'HEALTH', 'lesson8.png', 8, 8),
                                                                                                                       (9, '중국어 기초', '이중국', '중국어 입문', '중국어 왕초보 과정', 'LANGUAGE', 'lesson9.png', 8, 8),
                                                                                                                       (10, '사진 촬영', '박사진', '카메라 사용법', '취미로 배우는 사진 강좌', 'OTHERS', 'lesson10.png', 8, 8);

INSERT INTO lessonGisu (id, capacity, lessonFee, duration, lessonId, lessonRoomId) VALUES
                                                                                       (1, 20, 50000, '2025-01-01~2025-02-28', 1, 1),
                                                                                       (2, 15, 60000, '2025-01-05~2025-03-05', 2, 2),
                                                                                       (3, 25, 70000, '2025-02-01~2025-03-31', 3, 3),
                                                                                       (4, 30, 40000, '2025-03-01~2025-04-30', 4, 4),
                                                                                       (5, 10, 30000, '2025-03-15~2025-04-15', 5, 5),
                                                                                       (6, 12, 20000, '2025-04-01~2025-05-30', 6, 6),
                                                                                       (7, 18, 45000, '2025-04-10~2025-06-10', 7, 7),
                                                                                       (8, 22, 35000, '2025-05-01~2025-06-30', 8, 8),
                                                                                       (9, 16, 55000, '2025-05-15~2025-07-15', 9, 9),
                                                                                       (10, 20, 50000, '2025-06-01~2025-07-31', 10, 10);

INSERT INTO curriculum (id, content, lessonGisuId, startedAt, endedAt) VALUES
                                                                           (1, '스마트폰 기본기 다지기', 1, NOW(), NOW()),
                                                                           (2, '앱 설치와 활용', 1, NOW(), NOW()),
                                                                           (3, '인사말 배우기', 2, NOW(), NOW()),
                                                                           (4, '여행 영어', 2, NOW(), NOW()),
                                                                           (5, '주식 용어 기초', 3, NOW(), NOW()),
                                                                           (6, '투자 전략 세우기', 3, NOW(), NOW()),
                                                                           (7, '기초 요가 자세', 4, NOW(), NOW()),
                                                                           (8, '호흡과 명상', 4, NOW(), NOW()),
                                                                           (9, '조선시대 역사', 5, NOW(), NOW()),
                                                                           (10, '근현대사 이해', 5, NOW(), NOW());

INSERT INTO roomTime (id, lessonRoomId, startedAt, endedAt) VALUES
                                                                (1, 1, NOW(), NOW()),
                                                                (2, 2, NOW(), NOW()),
                                                                (3, 3, NOW(), NOW()),
                                                                (4, 4, NOW(), NOW()),
                                                                (5, 5, NOW(), NOW()),
                                                                (6, 6, NOW(), NOW()),
                                                                (7, 7, NOW(), NOW()),
                                                                (8, 8, NOW(), NOW()),
                                                                (9, 9, NOW(), NOW()),
                                                                (10, 10, NOW(), NOW());

INSERT INTO myLesson (id, memberId, lessonId) VALUES
                                                  (1, 1, 1),
                                                  (2, 2, 2),
                                                  (3, 3, 3),
                                                  (4, 4, 4),
                                                  (5, 5, 5),
                                                  (6, 6, 6),
                                                  (7, 7, 7),
                                                  (8, 8, 8),
                                                  (9, 9, 9),
                                                  (10, 10, 10);

INSERT INTO review (id, rating, reviewTxt, memberId, lessonId) VALUES
                                                                   (1, 5, '아주 유익했어요!', 1, 1),
                                                                   (2, 4, '강사님이 친절했어요.', 2, 2),
                                                                   (3, 5, '많이 배웠습니다.', 3, 3),
                                                                   (4, 3, '조금 어려웠어요.', 4, 4),
                                                                   (5, 4, '좋은 강의였습니다.', 5, 5),
                                                                   (6, 5, '추천합니다.', 6, 6),
                                                                   (7, 4, '재밌었습니다.', 7, 7),
                                                                   (8, 5, '유익했어요.', 8, 8),
                                                                   (9, 3, '좀 지루했어요.', 9, 9),
                                                                   (10, 5, '최고의 강의!', 10, 10);


INSERT INTO facilityTime (id, facilityId, memberId, startedAt, endedAt) VALUES
                                                                            (1, 1, 1, NOW(), NOW()),
                                                                            (2, 2, 2, NOW(), NOW()),
                                                                            (3, 3, 3, NOW(), NOW()),
                                                                            (4, 4, 4, NOW(), NOW()),
                                                                            (5, 5, 5, NOW(), NOW()),
                                                                            (6, 6, 6, NOW(), NOW()),
                                                                            (7, 7, 7, NOW(), NOW()),
                                                                            (8, 8, 8, NOW(), NOW()),
                                                                            (9, 9, 9, NOW(), NOW()),
                                                                            (10, 10, 10, NOW(), NOW());



