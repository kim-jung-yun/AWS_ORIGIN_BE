package com.ssgtarbucks.domain;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias("UserDTO")
public class UserDTO {
	private String user_id;
	private String user_pw;
	private String user_name;
	private String user_type;
	private String user_phone;
	private String user_email;
	private String branch_id;
	private String branch_name;
	private String branch_address;
	private String auth_code;
}
