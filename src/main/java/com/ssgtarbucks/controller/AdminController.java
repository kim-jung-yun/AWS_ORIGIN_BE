package com.ssgtarbucks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssgtarbucks.domain.TokenDTO;
import com.ssgtarbucks.domain.TotalDTO;
import com.ssgtarbucks.domain.UserDTO;
import com.ssgtarbucks.jwt.JwtUtil;
import com.ssgtarbucks.service.AdminService;
import com.ssgtarbucks.service.UserService;

import io.swagger.models.Model;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@Autowired
	AuthenticationManagerBuilder authenticationManagerBuilder;

	@Autowired
	JwtUtil jwtUtil;

	@Value("${jwt.name}")
	String tokenKey;

	@Autowired
	private AdminService adminService;
	
//	@GetMapping("/main")
//    public ResponseEntity<String> main(Model model) { // 회원 가입
//		HttpHeaders header = new HttpHeaders();
//		header.add("Content-Type","application/json;charset=UTF-8");
//		
//		
//		System.out.println("AdminController - /main(GET) >>>");
//    	//관리자 권한만 접근가능
//		
//        return ResponseEntity<>( new ,header,HttpStatus.OK);
//    }
	
	@GetMapping("/main")
	public ResponseEntity<String> main() {
	    // 필요한 데이터 삽입
	    System.out.println("AdminController - /main(GET) >>>");

	    HttpHeaders header = new HttpHeaders();
	    header.add("Content-Type", "application/json;charset=UTF-8");

	    // 응답 데이터를 생성
	    String responseData = "Main Page Data"; // 실제 응답 데이터를 생성하거나 가져오는 로직을 추가
	    return new ResponseEntity<>(responseData, header, HttpStatus.OK);
	}
	
	@GetMapping("/branch/search/")
    public ResponseEntity<List<UserDTO>> search(@RequestParam String branch_id, String searchWord) { 
		System.out.println("BranchController - /branch/search/(GET) >>>"+branch_id+"/"+searchWord);
		
		List<UserDTO> userList = adminService.selectSearchUserBySearchWord(searchWord);
		System.out.println(userList);
		
		return ResponseEntity.ok(userList);
    }
	
	@GetMapping("/branch/list")
    public ResponseEntity<List<UserDTO>> branchList () { 
		System.out.println("BranchController - /integrate/search(GET) >>>");
		
		List<UserDTO> userList = adminService.selectAllUserAndBranchInfo();
		System.out.println(userList);
		
		return ResponseEntity.ok(userList);
    }
	
	@GetMapping("/branch/detail")
    public ResponseEntity<UserDTO> branchDetail(@RequestParam String branch_id) { 
		System.out.println("BranchController - /branch/detail(GET) >>>"+branch_id);
		
		UserDTO userDTO = adminService.selectUserByBranchId(branch_id);
		
		System.out.println(userDTO);
		
		return ResponseEntity.ok(userDTO);
    }
	
	@GetMapping("/branch/user/modify/list")
    public ResponseEntity<List<UserDTO>> branchIsNullUser() { 
		System.out.println("BranchController - /branch/user/modify/list(GET) >>>");
		
		List<UserDTO> userList = adminService.selectUserWithBranchIsNull();
		
		return ResponseEntity.ok(userList);
    }
	
	@PutMapping("/branch/user/modify")
    public String modifyUser(@RequestBody UserDTO selectedData, @RequestParam String initialUserId) { 
		System.out.println("BranchController - /branch/user/modify(PUT) >>>");
		System.out.println(selectedData);
		adminService.updateUserTransaction(selectedData,initialUserId);
		//System.out.println(adminService.updateUserAddBranch(initialUserId, selectedData.getUser_id()));
		return "수정완료";
    }
	
	@GetMapping("/info")
    public ResponseEntity<UserDTO> info(@RequestParam String branch_id) { 
		System.out.println("AdminController - /info(GET) >>>"+branch_id);
		
		UserDTO userDTO = adminService.selectUserAndBranchInfo(branch_id);
		
		return ResponseEntity.ok(userDTO);
    }
}