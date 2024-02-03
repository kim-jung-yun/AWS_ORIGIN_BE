package com.ssgtarbucks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    private String jwtauthtoken;
	private String user_id;
	private String user_type;
	private String branch_id;
	private String branch_name;

}
