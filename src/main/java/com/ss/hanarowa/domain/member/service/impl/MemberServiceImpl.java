package com.ss.hanarowa.domain.member.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.member.dto.MemberRegistDTO;
import com.ss.hanarowa.domain.member.dto.request.ModifyPasswdRequestDTO;
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
	private final BranchRepository branchRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void credentialRegist(MemberRegistDTO memberRegistDTO) {
		memberRepository.findByEmail(memberRegistDTO.getEmail()).orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_EMAIL_EXIST));

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
	public void infoRegist(MemberInfoDTO memberInfoDTO, String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
		member.setBirth(Format.getBirthAsLocalDate(memberInfoDTO.getBirth()));
		member.setPhoneNumber(memberInfoDTO.getPhoneNumber());

		memberRepository.save(member);
	}

	@Override
	public void withdraw(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		member.setDeletedAt(LocalDateTime.now());

		memberRepository.save(member);
	}

	@Override
	public void modifyInfo(MemberInfoDTO memberInfoDTO, String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		if(Format.getBirthAsLocalDate(memberInfoDTO.getBirth()) != member.getBirth()){
			member.setBirth(Format.getBirthAsLocalDate(memberInfoDTO.getBirth()));
		}
		if(!Objects.equals(memberInfoDTO.getPhoneNumber(), member.getPhoneNumber())){
			member.setPhoneNumber(memberInfoDTO.getPhoneNumber());
		}

		memberRepository.save(member);
	}

	@Override
	public void modifyPassword(ModifyPasswdRequestDTO passwdRequestDTO, String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		// 현재 비밀번호 확인
		if(!passwordEncoder.matches(passwdRequestDTO.getCurrentPassword(), member.getPassword())) {
			throw new GeneralException(ErrorStatus.MEMBER_PASSWORD_WRONG);
		}

		// 새 비밀번호의 유효성 확인
		String regex = "^(?=.*[가-힣a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$";
		if (!passwdRequestDTO.getNewPassword().matches(regex)) {
			throw new GeneralException(ErrorStatus.MEMBER_PASSWORD_INVALID);
		}


		// 새 비밀번호, 새 비밀번호 확인이 같은지
		if(!Objects.equals(passwdRequestDTO.getNewPassword(), passwdRequestDTO.getCheckNewPassword())) {
			throw new GeneralException(ErrorStatus.MEMBER_PASSWORD_UNMATCHED);
		}

		member.setPassword(passwordEncoder.encode(passwdRequestDTO.getNewPassword()));
		memberRepository.save(member);
	}


	@Override
	public void updateMemberBranch(long branchId, String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		Branch branch = branchRepository.findById(branchId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.BRANCH_NOT_FOUND));

		member.setBranch(branch);
		memberRepository.save(member);
	}

	@Override
	public Long getMemberIdByEmail(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
		return member.getId();
	}

}
