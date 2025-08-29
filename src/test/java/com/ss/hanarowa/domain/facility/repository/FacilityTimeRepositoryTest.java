package com.ss.hanarowa.domain.facility.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
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
import com.ss.hanarowa.domain.facility.entity.Facility;
import com.ss.hanarowa.domain.facility.entity.FacilityTime;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.repository.RepositoryTest;

class FacilityTimeRepositoryTest extends RepositoryTest {

	@Autowired
	private FacilityTimeRepository facilityTimeRepository;

	@Autowired
	private FacilityRepository facilityRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private LocationRepository locationRepository;

	private Facility testFacility;
	private Member testMember;

	@BeforeEach
	void setUp() {
		Location testLocation = Location.builder()
			.name("테스트 지역")
			.build();
		testLocation = locationRepository.save(testLocation);

		Branch testBranch = Branch.builder()
			.name("테스트 지점")
			.address("테스트 주소")
			.telNumber("02-0000-0000")
			.location(testLocation)
			.build();
		testBranch = branchRepository.save(testBranch);

		testFacility = Facility.builder()
			.name("테스트 시설")
			.description("테스트용 시설")
			.branch(testBranch)
			.build();
		testFacility = facilityRepository.save(testFacility);

		testMember = Member.builder()
			.name("테스트 회원")
			.email("test@example.com")
			.birth(LocalDate.of(1990, 1, 1))
			.branch(testBranch)
			.build();
		testMember = memberRepository.save(testMember);
	}

	@Test
	@Order(1)
	@DisplayName("시설 예약 생성 테스트")
	void createFacilityTimeTest() {
		FacilityTime facilityTime = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(LocalDateTime.of(2024, 12, 1, 10, 0))
			.endedAt(LocalDateTime.of(2024, 12, 1, 11, 0))
			.build();

		FacilityTime savedTime = facilityTimeRepository.save(facilityTime);

		assertNotNull(savedTime.getId());
		assertEquals(testFacility.getId(), savedTime.getFacility().getId());
		assertEquals(testMember.getId(), savedTime.getMember().getId());
		assertEquals(LocalDateTime.of(2024, 12, 1, 10, 0), savedTime.getStartedAt());
		assertEquals(LocalDateTime.of(2024, 12, 1, 11, 0), savedTime.getEndedAt());

		print("Created FacilityTime: " + savedTime.getStartedAt() + " - " + savedTime.getEndedAt() + ", ID: " + savedTime.getId());
	}

	@Test
	@Order(2)
	@DisplayName("시설 예약 조회 테스트")
	void findFacilityTimeTest() {
		FacilityTime facilityTime = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(LocalDateTime.of(2024, 12, 2, 14, 0))
			.endedAt(LocalDateTime.of(2024, 12, 2, 15, 0))
			.build();

		FacilityTime savedTime = facilityTimeRepository.save(facilityTime);

		FacilityTime foundTime = facilityTimeRepository.findById(savedTime.getId()).orElse(null);

		assertNotNull(foundTime);
		assertEquals(savedTime.getId(), foundTime.getId());
		assertEquals(LocalDateTime.of(2024, 12, 2, 14, 0), foundTime.getStartedAt());

		print("Found FacilityTime: " + foundTime.getStartedAt() + " - " + foundTime.getEndedAt() + ", ID: " + foundTime.getId());
	}

	@Test
	@Order(3)
	@DisplayName("시설 예약 수정 테스트")
	void updateFacilityTimeTest() {
		FacilityTime facilityTime = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(LocalDateTime.of(2024, 12, 3, 9, 0))
			.endedAt(LocalDateTime.of(2024, 12, 3, 10, 0))
			.build();

		FacilityTime savedTime = facilityTimeRepository.save(facilityTime);

		savedTime.setStartedAt(LocalDateTime.of(2024, 12, 3, 10, 0));
		savedTime.setEndedAt(LocalDateTime.of(2024, 12, 3, 11, 0));

		FacilityTime updatedTime = facilityTimeRepository.save(savedTime);

		assertEquals(LocalDateTime.of(2024, 12, 3, 10, 0), updatedTime.getStartedAt());
		assertEquals(LocalDateTime.of(2024, 12, 3, 11, 0), updatedTime.getEndedAt());

		print("Updated FacilityTime: " + updatedTime.getStartedAt() + " - " + updatedTime.getEndedAt());
	}

