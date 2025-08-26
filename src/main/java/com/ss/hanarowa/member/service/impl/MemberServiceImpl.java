package com.ss.hanarowa.member.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.member.dto.MemberInfoDTO;
import com.ss.hanarowa.member.dto.MemberRegistDTO;
import com.ss.hanarowa.member.entity.Member;
import com.ss.hanarowa.member.repository.MemberRepository;
import com.ss.hanarowa.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void credentialRegist(MemberRegistDTO memberRegistDTO) {
		memberRepository.findByEmail(memberRegistDTO.getEmail()).ifPresent(member -> {
			throw new RuntimeException("이미 존재하는 이메일입니다.");
		});

		memberRegistDTO.setPassword(passwordEncoder.encode(memberRegistDTO.getPassword()));
		Member member = Member.builder()
			.email(memberRegistDTO.getEmail())
			.name(memberRegistDTO.getName())
			.password(memberRegistDTO.getPassword())
			.build();

		memberRepository.save(member);
	}

	@Override
	public void infoRegist(MemberInfoDTO memberInfoDTO, long id) {
		Member member = memberRepository.findById(id).orElseThrow();

		member.setBirth(memberInfoDTO.getBirth());
		member.setPhoneNumber(memberInfoDTO.getPhoneNumber());

		memberRepository.save(member);
	}

}
