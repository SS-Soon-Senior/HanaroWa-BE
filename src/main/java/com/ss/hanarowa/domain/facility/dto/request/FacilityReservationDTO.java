package com.ss.hanarowa.domain.facility.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FacilityReservationDTO {
		private long facilityId;

		private String reservationDate;

		private String startTime;

		private String endTime;
}
