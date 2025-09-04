package com.ss.hanarowa.domain.facility.service;

import java.util.List;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityDetailResponseDTO;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityReservationResponseDTO;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityResponseDTO;
import com.ss.hanarowa.domain.facility.dto.request.FacilityReservationDTO;

public interface FacilityService {
	List<FacilityResponseDTO> getAllFacilities(long branchId);

	FacilityDetailResponseDTO getDetailFacility(Long facilityId);

	void deleteFacilityReservation(Long reservationId);

	void reservateFacility(FacilityReservationDTO facilityReservationDTO, Long memberId);

	List<FacilityReservationResponseDTO> getAllMyFacilityReservations(String email);

	String getBranchName(Long branchId);
}
