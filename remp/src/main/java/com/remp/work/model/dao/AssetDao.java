package com.remp.work.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Product;

public interface AssetDao {
	/* ======================================== by 이동훈 ================================================= */
	/*as관련*/
	public int insertFix(Map<String, String> map); // as요청 위의 요청 및 as분류, as결과 삽입
	public int updateFix(Map<String, String> map); // as요청 정보갱신
	
	/*요청 응답페이지*/
	public Map<String, String> selectItemName(String buyId); // head부분에서 필요한 값 가져와서 DetailFront에 뿌려주는 메소드 (자산정보)
	public Map<String, String> selectVisitInfo(String visitId); // head부분에서 필요한 값 가져와서 DetailFront에 뿌려주는 메소드 (고객정보)
	public int updateState(String state, String buyId);	// 외부수리 및 회수에 따른 자산테이블 상태 갱신
	public Map<String, String> selectOutderId(String viCode, String visitId); // asID or 회수ID 가져오기
	public int updateViComplete(String viId); // 새로운 삽입 시 완료여부 갱신
	
	/*자산관리*/
	public List<Map<String, String>> selectProduct(String keyword);	// 자산관리 전체 및 검색조회
	public Map<String, String> selectProductUpdate(String prId, String itId);		// 자산관리 상세조회 (자산 table)
	public Map<String, String> selectItemUpdate(String itId);		// 자산관리 상세조회 (품목 table)
	public int updateProductUpdate(Map<String, String>map);	// 자산관리 변경하기(개별)
	public int updateProductUpdateAll(Map<String, String>map);	// 자산관리 품목테이블 변경하기(전체)
	
	/*자산등록*/
	public List<Map<String, String>> selectProductInsertRequest();	// 자산등록 조회
	public Map<String, String> selectProductInsertPr(String prId);	// 자산등록 정보 폼 가져오기(자산)
	public Map<String, String> selectProductInsertIt(String itId);	// 자산등록 정보 폼 가져오기(품목)
	public int updateProductInsertPr(Map<String, String> map);	// 자산등록하기(자산)
	public int insertProductInsertIt(Map<String, String> map);	// 자산등록하기(품목)
	public int updateInput(Map<String, String> map);	// 등록 후 입고테이블 완료여부 갱신
	
	/* ======================================== by 김재림 ================================================= */
	public int updateAssetState(String assetState, String assetId);
	public List<Map<String, String>> selectAssetList(String assetState, String itemId);
	public List<Map<String, String>> selectRequestAssetList(String assetState);
	public List<Map<String, String>> selectRequestSearchAssetList(String assetState, String keyword);
	
	/* ======================================== by 이민정 ================================================= */
	/*입고*/
	public List<Map<String, String>> selectInputRequest(String assetState); 
	public int updateInputState(String inputId, String assetState); 
	public List<Map<String, String>> selectInputRequest(String assetState, String productName); 
	public List<Map<String, String>> selectInputList(String assetState); 
	public List<Map<String, String>> selectInputSearch(String assetState, String productName);
	/*내부수리 점검*/
	public ArrayList<Product> selectRepairList();
	public List<Map<String, String>> selectRepairList(String keyword, String selectName) ; 
	public Map<String, String> selectRepairForm(String productId, String productState) ;
	public List<Map<String, String>> selectRepairPartsList(String itemId) ; 
	public int insertRepairResult(Map<String, Object> formInput, Map<String, String> partsInput) ;
	public int updateProductState(String productId, String repairSort) ;
	/*부속품*/
	public List<Map<String, String>> selectAllParts() ;
	public List<Map<String, String>> selectPartsList(String type, String keyword);
	/*내부수리 결과*/
	public List<Map<String,String>> selectAllRepairResult(String id) ;
	public List<Map<String, String>> selectRepairResult(String engineerId, String startDate, String endDate, String repairSort);
	/*렌탈구매*/
	public int insertCustomerBuy(String loginId, Map<String,String> rentalInfo) ;
	public int insertCustomerBuyOutput(Map<String, String> adviceInfo) ;
	
	/* ======================================== by 이원호 ================================================= */
	/*회수관련*/
	public int updateBuyEnd(String buyId, String date); // 렌탈회수에 따른 종료일 갱신
	public Map<String, String> selectCount(String itId, String itName);
}
 
