package com.ss.hanarowa.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDTO {
	private long facilityId;
	private String facilityName;
	private String description;
	private long branchId;
}
