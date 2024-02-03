package com.ssgtarbucks.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssgtarbucks.domain.UserDTO;
import com.ssgtarbucks.persistence.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public List<UserDTO> selectSearchUserBySearchWord(String searchWord) {
		return adminRepository.selectSearchUserBySearchWord(searchWord);
	}

	@Override
	public List<UserDTO> selectAllUserAndBranchInfo() {
		return adminRepository.selectAllUserAndBranchInfo();
	}

	@Override
	public UserDTO selectUserByBranchId(String branch_id) {
		return adminRepository.selectUserByBranchId(branch_id);
	}

	@Override
	public List<UserDTO> selectUserWithBranchIsNull() {
		return adminRepository.selectUserWithBranchIsNull();
	}

	@Transactional
	@Override
	public void updateUserTransaction(UserDTO userDTO, String initialUserId) {
		 try{
			 System.out.println(initialUserId+"/"+userDTO.getUser_id()+"/");
			 int update1 = adminRepository.updateUserAddBranch(initialUserId, userDTO.getUser_id());
			 System.out.println(update1);
			 int update2 = adminRepository.updateUserDeleteBranch(initialUserId);
			 System.out.println(update2);

	      }catch (Exception e){
	    	  System.out.println("sql update user error");
	      }
		 
	}

	@Override
	public int updateUserAddBranch(String initial_id, String user_id) {
		return adminRepository.updateUserAddBranch(initial_id, user_id);
	}

	@Override
	public UserDTO selectUserAndBranchInfo(String branch_id) {
		return adminRepository.selectUserAndBranchInfo(branch_id);
	}



}
