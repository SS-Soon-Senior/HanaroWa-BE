package com.ss.hanarowa.domain.facility.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.facility.dto.reponse.AdminFacilityResponseDTO;
import com.ss.hanarowa.domain.facility.service.FacilityService;
import com.ss.hanarowa.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/facility")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "[관리자] 시설", description = "관리자 시설 관련 API")
public class AdminFacilityController {
	private final FacilityService facilityService;

	@GetMapping("")
	@Operation(summary = "시설 예약 내역 API", description = "모든 시설 예약 내역을 최신순으로 조회합니다.")
	public ResponseEntity<ApiResponse<List<AdminFacilityResponseDTO>>> getAdminFacilityList() {
		List<AdminFacilityResponseDTO> reservations = facilityService.getAllFacilityReservations();
		return ResponseEntity.ok(ApiResponse.onSuccess(reservations));
	}

}
