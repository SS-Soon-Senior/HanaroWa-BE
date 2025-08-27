package com.ss.hanarowa.domain.facility.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.facility.dto.reponse.FacilityDetailResponseDTO;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityResponseDTO;
import com.ss.hanarowa.domain.facility.entity.Facility;
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

	@Override
	public List<FacilityResponseDTO> getAllFacilities(long branchId) {

		return facilityRepository.findAllByBranchId(branchId).stream().map(this::toMainDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteFacilityReservation(Long reservationId) throws GeneralException{
		facilityTimeRepository.deleteById(reservationId);
	}

	@Override
	public FacilityDetailResponseDTO getDetailFacility(Long facilityId) throws GeneralException{
		Facility facility = facilityRepository.findById(facilityId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.FACILITY_NOT_FOUND));
		return FacilityDetailResponseDTO.builder()
			.facilityName(facility.getName())
			.facilityImages(facility.getFacilityImages())
			.facilityDescription(facility.getDescription())
			.facilityId(facility.getId())
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

}