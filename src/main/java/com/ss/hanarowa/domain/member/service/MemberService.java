package com.ss.hanarowa.domain.member.service;

import com.ss.hanarowa.domain.branch.dto.response.BranchResponseDTO;
import com.ss.hanarowa.domain.member.dto.request.MemberRegistRequestDTO;
import com.ss.hanarowa.domain.member.dto.request.MemberInfoRequestDTO;
import com.ss.hanarowa.domain.member.dto.request.ModifyPasswdRequestDTO;
import com.ss.hanarowa.domain.member.entity.Member;

public interface MemberService {
	void credentialRegist(MemberRegistRequestDTO memberRegistRequestDTO);
	void infoRegist(MemberInfoRequestDTO memberInfoRequestDTO, String email);
	void withdraw(String email);
	void modifyInfo(MemberInfoRequestDTO memberInfoRequestDTO, String email);
	void updateMemberBranch(long branchId, String email);
	BranchResponseDTO getMemberBranch(String email);
	void modifyPassword(ModifyPasswdRequestDTO passwdRequestDTO, String email);
	Long getMemberIdByEmail(String email);

	Member getMemberByEmail(String email);
}
