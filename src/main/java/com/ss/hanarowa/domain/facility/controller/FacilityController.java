package com.ss.hanarowa.domain.facility.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.facility.dto.reponse.FacilityDetailResponseDTO;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityResponseDTO;
import com.ss.hanarowa.domain.facility.dto.request.FacilityReservationDTO;
import com.ss.hanarowa.domain.facility.service.FacilityService;
import com.ss.hanarowa.domain.member.service.MemberService;
import com.ss.hanarowa.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
@Tag(name = "[사용자] 시설", description = "사용자 시설 관련 API")
public class FacilityController {
	private final FacilityService facilityService;
	private final MemberService memberService;

	@GetMapping("/{branchId}")
	@Operation(summary = "시설 리스트 목록 API", description = "시설 리스트 목록을 조회합니다.")
	public ResponseEntity<ApiResponse<List<FacilityResponseDTO>>> getFacilityByBranchId(@PathVariable Long branchId) {
		List<FacilityResponseDTO> list = facilityService.getAllFacilities(branchId);
		return ResponseEntity.ok(ApiResponse.onSuccess(list));
	}

	@GetMapping("/detail/{facilityId}")
	@Operation(summary = "시설 상세보기", description = "시설 상세를 조회합니다.")
	public ResponseEntity<ApiResponse<FacilityDetailResponseDTO>> getDetailFacility(@PathVariable Long facilityId) {
		FacilityDetailResponseDTO facilityDetailResponseDTO = facilityService.getDetailFacility(facilityId);
		return ResponseEntity.ok(ApiResponse.onSuccess(facilityDetailResponseDTO));
	}

	@DeleteMapping("/{reservationId}")
	@Operation(summary = "시설 예약 취소", description = "시설 예약을 취소합니다.")
	public ResponseEntity<ApiResponse<Void>> deleteFacilityReservation(@PathVariable Long reservationId){
		facilityService.deleteFacilityReservation(reservationId);
		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

	@PostMapping("/reservation")
	@Operation(summary = "시설 예약하기", description = "시설을 예약합니다.")
	public ResponseEntity<ApiResponse<Void>> reservateFacility(@RequestBody FacilityReservationDTO facilityReservationDTO, Authentication authentication){
		String email = authentication.getName();
		Long memberId = memberService.getMemberIdByEmail(email);

		facilityService.reservateFacility(facilityReservationDTO, memberId);
		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

}
