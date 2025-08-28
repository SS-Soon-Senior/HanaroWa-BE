package com.ss.hanarowa.domain.member.service;

import com.ss.hanarowa.domain.member.dto.MemberRegistDTO;
import com.ss.hanarowa.domain.member.dto.MemberInfoDTO;
import com.ss.hanarowa.domain.member.dto.ModifyPasswdRequestDTO;
import com.ss.hanarowa.domain.member.entity.Member;

public interface MemberService {
	void credentialRegist(MemberRegistDTO memberRegistDTO);
	void infoRegist(MemberInfoDTO memberInfoDTO, String email);
	void withdraw(String email);
	void modifyInfo(MemberInfoDTO memberInfoDTO, String email);
	void updateMemberBranch(long branchId, String email);
	void modifyPassword(ModifyPasswdRequestDTO passwdRequestDTO, String email);
	Long getMemberIdByEmail(String email);
}
