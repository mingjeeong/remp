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
	public int updateInState(Map<String, String> map);	// as요청 회수처리 시 입고테이블 갱신
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
	public int updateProductInsertIt(Map<String, String> map);	// 자산등록하기(품목)
//	public int updateInput(Map<String, String> map);	// 등록 후 입고테이블 완료여부 갱신
	
	/* ======================================== by 김재림 ================================================= */
	public int updateAssetState(String assetState, String assetId);
	public List<Map<String, String>> selectAssetList(String assetState, String itemId);
	public List<Map<String, String>> selectRequestAssetList(String assetState);
	public List<Map<String, String>> selectRequestSearchAssetList(String assetState, String keyword);
	public int updateUnstore(Map<String, String> info);
	public int updateAssetRentalOut(Map<String, String> info);
	/* ======================================== by 이민정 ================================================= */
	/**
	 * 입고요청 자산조회
	 * @param assetState 입고요청 자산상태
	 * @return 입고요청조회 결과 리스트
	 */
	public List<Map<String, String>> selectInputRequest(String assetState); 
	
	/**
	 * 입고요청 등록
	 * @param inputId 입고아이디
	 * @param assetState 입고자산상태
	 * @return 성공 1 실패 0
	 */
	public int updateInputState(String inputId, String assetState); 
	
	/**
	 * 입고요청 자산 검색
	 * @param assetState 자산상태
	 * @param productName 검색어
	 * @return 검색 결과 리스트
	 */
	public List<Map<String, String>> selectInputRequest(String assetState, String productName); 
	
	/**
	 * 입고조회
	 * @param assetState 입고자산상태
	 * @return 입고 조회결과 리스트
	 */
	public List<Map<String, String>> selectInputList(String assetState); 
	
	/**
	 * 입고 검색 조회
	 * @param assetState 입고자산 상태
	 * @param productName 검색어
	 * @return 입고 검색 결과 리스트
	 */
	public List<Map<String, String>> selectInputSearch(String assetState, String productName);
	
	/**
	 * 점검대기 조회
	 * @return 점검대기 리스트
	 */
	public ArrayList<Product> selectRepairList();
	
	/**
	 * 점검대기 검색 조회
	 * @param keyword 검색어
	 * @param selectName 검색카테고리
	 * @return 점검대기 검색 결과 리스트
	 */
	public List<Map<String, String>> selectRepairList(String keyword, String selectName) ; 
	
	/**
	 * 점검헤드에서 디테일로 가져오기
	 * @param productId 자산아이디
	 * @param productState 자산상태
	 * @return 헤드의 정보 map
	 */
	public Map<String, String> selectRepairForm(String productId, String productState) ;
	
	/**
	 * 내부수리 해당 품목에 부품 조회
	 * @param itemId 품목아이디
	 * @return 부품 리스트
	 */
	public List<Map<String, String>> selectRepairPartsList(String itemId) ; 
	
	/**
	 * 내부수리기사 점검 후 점검테이블에 점검내역 등록
	 * @param formInput 폼내역
	 * @param partsInput 부품폼내역
	 * @return 쿼리반환 여부에 따라 int 반환
	 */
	public int insertRepairResult(Map<String, Object> formInput, Map<String, String> partsInput) ;
	
	/**
	 * 내부수리기사 점검 후 자산테이블에 자산상태 변경
	 * @param productId 자산아이디
	 * @param repairSort 점검분류
	 * @return 성공 시 1, 실패 시 0 반환
	 */
	public int updateProductState(String productId, String repairSort) ;
	
	/*부속품*/
	/**
	 * 모든 부품리스트 조회
	 * @return 모든 부품리스트
	 */
	public List<Map<String, String>> selectAllParts() ;
	
	/**
	 * 부품검색
	 * @param type 검색카테고리
	 * @param keyword 검색어
	 * @return 부품검색 결과 리스트
	 */
	public List<Map<String, String>> selectPartsList(String type, String keyword);
	
	/*내부수리 결과*/
	/**
	 * 모든 점검결과 리스트 조회
	 * @param id 내부수리기사 아이디
	 * @return 모든 점검결과 리스트
	 */
	public List<Map<String,String>> selectAllRepairResult(String id) ;
	
	/**
	 * 내부수리기사 점검내역 검색 조회
	 * @param engineerId 내부수리기사 아이디
	 * @param startDate 검색기간 시작일
	 * @param endDate 검색기간 마지막일
	 * @param repairSort 점검분류
	 * @return 점검내역 결과 리스트
	 */
	public List<Map<String, String>> selectRepairResult(String engineerId, String startDate, String endDate, String repairSort);
	
	/*렌탈구매*/
	/**
	 * 고객 렌탈 구매
	 * @param loginId 고객아이디
	 * @param rentalInfo 렌탈 제품정보
	 * @return 성공 시 1, 실패 시 0 반환
	 */
	public int insertCustomerBuy(String loginId, Map<String,String> rentalInfo) ;
	
	/**
	 * 고객 렌탈 구매하여 출고
	 * @param adviceInfo 렌탈 제품정보
	 * @return 성공 시 1, 실패 시 0 반환
	 */
	public int insertCustomerBuyOutput(Map<String, String> adviceInfo) ;
	
	/**
	 * 자산 등록
	 * @return 성공 시 1, 실패시 0 반환
	 */
	public int insertProduct();
	/**
	 * 자산상태 변경
	 * @param inputId 입고아이디
	 * @param assetState 자산상태
	 * @return 성공 시 1, 실패시 0 반환
	 */
	public int updateProduct(String inputId, String assetState);		
	
	/* ======================================== by 이원호 ================================================= */
	/*회수관련*/
	public int updateBuyEnd(String buyId, String date); // 렌탈회수에 따른 종료일 갱신
	public Map<String, String> selectCount(String itId, String itName);
}
 
