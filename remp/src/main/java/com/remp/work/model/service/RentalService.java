package com.remp.work.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Deprive;
import com.remp.work.model.dto.Item;

public interface RentalService {
	/* ======================================== by 이동훈 ================================================= */
	public String setAdvice(Map<String, String> map);	// as상담등록
	
	/* ======================================== by 이원호 ================================================= */
	public List<Map<String, String>> getVisitSearchList(String state, String select, String search);	// 방문테이블 검색가져오기
	public Map<String, String> getBuyList(String buyId, String visitId);	// 구매테이블 및 자산관리자테이블, 품목테이블 가져오기
	public boolean addRefundResult(Deprive depriveDto, String buyId);
	public boolean setRefundResult(Deprive depriveDto, String buyId);
	public String getAdviceId(String employeeId);
	public int addNomalAdvice(Map<String, String> nomalInfo);
	public int addRentalAdvice(Map<String, String> rentalInfo);
	
	/* ======================================== by 이민정 ================================================= */
	public ArrayList<Item> getItemList();
	public ArrayList<Item> getSearchList(String sb_search, String item);
	public Item getItem(String itemId);
}
