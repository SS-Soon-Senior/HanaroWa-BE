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
public class FacilityReservationResponseDTO {
	private Long reservationId; // FacilityTime ID
	private String facilityName; // 시설명
	private String memberName; // 예약자 이름
	private String memberEmail; // 예약자 이메일
	private String branchName; // 지점명
	private String startedAt; // 예약 시작 시간
	private String reservedAt; // 예약 한 시간
	private Boolean isUsed; // 사용 유무
}
