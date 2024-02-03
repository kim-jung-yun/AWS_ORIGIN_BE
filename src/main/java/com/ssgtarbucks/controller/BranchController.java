package com.ssgtarbucks.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssgtarbucks.domain.ProductDTO;
import com.ssgtarbucks.domain.StockLocationDTO;
import com.ssgtarbucks.domain.TotalDTO;
import com.ssgtarbucks.domain.UserDTO;
import com.ssgtarbucks.jwt.JwtUtil;
import com.ssgtarbucks.service.BranchService;


@Controller
@RequestMapping("/api/v1/branch")
public class BranchController {

	@Autowired
	AuthenticationManagerBuilder authenticationManagerBuilder;

	@Autowired
	JwtUtil jwtUtil;

	@Value("${jwt.name}")
	String tokenKey;

	@Autowired
	private BranchService branchService;

	@GetMapping("/main/exp")
	@ResponseBody
	public ResponseEntity<List<TotalDTO>> branch_main(@RequestParam String branch_id,
			@RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().toString()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String curDate) {
		System.out.println("BranchController - /main(GET) >>>" + branch_id + "/" + curDate);

		List<TotalDTO> totalList = branchService.selectExpirationDateList(branch_id, curDate);
		return ResponseEntity.ok(totalList);
	}

	@GetMapping("/main/remain")
	@ResponseBody
	public ResponseEntity<List<ProductDTO>> branch_main2(@RequestParam String branch_id) {
		System.out.println("BranchController - /main2(GET) >>>" + branch_id);

		List<ProductDTO> lastProductList = branchService.joinProductFortotalProductQuantity(branch_id);

		System.out.println("lastProductList : " + lastProductList + "\n");
		return ResponseEntity.ok(lastProductList);
	}

	@GetMapping("/integrate/search")
	@ResponseBody
	public ResponseEntity<List<TotalDTO>> search(@RequestParam String branch_id, String searchWord) {
		System.out.println("BranchController - /integrate/search(GET) >>>" + branch_id + "/" + searchWord);
		TotalDTO dto =new TotalDTO();
		dto.setBranch_id(branch_id);
		dto.setSearchWord(searchWord);
		List<TotalDTO> totalList = branchService.selectSearchBySearchWord(dto);

		return ResponseEntity.ok(totalList);
	}

	@GetMapping("/info")
	@ResponseBody
	public ResponseEntity<UserDTO> info(@RequestParam String branch_id) {
		System.out.println("BranchController - /info(GET) >>>" + branch_id);

		UserDTO userDTO = branchService.selectUserAndBranchInfo(branch_id);

		return ResponseEntity.ok(userDTO);
	}

	// 보관장소등록 -> QR등록 (forward)
	@PostMapping("/location/new")
	public ModelAndView registerLocation(@RequestParam(required = false) String branch_id,
			@RequestBody(required = false) List<StockLocationDTO> list, HttpSession session) {
		System.out.println("BranchController - /location/new(POST) >>>" + list + " " + branch_id);
		// 세션저장
		session.setAttribute("list", list);
		session.setAttribute("branch_id", branch_id);

		branchService.registerStockLocatioinWithTrans(list);

		// QR등록으로 전달
		return new ModelAndView("forward:/api/v1/qrcode/branch/location/new");
	}

	// 보관장소내역
	@GetMapping("/location/list")
	public ResponseEntity<List<StockLocationDTO>> showLocation(@RequestParam(required = false) String branch_id) {
		System.out.println("BranchController - /location/list(GET) >>>" + branch_id);

		List<StockLocationDTO> locationList = branchService.selectLocaitonToShow(branch_id);

		return ResponseEntity.ok(locationList);
	}

	// 보관장소삭제
	@DeleteMapping("/location/{location_id}")
	public ResponseEntity<Integer> deleteLocation(@RequestHeader(name = "branch_id", required = false) String branch_id,
			@PathVariable("location_id") int location_id){
			System.out.println("BranchController - /location/{location_id}(delete) >>>" + branch_id + ",  "+location_id);
			StockLocationDTO dto = new StockLocationDTO();
			dto.setBranch_id(branch_id);
			dto.setLocation_id(location_id);
		int result=branchService.deleteLocation(dto);

		return ResponseEntity.ok(result);
	}

}