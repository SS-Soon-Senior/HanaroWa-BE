package com.ss.hanarowa.member.dto;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.ss.hanarowa.member.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAuthDTO extends User {
	private Long id;

	private String name;

	@Email(message = "올바른 이메일 형식이어야 합니다.")
	@NotBlank(message = "이메일은 필수입력 값입니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수입력 값입니다.")
	@Pattern(
		regexp = "^(?=.*[가-힣a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$",
		message = "비밀번호는 6~20자 사이이며, 문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다."
	)
	private String password;

	private Role role;


	public MemberAuthDTO(String email, String name, String password, Role role) {
		super(email, password, Collections.singletonList(new SimpleGrantedAuthority(role.name())));
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
	}

	public Map<String, Object> getClaims() {
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		map.put("name", name);
		map.put("role", role);

		return map;
	}
}
