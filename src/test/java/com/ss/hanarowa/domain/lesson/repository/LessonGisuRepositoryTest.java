package com.ss.hanarowa.domain.lesson.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

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
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.LessonRoom;
import com.ss.hanarowa.domain.lesson.entity.LessonState;
import com.ss.hanarowa.repository.RepositoryTest;

class LessonGisuRepositoryTest extends RepositoryTest {
    
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
    
    private Lesson testLesson;
    private LessonRoom testLessonRoom;
    
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
        
        testLesson = Lesson.builder()
                .lessonName("테스트 수업")
                .instructor("테스트 강사")
                .instruction("테스트 수업 설명")
                .description("테스트 수업 상세 설명")
                .category(Category.DIGITAL)
                .branch(testBranch)
                .build();
        testLesson = lessonRepository.save(testLesson);
        
        testLessonRoom = LessonRoom.builder()
                .name("테스트 강의실")
                .branch(testBranch)
                .build();
        testLessonRoom = lessonRoomRepository.save(testLessonRoom);
    }
    
    @Test
    @Order(1)
    @DisplayName("수업기수 생성 테스트")
    void createLessonGisuTest() {
        LessonGisu lessonGisu = LessonGisu.builder()
                .capacity(20)
                .lessonFee(100000)
                .duration("2시간")
                .lessonState(LessonState.PENDING)
                .startedAt(LocalDateTime.now())
                .lesson(testLesson)
                .lessonRoom(testLessonRoom)
                .build();
        
        LessonGisu savedLessonGisu = lessonGisuRepository.save(lessonGisu);
        
        assertNotNull(savedLessonGisu.getId());
        assertEquals(20, savedLessonGisu.getCapacity());
        assertEquals(100000, savedLessonGisu.getLessonFee());
        assertEquals("2시간", savedLessonGisu.getDuration());
        assertEquals(LessonState.PENDING, savedLessonGisu.getLessonState());
        assertEquals(testLesson.getId(), savedLessonGisu.getLesson().getId());
        assertEquals(testLessonRoom.getId(), savedLessonGisu.getLessonRoom().getId());
        
        print("Created LessonGisu: Duration=" + savedLessonGisu.getDuration() + ", ID: " + savedLessonGisu.getId());
    }
    
    @Test
    @Order(2)
    @DisplayName("수업기수 조회 테스트")
    void findLessonGisuTest() {
        LessonGisu lessonGisu = LessonGisu.builder()
                .capacity(15)
                .lessonFee(80000)
                .duration("1.5시간")
                .lessonState(LessonState.APPROVED)
                .startedAt(LocalDateTime.now())
                .lesson(testLesson)
                .lessonRoom(testLessonRoom)
                .build();
        
        LessonGisu savedLessonGisu = lessonGisuRepository.save(lessonGisu);
        
        LessonGisu foundLessonGisu = lessonGisuRepository.findById(savedLessonGisu.getId()).orElse(null);
        
        assertNotNull(foundLessonGisu);
        assertEquals(savedLessonGisu.getId(), foundLessonGisu.getId());
        assertEquals(15, foundLessonGisu.getCapacity());
        assertEquals(80000, foundLessonGisu.getLessonFee());
        assertEquals("1.5시간", foundLessonGisu.getDuration());
        assertEquals(LessonState.APPROVED, foundLessonGisu.getLessonState());
        
        print("Found LessonGisu: Duration=" + foundLessonGisu.getDuration() + ", ID: " + foundLessonGisu.getId());
    }
    
    @Test
    @Order(3)
    @DisplayName("수업기수 수정 테스트")
    void updateLessonGisuTest() {
        LessonGisu lessonGisu = LessonGisu.builder()
                .capacity(25)
                .lessonFee(120000)
                .duration("3시간")
                .lessonState(LessonState.PENDING)
                .startedAt(LocalDateTime.now())
                .lesson(testLesson)
                .lessonRoom(testLessonRoom)
                .build();
        
        LessonGisu savedLessonGisu = lessonGisuRepository.save(lessonGisu);
        
        savedLessonGisu.setCapacity(30);
        savedLessonGisu.setLessonFee(150000);
        savedLessonGisu.updateState(LessonState.APPROVED);
        
        LessonGisu updatedLessonGisu = lessonGisuRepository.save(savedLessonGisu);
        
        assertEquals(30, updatedLessonGisu.getCapacity());
        assertEquals(150000, updatedLessonGisu.getLessonFee());
        assertEquals(LessonState.APPROVED, updatedLessonGisu.getLessonState());
        
        print("Updated LessonGisu: Capacity=" + updatedLessonGisu.getCapacity() + ", Fee=" + updatedLessonGisu.getLessonFee());
    }
    
    @Test
    @Order(4)
    @DisplayName("수업기수 삭제 테스트")
    void deleteLessonGisuTest() {
        LessonGisu lessonGisu = LessonGisu.builder()
                .capacity(10)
                .lessonFee(60000)
                .duration("1시간")
                .lessonState(LessonState.REJECTED)
                .startedAt(LocalDateTime.now())
                .lesson(testLesson)
                .lessonRoom(testLessonRoom)
                .build();
        
        LessonGisu savedLessonGisu = lessonGisuRepository.save(lessonGisu);
        Long lessonGisuId = savedLessonGisu.getId();
        
        lessonGisuRepository.deleteById(lessonGisuId);
        
        assertTrue(lessonGisuRepository.findById(lessonGisuId).isEmpty());
        
        print("LessonGisu with ID " + lessonGisuId + " has been deleted");
    }
    
    @Test
    @Order(5)
    @DisplayName("모든 수업기수 조회 테스트")
    void findAllLessonGisusTest() {
        long initialCount = lessonGisuRepository.count();
        
        LessonGisu lessonGisu1 = LessonGisu.builder()
                .capacity(20)
                .lessonFee(90000)
                .duration("2시간")
                .lessonState(LessonState.PENDING)
                .startedAt(LocalDateTime.now())
                .lesson(testLesson)
                .lessonRoom(testLessonRoom)
                .build();
        
        LessonGisu lessonGisu2 = LessonGisu.builder()
                .capacity(25)
                .lessonFee(110000)
                .duration("2.5시간")
                .lessonState(LessonState.APPROVED)
                .startedAt(LocalDateTime.now())
                .lesson(testLesson)
                .lessonRoom(testLessonRoom)
                .build();
        
        lessonGisuRepository.save(lessonGisu1);
        lessonGisuRepository.save(lessonGisu2);
        
        assertEquals(initialCount + 2, lessonGisuRepository.count());
        
        lessonGisuRepository.findAll().forEach(lessonGisu -> 
                print("LessonGisu: Duration=" + lessonGisu.getDuration() + ", ID: " + lessonGisu.getId()));
    }
    
    @Test
    @Order(6)
    @DisplayName("특정 수업의 기수 조회 테스트")
    void findByLessonIdTest() {
        LessonGisu lessonGisu1 = LessonGisu.builder()
                .capacity(20)
                .lessonFee(90000)
                .duration("2시간")
                .lessonState(LessonState.PENDING)
                .startedAt(LocalDateTime.now())
                .lesson(testLesson)
                .lessonRoom(testLessonRoom)
                .build();
        
        LessonGisu lessonGisu2 = LessonGisu.builder()
                .capacity(25)
                .lessonFee(110000)
                .duration("2.5시간")
                .lessonState(LessonState.APPROVED)
                .startedAt(LocalDateTime.now())
                .lesson(testLesson)
                .lessonRoom(testLessonRoom)
                .build();
        
        lessonGisuRepository.save(lessonGisu1);
        lessonGisuRepository.save(lessonGisu2);
        
        List<LessonGisu> foundLessonGisus = lessonGisuRepository.findByLessonId(testLesson.getId());
        
        assertTrue(foundLessonGisus.size() >= 2);
        foundLessonGisus.forEach(lessonGisu -> {
            assertEquals(testLesson.getId(), lessonGisu.getLesson().getId());
            print("Found LessonGisu for lesson " + testLesson.getId() + ": Duration=" + lessonGisu.getDuration());
        });
    }
}