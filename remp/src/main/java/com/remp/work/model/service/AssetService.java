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
	public int setFix(Map<String, String> map); // as요청 정보갱신

	/*as 및 회수요청 부분*/
	public Map<String, String> getVisitInfo(String buyId); // head부분에서 필요한 값 가져와서 DetailFront에 뿌려주는 메소드 (고객정보)
	public int setState(String state, String buyId);	// 외부수리 및 회수에 따른 자산테이블 상태 갱신
	public int setInState(Map<String, String> map);	// as요청 회수처리 시 입고테이블 갱신
	
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
	int setAssetRentalOut(Map<String, String> info);
	List<Map<String, String>> getAssetList(String itemId);
	List<Map<String, String>> getRentalRequestList(String keyword);
	List<Map<String, String>> getRequestAssetList(String assetState);
	List<Map<String, String>> getRequestSearchAssetList(String assetState, String keyword);
	int setDueDiligencePlan(Map<String, String> jsonToMap);
	List<Map<String, String>> getDueDiligencePlanList(String keyword);
	int newDueDiligencePlan(Map<String, String> jsonToMap);
	int setUnstore(Map<String, String> jsonToMap);
	
	/* ======================================== by 이민정 ================================================= */
	/**
	 * 입고요청 자산조회
	 * @param assetState 입고요청 자산상태
	 * @return 입고요청조회 결과 리스트
	 */
	public List<Map<String, String>> getInputRequest(String assetState);
	
	/**
	 * 입고 상태 변경
	 * @param inputId 입고아이디
	 * @param assetState 입고자산상태
	 * @return 성공 1 실패 0
	 */
	public int setInputState(String inputId, String assetState);
	
	/**
	 * 입고요청 자산 검색
	 * @param assetState 자산상태
	 * @param productName 검색어
	 * @return 검색 결과 리스트
	 */
	public List<Map<String,String>> searchInputRequest(String assetState, String productName);
	
	/**
	 * 입고조회
	 * @param assetState 입고자산상태
	 * @return 입고 조회결과 리스트
	 */
	public List<Map<String, String>> getInput(String assetState);
	
	/**
	 * 입고 검색 조회
	 * @param assetState 입고자산 상태
	 * @param productName 검색어
	 * @return 입고 검색 결과 리스트
	 */
	public List<Map<String,String>> searchInput(String assetState, String productName);
	
	/**
	 * 점검대기 조회
	 * @return 점검대기 리스트
	 */
	public ArrayList<Product> getRepairList();
	
	/**
	 * 점검대기 검색 조회
	 * @param keyword 검색어
	 * @param selectName 검색카테고리
	 * @return 점검대기 검색 결과 리스트
	 */
	public List<Map<String,String>> searchRepairList(String keyword, String selectName);
	
	/**
	 * 점검헤드에서 디테일로 가져오기
	 * @param productId 자산아이디
	 * @param productState 자산상태
	 * @return 헤드의 정보 map
	 */
	public Map<String, String> getRepairForm(String productId, String productState);
	
	/**
	 * 내부수리 해당 품목에 부품 조회
	 * @param itemId 품목아이디
	 * @return 부품 리스트
	 */
	public List<Map<String,String>> getRepairPartsList(String itemId);
	
	/**
	 * 내부수리기사 점검 후 점검테이블에 점검내역 등록
	 * @param formInput 폼내역
	 * @param partsInput 부품폼내역
	 * @return 쿼리반환 여부에 따라 int 반환
	 */
	public int addRepairResult(Map<String, Object> formInput, Map<String, String> partsInput);
	
	/**
	 * 내부수리기사 점검 후 자산테이블에 자산상태 변경
	 * @param productId 자산아이디
	 * @param repairSort 점검분류
	 * @return 성공 시 1, 실패 시 0 반환
	 */
	public int updateProductState(String productId, String repairSort);
	
	/**
	 * 고객 렌탈 구매
	 * @param loginId 고객아이디
	 * @param rentalInfo 렌탈 제품정보
	 * @return 성공 시 1, 실패 시 0 반환
	 */
	public int insertCustomerBuy(String loginId, Map<String, String> rentalInfo) ;
	
	/**
	 * 고객 렌탈 구매하여 출고
	 * @param map 렌탈 제품정보
	 * @return 성공 시 1, 실패 시 0 반환
	 */
	public int insertOutput(Map<String, String> map); 
	
	/**
	 * 모든 부품리스트 조회
	 * @return 모든 부품리스트
	 */
	public List<Map<String, String>> getAllParts();
	
	/**
	 * 모든 점검결과 리스트 조회
	 * @param id 내부수리기사 아이디
	 * @return 모든 점검결과 리스트
	 */
	public List<Map<String, String>> getAllRepairResult(String id);
	
	/**
	 * 부품검색
	 * @param type 검색카테고리
	 * @param keyword 검색어
	 * @return 부품검색 결과 리스트
	 */
	public List<Map<String, String>> getRepairResult(String type, String keyword);
	
	/**
	 * 내부수리기사 점검내역 검색 조회
	 * @param engineerId 내부수리기사 아이디
	 * @param startDate 검색기간 시작일
	 * @param endDate 검색기간 마지막일
	 * @param repairSort 점검분류
	 * @return 점검내역 결과 리스트
	 */
	public List<Map<String, String>> getRepairResult(String engineerId,String startDate, String endDate, String repairSort);
	
	/* ======================================== by 이원호 ================================================= */
	/*회수부분*/
	public int setBuyEnd(String buyId, String date); // 렌탈회수에 따른 종료일 갱신
	public Map<String, String> getProductCount(String itId, String itName); // 렌탈회수에 따른 종료일 갱신
}
