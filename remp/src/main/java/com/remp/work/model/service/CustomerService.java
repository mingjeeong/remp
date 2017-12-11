package com.remp.work.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CustomerService {
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * 회원의 비밀번호를 찾는 메소드
	 * @param id 회원의 아이디
	 * @param name 회원의 이름
	 * @param birth 회원의 생년월일
	 * @param mobile 회원의 휴대전화번호
	 * @return String 임시비밀번호를 전송하기 위해 회원의 아이디(이메일)를 반환
	 */
	public String getPw(String id, String name, String birth, String mobile);
	
	/**
	 * 회원의 비밀번호를 도출된 임시비밀번호로 바꿔주는 메소드
	 * @param id 비밀번호를 변경 할 회원을 식별하는 아이디
	 * @param tmpPW 비밀번호 찾기를 통해 도출된 임시비밀번호
	 * @return int 변경 성공 시 1, 실패 시 0 반환
	 */
	public int setTmpPw(String id, String tmpPW);
	public List<Map<String,String>> getItemInfo(String customerId);	// 상담테이블 자산가져오기
	public Map<String,String> getChangeInfo(String sbValue);	// 상담테이블 자산의 기간가져오기
	
	/* ======================================== by 이원호 ================================================= */
	 public Map<String, String> getCustomerInfo(String customerId);	// 상담테이블 가입여부 확인하기
	 public HashMap<String, String> getLogin(String id, String pw);
	 public boolean addJoin(Map<String, String> customerJoin);
	 public boolean getIdCheck(String customerId);
	 public List<Map<String, String>> getCustomerList(String searchId);
	 public boolean setCustomerInfo(Map<String, String> customerInfo);
	 public boolean setCustomerInfoDetail(Map<String, String> customerInfo);
	public String addTempCustomer(Map<String, String> tempInfo);
	
	/* ======================================== by 이민정 ================================================= */
	public Boolean setPassword(String id, String pw, String newPw);
	
	/* ======================================== by 김재림 ================================================= */
	public String getCustomerId(HashMap<String, String> memberinfo);
	
}
