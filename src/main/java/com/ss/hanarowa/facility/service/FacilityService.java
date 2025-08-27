package com.ss.hanarowa.facility.service;

import java.util.List;

import com.ss.hanarowa.facility.dto.reponse.FacilityResponseDTO;

public interface FacilityService {
	List<FacilityResponseDTO> getAllFacilities(long branchId);
}
