package com.ss.hanarowa.domain.ai.service; // 경로는 예시입니다.

import com.ss.hanarowa.domain.ai.dto.response.CourseRecResponseDto;
import com.ss.hanarowa.domain.ai.dto.response.JobRecResponseDto;

public interface AIRecService {

	/**
	 * AI 강좌 추천
	 * @param interest 사용자의 관심사
	 * @return 추천 강좌 정보 DTO
	 */
	CourseRecResponseDto recommendCourses(String interest);

	/**
	 * AI 직업 추천
	 * @param experience 사용자의 이전 경험/경력
	 * @return 추천 직업 정보 DTO
	 */
	JobRecResponseDto recommendJobs(String experience);
}