package com.ss.hanarowa.domain.lesson.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeAvailabilityRequestDTO {
	@NotNull
	private Long branchId;
	
	@NotNull
	private String duration; // 2025-09-02 ~ 2025-09-29 tue-thu 17:00-18:00 형태
}