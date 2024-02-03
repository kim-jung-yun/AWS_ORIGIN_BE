package com.ssgtarbucks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssgtarbucks.domain.TokenDTO;
import com.ssgtarbucks.domain.UserDTO;
import com.ssgtarbucks.jwt.JwtUtil;
import com.ssgtarbucks.service.UserService;
import com.ssgtarbucks.util.SendMessage;



@RestController
@RequestMapping("/api/v1")
public class UserController {


	@Autowired
	AuthenticationManagerBuilder authenticationManagerBuilder;
	
	@Autowired
	JwtUtil jwtUtil;

	@Value("${jwt.name}")
	String tokenKey;

	@Autowired
	private UserService userService;

	@Autowired
	private SendMessage sendMessage;
     
	@PostMapping("/user/login")
	public ResponseEntity<TokenDTO> login(@RequestBody UserDTO userDTO) {
		System.out.println("UserController - /user/login(POST) >>> userDTO : " + userDTO);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userDTO.getUser_id(), userDTO.getUser_pw());
		
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtil.generateToken(userDTO.getUser_id());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(tokenKey, "Bearer+" + jwt);
		
		
		TokenDTO tokenDTO = new TokenDTO();
		userDTO = userService.selectUserAndBranchToInfo(userDTO.getUser_id());
		tokenDTO.setUser_id(userDTO.getUser_id());
		tokenDTO.setUser_type(userDTO.getUser_type());
		tokenDTO.setBranch_id(userDTO.getBranch_id());
		tokenDTO.setBranch_name(userDTO.getBranch_name());
		tokenDTO.setJwtauthtoken("Bearer+" + jwt);
		
		
		System.out.println("UserController - /user/login(POST) >>> tokenDTO : " + tokenDTO);
		return new ResponseEntity<>(tokenDTO, httpHeaders, HttpStatus.OK);
	}
	
	@PostMapping("/user/find")
    public ResponseEntity<UserDTO> find(@RequestBody UserDTO userDTO) { //비밀번호찾기
		System.out.println("UserController - /user/find(POST) >>> userDTO : " + userDTO);

		if(userDTO.getAuth_code()==null) {
			System.out.println("리액트에서 보낸 인증코드:"+userDTO.getAuth_code());
			
			
			
		}else {
			//입력한 id,email일치하는 사람 있는지 조회
			int count = userService.selectCountToFindUserExist(userDTO);
			System.out.println(count);
			
			//일치하는 사원있음
			if(count>0) {
				userService.deleteTempCodeByUserId(userDTO.getUser_id());
				System.out.println("인증보냄");
				String tempCode = userService.generateTempPw();
				System.out.println("인증코드 : " + tempCode);
		        userService.insertTempCode(tempCode, userDTO.getUser_id());
		        
			    //메일인증
		        userService.Mail(userDTO.getUser_email(), tempCode);
		        
		        //문자인증
		        //userService.Message(userDTO.getUser_phone(), tempCode);
		        //userService.MessageCompnt(userDTO.getUser_phone(), tempCode);
			
			}else{
				userDTO = null;
			}

		}
		
        return ResponseEntity.ok(userDTO);
    }
	
	@PostMapping("/user/verify")
    public ResponseEntity<String> verify(@RequestBody UserDTO userDTO) {
		String returnValue = "실패";
		System.out.println("UserController - /user/varify(POST) >>> userDTO : " + userDTO);
		
		String savedTempCode = userService.selectTempCodeByUserId(userDTO.getUser_id());
		System.out.println(savedTempCode);
		if(savedTempCode.equals(userDTO.getAuth_code())) {
			System.out.println("성공했습니다.");
			returnValue = "성공";
			userDTO.setUser_pw(new BCryptPasswordEncoder().encode(userDTO.getUser_id()));
			int result = userService.updateUserByUserIdToChgPW(userDTO);
			userService.deleteTempCodeByUserId(userDTO.getUser_id());
		}
		
		return ResponseEntity.ok(returnValue);
    }
}
