package com.ssgtarbucks.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssgtarbucks.domain.IncomeDTO;
import com.ssgtarbucks.domain.ItemDTO;
import com.ssgtarbucks.persistence.IncomeRepository;

@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	private IncomeRepository incomeRepository;
	
	@Override
	public List<IncomeDTO> selectIncomeListByBranchId(String branch_id) {
		return incomeRepository.selectIncomeListByBranchId(branch_id);
	}

	@Override
	public List<IncomeDTO> selectIncomeListByIncomeId(String income_id) {
		return incomeRepository.selectIncomeListByIncomeId(income_id);
	}

	@Override
	public ItemDTO selectItemAllByItemCode(String item_code) {
		return incomeRepository.selectItemAllByItemCode(item_code);
	}

	@Override
	public int updateIncomeListResult(int item_id) {
		return incomeRepository.updateIncomeListResult(item_id);
	}

	@Override
	public int selectSameItemCount(String item_code, Date item_exp) {
		return incomeRepository.selectSameItemCount(item_code, item_exp);
	}

	@Override
	public int selectSameProductCount(int item_code) {
		return incomeRepository.selectSameProductCount(item_code);
	}

	@Override
	public int insertStockItem(String branch_id, int item_id) {
		return incomeRepository.insertStockItem(branch_id, item_id);
	}

	@Override
	public int updateStockQuantity(int item_id) {
		return incomeRepository.updateStockQuantity(item_id);
	}

	@Override
	public int updateIncomeStatus(String income_status, String income_id) {
		return incomeRepository.updateIncomeStatus(income_status,income_id);
	}

	@Override
	public int updateItemStatus(int item_id,String item_status) {
		return incomeRepository.updateItemStatus(item_id,item_status);
	}

}
