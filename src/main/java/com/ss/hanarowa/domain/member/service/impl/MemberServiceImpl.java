package com.ss.hanarowa.domain.member.service.impl;

import static java.time.LocalDate.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.member.dto.MemberRegistDTO;
import com.ss.hanarowa.domain.member.dto.ModifyPasswdRequestDTO;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.domain.member.dto.MemberInfoDTO;
import com.ss.hanarowa.domain.member.service.MemberService;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;
import com.ss.hanarowa.global.util.Format;

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
			.role(memberRegistDTO.getRole())
			.build();

		memberRepository.save(member);
	}

	@Override
	public void infoRegist(MemberInfoDTO memberInfoDTO, long id) {
		Member member = memberRepository.findById(id).orElseThrow();

		member.setBirth(Format.getBirthAsLocalDate(memberInfoDTO.getBirth()));
		member.setPhoneNumber(memberInfoDTO.getPhoneNumber());

		memberRepository.save(member);
	}

	@Override
	public void withdraw(long id) {
		Member member = memberRepository.findById(id).orElseThrow();

		member.setDeletedAt(LocalDateTime.now());

		memberRepository.save(member);
	}

	@Override
	public void modifyInfo(MemberInfoDTO memberInfoDTO, long id) {
		Member member = memberRepository.findById(id).orElseThrow();

		if(Format.getBirthAsLocalDate(memberInfoDTO.getBirth()) != member.getBirth()){
			member.setBirth(Format.getBirthAsLocalDate(memberInfoDTO.getBirth()));
		}
		if(!Objects.equals(memberInfoDTO.getPhoneNumber(), member.getPhoneNumber())){
			member.setPhoneNumber(memberInfoDTO.getPhoneNumber());
		}

		memberRepository.save(member);
	}

	@Override
	public void modifyPassword(ModifyPasswdRequestDTO passwdRequestDTO, long id) {
		Member member = memberRepository.findById(id).orElseThrow();

		if(!passwordEncoder.matches(passwdRequestDTO.getCurrentPassword(), member.getPassword())) {
			throw new GeneralException(ErrorStatus.MEMBER_PASSWORD_WRONG);
		}
		if(!Objects.equals(passwdRequestDTO.getNewPassword(), passwdRequestDTO.getCheckNewPassword())) {
			throw new GeneralException(ErrorStatus.MEMBER_PASSWORD_UNMATCHED);
		}
		member.setPassword(passwordEncoder.encode(passwdRequestDTO.getNewPassword()));
		memberRepository.save(member);
	}

}
