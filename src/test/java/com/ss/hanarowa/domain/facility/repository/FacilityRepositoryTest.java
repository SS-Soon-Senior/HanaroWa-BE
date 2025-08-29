package com.ss.hanarowa.domain.facility.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.entity.Location;
import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.branch.repository.LocationRepository;
import com.ss.hanarowa.domain.facility.entity.Facility;
import com.ss.hanarowa.repository.RepositoryTest;

class FacilityRepositoryTest extends RepositoryTest {

	@Autowired
	private FacilityRepository facilityRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private LocationRepository locationRepository;

	private Branch testBranch;

	@BeforeEach
	void setUp() {
		Location testLocation = Location.builder()
			.name("테스트 지역")
			.build();
		testLocation = locationRepository.save(testLocation);

		testBranch = Branch.builder()
			.name("테스트 지점")
			.address("테스트 주소")
			.telNumber("02-0000-0000")
			.location(testLocation)
			.build();
		testBranch = branchRepository.save(testBranch);
	}

	@Test
	@Order(1)
	@DisplayName("시설 생성 테스트")
	void createFacilityTest() {
		Facility facility = Facility.builder()
			.name("헬스장")
			.description("최신 헬스 기구가 구비된 헬스장입니다.")
			.branch(testBranch)
			.build();

		Facility savedFacility = facilityRepository.save(facility);

		assertNotNull(savedFacility.getId());
		assertEquals("헬스장", savedFacility.getName());
		assertEquals("최신 헬스 기구가 구비된 헬스장입니다.", savedFacility.getDescription());
		assertEquals(testBranch.getId(), savedFacility.getBranch().getId());

		print("Created Facility: " + savedFacility.getName() + ", ID: " + savedFacility.getId());
	}

	@Test
	@Order(2)
	@DisplayName("시설 조회 테스트")
	void findFacilityTest() {
		Facility facility = Facility.builder()
			.name("수영장")
			.description("25m 실내 수영장")
			.branch(testBranch)
			.build();

		Facility savedFacility = facilityRepository.save(facility);

		Facility foundFacility = facilityRepository.findById(savedFacility.getId()).orElse(null);

		assertNotNull(foundFacility);
		assertEquals(savedFacility.getId(), foundFacility.getId());
		assertEquals("수영장", foundFacility.getName());

		print("Found Facility: " + foundFacility.getName() + ", ID: " + foundFacility.getId());
	}

	@Test
	@Order(3)
	@DisplayName("시설 수정 테스트")
	void updateFacilityTest() {
		Facility facility = Facility.builder()
			.name("요가실")
			.description("조용한 요가 연습실")
			.branch(testBranch)
			.build();

		Facility savedFacility = facilityRepository.save(facility);

		savedFacility.setName("필라테스실");
		savedFacility.setDescription("필라테스 전용 연습실");

		Facility updatedFacility = facilityRepository.save(savedFacility);

		assertEquals("필라테스실", updatedFacility.getName());
		assertEquals("필라테스 전용 연습실", updatedFacility.getDescription());

		print("Updated Facility: " + updatedFacility.getName() + ", Description: " + updatedFacility.getDescription());
	}

	@Test
	@Order(4)
	@DisplayName("시설 삭제 테스트")
	void deleteFacilityTest() {
		Facility facility = Facility.builder()
			.name("탁구실")
			.description("탁구 연습실")
			.branch(testBranch)
			.build();

		Facility savedFacility = facilityRepository.save(facility);
		Long facilityId = savedFacility.getId();

		facilityRepository.deleteById(facilityId);

		assertTrue(facilityRepository.findById(facilityId).isEmpty());

		print("Facility with ID " + facilityId + " has been deleted");
	}

	@Test
	@Order(5)
	@DisplayName("지점별 시설 조회 테스트")
	void findAllByBranchIdTest() {
		Facility facility1 = Facility.builder()
			.name("농구장")
			.description("실내 농구장")
			.branch(testBranch)
			.build();

		Facility facility2 = Facility.builder()
			.name("배드민턴장")
			.description("배드민턴 코트")
			.branch(testBranch)
			.build();

		facilityRepository.save(facility1);
		facilityRepository.save(facility2);

		var facilitiesByBranch = facilityRepository.findAllByBranchId(testBranch.getId());

		assertTrue(facilitiesByBranch.size() >= 2);

		facilitiesByBranch.forEach(facility ->
			print("Branch Facility: " + facility.getName() + ", ID: " + facility.getId()));
	}

	@Test
	@Order(6)
	@DisplayName("모든 시설 조회 테스트")
	void findAllFacilitiesTest() {
		long initialCount = facilityRepository.count();

		Facility facility1 = Facility.builder()
			.name("시설1")
			.description("설명1")
			.branch(testBranch)
			.build();

		Facility facility2 = Facility.builder()
			.name("시설2")
			.description("설명2")
			.branch(testBranch)
			.build();

		facilityRepository.save(facility1);
		facilityRepository.save(facility2);

		assertEquals(initialCount + 2, facilityRepository.count());

		facilityRepository.findAll().forEach(facility ->
			print("Facility: " + facility.getName() + ", ID: " + facility.getId()));
	}
}
