package com.ss.hanarowa.domain.facility.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.facility.dto.reponse.FacilityResponseDTO;
import com.ss.hanarowa.domain.facility.service.FacilityService;
import com.ss.hanarowa.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
public class FacilityController {
	private final FacilityService facilityService;

	@GetMapping("/{branchId}")
	@Tag(name = "시설 리스트 목록")
	public ResponseEntity<ApiResponse<List<FacilityResponseDTO>>> getFacilityByBranchId(@PathVariable Long branchId) {
		List<FacilityResponseDTO> list = facilityService.getAllFacilities(branchId);
		return ResponseEntity.ok(ApiResponse.onSuccess(list,"시설 리스트 목록 출력"));
	}
}
