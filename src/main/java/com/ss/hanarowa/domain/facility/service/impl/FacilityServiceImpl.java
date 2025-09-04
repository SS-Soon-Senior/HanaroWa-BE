package com.ss.hanarowa.domain.facility.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.entity.Location;
import com.ss.hanarowa.domain.facility.dto.reponse.AdminFacilityResponseDTO;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {
	private final FacilityRepository facilityRepository;
	private final FacilityTimeRepository facilityTimeRepository;
	private final MemberRepository memberRepository;

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
	public List<AdminFacilityResponseDTO> getAllFacilityReservations() {
		List<FacilityTime> reservations = facilityTimeRepository.findAllByOrderByIdDesc();
		
		return reservations.stream()
			.map(reservation -> AdminFacilityResponseDTO.builder()
				.reservationId(reservation.getId())
				.facilityName(reservation.getFacility().getName())
				.memberName(reservation.getMember().getName())
				.branchName(reservation.getFacility().getBranch().getName())
				.startedAt(getFormattedLessonFirstDate(reservation.getStartedAt()))
				.reservedAt(reservedTime(reservation.getReservedAt()))
				.isUsed(isUsed(reservation.getEndedAt()))
				.build())
			.collect(Collectors.toList());
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
				.memberName(reservation.getMember().getName())
				.branchName(reservation.getFacility().getBranch().getName())
				.startedAt(getFormattedLessonFirstDate(reservation.getStartedAt()))
				.reservedAt(reservedTime(reservation.getReservedAt()))
				.isUsed(isUsed(reservation.getEndedAt()))
				.build())
			.collect(Collectors.toList());
	}
	public String getFormattedLessonFirstDate(LocalDateTime time) {
		try {
			DateTimeFormatter dateFormatter = DateTimeFormatter
				.ofPattern("M월 d일 (E)")
				.withLocale(Locale.KOREAN);
			String formattedDate = time.format(dateFormatter);

			DateTimeFormatter timeFormatter = DateTimeFormatter
				.ofPattern("a h:mm")
				.withLocale(Locale.KOREAN);
			String formattedTime = time.format(timeFormatter);

			return formattedDate + " " + formattedTime;

		} catch (Exception e) {
			return "날짜/시간 형식 오류";
		}
	}

	public boolean isUsed(LocalDateTime time) {
		LocalDateTime today = LocalDateTime.now();
		return today.isAfter(time);
	}

	public String reservedTime(LocalDateTime time) {
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy.MM.dd");

		return time.format(formatter2);
	}
}

