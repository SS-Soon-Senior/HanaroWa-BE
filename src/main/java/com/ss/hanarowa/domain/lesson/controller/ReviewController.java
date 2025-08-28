package com.ss.hanarowa.domain.lesson.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.lesson.dto.request.ReviewRequestDTO;
import com.ss.hanarowa.domain.lesson.service.ReviewService;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "리뷰", description = "리뷰 관련 API")
public class ReviewController {
    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    /**
     * 강좌 리뷰 작성
     * @param lessonId 강좌 아이디
     * @param reviewRequestDTO 평점, 내용
     */
    @PostMapping("/lesson/{lessonId}/review")
    @Operation(summary = "강좌 리뷰 작성", description = "사용자가 특정 강좌에 대한 리뷰를 작성합니다.")
    public ResponseEntity<ApiResponse<Void>> createReview(
            @PathVariable Long lessonId,
            @Valid @RequestBody ReviewRequestDTO reviewRequestDTO,
            Authentication authentication) {
        
        String email = authentication.getName();
        Member member = memberRepository.getMemberByEmail(email);
        
        reviewService.createReview(lessonId, member.getId(), reviewRequestDTO);
        
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
