package com.ss.hanarowa.domain.branch.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.entity.Location;
import com.ss.hanarowa.repository.RepositoryTest;

class BranchRepositoryTest extends RepositoryTest {
    
    @Autowired
    private BranchRepository branchRepository;
    
    @Autowired
    private LocationRepository locationRepository;
    
    private Location testLocation;
    
    @BeforeEach
    void setUp() {
        testLocation = Location.builder()
                .name("테스트 지역")
                .build();
        testLocation = locationRepository.save(testLocation);
    }
    
    @Test
    @Order(1)
    @DisplayName("지점 생성 테스트")
    void createBranchTest() {
        Branch branch = Branch.builder()
                .name("테스트 지점")
                .address("서울시 강남구 테헤란로 123")
                .telNumber("02-1234-5678")
                .location(testLocation)
                .build();
        
        Branch savedBranch = branchRepository.save(branch);
        
        assertNotNull(savedBranch.getId());
        assertEquals("테스트 지점", savedBranch.getName());
        assertEquals("서울시 강남구 테헤란로 123", savedBranch.getAddress());
        assertEquals("02-1234-5678", savedBranch.getTelNumber());
        assertEquals(testLocation.getId(), savedBranch.getLocation().getId());
        
        print("Created Branch: " + savedBranch.getName() + ", ID: " + savedBranch.getId());
    }
    
    @Test
    @Order(2)
    @DisplayName("지점 조회 테스트")
    void findBranchTest() {
        Branch branch = Branch.builder()
                .name("조회 테스트 지점")
                .address("서울시 서초구 서초대로 456")
                .telNumber("02-9876-5432")
                .location(testLocation)
                .build();
        
        Branch savedBranch = branchRepository.save(branch);
        
        Branch foundBranch = branchRepository.findById(savedBranch.getId()).orElse(null);
        
        assertNotNull(foundBranch);
        assertEquals(savedBranch.getId(), foundBranch.getId());
        assertEquals("조회 테스트 지점", foundBranch.getName());
        
        print("Found Branch: " + foundBranch.getName() + ", ID: " + foundBranch.getId());
    }
    
    @Test
    @Order(3)
    @DisplayName("지점 수정 테스트")
    void updateBranchTest() {
        Branch branch = Branch.builder()
                .name("수정 전 지점")
                .address("서울시 종로구 종로 789")
                .telNumber("02-5555-1234")
                .location(testLocation)
                .build();
        
        Branch savedBranch = branchRepository.save(branch);
        
        savedBranch.setName("수정 후 지점");
        savedBranch.setAddress("서울시 마포구 홍대로 321");
        
        Branch updatedBranch = branchRepository.save(savedBranch);
        
        assertEquals("수정 후 지점", updatedBranch.getName());
        assertEquals("서울시 마포구 홍대로 321", updatedBranch.getAddress());
        
        print("Updated Branch: " + updatedBranch.getName() + ", Address: " + updatedBranch.getAddress());
    }
    
    @Test
    @Order(4)
    @DisplayName("지점 삭제 테스트")
    void deleteBranchTest() {
        Branch branch = Branch.builder()
                .name("삭제 테스트 지점")
                .address("서울시 영등포구 여의도로 999")
                .telNumber("02-1111-2222")
                .location(testLocation)
                .build();
        
        Branch savedBranch = branchRepository.save(branch);
        Long branchId = savedBranch.getId();
        
        branchRepository.deleteById(branchId);
        
        assertTrue(branchRepository.findById(branchId).isEmpty());
        
        print("Branch with ID " + branchId + " has been deleted");
    }
    
    @Test
    @Order(5)
    @DisplayName("모든 지점 조회 테스트")
    void findAllBranchesTest() {
        long initialCount = branchRepository.count();
        
        Branch branch1 = Branch.builder()
                .name("지점1")
                .address("주소1")
                .telNumber("02-1111-1111")
                .location(testLocation)
                .build();
        
        Branch branch2 = Branch.builder()
                .name("지점2")
                .address("주소2")
                .telNumber("02-2222-2222")
                .location(testLocation)
                .build();
        
        branchRepository.save(branch1);
        branchRepository.save(branch2);
        
        assertEquals(initialCount + 2, branchRepository.count());
        
        branchRepository.findAll().forEach(branch -> 
                print("Branch: " + branch.getName() + ", ID: " + branch.getId()));
    }
}
