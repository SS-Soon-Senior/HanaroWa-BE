package com.ss.hanarowa.domain.lesson.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeAvailabilityResponseDTO {
	private boolean available;
	private int availableRoomsCount;
	private List<TimeSlotAvailability> timeSlots;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class TimeSlotAvailability {
		private LocalDateTime startTime;
		private LocalDateTime endTime;
		private boolean available;
		private int availableRoomsCount;
	}
}