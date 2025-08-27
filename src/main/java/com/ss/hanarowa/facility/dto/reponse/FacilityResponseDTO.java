package com.ss.hanarowa.facility.dto.reponse;

import com.ss.hanarowa.facility.entity.FacilityImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class FacilityResponseDTO {
	private long facilityId;
	private String facilityName;
	private String facilityDescription;

	private FacilityImage mainImage;          // 대표 이미지만 필요할 때 사용

}


