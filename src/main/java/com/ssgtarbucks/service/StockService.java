package com.ssgtarbucks.service;

import java.util.List;

import com.ssgtarbucks.domain.IncomeDTO;
import com.ssgtarbucks.domain.MoveItemDTO;
import com.ssgtarbucks.domain.SaleDTO;
import com.ssgtarbucks.domain.StockDTO;
import com.ssgtarbucks.domain.StockLocationDTO;


public interface StockService {
		
	public List<StockDTO> selectStorageByBranchId(String branch_id);
	
	public int updateStockQuantityByItemId(int stock_quantity, int item_id);
	
	public List<SaleDTO> selectSaleListByBranchId(String branch_id);
	
	public void updateSaleTransaction(String branch_id, List<SaleDTO> saleList);

	int updateStockByItemIdToMove(MoveItemDTO moveItemDTOList);
	
	public List<IncomeDTO> selectInspectionListByBranchId(String branch_id);
	
	public StockLocationDTO selectStockLocationByLocationCode(String location_code);

	public StockDTO selectStockByItemId(int item_id);
	
	public int updateStockLocation(int location_id, int item_id);
	
	public List<StockLocationDTO> selectStockLocationByBranchId(String branch_id);

}
