package com.ssgtarbucks.domain;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//stock+stock_location+product+item+qrcode => Storage
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias("StockDTO")
public class StockDTO {
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
	
	int product_id;
	String product_code;
	String product_name;
	String product_standard;
	String product_unit;
	String product_spec;
	String category_id;
	String image_id;
	
	int item_id;
	String item_code;
	Date item_exp;
	
	int qrcode_id;
	byte[] qrcode_code;
}

/*
SELECT distinct
    p.product_id,		
    p.product_code,
    p.product_name,
    p.product_standard,
    p.product_unit,
    p.product_spec,
    p.category_id,
    p.image_id,		
    
	s.stock_id,
    s.stock_quantity,
    
    sl.location_id,
    sl.location_code,	
    sl.location_area,
    sl.location_section_name,
    sl.location_colum,
    sl.location_row,
    sl.location_alias,
    
    i.item_id,
    i.item_code,
    i.item_exp,			
    
	q.qrcode_id,
    q.qrcode_code

FROM
    item i
JOIN qrcode q ON i.qrcode_id = q.qrcode_id
JOIN product p ON i.product_id = p.product_id
JOIN stock s ON s.item_id = i.item_id
JOIN stock_location sl ON sl.location_id = s.location_id
where  s.branch_id='bid058'
order by sl.location_id
;
 */