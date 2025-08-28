package com.ss.hanarowa.domain.member.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.member.dto.response.MemberListResponseDTO;
import com.ss.hanarowa.domain.member.entity.Role;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.domain.member.service.AdminMemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminMemberServiceImpl implements AdminMemberService {
	private final MemberRepository memberRepository;

	@Override
	public List<MemberListResponseDTO> getAllMembers() {
		return memberRepository.findAllByRoleNot(Role.ADMIN).stream()
			.map(m -> new MemberListResponseDTO(
				m.getName(),
				m.getBranch().getName(),
				m.getPhoneNumber(),
				m.getEmail(),
				m.getBirth()
			))
			.toList();
	}
}
