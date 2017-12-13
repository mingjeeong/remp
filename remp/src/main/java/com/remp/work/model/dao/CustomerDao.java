package com.remp.work.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CustomerDao {
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * 회원의 비밀번호를 찾는 메소드 
	 * @param id 회원의 아이디
	 * @param name 회원의 이름
	 * @param birth 회원의 생년월일
	 * @param mobile 회원의 휴대전화번호
	 * @return String 임시비밀번호를 전송하기 위해 회원의 아이디(이메일)를 반환
	 */
	public String selectPw(String id, String name, String birth, String mobile);
	
	/**
	 * 회원의 비밀번호를 도출된 임시비밀번호로 바꿔주는 메소드
	 * @param id 비밀번호를 변경 할 회원을 식별하는 아이디
	 * @param tmpPW 비밀번호 찾기를 통해 도출된 임시비밀번호
	 * @return int 변경 성공 시 1, 실패 시 0 반환
	 */
	public int updateTmpPw(String id, String tmpPW);
	
	public List<Map<String, String>> selectItemInfo(String customerId);	// 상담테이블 회원의 자산여부 가져오기
	public Map<String, String> selectChangeInfo(String sbValue);	// 상담테이블 자산의 기간 가져오기
	
	/* ======================================== by 이원호 ================================================= */
	public Map<String, String> selectCustomerInfo(String customerId);	// 상담테이블 가입여부 가져오기
	public HashMap<String, String> selectLogin(String id, String pw);
	public int insertJoin(Map<String, String> customerJoin);
	public boolean selectIdCheck(String customerId);
	public List<Map<String, String>> selectCustomerList(String searchId);
	public int updateCustomerInfo(Map<String, String> customerInfo);
	public int updateCustomerInfoDetail(Map<String, String> customerInfo);
	
	public int insertTempCustomer(Map<String, String> tempInfo);
	public String selectTempInfo(Map<String, String> tempInfo);
	
	/* ======================================== by 김재림 ================================================= */
	public String selectCustomerId(HashMap<String, String> memberinfo);
	public Map<String, String> selectUserInfo(String id) ;
	public int UpdateUserAccount(Map<String, String> info) ;
	public int UpdateUserCard(Map<String, String> info) ;
	public int UpdateUserAddress(Map<String, String> info) ;
	public int UpdateUserMobile(Map<String, String> info) ;
	public int UpdateUserPassword(Map<String, String> info) ;

	/* ======================================== by 이민정 ================================================= */
	/**
	 * 고객 비밀번호 변경
	 * @param id 고객아이디
	 * @param pw 현재비밀번호
	 * @param newPw 변경할 새 비밀번호
	 * @return
	 */
	public boolean updatePassword(String id, String pw, String newPw);
}
