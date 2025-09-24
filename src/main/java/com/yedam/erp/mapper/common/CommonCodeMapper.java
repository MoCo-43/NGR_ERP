package com.yedam.erp.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.common.CommonCodeVO;


@Mapper
public interface CommonCodeMapper {

   
    List<CommonCodeVO> selectCommonCodes(
        @Param("groupId") String groupId,
        @Param("keyword") String keyword,
        @Param("useYn") String useYn
    );

  
    CommonCodeVO selectCommonCode(
        @Param("groupId") String groupId,
        @Param("codeId") String codeId
    );
}
