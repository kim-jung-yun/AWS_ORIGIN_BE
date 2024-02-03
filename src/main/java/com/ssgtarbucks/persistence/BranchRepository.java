package com.ssgtarbucks.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssgtarbucks.domain.IncomeDTO;
import com.ssgtarbucks.domain.ProductDTO;
import com.ssgtarbucks.domain.StockLocationDTO;
import com.ssgtarbucks.domain.TotalDTO;
import com.ssgtarbucks.domain.UserDTO;

@Mapper
public interface BranchRepository {
	public List<TotalDTO> selectSearchBySearchWord(TotalDTO totalDTO);
	public List<TotalDTO> selectExpirationDateList(String branch_id, String curDate);
	public UserDTO selectUserAndBranchInfo(String branch_id);
	public int selectLocationSectionTofindMaxValue(StockLocationDTO dto);
	public int insertStockLocation(StockLocationDTO dto);
	public int updateLocationCode(StockLocationDTO dto);
	public List<ProductDTO> joinProductFortotalProductQuantity(String branch_id);
	public List<StockLocationDTO> selectLocaitonToShowList(String string);
	public List<StockLocationDTO> selectLocaitonToShow(String string);
	public int deleteLocation(StockLocationDTO dto);
}