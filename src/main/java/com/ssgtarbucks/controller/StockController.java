package com.ssgtarbucks.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssgtarbucks.domain.IncomeDTO;
import com.ssgtarbucks.domain.ItemDTO;
import com.ssgtarbucks.domain.MoveItemDTO;
import com.ssgtarbucks.domain.SaleDTO;
import com.ssgtarbucks.domain.StockDTO;
import com.ssgtarbucks.domain.StockLocationDTO;
import com.ssgtarbucks.domain.UploadDTO;
import com.ssgtarbucks.service.StockService;

//이미지 조회
import org.springframework.core.io.Resource; 

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

	@Autowired
	private StockService stockService;

	@GetMapping("/list")
	public ResponseEntity<List<StockDTO>> stockList(@RequestParam String branch_id) {
		System.out.println("branch_id>>>>>>>>>>>>" + branch_id);

		List<StockDTO> stockList = stockService.selectStorageByBranchId(branch_id);
		System.out.println("StockController - /stock/list/stockList(get) >>> stockList :" + stockList);

		return ResponseEntity.ok(stockList);
	}

	@PutMapping("/quantity")
	public ResponseEntity<List<StockDTO>> changeQuantity(@RequestParam String branch_id,
			@RequestBody StockDTO stockDTO) {
		System.out.println("branch_id>>>>>>>>>>>>" + branch_id);
		System.out.println(stockDTO.getItem_id());
		System.out.println(stockDTO.getStock_quantity());

		int result = stockService.updateStockQuantityByItemId(stockDTO.getStock_quantity(), stockDTO.getItem_id());
		if (result > 0) {
			System.out.println("StockController - /stock/list/quantity(put) >>> 수량정정성공");
		}

		List<StockDTO> stockList = stockService.selectStorageByBranchId(branch_id);
		System.out.println("StockController - /stock/list/quantity(put) >>> stockList :" + stockList);

		return ResponseEntity.ok(null);
	}

	@GetMapping("/sale/list")
	public ResponseEntity<List<SaleDTO>> saleList(@RequestParam String branch_id) {
		System.out.println("branch_id>>>>>>>>>>>>" + branch_id);

		List<SaleDTO> saleList = stockService.selectSaleListByBranchId(branch_id);
		System.out.println("StockController - /stock/sale/list/stockList(get) >>> stockList :" + saleList);

		return ResponseEntity.ok(saleList);
	}

	@PutMapping("/sale/product")
	public ResponseEntity<List<SaleDTO>> changeQuantity(@RequestParam String branch_id) {
		System.out.println("branch_id>>>>>>>>>>>>" + branch_id);

		List<SaleDTO> saleList = stockService.selectSaleListByBranchId(branch_id);

		stockService.updateSaleTransaction(branch_id, saleList);

		System.out.println("StockController - /stock/list/quantity(put) >>> stockList :" + saleList);

		return ResponseEntity.ok(null);
	}

	@GetMapping("/checked/inspection")
	public ResponseEntity<List<IncomeDTO>> inspectionList(@RequestParam String branch_id) {
		System.out.println("branch_id>>>>>>>>>>>>" + branch_id);

		List<IncomeDTO> inspectionList = stockService.selectInspectionListByBranchId(branch_id);

		System.out.println(inspectionList);

		return ResponseEntity.ok(inspectionList);
	}

	// QR코드
	@GetMapping("/checked/insert/location/qr")
	public String insertLocationQR(@RequestParam String scanResult,
			@RequestParam int item_id) {
		System.out.println("branch_id>>>>>>>>>>>>" + scanResult + "|" + item_id);

		StockLocationDTO stockLocationDTO = stockService.selectStockLocationByLocationCode(scanResult);
		StockDTO stockDTO = stockService.selectStockByItemId(item_id);

		int result1 = stockService.updateStockLocation(stockLocationDTO.getLocation_id(), item_id);
		System.out.println(stockDTO);

		String resultValue = stockDTO.getProduct_name()+"("+stockDTO.getItem_id()+")"+"보관장소 등록 완료되었습니다.";
		
		System.out.println(result1);

		
		if (result1 == 0) {
			resultValue = stockDTO.getProduct_name()+"("+stockDTO.getItem_id()+")"+"보관장소 등록 실패했습니다.";
		}
		return resultValue;
	}

	// 수기
	@GetMapping("/checked/insert/location")
	public ResponseEntity<List<IncomeDTO>> insertLocation(@RequestParam int location_id, @RequestParam int item_id) {
		System.out.println("/checked/insert/location>>>>>>>>>>>>" + location_id + item_id);

		int result = stockService.updateStockLocation(location_id, item_id);

		return ResponseEntity.ok(null);
	}

	@GetMapping("/checked/show/location")
	public ResponseEntity<List<StockLocationDTO>> selectStockLocationList(@RequestParam String branch_id) {

		List<StockLocationDTO> stockLocationList = stockService.selectStockLocationByBranchId(branch_id);

		return ResponseEntity.ok(stockLocationList);
	}

	@PutMapping("/location/move")
	public ResponseEntity<List<SaleDTO>> moveItems(@RequestParam String branch_id,
			@RequestBody MoveItemDTO moveItemDTO) {
		System.out.println("StockController - /stock/location/move(put) >>> MoveItemDTO :" + moveItemDTO);

		int result = stockService.updateStockByItemIdToMove(moveItemDTO);

		return ResponseEntity.ok(null);
	}

	@PutMapping("/product/image")
	public ResponseEntity<Resource> displayImg(@RequestBody Map<String, String> requestBody) {
	    System.out.println("StockController - /stock/product/image(put)");
	    String image_path = requestBody.get("image_path");
	    String path = image_path + ".jpg";
	    System.out.println("파일경로 : " + path);

	    Path filePath = Paths.get(path);

	    try {
	        byte[] data = Files.readAllBytes(filePath);
	        ByteArrayResource resource = new ByteArrayResource(data);

	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=image.jpg");
	        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);

	        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
