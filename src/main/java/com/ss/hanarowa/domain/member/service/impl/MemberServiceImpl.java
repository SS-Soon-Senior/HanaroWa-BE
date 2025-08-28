package com.ss.hanarowa.domain.member.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.member.dto.request.MemberRegistRequestDTO;
import com.ss.hanarowa.domain.member.dto.request.ModifyPasswdRequestDTO;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.domain.member.dto.request.MemberInfoRequestDTO;
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
	public void credentialRegist(MemberRegistRequestDTO memberRegistRequestDTO) {
		memberRepository.findByEmail(memberRegistRequestDTO.getEmail()).orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_EMAIL_EXIST));

		memberRegistRequestDTO.setPassword(passwordEncoder.encode(memberRegistRequestDTO.getPassword()));

		Member member = Member.builder()
			.email(memberRegistRequestDTO.getEmail())
			.name(memberRegistRequestDTO.getName())
			.password(memberRegistRequestDTO.getPassword())
			.role(memberRegistRequestDTO.getRole())
			.build();

		memberRepository.save(member);
	}

	@Override
	public void infoRegist(MemberInfoRequestDTO memberInfoRequestDTO, String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
		member.setBirth(Format.getBirthAsLocalDate(memberInfoRequestDTO.getBirth()));
		member.setPhoneNumber(memberInfoRequestDTO.getPhoneNumber());

		memberRepository.save(member);
	}

	@Override
	public void withdraw(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		member.setDeletedAt(LocalDateTime.now());

		memberRepository.save(member);
	}

	@Override
	public void modifyInfo(MemberInfoRequestDTO memberInfoRequestDTO, String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		if(Format.getBirthAsLocalDate(memberInfoRequestDTO.getBirth()) != member.getBirth()){
			member.setBirth(Format.getBirthAsLocalDate(memberInfoRequestDTO.getBirth()));
		}
		if(!Objects.equals(memberInfoRequestDTO.getPhoneNumber(), member.getPhoneNumber())){
			member.setPhoneNumber(memberInfoRequestDTO.getPhoneNumber());
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
