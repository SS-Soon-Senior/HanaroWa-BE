package com.ss.hanarowa.domain.member.service;

import com.ss.hanarowa.domain.member.dto.request.MemberRegistRequestDTO;
import com.ss.hanarowa.domain.member.dto.request.MemberInfoRequestDTO;
import com.ss.hanarowa.domain.member.dto.request.ModifyPasswdRequestDTO;

public interface MemberService {
	void credentialRegist(MemberRegistRequestDTO memberRegistRequestDTO);
	void infoRegist(MemberInfoRequestDTO memberInfoRequestDTO, long id);
	void withdraw(long id);
	void modifyInfo(MemberInfoRequestDTO memberInfoRequestDTO, long id);
	void updateMemberBranch(long branchId, long memberId);
	void modifyPassword(ModifyPasswdRequestDTO passwdRequestDTO, long id);
	Long getMemberIdByEmail(String email);
}
