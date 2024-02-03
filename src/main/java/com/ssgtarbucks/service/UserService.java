package com.ssgtarbucks.service;

import com.ssgtarbucks.domain.UserDTO;

public interface UserService {
	
	public UserDTO selectUserByUserId(String user_id);
	
	UserDTO selectUserAndBranchToInfo(String string);
	
	int selectCountToFindUserExist(UserDTO userDTO);
	
	int updateUserByUserIdToChgPW(UserDTO userDTO);

	String generateTempPw();
	
	int insertTempCode(String temp_pw_code, String user_id);
	
	String selectTempCodeByUserId(String user_id);

	int deleteTempCodeByUserId(String user_id);

	public void Mail(String to, String tempCode);
	
	public void Message(String to, String tempCode);
	
	public void MessageCompnt(String to, String tempCode);
}
