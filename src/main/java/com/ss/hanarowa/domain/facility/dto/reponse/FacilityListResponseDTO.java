package com.ss.hanarowa.domain.facility.dto.reponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FacilityListResponseDTO {
	private String facilityName;
	private List<FacilityResponseDTO> facilities;
}
