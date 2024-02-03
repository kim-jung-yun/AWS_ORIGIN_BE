package com.ssgtarbucks.service;

import java.util.List;

import com.ssgtarbucks.domain.UserDTO;

public interface AdminService {
	public List<UserDTO> selectSearchUserBySearchWord(String searchWord);
	public List<UserDTO> selectAllUserAndBranchInfo();
	public UserDTO selectUserByBranchId(String branch_id);
	public List<UserDTO> selectUserWithBranchIsNull();
	
	public void updateUserTransaction(UserDTO userDTO, String initialUserId);
	public int updateUserAddBranch(String initial_id, String user_id);
	
	public UserDTO selectUserAndBranchInfo(String branch_id);


}
