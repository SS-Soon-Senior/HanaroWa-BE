package com.ss.hanarowa.domain.facility.dto.reponse;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AdminFacilityResponseDTO {
	private Long reservationId; // FacilityTime ID
	private String facilityName; // 시설명
	private String memberName; // 예약자 이름
	private String memberEmail; // 예약자 이메일₩
	private String branchName; // 지점명
	private String locationName; // 위치명
	private LocalDateTime startedAt; // 예약 시작 사간
	private LocalDateTime endedAt; // 예약 종료 시간
}
