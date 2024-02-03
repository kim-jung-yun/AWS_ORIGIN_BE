package com.ssgtarbucks.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.ssgtarbucks.domain.StockLocationDTO;
import com.ssgtarbucks.domain.UserDTO;

@Mapper
public interface UserRepository {
	
	int insertUserToJoin(UserDTO dto);
	
	UserDTO selectUserByUserId(String user_id);

	UserDTO selectUserAndBranchToInfo(String string);
	
	int selectCountToFindUserExist(UserDTO userDTO);
	
	int updateUserByUserIdToChgPW(UserDTO userDTO);
	
	int insertTempCode(String temp_pw_code, String user_id);
	
	String selectTempCodeByUserId(String user_id);
	
	int deleteTempCodeByUserId(String user_id);
	
}