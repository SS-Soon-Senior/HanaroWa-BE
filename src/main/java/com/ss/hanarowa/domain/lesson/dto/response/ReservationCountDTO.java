package com.ss.hanarowa.domain.lesson.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCountDTO {
	Long gisuId;
	int reservedCount;
	int capacity;
}
