package com.ss.hanarowa.domain.lesson.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.entity.Location;
import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.branch.repository.LocationRepository;
import com.ss.hanarowa.domain.lesson.entity.Category;
import com.ss.hanarowa.domain.lesson.entity.Curriculum;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.LessonRoom;
import com.ss.hanarowa.domain.lesson.entity.LessonState;
import com.ss.hanarowa.repository.RepositoryTest;

class CurriculumRepositoryTest extends RepositoryTest {
    
    @Autowired
    private CurriculumRepository curriculumRepository;
    
    @Autowired
    private LessonGisuRepository lessonGisuRepository;
    
    @Autowired
    private LessonRepository lessonRepository;
    
    @Autowired
    private LessonRoomRepository lessonRoomRepository;
    
    @Autowired
    private BranchRepository branchRepository;
    
    @Autowired
    private LocationRepository locationRepository;
    
    private LessonGisu testLessonGisu;
    
    @BeforeEach
    void setUp() {
        Location testLocation = Location.builder()
                .name("테스트 지역")
                .build();
        testLocation = locationRepository.save(testLocation);
        
        Branch testBranch = Branch.builder()
                .name("테스트 지점")
                .address("서울시 강남구 테헤란로 123")
                .telNumber("02-1234-5678")
                .location(testLocation)
                .build();
        testBranch = branchRepository.save(testBranch);
        
        Lesson testLesson = Lesson.builder()
                .lessonName("테스트 수업")
                .instructor("테스트 강사")
                .instruction("테스트 수업 설명")
                .description("테스트 수업 상세 설명")
                .category(Category.DIGITAL)
                .branch(testBranch)
                .build();
        testLesson = lessonRepository.save(testLesson);
        
        LessonRoom testLessonRoom = LessonRoom.builder()
                .name("테스트 강의실")
                .branch(testBranch)
                .build();
        testLessonRoom = lessonRoomRepository.save(testLessonRoom);
        
        testLessonGisu = LessonGisu.builder()
                .capacity(20)
                .lessonFee(100000)
                .duration("2시간")
                .lessonState(LessonState.PENDING)
                .startedAt(LocalDateTime.now())
                .lesson(testLesson)
                .lessonRoom(testLessonRoom)
                .build();
        testLessonGisu = lessonGisuRepository.save(testLessonGisu);
    }
    
    @Test
    @Order(1)
    @DisplayName("커리큘럼 생성 테스트")
    void createCurriculumTest() {
        Curriculum curriculum = Curriculum.builder()
                .content("1주차: Java 기초 문법")
                .startedAt(LocalDateTime.now())
                .lessonGisu(testLessonGisu)
                .build();
        
        Curriculum savedCurriculum = curriculumRepository.save(curriculum);
        
        assertNotNull(savedCurriculum.getId());
        assertEquals("1주차: Java 기초 문법", savedCurriculum.getContent());
        assertEquals(testLessonGisu.getId(), savedCurriculum.getLessonGisu().getId());
        
        print("Created Curriculum: " + savedCurriculum.getContent() + ", ID: " + savedCurriculum.getId());
    }
    
    @Test
    @Order(2)
    @DisplayName("커리큘럼 조회 테스트")
    void findCurriculumTest() {
        Curriculum curriculum = Curriculum.builder()
                .content("2주차: 객체지향 프로그래밍")
                .startedAt(LocalDateTime.now())
                .lessonGisu(testLessonGisu)
                .build();
        
        Curriculum savedCurriculum = curriculumRepository.save(curriculum);
        
        Curriculum foundCurriculum = curriculumRepository.findById(savedCurriculum.getId()).orElse(null);
        
        assertNotNull(foundCurriculum);
        assertEquals(savedCurriculum.getId(), foundCurriculum.getId());
        assertEquals("2주차: 객체지향 프로그래밍", foundCurriculum.getContent());
        
        print("Found Curriculum: " + foundCurriculum.getContent() + ", ID: " + foundCurriculum.getId());
    }
    
    @Test
    @Order(3)
    @DisplayName("커리큘럼 수정 테스트")
    void updateCurriculumTest() {
        Curriculum curriculum = Curriculum.builder()
                .content("3주차: 컬렉션 프레임워크")
                .startedAt(LocalDateTime.now())
                .lessonGisu(testLessonGisu)
                .build();
        
        Curriculum savedCurriculum = curriculumRepository.save(curriculum);
        
        savedCurriculum.setContent("3주차: 컬렉션과 제네릭");
        
        Curriculum updatedCurriculum = curriculumRepository.save(savedCurriculum);
        
        assertEquals("3주차: 컬렉션과 제네릭", updatedCurriculum.getContent());
        
        print("Updated Curriculum: " + updatedCurriculum.getContent());
    }
    
    @Test
    @Order(4)
    @DisplayName("커리큘럼 삭제 테스트")
    void deleteCurriculumTest() {
        Curriculum curriculum = Curriculum.builder()
                .content("4주차: 예외처리")
                .startedAt(LocalDateTime.now())
                .lessonGisu(testLessonGisu)
                .build();
        
        Curriculum savedCurriculum = curriculumRepository.save(curriculum);
        Long curriculumId = savedCurriculum.getId();
        
        curriculumRepository.deleteById(curriculumId);
        
        assertTrue(curriculumRepository.findById(curriculumId).isEmpty());
        
        print("Curriculum with ID " + curriculumId + " has been deleted");
    }
    
    @Test
    @Order(5)
    @DisplayName("모든 커리큘럼 조회 테스트")
    void findAllCurriculumsTest() {
        long initialCount = curriculumRepository.count();
        
        Curriculum curriculum1 = Curriculum.builder()
                .content("5주차: 스트림 API")
                .startedAt(LocalDateTime.now())
                .lessonGisu(testLessonGisu)
                .build();
        
        Curriculum curriculum2 = Curriculum.builder()
                .content("6주차: 멀티스레딩")
                .startedAt(LocalDateTime.now())
                .lessonGisu(testLessonGisu)
                .build();
        
        curriculumRepository.save(curriculum1);
        curriculumRepository.save(curriculum2);
        
        assertEquals(initialCount + 2, curriculumRepository.count());
        
        curriculumRepository.findAll().forEach(curriculum -> 
                print("Curriculum: " + curriculum.getContent() + ", ID: " + curriculum.getId()));
    }
}