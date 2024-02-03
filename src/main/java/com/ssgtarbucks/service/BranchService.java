package com.ssgtarbucks.service;

import java.util.List;


import com.ssgtarbucks.domain.StockLocationDTO;
import com.ssgtarbucks.domain.TotalDTO;
import com.ssgtarbucks.domain.UserDTO;
import com.ssgtarbucks.domain.ProductDTO;

public interface BranchService {
	public List<TotalDTO> selectSearchBySearchWord(TotalDTO totalDTO);
	public List<TotalDTO> selectExpirationDateList(String branch_id, String curDate);
	public UserDTO selectUserAndBranchInfo(String branch_id);
	public void registerStockLocatioinWithTrans(List<StockLocationDTO> locationDTOList);
	public List<ProductDTO> joinProductFortotalProductQuantity(String branch_id);
	public List<StockLocationDTO> selectLocaitonToShow(String string);
	public int deleteLocation(StockLocationDTO dto);
}
