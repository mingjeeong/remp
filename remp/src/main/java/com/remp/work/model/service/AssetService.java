package com.remp.work.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Product;

public interface AssetService {
	
	/* ======================================== by 이동훈 ================================================= */
	/*as관련*/
	public int addFix(Map<String, String> map); // as요청 위의 요청 및 as분류, as결과 삽입
	public HashMap<String, String> getFixInfo(); // as요청 결과조회
	public int setFix(Map<String, String> map); // as요청 정보갱신

	/*as 및 회수요청 부분*/
	public Map<String, String> getVisitInfo(String buyId); // head부분에서 필요한 값 가져와서 DetailFront에 뿌려주는 메소드 (고객정보)
	public int setState(String state, String buyId);	// 외부수리 및 회수에 따른 자산테이블 상태 갱신
	
	/*자산관리부분*/
	public List<Map<String, String>> getProduct(String keyword);	// 자산관리 전체 및 검색조회
	public Map<String, String> getProductUpdate(String prId, String itId);		// 자산관리 상세조회
	public String setProductUpdate(Map<String, String>map);	// 자산관리 변경하기(개별)
	public String setProductUpdateAll(Map<String, String>map);	// 자산관리 변경하기(전체)
	
	/*자산등록부분*/
	public List<Map<String, String>> getProductInsertRequest();	// 자산등록 조회
	public Map<String, String> getProductInsert(String prId);	// 자산등록 정보 폼 가져오기
	public String setProductInsert(Map<String, String> map);	// 자산등록하기
	
	/* ======================================== by 김재림 ================================================= */
	int setAssetRentalOut(String productId);
	List<Map<String, String>> getAssetList(String itemId);
	List<Map<String, String>> getRentalRequestList(String keyword);
	List<Map<String, String>> getRequestAssetList(String assetState);
	List<Map<String, String>> getRequestSearchAssetList(String assetState, String keyword);
	int setDueDiligencePlan(Map<String, String> jsonToMap);
	List<Map<String, String>> getDueDiligencePlanList(String keyword);
	int newDueDiligencePlan(Map<String, String> jsonToMap);
	
	/* ======================================== by 이민정 ================================================= */
	/*입고*/
	public List<Map<String, String>> getInputRequest(String assetState);
	public int setInputState(String inputId, String assetState);
	public List<Map<String,String>> searchInputRequest(String assetState, String productName);
	public List<Map<String, String>> getInput(String assetState);
	public List<Map<String,String>> searchInput(String assetState, String productName);
	/*내부수리 점검*/
	public ArrayList<Product> getRepairList();
	public List<Map<String,String>> searchRepairList(String keyword, String selectName);
	public Map<String, String> getRepairForm(String productId, String productState);
	public List<Map<String,String>> getRepairPartsList(String itemId);
	public int addRepairResult(Map<String, Object> formInput, Map<String, String> partsInput);
	public int updateProductState(String productId, String repairSort);
	/*렌탈구매*/
	public int insertCustomerBuy(String loginId, Map<String, String> rentalInfo) ;
	public int insertOutput(Map<String, String> map); 
	/*부속품*/
	public List<Map<String, String>> getAllParts();
	public List<Map<String, String>> getAllRepairResult(String id);
	/*내부수리 결과*/
	public List<Map<String, String>> getRepairResult(String type, String keyword);
	public List<Map<String, String>> getRepairResult(String engineerId,String startDate, String endDate, String repairSort);
	
	/* ======================================== by 이원호 ================================================= */
	/*회수부분*/
	public int setBuyEnd(String buyId, String date); // 렌탈회수에 따른 종료일 갱신
	public Map<String, String> getProductCount(String itId, String itName); // 렌탈회수에 따른 종료일 갱신
}
