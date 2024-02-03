package com.ssgtarbucks.service;

import java.util.List;

import com.ssgtarbucks.domain.ProductDTO;
import com.ssgtarbucks.domain.StorageDTO;

public interface ProductService {

	public List<ProductDTO> selectProductListByBranchId(String branch_id);
	public List<StorageDTO> selectProductDetailByBranchId(StorageDTO dto);

}
