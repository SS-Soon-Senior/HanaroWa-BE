package com.ss.hanarowa.domain.member.dto;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.ss.hanarowa.domain.member.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAuthResponseDTO extends User {
	private String email;

	private String password;

	private Role role;

	public MemberAuthResponseDTO(String email, String password, Role role) {
		super(email, password, Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role.name())));
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Map<String, Object> getClaims() {
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		map.put("role", role);

		return map;
	}
}
