package com.ssgtarbucks.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssgtarbucks.domain.UserDTO;

@Mapper
public interface AdminRepository {
	public List<UserDTO> selectSearchUserBySearchWord(String searchWord);
	public List<UserDTO> selectAllUserAndBranchInfo();
	public UserDTO selectUserByBranchId(String branch_id);
	public List<UserDTO> selectUserWithBranchIsNull();
	public int updateUserDeleteBranch(String user_id);
	public int updateUserAddBranch(String initial_id, String user_id);
	public UserDTO selectUserAndBranchInfo(String branch_id);
}
