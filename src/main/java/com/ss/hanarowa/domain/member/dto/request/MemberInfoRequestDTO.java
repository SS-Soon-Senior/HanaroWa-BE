package com.ss.hanarowa.domain.member.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoRequestDTO {
	@Column
	@Pattern(
		regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$",
		message = "생년월일은 yyyyMMdd 형식이어야 합니다."
	)
	private String birth;

	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식이 아닙니다.")
	@Column
	private String phoneNumber;


}