	@Test
	@Order(4)
	@DisplayName("시설 예약 삭제 테스트")
	void deleteFacilityTimeTest() {
		FacilityTime facilityTime = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(LocalDateTime.of(2024, 12, 4, 16, 0))
			.endedAt(LocalDateTime.of(2024, 12, 4, 17, 0))
			.build();

		FacilityTime savedTime = facilityTimeRepository.save(facilityTime);
		Long timeId = savedTime.getId();

		facilityTimeRepository.deleteById(timeId);

		assertTrue(facilityTimeRepository.findById(timeId).isEmpty());

		print("FacilityTime with ID " + timeId + " has been deleted");
	}

	@Test
	@Order(5)
	@DisplayName("시설별 기간별 예약 조회 테스트")
	void findFacilityTimesByFacilityAndDateRangeTest() {
		LocalDateTime start1 = LocalDateTime.of(2024, 12, 5, 10, 0);
		LocalDateTime end1 = LocalDateTime.of(2024, 12, 5, 11, 0);

		LocalDateTime start2 = LocalDateTime.of(2024, 12, 5, 14, 0);
		LocalDateTime end2 = LocalDateTime.of(2024, 12, 5, 15, 0);

		LocalDateTime start3 = LocalDateTime.of(2024, 12, 6, 10, 0);
		LocalDateTime end3 = LocalDateTime.of(2024, 12, 6, 11, 0);

		FacilityTime time1 = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(start1)
			.endedAt(end1)
			.build();

		FacilityTime time2 = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(start2)
			.endedAt(end2)
			.build();

		FacilityTime time3 = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(start3)
			.endedAt(end3)
			.build();

		facilityTimeRepository.save(time1);
		facilityTimeRepository.save(time2);
		facilityTimeRepository.save(time3);

		LocalDateTime searchStart = LocalDateTime.of(2024, 12, 5, 0, 0);
		LocalDateTime searchEnd = LocalDateTime.of(2024, 12, 5, 23, 59);

		List<FacilityTime> facilityTimes = facilityTimeRepository
			.findFacilityTimesByFacilityAndStartedAtBetween(testFacility, searchStart, searchEnd);

		assertEquals(2, facilityTimes.size());

		facilityTimes.forEach(time ->
			print("FacilityTime in range: " + time.getStartedAt() + " - " + time.getEndedAt()));
	}

	@Test
	@Order(6)
	@DisplayName("회원별 예약 조회 테스트")
	void findAllByMemberIdTest() {
		FacilityTime time1 = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(LocalDateTime.of(2024, 12, 7, 10, 0))
			.endedAt(LocalDateTime.of(2024, 12, 7, 11, 0))
			.build();

		FacilityTime time2 = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(LocalDateTime.of(2024, 12, 8, 10, 0))
			.endedAt(LocalDateTime.of(2024, 12, 8, 11, 0))
			.build();

		facilityTimeRepository.save(time1);
		facilityTimeRepository.save(time2);

		List<FacilityTime> memberTimes = facilityTimeRepository.findAllByMemberId(testMember.getId());

		assertTrue(memberTimes.size() >= 2);

		memberTimes.forEach(time ->
			print("Member FacilityTime: " + time.getStartedAt() + " - " + time.getEndedAt()));
	}

	@Test
	@Order(7)
	@DisplayName("최신순 전체 예약 조회 테스트")
	void findAllByOrderByIdDescTest() {
		long initialCount = facilityTimeRepository.count();

		FacilityTime time1 = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(LocalDateTime.of(2024, 12, 9, 10, 0))
			.endedAt(LocalDateTime.of(2024, 12, 9, 11, 0))
			.build();

		FacilityTime time2 = FacilityTime.builder()
			.facility(testFacility)
			.member(testMember)
			.startedAt(LocalDateTime.of(2024, 12, 10, 10, 0))
			.endedAt(LocalDateTime.of(2024, 12, 10, 11, 0))
			.build();

		facilityTimeRepository.save(time1);
		facilityTimeRepository.save(time2);

		List<FacilityTime> allTimes = facilityTimeRepository.findAllByOrderByIdDesc();

		assertTrue(allTimes.size() >= 2);

		print("Total FacilityTimes: " + allTimes.size());
		allTimes.stream().limit(5).forEach(time ->
			print("Recent FacilityTime: " + time.getStartedAt() + " - " + time.getEndedAt() + ", ID: " + time.getId()));
	}
}
