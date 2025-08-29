package com.ss.hanarowa.domain.branch.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ss.hanarowa.domain.branch.entity.Location;
import com.ss.hanarowa.repository.RepositoryTest;

class LocationRepositoryTest extends RepositoryTest {
    
    @Autowired
    private LocationRepository locationRepository;
    
    @Test
    @Order(1)
    @DisplayName("지역 생성 테스트")
    void createLocationTest() {
        Location location = Location.builder()
                .name("서울")
                .build();
        
        Location savedLocation = locationRepository.save(location);
        
        assertNotNull(savedLocation.getId());
        assertEquals("서울", savedLocation.getName());
        
        print(savedLocation);
    }
    
    @Test
    @Order(2)
    @DisplayName("지역 조회 테스트")
    void findLocationTest() {
        Location location = Location.builder()
                .name("부산")
                .build();
        
        Location savedLocation = locationRepository.save(location);
        
        Location foundLocation = locationRepository.findById(savedLocation.getId()).orElse(null);
        
        assertNotNull(foundLocation);
        assertEquals(savedLocation.getId(), foundLocation.getId());
        assertEquals("부산", foundLocation.getName());
        
        print(foundLocation);
    }
    
    @Test
    @Order(3)
    @DisplayName("지역 수정 테스트")
    void updateLocationTest() {
        Location location = Location.builder()
                .name("대구")
                .build();
        
        Location savedLocation = locationRepository.save(location);
        
        savedLocation.setName("대전");
        Location updatedLocation = locationRepository.save(savedLocation);
        
        assertEquals("대전", updatedLocation.getName());
        assertEquals(savedLocation.getId(), updatedLocation.getId());
        
        print(updatedLocation);
    }
    
    @Test
    @Order(4)
    @DisplayName("지역 삭제 테스트")
    void deleteLocationTest() {
        Location location = Location.builder()
                .name("인천")
                .build();
        
        Location savedLocation = locationRepository.save(location);
        Long locationId = savedLocation.getId();
        
        locationRepository.deleteById(locationId);
        
        assertTrue(locationRepository.findById(locationId).isEmpty());
        
        print("Location with ID " + locationId + " has been deleted");
    }
    
    @Test
    @Order(5)
    @DisplayName("모든 지역 조회 테스트")
    void findAllLocationsTest() {
        locationRepository.deleteAll();
        
        Location location1 = Location.builder()
                .name("광주")
                .build();
        
        Location location2 = Location.builder()
                .name("울산")
                .build();
        
        Location location3 = Location.builder()
                .name("세종")
                .build();
        
        locationRepository.save(location1);
        locationRepository.save(location2);
        locationRepository.save(location3);
        
        assertEquals(3, locationRepository.findAll().size());
        
        locationRepository.findAll().forEach(this::print);
    }
}