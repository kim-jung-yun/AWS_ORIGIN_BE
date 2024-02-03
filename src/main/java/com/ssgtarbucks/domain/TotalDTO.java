package com.ssgtarbucks.domain;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//통합검색 : product,item,category,stock,stock_location,qrcode 
//제외컬럼 : product_quantity, item_code, location_row,location_colunm,qrcode_모든컬럼
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias("TotalDTO")
public class TotalDTO {
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
/*
SELECT 
    p.product_id,		
    p.product_code,
    p.product_name,
    p.product_standard,
    p.product_unit,
    p.product_spec,
    p.product_quantity,
    p.category_id,
	p.image_id,		
	
    c.category_name,
    
    i.item_id,
    i.item_code,
    i.item_exp,
    i.item_status,
    
	q.qrcode_id,
	
    sl.location_code,	
    sl.location_area,
    sl.location_section,
    sl.location_section_name,
    sl.location_alias,
    s.stock_quantity
FROM
    item i
JOIN qrcode q ON i.qrcode_id = q.qrcode_id
JOIN product p ON i.product_id = p.product_id
JOIN stock s ON s.item_id = i.item_id
JOIN stock_location sl ON sl.location_id = s.location_id
JOIN category c on c.category_id = p.category_id
where s.branch_id='bid001' and (p.product_code like '%딸기%' or p.product_name like '%딸기%' 
								or p.product_standard like '%딸기%' or p.product_spec like '%딸기%'
								or c.category_name like  '%딸기%' or i.item_exp like '%딸기%' 
                                or sl.location_area like '%딸기%' or sl.location_code like '%딸기%'
                                or sl.location_section_name like '%딸기%' or sl.location_alias like '%딸기%'
                                or sl.location_alias like '%딸기%')
;
 */