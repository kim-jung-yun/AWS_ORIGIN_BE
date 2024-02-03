package com.ssgtarbucks.persistence;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssgtarbucks.domain.IncomeDTO;
import com.ssgtarbucks.domain.ItemDTO;

@Mapper
public interface IncomeRepository {
	//입고내역 조회
	public List<IncomeDTO> selectIncomeListByBranchId(String branch_id);	
	public List<IncomeDTO> selectIncomeListByIncomeId(String income_id);	
	public ItemDTO selectItemAllByItemCode(String item_code);
	public int updateIncomeListResult(int item_id);
	public int selectSameItemCount(String item_code, Date item_exp);
	public int selectSameProductCount(int item_code);
	public int insertStockItem(String branch_id, int item_id);
	public int updateStockQuantity(int item_id);
	public int updateIncomeStatus(String income_status, String income_id);
	public int updateItemStatus(int item_id, String item_status);
}