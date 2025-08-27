package com.ss.hanarowa.facility.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.facility.dto.reponse.FacilityResponseDTO;
import com.ss.hanarowa.facility.entity.Facility;
import com.ss.hanarowa.facility.repository.FacilityRepository;
import com.ss.hanarowa.facility.service.FacilityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {
	private final FacilityRepository facilityRepository;

	@Override
	public List<FacilityResponseDTO> getAllFacilities(long branchId) {

		return facilityRepository.findAllByBranchId(branchId).stream().map(this::toMainDTO).collect(Collectors.toList());
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
