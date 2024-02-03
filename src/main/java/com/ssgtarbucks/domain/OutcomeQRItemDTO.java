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
@Alias("OutcomeQRItemDTO")
public class OutcomeQRItemDTO {
	String branch_id;
	String qrcode_value;
	int location_id;
	int item_id;
	////////////////////////////////////////
	Long outcome_code;
	int outcome_id;
	/////////////////////////////////////
	Long discard_code;
	int discard_id;
}
