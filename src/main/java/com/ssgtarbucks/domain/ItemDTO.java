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
@Alias("ItemDTO")
public class ItemDTO {
	int item_id;
	String item_code;
	Date item_exp;
	String item_status;
	int product_id;
	
	String image_id;
	String image_name;
	String image_path;
}
