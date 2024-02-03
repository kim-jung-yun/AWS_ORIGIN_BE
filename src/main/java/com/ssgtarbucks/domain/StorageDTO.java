package com.ssgtarbucks.domain;

import java.sql.Date;

import lombok.Data;


//stock+stock_location+product+item+qrcode 
@Data
public class StorageDTO {
	int item_id;
	String item_code;
	Date item_exp;
	String item_status;
	
	int product_id;
	String product_name;
	String product_standard;
	String product_unit;
	String product_spec;
	String category_id;
	String category_name;
	String image_path;
	String image_id;
	String image_name;
	

	int stock_id;
	int stock_quantity;
	Date stock_date;
	String branch_id;
	
	int location_id;
	String location_code;
	String location_area;
	String location_section;
	String location_section_name;
	int location_column;
	int location_row;
	String location_alias;

}
