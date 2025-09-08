package com.ss.hanarowa.domain.facility.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.facility.dto.reponse.AdminFacilityResponseDTO;
import com.ss.hanarowa.domain.facility.entity.FacilityTime;
import com.ss.hanarowa.domain.facility.repository.FacilityTimeRepository;
import com.ss.hanarowa.domain.facility.service.AdminFacilityService;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.util.Format;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminFacilityServiceImpl implements AdminFacilityService {
	private final FacilityTimeRepository facilityTimeRepository;

	@Override
	public List<AdminFacilityResponseDTO> getAllFacilityReservations() {
		List<FacilityTime> reservations = facilityTimeRepository.findAllByOrderByIdDesc();

		return reservations.stream()
			.map(reservation -> AdminFacilityResponseDTO.builder()
				.reservationId(reservation.getId())
				.facilityName(reservation.getFacility().getName())
				.memberName(reservation.getMember().getName())
				.branchName(String.format("%s %s", reservation.getFacility().getBranch().getLocation().getName(), reservation.getFacility().getBranch().getName()))
				.startedAt(Format.getFormattedDate(reservation.getStartedAt()))
				.reservedAt(Format.reservedTime(reservation.getReservedAt()))
				.isUsed(Format.isUsed(reservation.getStartedAt()))
				.build())
			.collect(Collectors.toList());
	}

	@Override
	public void deleteFacilityReservation(Long reservationId) throws GeneralException {
		facilityTimeRepository.deleteById(reservationId);
	}
}
