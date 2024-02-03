package com.ssgtarbucks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssgtarbucks.domain.IncomeDTO;
import com.ssgtarbucks.domain.ItemDTO;
import com.ssgtarbucks.domain.ProductDTO;
import com.ssgtarbucks.domain.UserDTO;
import com.ssgtarbucks.service.IncomeService;

import net.bytebuddy.agent.builder.AgentBuilder.InitializationStrategy.SelfInjection.Split;

@RestController
@RequestMapping("/api/v1/income")
public class IncomeController {
	
	@Autowired
	private IncomeService incomeService;
	
	@GetMapping("/list")
	public ResponseEntity<List<IncomeDTO>> incomeList (@RequestParam String branch_id) { //입고목록
		System.out.println("branch_id>>>>>>>>>>>>" + branch_id);
				
		List<IncomeDTO> incomeList = incomeService.selectIncomeListByBranchId(branch_id);
		System.out.println("IncomeController - /income/list/incomeList(get) >>> incomeList :" + incomeList);

        return ResponseEntity.ok(incomeList);
    }
	
	@GetMapping("/inspection")
	public String inspection(String scanResult) {

		System.out.println(scanResult);
		String result [] = scanResult.split("@");
		
		
		return result[1];
	} 
	
	//@GetMapping("/list/inspection")
	//public ResponseEntity<List<IncomeDTO>> listInspection(@RequestParam String incomeId) {
	@GetMapping("/list/inspection/{incomeId}")
	public ResponseEntity<List<IncomeDTO>> listInspection(@PathVariable String incomeId) {
		System.out.println("IncomeController - /list/inspection(GET) >>>"+incomeId);
		
		List<IncomeDTO> incomeList = incomeService.selectIncomeListByIncomeId(incomeId);
		
		return ResponseEntity.ok(incomeList);
	} 
	

	@GetMapping("/inspection/product")
	public String inspectionProduct(String scanResult, String itemCode) {
		String returnValue = "실패";
		String [] result = scanResult.split("@");
		
		System.out.println("IncomeController - /inspection/product(GET) >>>"+result[1]);
		System.out.println("IncomeController - /inspection/product(GET) >>>"+itemCode);

		ItemDTO scanItemDTO = incomeService.selectItemAllByItemCode(result[1]);
		ItemDTO originItemDTO = incomeService.selectItemAllByItemCode(itemCode);
		
		System.out.println(scanItemDTO+"<-scan///////////origin->"+originItemDTO);

		if (originItemDTO != null) {
			System.out.println(scanItemDTO.getItem_code()+"<-scan///////////origin->"+originItemDTO.getItem_code());
			
			if(scanItemDTO.getItem_code().equals( originItemDTO.getItem_code() )) {

				int updateResult = incomeService.updateIncomeListResult(originItemDTO.getItem_id());
				if(updateResult>=1) returnValue = "성공";
			}
		}
		
		return returnValue;
	}
	
	@GetMapping("/inspection/complete")
	public String inspectionComplete(@RequestParam String incomeId, @RequestParam String branch_id) {
		System.out.println(incomeId+"/"+branch_id);
		List<IncomeDTO> incomeList = incomeService.selectIncomeListByIncomeId(incomeId);
		int count = 0;
		String returnValue = "실패";
		
		for(int i=0; i<incomeList.size(); i++) {
			IncomeDTO incomeDTO = incomeList.get(i);
			if(incomeDTO.getIncome_list_result().equals("승인")) {
				count +=1;
			}
		}
		
		if (incomeList.size() == count ) {
			//모두 승인상태
			returnValue="모두 승인되었습니다.";

		} else if (incomeList.size() > count) {
			returnValue= count+"건 부분 승인 되었습니다.";

		} 
		
		
		for(int i=0; i<incomeList.size(); i++) {
			IncomeDTO incomeDTO = incomeList.get(i);
			//승인된 재고 중
			if(incomeDTO.getIncome_list_result().equals("승인")) {
				String[] item_code = incomeDTO.getItem_code().split("_");
				int result1 = incomeService.selectSameItemCount(item_code[0],incomeDTO.getItem_exp() );
				//int result2 = incomeService.selectSameProductCount(item_code[0]);
				System.out.println("같은 품목 개수::::::::::::::::::::::::::::"+item_code[0]+"/"+result1);
				
				//같은 상품코드를 가진 재고가 있음 (입고완료인데 검수전상품)->같은아이템아이디를 가진 재고는 없다고 가정
				if(result1>0) {
					//int updateStockResult = incomeService.updateIncomeListResult(incomeDTO.getItem_id());
					System.out.println("같은 품목 개수가 있음::::::::::::::::::::"+result1);
					int updateItemStatus = incomeService.updateItemStatus(incomeDTO.getItem_id(), "보관");
					
				//같은 상품번호를 가진 재고가 없음 (입고전+검수전)
				}else {
					int itemCount = incomeService.selectSameProductCount(incomeDTO.getItem_id());
					System.out.println("같은 품목 개수가 없음::::::::::::::::::::"+incomeDTO.getItem_id()+"/"+result1);

					if(itemCount>=1) {
						System.out.println("~~~"+itemCount);

					} else {
						System.out.println("~~~");
						int insertStockResult = incomeService.insertStockItem(branch_id, incomeDTO.getItem_id());
						int updateItemStatus = incomeService.updateItemStatus(incomeDTO.getItem_id(), "입고전");
					}

				}
			}
		}
		
		int result =  incomeService.updateIncomeStatus("검수완료",incomeId);

				
		return returnValue;
		
	}
	
	
	
	
}
