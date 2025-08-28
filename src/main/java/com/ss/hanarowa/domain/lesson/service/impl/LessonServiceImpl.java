package com.ss.hanarowa.domain.lesson.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListByBranchId;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.lesson.service.LessonService;
import com.ss.hanarowa.domain.member.entity.MyLesson;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonServiceImpl implements LessonService {
	private final LessonRepository lessonRepository;
	private final BranchRepository branchRepository;

	@Override
	public List<MyLesson> getAllAppliedLessons() {
		return null;
	}

	@Override
	public List<Lesson> getAllOfferedLessons() {
		return null;
	}

	@Override
	public LessonListByBranchId getLessonListByBranchId(Long branchId) {
		// 지점 조회
		Branch branch = branchRepository.findById(branchId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.BRANCH_NOT_FOUND));
		
		// 지점별 강좌 목록 조회
		List<Lesson> lessons = lessonRepository.findByBranchIdOrderByIdDesc(branchId);

		return LessonListByBranchId.builder()
			.branchId(branch.getId())
			.locationName(branch.getLocation().getName())
			.branchName(branch.getName())
			.Lessons(lessons)
			.build();
	}
}
