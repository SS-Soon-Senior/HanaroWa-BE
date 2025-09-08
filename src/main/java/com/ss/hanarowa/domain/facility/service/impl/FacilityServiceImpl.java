package com.ss.hanarowa.domain.facility.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityDetailResponseDTO;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityImageResponseDTO;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityReservationResponseDTO;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityResponseDTO;

import com.ss.hanarowa.domain.facility.dto.request.FacilityReservationDTO;
import com.ss.hanarowa.domain.facility.entity.Facility;
import com.ss.hanarowa.domain.facility.entity.FacilityImage;
import com.ss.hanarowa.domain.facility.entity.FacilityTime;
import com.ss.hanarowa.domain.facility.repository.FacilityRepository;
import com.ss.hanarowa.domain.facility.repository.FacilityTimeRepository;
import com.ss.hanarowa.domain.facility.service.FacilityService;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;
import com.ss.hanarowa.global.util.Format;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {
	private final FacilityRepository facilityRepository;
	private final FacilityTimeRepository facilityTimeRepository;
	private final MemberRepository memberRepository;
	private final BranchRepository branchRepository;

	@Override
	public List<FacilityResponseDTO> getAllFacilities(long branchId) {

		return facilityRepository.findAllByBranchId(branchId).stream().map(this::toMainDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteFacilityReservation(Long reservationId) throws GeneralException{
		facilityTimeRepository.deleteById(reservationId);
	}

	@Override
	public void reservateFacility(FacilityReservationDTO facilityReservationDTO, Long memberId) {
		Facility facility = facilityRepository.findById(facilityReservationDTO.getFacilityId()).orElseThrow(()->new GeneralException(ErrorStatus.FACILITY_NOT_FOUND));
		Member member = memberRepository.findById(memberId).orElseThrow(() ->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
		String reservationDate = facilityReservationDTO.getReservationDate();
		String startTime = facilityReservationDTO.getStartTime();
		String endTime = facilityReservationDTO.getEndTime();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime reservationDateTime = LocalDateTime.parse(reservationDate + " " + startTime, formatter);
		LocalDateTime reservationEndTime = LocalDateTime.parse(reservationDate + " " + endTime, formatter);


		FacilityTime facilityTime = FacilityTime.builder()
			.facility(facility)
			.member(member)
			.startedAt(reservationDateTime)
			.endedAt(reservationEndTime)
			.build();

		facilityTimeRepository.save(facilityTime);
	}

	@Override
	public FacilityDetailResponseDTO getDetailFacility(Long facilityId) throws GeneralException{
		Facility facility = facilityRepository.findById(facilityId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.FACILITY_NOT_FOUND));

		LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
		LocalDateTime endDateTime = LocalDate.now().plusDays(3).atTime(LocalTime.MAX);

		List<FacilityTime> availableBlocks = facilityTimeRepository.findFacilityTimesByFacilityAndStartedAtBetween(facility, startDateTime, endDateTime);

		Map<String, List<String>> availableSlotsByDate = new TreeMap<>();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		for (FacilityTime timeBlock : availableBlocks) {
			LocalDateTime start = timeBlock.getStartedAt();
			LocalDateTime end = timeBlock.getEndedAt();
			String dateKey = start.format(dateFormatter);

			LocalDateTime currentStartTime = start;

			while (currentStartTime.isBefore(end)) {
				availableSlotsByDate.computeIfAbsent(dateKey, k -> new ArrayList<>())
					.add(currentStartTime.format(timeFormatter));

				currentStartTime = currentStartTime.plusHours(1);
			}
		}

		List<FacilityImage> facilityImages = facility.getFacilityImages();
		List<FacilityImageResponseDTO> imageResponseDTOS = facilityImages.stream().map(f ->
			FacilityImageResponseDTO.builder()
				.facilityImgId(f.getId())
				.imgUrl(f.getFacilityImage())
				.build()
		).toList();

		return FacilityDetailResponseDTO.builder()
			.facilityName(facility.getName())
			.facilityImages(imageResponseDTOS)
			.facilityDescription(facility.getDescription())
			.facilityId(facility.getId())
			.facilityTimes(availableSlotsByDate)
			.build();
	}

	private FacilityResponseDTO toMainDTO(Facility facility) {
		return FacilityResponseDTO.builder()
			.facilityId(facility.getId())
			.facilityName(facility.getName())
			.facilityDescription(facility.getDescription())
			.mainImage(facility.getFacilityImages().getFirst())
			.build();
	}


	@Override
	public List<FacilityReservationResponseDTO> getAllMyFacilityReservations(String email) {

		Member currentUser = memberRepository.findByEmail(email)
											 .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_AUTHORITY));

		List<FacilityTime> reservations = facilityTimeRepository.findAllByMemberId(currentUser.getId());

		return reservations.stream()
			.map(reservation -> FacilityReservationResponseDTO.builder()
				.reservationId(reservation.getId())
				.facilityName(reservation.getFacility().getName())
				.branchName(reservation.getFacility().getBranch().getName())
				.startedAt(Format.getFormattedDate(reservation.getStartedAt()))
				.reservedAt(Format.reservedTime(reservation.getReservedAt()))
				.isUsed(Format.isUsed(reservation.getStartedAt()))
				.build())
			.collect(Collectors.toList());
	}

	public String getBranchName(Long branchId) {
		return branchRepository.findById(branchId)
							   .map(Branch::getName)
							   .orElseThrow(() -> new RuntimeException("해당 지점을 찾을 수 없습니다. branchId=" + branchId));
	}
}

