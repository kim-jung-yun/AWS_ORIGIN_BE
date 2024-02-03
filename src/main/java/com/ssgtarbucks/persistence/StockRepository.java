package com.ssgtarbucks.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssgtarbucks.domain.IncomeDTO;
import com.ssgtarbucks.domain.MoveItemDTO;
import com.ssgtarbucks.domain.MoveQRItemDTO;
import com.ssgtarbucks.domain.SaleDTO;
import com.ssgtarbucks.domain.StockDTO;
import com.ssgtarbucks.domain.StockLocationDTO;

@Mapper
public interface StockRepository {
		
	public List<StockDTO> selectStorageByBranchId(String branch_id);
	
	//수정된 갯수로 넘김 (3으로 넘길시 재고 3으로 수정됨) -> 수량정정시 필요
	public int updateStockQuantityByItemId(int stock_quantity, int item_id);
	
	//차감할 갯수로 넘김 (3으로 넘길 시 원래재고 빼기 3으로 수정됨) -> 판매갱신시 필요
	public int updateStockQuantity(int stock_quantity, int item_id);
	
	public List<SaleDTO> selectSaleListByBranchId(String branch_id);
	
	public int updateSaleList(String branch_id);
	
	public int updateItemStatus(int item_id);

	public int updateStockByItemIdToMove(MoveItemDTO dto);

	public int updateStockQByItemQRCodeToMove(MoveQRItemDTO dto);

	public int selectLocationToFindLocationId(MoveItemDTO dto);

	public String selectLocationToFindSection(MoveItemDTO dto);

	public List<IncomeDTO> selectInspectionListByBranchId(String branch_id);
	
	public StockLocationDTO selectStockLocationByLocationCode(String location_code);
	
	public StockDTO selectStockByItemId(int item_id);
	
	public int updateStockLocation(int location_id, int item_id);
	
	public List<StockLocationDTO> selectStockLocationByBranchId(String branch_id);

}