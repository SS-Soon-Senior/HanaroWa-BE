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
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityListResponseDTO;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityReservationResponseDTO;
import com.ss.hanarowa.domain.facility.dto.reponse.FacilityResponseDTO;
import com.ss.hanarowa.domain.facility.dto.request.FacilityReservationDTO;
import com.ss.hanarowa.domain.facility.service.FacilityService;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.service.MemberService;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.ApiResponse;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

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

	@GetMapping
	@Operation(summary = "내 지점 시설 목록 API", description = "로그인한 멤버의 branchId로 시설 목록을 조회합니다.")
	public ResponseEntity<ApiResponse<FacilityListResponseDTO>> getFacilityByBranchId(Authentication authentication) {
		// 로그인한 사용자 가져오기
		String email = authentication.getName();
		Member member = memberService.getMemberByEmail(email);

		// branchId와 branchName 추출
		Long branchId = member.getBranch().getId();
		String branchName = member.getBranch().getName();

		// 해당 branch의 시설 리스트 조회
		List<FacilityResponseDTO> facilities = facilityService.getAllFacilities(branchId);

		// 응답 DTO 조립
		FacilityListResponseDTO responseDto = new FacilityListResponseDTO(branchName, facilities);

		return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
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

	@GetMapping("/reservation")
	@Operation(summary = "시설 예약 목록 조회", description = "시설 예약 목록을 조회합니다.")
	public ResponseEntity<ApiResponse<List<FacilityReservationResponseDTO>>> getAllMyFacilityReservations(
		Authentication authentication) {
		String email = authentication.getName();
		List<FacilityReservationResponseDTO> reservations =
			facilityService.getAllMyFacilityReservations(email);

		return ResponseEntity.ok(ApiResponse.onSuccess(reservations));
	}

}
