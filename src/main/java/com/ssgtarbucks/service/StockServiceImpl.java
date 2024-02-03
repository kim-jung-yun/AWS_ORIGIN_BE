package com.ssgtarbucks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssgtarbucks.domain.IncomeDTO;
import com.ssgtarbucks.domain.MoveItemDTO;
import com.ssgtarbucks.domain.SaleDTO;
import com.ssgtarbucks.domain.StockDTO;
import com.ssgtarbucks.domain.StockLocationDTO;
import com.ssgtarbucks.persistence.StockRepository;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;


	@Override
	public List<StockDTO> selectStorageByBranchId(String branch_id) {
		return stockRepository.selectStorageByBranchId(branch_id);
	}


	@Override
	public int updateStockQuantityByItemId(int stock_quantity, int item_id) {
		return stockRepository.updateStockQuantityByItemId(stock_quantity, item_id);
	}


	@Override
	public List<SaleDTO> selectSaleListByBranchId(String branch_id) {
		return stockRepository.selectSaleListByBranchId(branch_id);
	}


	@Transactional
	@Override
	public void updateSaleTransaction(String branch_id, List<SaleDTO> saleList) {
		 try{
			 
			 stockRepository.updateSaleList(branch_id);
			 
			 for(int i = 0; i<saleList.size(); i++) {
				 stockRepository.updateStockQuantityByItemId(saleList.get(i).getSale_list_quantity(), saleList.get(i).getItem_id());
				 stockRepository.updateItemStatus(saleList.get(i).getItem_id());
			 }
			 
	      }catch (Exception e){
	   }		
	}

	@Override
	public int updateStockByItemIdToMove(MoveItemDTO moveItemDTO) {
		//별칭 -> location_section 조회
		String location_section = stockRepository.selectLocationToFindSection(moveItemDTO);
		moveItemDTO.setLocation_section(location_section);
		//location_code로 파싱
		//나중에 @로 변경!!
		String location_code = moveItemDTO.getBranch_id()+"-"+ moveItemDTO.getLocation_area()+"-"+location_section;
		moveItemDTO.setLocation_code(location_code);	
		System.out.println("location_code : "+ location_code);
		//location_id 조회
		int location_id = stockRepository.selectLocationToFindLocationId(moveItemDTO);
		System.out.println("location_id : " + location_id);
		moveItemDTO.setLocation_id(location_id);
		//장소이동
		System.out.println("moveItemDTO 출력 >>> " + moveItemDTO);
		int result = stockRepository.updateStockByItemIdToMove(moveItemDTO);
		System.out.println("장소이동 갯수 : "+ result);
		return result;
	};



	@Override
	public List<IncomeDTO> selectInspectionListByBranchId(String branch_id) {
		return stockRepository.selectInspectionListByBranchId(branch_id);
	}


	@Override
	public StockLocationDTO selectStockLocationByLocationCode(String location_code) {
		return stockRepository.selectStockLocationByLocationCode(location_code);
	}


	@Override
	public StockDTO selectStockByItemId(int item_id) {
		return stockRepository.selectStockByItemId(item_id);
	}


	@Override
	public int updateStockLocation(int location_id, int item_id) {
		return stockRepository.updateStockLocation(location_id, item_id);
	}


	@Override
	public List<StockLocationDTO> selectStockLocationByBranchId(String branch_id) {
		return stockRepository.selectStockLocationByBranchId(branch_id);
	}


    @Transactional
	public void updateStock(StockLocationDTO stockLocationDTO, StockDTO stockDTO) {

	};

	
	
	
}
