package com.ss.hanarowa.domain.lesson.service;

import com.ss.hanarowa.domain.lesson.dto.request.ReviewRequestDTO;

public interface ReviewService {
    void createReview(Long lessonId, Long memberId, ReviewRequestDTO reviewRequestDTO);
}
