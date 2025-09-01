package com.ss.hanarowa.domain.lesson.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ss.hanarowa.domain.lesson.dto.request.ReviewRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.request.CreateLessonRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListByBranchIdResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListSearchResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.AppliedLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonMoreDetailResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.OfferedLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.service.LessonService;
import com.ss.hanarowa.domain.lesson.service.ReviewService;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.global.S3Service;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.ApiResponse;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name="[사용자] 강좌")
@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {
	private final LessonService lessonService;
	private final ReviewService reviewService;
	private final MemberRepository memberRepository;
	private final S3Service s3Service;

	@PostMapping("/{lessonGisuId}/review")
	@Operation(summary = "강좌 기수 리뷰 작성", description = "사용자가 특정 강좌 기수에 대한 리뷰를 작성합니다.")
	public ResponseEntity<ApiResponse<Void>> createReview(
		@PathVariable Long lessonGisuId,
		@Valid @RequestBody ReviewRequestDTO reviewRequestDTO,
		Authentication authentication) {

		String email = authentication.getName();

		reviewService.createReview(lessonGisuId, email, reviewRequestDTO);

		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

	@Operation(summary="사용자 강좌 상세 보기")
	@GetMapping("/{lessonId}")
	public ResponseEntity<ApiResponse<LessonMoreDetailResponseDTO>> getLessonDetail(@PathVariable Long lessonId){
		return ResponseEntity.ok(ApiResponse.onSuccess(lessonService.getLessonMoreDetail(lessonId)));
	}

	@GetMapping("/list/{branchId}")
	@Operation(summary = "지점별 카테고리별 강좌 목록 가져오기", description = "사용자가 지점별 카테고리별 강좌 목록 최신순으로 가져오기 조회합니다.")
	public ResponseEntity<ApiResponse<LessonListByBranchIdResponseDTO>> getLessonListByBranchId(
			@PathVariable Long branchId,
			@RequestParam(required = false) List<String> categories) {
		LessonListByBranchIdResponseDTO lessonList = lessonService.getLessonListByBranchId(branchId, categories);
		return ResponseEntity.ok(ApiResponse.onSuccess(lessonList));
	}

	@GetMapping("/list")
	@Operation(summary = "전체 강좌 검색", description = "사용자가 강좌 목록 검색 조회합니다.")
	public ResponseEntity<ApiResponse<List<LessonListSearchResponseDTO>>> getLessonListSearch(@RequestParam(value = "query",required = false) String query) {
		List<LessonListSearchResponseDTO> lessonList = lessonService.getLessonListSearch(query);
		return ResponseEntity.ok(ApiResponse.onSuccess(lessonList));
	}


	@Operation(summary="신청 강좌 목록 보기")
	@GetMapping("/reservation/applied/{memberId}")
	public ResponseEntity<AppliedLessonListResponseDTO> getAllAppliedLessons(
		@PathVariable Long memberId,
		Authentication authentication) {

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new GeneralException(ErrorStatus.MEMBER_NOT_AUTHORITY);
		}

		String email = authentication.getName();
		Member currentUser = memberRepository.findByEmail(email)
											 .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		if (!currentUser.getId().equals(memberId)) {
			throw new GeneralException(ErrorStatus.LESSONLIST_NOT_AUTHORITY);
		}

		List<LessonListResponseDTO> appliedLessons = lessonService.getAllAppliedLessons(memberId);
		return ResponseEntity.ok(new AppliedLessonListResponseDTO(appliedLessons));
	}

	@Operation(summary="개설 강좌 목록 보기")
	@GetMapping("/reservation/offered/{memberId}")
	public ResponseEntity<OfferedLessonListResponseDTO> getAllOfferedLessons(
		@PathVariable Long memberId,
		Authentication authentication) {

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new GeneralException(ErrorStatus.MEMBER_NOT_AUTHORITY);
		}

		String email = authentication.getName();
		Member currentUser = memberRepository.findByEmail(email)
											 .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		if (!currentUser.getId().equals(memberId)) {
			throw new GeneralException(ErrorStatus.LESSONLIST_NOT_AUTHORITY);
		}

		List<LessonListResponseDTO> offeredLessons = lessonService.getAllOfferedLessons(memberId);
		return ResponseEntity.ok(new OfferedLessonListResponseDTO(offeredLessons));
	}
	@PostMapping(path = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "강좌 개설", description = "사용자가 새로운 강좌를 개설합니다.")
	public ResponseEntity<ApiResponse<Void>> createLesson(
		@Valid @ModelAttribute CreateLessonRequestDTO createLessonRequestDTO, Authentication authentication) {

		try {
			String email = authentication.getName();
			MultipartFile lessonImg = createLessonRequestDTO.getLessonImg();
			// 이미지 파일이 있는 경우 S3에 업로드
			String imageUrl = null;
			if (lessonImg != null && !lessonImg.isEmpty()) {
				imageUrl = s3Service.uploadFile(lessonImg);
			}
			
			lessonService.createLesson(createLessonRequestDTO, email,imageUrl);
			return ResponseEntity.ok(ApiResponse.onSuccess(null));
		} catch (Exception e) {
			log.error("강좌 생성 중 오류 발생: {}", e.getMessage(), e);
			throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{lessonGisuId}")
	@Operation(summary = "강좌 수강 신청", description = "사용자가 특정 강좌 기수에 대한 수강을 신청합니다.")
	public ResponseEntity<ApiResponse<String>> applyForLesson( // 메서드 이름 변경
		@PathVariable Long lessonGisuId,
		Authentication authentication) {

		String email = authentication.getName();
		lessonService.applyForLesson(lessonGisuId, email); // 서비스 메서드 호출 변경
		String result = "강좌 기수 ID :" + lessonGisuId.toString();
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

}
