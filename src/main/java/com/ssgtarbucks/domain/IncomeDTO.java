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
@Alias("IncomeDTO")
public class IncomeDTO {
	int income_id;
	Long income_code;
	Date income_date;
	int income_amount;;
	String income_status;
	String branch_id;
	
	int income_list_id;
	int income_list_quantity;
	String income_list_result;
	int item_id;
	String item_code;
	Date item_exp;
	String item_status;
	String product_name;
	String product_standard;
	String product_unit;
}

/*

select 
	i.income_id,
	i.income_code,
    i.income_date,
    i.income_amount,
    i.income_status,
    i.branch_id,
    il.income_list_id,
    il.income_list_quantity,
    il.income_list_result,
    p.product_name,
    item.item_exp
from income i
join income_list il on il.income_id = i.income_id
join item item on item.item_id = il.item_id
join product p on item.product_id = p.product_id
#order by income_date desc
#where i.branch_id ='bid001
;
 */