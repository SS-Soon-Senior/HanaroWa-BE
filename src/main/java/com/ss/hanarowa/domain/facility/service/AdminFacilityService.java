package com.ss.hanarowa.domain.facility.service;

import java.util.List;

import com.ss.hanarowa.domain.facility.dto.reponse.AdminFacilityResponseDTO;

public interface AdminFacilityService {

	List<AdminFacilityResponseDTO> getAllFacilityReservations();

	void deleteFacilityReservation(Long reservationId);
}
