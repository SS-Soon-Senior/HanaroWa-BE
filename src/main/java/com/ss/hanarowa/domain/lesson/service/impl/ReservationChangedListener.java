package com.ss.hanarowa.domain.lesson.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.ss.hanarowa.domain.lesson.event.ReservationChangedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReservationChangedListener {
	private final ReservationCountBroadcaster broadcaster;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onChanged(ReservationChangedEvent event){
		broadcaster.publish(event.getGisuId());
	}
}
