package com.ssgtarbucks.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssgtarbucks.domain.MoveQRItemDTO;
import com.ssgtarbucks.domain.OutcomeQRItemDTO;
import com.ssgtarbucks.domain.ProductDTO;
import com.ssgtarbucks.domain.QRCodeDTO;
import com.ssgtarbucks.domain.SearchDTO;
import com.ssgtarbucks.domain.StockLocationDTO;
import com.ssgtarbucks.domain.StorageDTO;

@Mapper
public interface QRCodeRepository {

	StorageDTO joinStroagebyQRCodeIdToSearch(int qrcode_id);
	
	List<SearchDTO> selectItemAndLocationToSearchbyQRcode(QRCodeDTO dto);

	int insertQrcodeToRegisterLocation(QRCodeDTO dto);

	int selectQrcodeIdToCount();

	int selectQrcodeIdToFind(String qrcode_value);

	int updateLocationToAddQrcodeId(StockLocationDTO dto);
	
	ProductDTO selectProductByBranchId(String branch_id, String item_code);
	
	int selectQRToFindItemIDByItemQRValue(MoveQRItemDTO dto);
	
	int selectQRToFindLocationIDByLocQRValue(MoveQRItemDTO dto);
	
	int updateStockToMoveQR(MoveQRItemDTO dto);
	
	int selectItemIdForOutcomeItemByItemQR(OutcomeQRItemDTO dto);
	
	int updateStockCountForOutcomeItemByitemId(OutcomeQRItemDTO dto);
	
	long selectOutcomeIdForFindOutcomeCode();
	
	int insertOutcomeToUseItem(OutcomeQRItemDTO dto);
	
	int selectOutcomeListForFindOutcomeId();
	
	int insertOutcomeListToUseItem(OutcomeQRItemDTO dto);
	
	long selectDiscardIdForFindDiscardCode();
	
	int insertDiscardToUseItem(OutcomeQRItemDTO dto);
	
	int selectDiscardListForFindDiscardId();
	
	int insertDiscardListToUseItem(OutcomeQRItemDTO dto);
	
	
}
