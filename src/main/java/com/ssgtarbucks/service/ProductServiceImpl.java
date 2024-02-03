package com.ssgtarbucks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssgtarbucks.domain.ProductDTO;
import com.ssgtarbucks.domain.StorageDTO;
import com.ssgtarbucks.persistence.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<ProductDTO> selectProductListByBranchId(String branch_id) {
		return productRepository.selectProductListByBranchId(branch_id);
	}

	@Override
	public List<StorageDTO> selectProductDetailByBranchId(StorageDTO dto) {
		
		return productRepository.selectProductDetailByBranchId(dto);
	}

}
