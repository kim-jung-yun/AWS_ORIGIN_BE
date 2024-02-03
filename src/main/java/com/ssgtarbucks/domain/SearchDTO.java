package com.ssgtarbucks.domain;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias("SearchDTO")
public class SearchDTO {
	int product_id;
	String product_code;
	String product_name;
	String product_standard;
	String product_unit;
	String product_spec;
	String category_id;
	String image_id;
	String item_code;
	String image_path;
	String item_qrcode_value;
	String item_qrcode_path;
	
	String category_name;
	
	int item_id;
	Date item_exp;
	String item_status;
	
	int qrcode_id;

	int location_id;
	String location_code;
	String location_area;
	String location_section;
	String location_section_name;
	String location_alias;
	String location_qrcode_value;
	String location_qrcode_path;
	
	int stock_id;
	Date stock_date;
	int stock_quantity;
	String branch_id;
	String searchWord;

}
