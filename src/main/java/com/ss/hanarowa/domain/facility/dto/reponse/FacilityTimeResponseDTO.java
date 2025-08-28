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
public class FacilityTimeResponseDTO {
	private String date;
	private String startTime;

	private String endTime;
}
