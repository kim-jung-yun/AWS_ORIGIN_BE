package com.ssgtarbucks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssgtarbucks.domain.IncomeDTO;
import com.ssgtarbucks.domain.ProductDTO;
import com.ssgtarbucks.domain.StorageDTO;
import com.ssgtarbucks.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/list")
	public ResponseEntity<List<ProductDTO>> incomeList (@RequestParam String branch_id) { 
		System.out.println("ProductController - /list(GET) >>>  branch_id "+ branch_id);

		List<ProductDTO> productList = productService.selectProductListByBranchId(branch_id);
		System.out.println("ProductController - /product/list/productList(get) >>> productList :" + productList);

        return ResponseEntity.ok(productList);
    }
	

	@GetMapping("/detail/{product_id}")
	public ResponseEntity<List<StorageDTO>> productDetail (@RequestParam String branch_id,@PathVariable int product_id) { 
		System.out.println("ProductController - /detail/{product_id} >>>  branch_id : "+ branch_id+", product_id  :"+product_id);
		//상품상세조회
		StorageDTO dto = new StorageDTO();
		dto.setBranch_id(branch_id);
		dto.setProduct_id(product_id);
		List<StorageDTO> productList = productService.selectProductDetailByBranchId(dto);
		System.out.println("ProductController - /product/list/productList(get) >>> productList :" + productList);
        return ResponseEntity.ok(productList);
    }

}
