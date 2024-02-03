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
@Alias("MoveQRItemDTO")
public class MoveQRItemDTO {
	String branch_id;
	String location_qrcode_value;
	String item_qrcode_value;
	int location_id;
	int item_id;
}
