package com.ssgtarbucks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssgtarbucks.domain.IncomeDTO;
import com.ssgtarbucks.domain.ProductDTO;
import com.ssgtarbucks.domain.StockLocationDTO;
import com.ssgtarbucks.domain.TotalDTO;
import com.ssgtarbucks.domain.UserDTO;
import com.ssgtarbucks.persistence.BranchRepository;
import com.ssgtarbucks.persistence.IncomeRepository;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	private BranchRepository branchRepository;

	@Override
	public List<TotalDTO> selectSearchBySearchWord(TotalDTO totalDTO) {
		return branchRepository.selectSearchBySearchWord(totalDTO);
	}

	@Override
	public List<TotalDTO> selectExpirationDateList(String branch_id, String curDate) {
		return branchRepository.selectExpirationDateList(branch_id, curDate);
	}

	@Override
	public UserDTO selectUserAndBranchInfo(String branch_id) {
		return branchRepository.selectUserAndBranchInfo(branch_id);
	}

	

	
	// 장소등록 및 location_code 트랜잭션 처리
	@Transactional
	@Override
	public void registerStockLocatioinWithTrans(List<StockLocationDTO> list) {
		// 리스트 하나하씩 만드는 쿼리
		// 장소조회할때마다 장소별 MaxCount값을를 조회해야해서 for문으로 1개씩 진행
		for (StockLocationDTO dto : list) {
			try {
			// 코드번호 조회
			int locationSectioMaxCount = branchRepository.selectLocationSectionTofindMaxValue(dto);
			System.out.println("======================================================="+locationSectioMaxCount);
			// location_section 변경 (E->E5)
			String locationSection = dto.getLocation_section() + Integer.toString(locationSectioMaxCount);
			dto.setLocation_section(locationSection);
			// location_code 생성
			String locationCode =dto.getBranch_id()+"-"+dto.getLocation_area()+"-"+dto.getLocation_section();
			dto.setLocation_code(locationCode);

			// 장소등록 후 locationCode 등록
			try {
				branchRepository.insertStockLocation(dto);
				System.out.println("=======================================================성공");
				
				branchRepository.updateLocationCode(dto);

			} catch (Exception e) {
				System.out.println("장소등록 에러 발생");
				e.getMessage();
			}
		}catch(Exception e) {
			System.out.println("for문 에러 발생");
			e.getMessage();
			}
		}

		/* 트랜잭션처리 */
		/*
		 * catch (DuplicateKeyException e) { // DuplicateKeyException은 중복된 키 에러
		 * log.error("장소 등록 중 중복된 키 오류 발생: " + e.getMessage()); // 다른 예외는 롤백 처리를 유지
		 * TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
		 * catch (Exception e) { // 다른 예외 처리: 로그 출력 또는 다른 처리 로직을 추가
		 * log.error("장소 등록 중 오류 발생: " + e.getMessage()); // 트랜잭션 롤백
		 * TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
		 */
	}

	@Override
	public List<ProductDTO> joinProductFortotalProductQuantity(String branch_id) {
		return branchRepository.joinProductFortotalProductQuantity(branch_id);
	}

	@Override
	public List<StockLocationDTO> selectLocaitonToShow(String branch_id) {
		return branchRepository.selectLocaitonToShow(branch_id);
	}

	
	@Override
	public int deleteLocation(StockLocationDTO dto) {
		return branchRepository.deleteLocation(dto);
	}
	
}
