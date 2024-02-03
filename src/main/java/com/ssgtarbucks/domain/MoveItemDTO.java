package com.ssgtarbucks.domain;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias("MoveItemDTO")
public class MoveItemDTO {
	String branch_id;
	String location_code;
	int location_id;
	List<Integer> item_list;
	String location_area;
	String location_alias;
	String location_section;
    public List<Integer> getList() {
        return item_list;
    }

}