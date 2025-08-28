package com.ss.hanarowa.domain.facility.dto.reponse;

import java.util.List;

import com.ss.hanarowa.domain.facility.entity.FacilityImage;
import com.ss.hanarowa.domain.facility.entity.FacilityTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FacilityDetailResponseDTO {
	private long facilityId;
	private String facilityName;
	private String facilityDescription;

	private List<FacilityImageResponseDTO> facilityImages;

	private List<FacilityTimeResponseDTO> facilityTimes;
}