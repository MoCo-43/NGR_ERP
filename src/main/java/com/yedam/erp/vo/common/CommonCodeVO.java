package com.yedam.erp.vo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeVO {
    private String codeId;
    private String codeName;
    private String groupId;
    private Integer sortOrder;
    private String useYn;
    private String codeNote;
}
