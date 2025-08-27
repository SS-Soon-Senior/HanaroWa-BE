package com.ss.hanarowa.domain.facility.service;

import java.util.List;

import com.ss.hanarowa.domain.facility.dto.reponse.FacilityResponseDTO;

public interface FacilityService {
	List<FacilityResponseDTO> getAllFacilities(long branchId);
}