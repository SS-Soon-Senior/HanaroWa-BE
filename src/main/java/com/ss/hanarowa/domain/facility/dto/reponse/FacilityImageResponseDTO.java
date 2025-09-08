package com.ss.hanarowa.domain.facility.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityImageResponseDTO {
	private long facilityImgId;
	private String imgUrl;
}
