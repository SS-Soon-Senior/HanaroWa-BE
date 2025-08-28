package com.ss.hanarowa.global.response.code.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.ss.hanarowa.domain.facility.entity.Facility;
import com.ss.hanarowa.global.response.code.BaseErrorCode;
import com.ss.hanarowa.global.response.code.ReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
	//공통 서버 에러
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러"),
	
	//멤버 관련 에러
	MEMBER_BAD_REQUEST(HttpStatus.BAD_REQUEST,"MEMBER400","잘못된 요청입니다."),
	MEMBER_PASSWORD_UNMATCHED(HttpStatus.BAD_REQUEST, "MEMBER400", "새 비밀번호가 일치하지 않습니다."),
	MEMBER_PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "MEMBER400", "비밀번호는 6~20자 사이이며, 문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다."),
	MEMBER_PASSWORD_WRONG(HttpStatus.UNAUTHORIZED, "MEMBER401", "비밀번호가 틀렸습니다."),
	MEMBER_NOT_AUTHORITY(HttpStatus.FORBIDDEN, "MEMBER403", "권한이 없습니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "해당 유저가 없습니다"),
	MEMBER_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED,"MEMBER401","아이디 또는 비밀번호를 확인해주세요."),
	MEMBER_EMAIL_EXIST(HttpStatus.CONFLICT, "MEMBER409", "이미 존재하는 이메일입니다"),

	//지점 관련 에러
	BRANCH_NOT_FOUND(HttpStatus.NOT_FOUND, "BRANCH404", "해당 지점이 없습니다."),
    //시설 관련 에러
	FACILITY_NOT_FOUND(HttpStatus.NOT_FOUND, "FACILITY404", "해당 시설이 없습니다."),
	//강좌 관련 에러
	LESSONGISU_NOT_FOUND(HttpStatus.NOT_FOUND, "LESSONGISU404", "해당 강좌 기수를 찾을 수 없습니다."),
	LESSON_NOT_FOUND(HttpStatus.NOT_FOUND, "LESSON404", "해당 강좌를 찾을 수 없습니다."),
	APPLIED_NOT_FOUND(HttpStatus.NOT_FOUND, "LESSON404", "수강 중인 강의 정보를 불러올 수 없습니다."),
	OFFERED_NOT_FOUND(HttpStatus.NOT_FOUND, "LESSON404", "개설한 강의 정보를 불러올 수 없습니다."),
	LESSONLIST_NOT_AUTHORITY(HttpStatus.FORBIDDEN, "LESSON403", "강좌 목록을 조회할 권한이 없습니다."),
	LESSON_ALREADY_APPLIED(HttpStatus.BAD_REQUEST, "LESSON400", "이미 신청한 강좌입니다."),
	LESSON_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST, "LESSON400", "강좌 정원이 초과되었습니다."),
	LESSONROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "LESSON_ROOM404", "해당 강의실을 찾을 수 없습니다."),
	CURRICULUM_NOT_FOUND(HttpStatus.NOT_FOUND,"CURRICULUM404","해당 커리큘럼을 찾을 수 없습니다."),
	//리뷰 관련 에러
	REVIEW_BAD_REQUEST(HttpStatus.BAD_REQUEST, "REVIEW400", "평점은 1점 이상이어야 합니다."),

	//Gemini API 관련 에러
	AI_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AI500", "AI 서비스 요청 중 오류가 발생했습니다.");

	private final HttpStatusCode httpStatusCode;
	private final String code;
	private final String message;

	@Override
	public ReasonDTO getReason() {
		return ReasonDTO.builder()
						.httpStatusCode(httpStatusCode)
						.isSuccess(false)
						.code(code)
						.message(message)
						.build();
	}
}
