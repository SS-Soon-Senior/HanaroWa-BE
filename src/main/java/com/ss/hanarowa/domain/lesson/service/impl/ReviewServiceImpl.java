package com.ss.hanarowa.domain.lesson.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.lesson.dto.request.ReviewRequestDTO;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.Review;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.repository.ReviewRepository;
import com.ss.hanarowa.domain.lesson.service.ReviewService;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final LessonGisuRepository lessonGisuRepository;

    @Override
    public void createReview(Long lessonGisuId, String email, ReviewRequestDTO reviewRequestDTO) {
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        
        LessonGisu lessonGisu = lessonGisuRepository.findById(lessonGisuId)
            .orElseThrow(() -> new GeneralException(ErrorStatus.LESSON_NOT_FOUND));

        Review review = Review.builder()
            .rating(reviewRequestDTO.getRating())
            .reviewTxt(reviewRequestDTO.getReviewTxt())
            .member(member)
            .lessonGisu(lessonGisu)
            .build();

        reviewRepository.save(review);

    }
}
