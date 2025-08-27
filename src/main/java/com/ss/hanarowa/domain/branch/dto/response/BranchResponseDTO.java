package com.ss.hanarowa.domain.branch.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponseDTO {
	private long branchId;
	private String locationName;
	private String branchName;
}
