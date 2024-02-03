package com.ssgtarbucks.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssgtarbucks.domain.ProductDTO;
import com.ssgtarbucks.domain.StockDTO;
import com.ssgtarbucks.domain.StorageDTO;

@Mapper
public interface ProductRepository {
		
	public List<ProductDTO> selectProductListByBranchId(String branch_id);
	public List<StorageDTO> selectProductDetailByBranchId(StorageDTO dto);
}