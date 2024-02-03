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
@Alias("StockLocationDTO")
public class StockLocationDTO {
	int location_id;
	String location_code;
	String location_area;	
	String location_section;	
	String location_section_name;	
	String location_alias;	
	int qrcode_id;	
	String branch_id;
}