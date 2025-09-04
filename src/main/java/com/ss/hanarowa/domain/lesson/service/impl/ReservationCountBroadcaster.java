package com.ss.hanarowa.domain.lesson.service.impl;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.lesson.dto.response.ReservationCountDTO;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.member.repository.MyLessonRepository;

import org.springframework.messaging.simp.SimpMessagingTemplate;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationCountBroadcaster {

	private final SimpMessagingTemplate template;
	private final LessonRepository lessonRepository;
	private final MyLessonRepository myLessonRepository;
	private final LessonGisuRepository lessonGisuRepository;

	public void publish(Long gisuId){
		LessonGisu lessongisu = lessonGisuRepository.findById(gisuId).orElseThrow(() -> new RuntimeException("LessonGisu not found"));
		int reserved = myLessonRepository.countByLessonGisu(lessongisu);
		int capacity = lessongisu.getCapacity();
		ReservationCountDTO reservationCountDTO = new ReservationCountDTO(gisuId,reserved,capacity);
		template.convertAndSend("/topic/lesson/"+gisuId+"/count", reservationCountDTO);
	}

}
