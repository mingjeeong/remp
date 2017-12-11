package com.remp.work.model.dao;

import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Advice;
import com.remp.work.model.dto.Deprive;

public interface RentalDao {
	/* ======================================== by 이동훈 ================================================= */
	public int insertNomalAdvice(Advice adviceDto);
	public int insertVisit(Map<String, String> map);	// 회수상담 등록 시 방문테이블 삽입
	
	/* ======================================== by 이원호 ================================================= */
	public List<Map<String, String>> selectVisitSearchList(String state, String select, String Search); // 방문테이블 검색결과 가져오기
	public Map<String, String> selectBuyDay(String buyId); // 구매테이블 및 자산관리자테이블, 품목테이블 가져오기
	public int insertRefundResult(Deprive depriveDto); // 회수관련 
	public int updateRefundResult(Deprive depriveDto);
	public Map<String, String> selectRefundResult(String prId, String cuId, String viDay);
	public String selectAdviceId();
	public int insertAdvice(String adviceId, String employeeId); // 상담등록
	public int insertBuyAdvice(Map<String, String> adviceInfo);
	public int insertOutput(Map<String, String> adviceInfo);
	public int insertAdvice(Map<String, String> map);
	public String selectBuyId();
}
