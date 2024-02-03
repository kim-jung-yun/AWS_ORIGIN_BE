package com.ssgtarbucks.domain;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//product+item+stock+image+category+stock_location
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias("ProductDTO")
public class ProductDTO {
	int product_id;
	String product_code;
	String product_name;
	String product_standard;
	String product_unit;
	String product_spec;
	
	String category_name;
	
	int item_id;
	String item_code;
	Date item_exp;
	int qrcode_id;
	
	int stock_quantity;
	Date stock_date;
	
	String image_id;
	String image_path;
	
	String location_code;
	String location_area;
	String location_section;
	String location_section_name;
	String location_alias;
	
	//총 갯수
	int total_product_quantity;

}

/*
select 
	p.product_code,
	p.product_name,
    p.product_standard,
    p.product_unit,
    p.category_id,
    i.item_id,
	i.item_exp,
    i.qrcode_id,
    s.stock_quantity,
    s.stock_date,
    img.image_id,
    img.image_path
from product p 
join item i on p.product_id = i.product_id
join stock s on s.item_id = i.item_id
join image img on img.image_id = p.image_id
where s.branch_id = 'bid001'
;

 */