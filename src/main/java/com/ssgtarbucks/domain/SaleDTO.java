package com.ssgtarbucks.domain;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//income+income_list
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias("SaleDTO")
public class SaleDTO {
	
	Long sale_code;
	Date sale_date;
	int sale_amount;
	String sale_status;
	int item_id;
	String product_name;
	String product_unit;
	String product_spec;
	String product_standard;
	int sale_list_quantity;
}

/*
select 
	s.sale_code,
	s.sale_date,
    s.sale_amount,
    s.sale_status,
    sl.item_id,
    p.product_name,
    sl.sale_list_quantity
from sale s
join sale_list sl on sl.sale_id = s.sale_id
join item i on i.item_id = sl.item_id
join product p on p.product_id = i.product_id
where s.branch_id = 'bid001'
;
 */