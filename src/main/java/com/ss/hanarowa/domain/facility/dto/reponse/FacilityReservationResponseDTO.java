package com.ss.hanarowa.domain.facility.dto.reponse;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityReservationResponseDTO {
	private long facilityId;
	private String facilityName;
	private String startedAt;
	private String duration;
	private String placeName;
	private String reservedAt;
}
