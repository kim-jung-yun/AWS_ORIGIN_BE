package com.ssgtarbucks.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import com.google.zxing.WriterException;
import com.ssgtarbucks.domain.MoveQRItemDTO;
import com.ssgtarbucks.domain.OutcomeQRItemDTO;
import com.ssgtarbucks.domain.ProductDTO;
import com.ssgtarbucks.domain.QRCodeDTO;
import com.ssgtarbucks.domain.SearchDTO;
import com.ssgtarbucks.domain.StockLocationDTO;
import com.ssgtarbucks.domain.StorageDTO;

public interface QRCodeService {
	//search
	StorageDTO joinStroagebyQRCodeIdToSearch(int qrcode_id);
	
	void insertQrcodeToRegisterLocation(List<StockLocationDTO> resList);

	ProductDTO selectProductByBranchId(String branch_id, String item_code);

	List<SearchDTO> selectItemAndLocationToSearchbyQRcode(QRCodeDTO qrcode_dto);

	public int updateMoveItemByQR(MoveQRItemDTO dto);

	int outcomeItemsByQR(OutcomeQRItemDTO dto);

	int discardItemsByQR(OutcomeQRItemDTO dto);
}
