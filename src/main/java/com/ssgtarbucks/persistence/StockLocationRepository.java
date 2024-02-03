package com.ssgtarbucks.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.ssgtarbucks.domain.UserDTO;

@Mapper
public interface StockLocationRepository {
	/* 테스트용으로 만듬 -> 안씀 */
	int insertUserToJoin(UserDTO dto);
	
	UserDTO selectUserByUserId(String user_id);

	UserDTO selectUserAndBranchToInfo(String string);
	
}