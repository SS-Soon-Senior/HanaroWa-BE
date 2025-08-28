package com.ss.hanarowa.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswdRequestDTO {
	private String currentPassword; //현재 비밀번호

	private String newPassword; //새 비밀번호

	private String checkNewPassword; // 새 비밀번호 확인
}
