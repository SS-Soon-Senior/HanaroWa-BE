package com.ss.hanarowa.domain.lesson.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ReservationChangedEvent {
	Long gisuId;
}
