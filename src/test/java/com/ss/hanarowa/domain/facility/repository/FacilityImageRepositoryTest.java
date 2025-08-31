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
import com.ss.hanarowa.domain.facility.entity.FacilityImage;
import com.ss.hanarowa.repository.RepositoryTest;

class FacilityImageRepositoryTest extends RepositoryTest {

	@Autowired
	private FacilityImageRepository facilityImageRepository;

	@Autowired
	private FacilityRepository facilityRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private LocationRepository locationRepository;

	private Facility testFacility;

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
	}

	@Test
	@Order(1)
	@DisplayName("시설 이미지 생성 테스트")
	void createFacilityImageTest() {
		FacilityImage facilityImage = FacilityImage.builder()
			.facilityImage("https://example.com/gym1.jpg")
			.facility(testFacility)
			.build();

		FacilityImage savedImage = facilityImageRepository.save(facilityImage);

		assertNotNull(savedImage.getId());
		assertEquals("https://example.com/gym1.jpg", savedImage.getFacilityImage());
		assertEquals(testFacility.getId(), savedImage.getFacility().getId());

		print("Created FacilityImage: " + savedImage.getFacilityImage() + ", ID: " + savedImage.getId());
	}

	@Test
	@Order(2)
	@DisplayName("시설 이미지 조회 테스트")
	void findFacilityImageTest() {
		FacilityImage facilityImage = FacilityImage.builder()
			.facilityImage("https://example.com/pool1.jpg")
			.facility(testFacility)
			.build();

		FacilityImage savedImage = facilityImageRepository.save(facilityImage);

		FacilityImage foundImage = facilityImageRepository.findById(savedImage.getId()).orElse(null);

		assertNotNull(foundImage);
		assertEquals(savedImage.getId(), foundImage.getId());
		assertEquals("https://example.com/pool1.jpg", foundImage.getFacilityImage());

		print("Found FacilityImage: " + foundImage.getFacilityImage() + ", ID: " + foundImage.getId());
	}

	@Test
	@Order(3)
	@DisplayName("시설 이미지 수정 테스트")
	void updateFacilityImageTest() {
		FacilityImage facilityImage = FacilityImage.builder()
			.facilityImage("https://example.com/old-image.jpg")
			.facility(testFacility)
			.build();

		FacilityImage savedImage = facilityImageRepository.save(facilityImage);

		savedImage.setFacilityImage("https://example.com/new-image.jpg");
		FacilityImage updatedImage = facilityImageRepository.save(savedImage);

		assertEquals("https://example.com/new-image.jpg", updatedImage.getFacilityImage());
		assertEquals(savedImage.getId(), updatedImage.getId());

		print("Updated FacilityImage: " + updatedImage.getFacilityImage());
	}

	@Test
	@Order(4)
	@DisplayName("시설 이미지 삭제 테스트")
	void deleteFacilityImageTest() {
		FacilityImage facilityImage = FacilityImage.builder()
			.facilityImage("https://example.com/delete-test.jpg")
			.facility(testFacility)
			.build();

		FacilityImage savedImage = facilityImageRepository.save(facilityImage);
		Long imageId = savedImage.getId();

		facilityImageRepository.deleteById(imageId);

		assertTrue(facilityImageRepository.findById(imageId).isEmpty());

		print("FacilityImage with ID " + imageId + " has been deleted");
	}

	@Test
	@Order(5)
	@DisplayName("모든 시설 이미지 조회 테스트")
	void findAllFacilityImagesTest() {
		long initialCount = facilityImageRepository.count();

		FacilityImage image1 = FacilityImage.builder()
			.facilityImage("https://example.com/image1.jpg")
			.facility(testFacility)
			.build();

		FacilityImage image2 = FacilityImage.builder()
			.facilityImage("https://example.com/image2.jpg")
			.facility(testFacility)
			.build();

		FacilityImage image3 = FacilityImage.builder()
			.facilityImage("https://example.com/image3.jpg")
			.facility(testFacility)
			.build();

		facilityImageRepository.save(image1);
		facilityImageRepository.save(image2);
		facilityImageRepository.save(image3);

		assertEquals(initialCount + 3, facilityImageRepository.count());

		facilityImageRepository.findAll().forEach(image ->
			print("FacilityImage: " + image.getFacilityImage() + ", ID: " + image.getId()));
	}
}
