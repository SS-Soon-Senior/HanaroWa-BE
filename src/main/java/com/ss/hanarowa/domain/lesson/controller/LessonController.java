package com.ss.hanarowa.domain.lesson.controller;

public class LessonController {
	private final ReviewService reviewService;
	private final MemberRepository memberRepository;

	@PostMapping("/lesson/{lessonId}/review")
	@Operation(summary = "강좌 리뷰 작성", description = "사용자가 특정 강좌에 대한 리뷰를 작성합니다.")
	public ResponseEntity<ApiResponse<Void>> createReview(
		@PathVariable Long lessonId,
		@Valid @RequestBody ReviewRequestDTO reviewRequestDTO,
		Authentication authentication) {

		String email = authentication.getName();
		Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		reviewService.createReview(lessonId, member.getId(), reviewRequestDTO);

		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

	private final LessonService lessonService;

	@Operation(summary="관리자 강좌 상세 보기")
	@GetMapping("/{lessonId}")
	public ResponseEntity<ApiResponse<LessonMoreDetailResponseDTO>> getLessonDetail(@PathVariable Long lessonId){
		return ResponseEntity.ok(ApiResponse.onSuccess(lessonService.getLessonMoreDetail(lessonId)));
	}
}
