package com.ss.hanarowa.facility.dto.reponse;

import java.util.List;

import com.ss.hanarowa.facility.entity.FacilityImage;

public class FacilityDetailResponseDTO {
	private long facilityId;
	private String facilityName;
	private String facilityDescription;

	private List<FacilityImage> facilityImages;
}
