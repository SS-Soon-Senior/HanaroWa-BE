package com.ss.hanarowa.facility.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.facility.dto.FacilityDTO;
import com.ss.hanarowa.facility.entity.Facility;
import com.ss.hanarowa.facility.repository.FacilityRepository;
import com.ss.hanarowa.facility.service.FacilityService;

@Service
public class FacilityServiceImpl implements FacilityService {
	FacilityRepository facilityRepository;

	@Override
	public List<FacilityDTO> getAllFacilities() {
		return facilityRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	private FacilityDTO toDTO(Facility facility) {
		return FacilityDTO.builder()
			.facilityId(facility.getId())
			.facilityName(facility.getName())
			.facilityDescription(facility.getDescription())
			.build();
	}

}
