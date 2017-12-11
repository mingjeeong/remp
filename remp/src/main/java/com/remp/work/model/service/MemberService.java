package com.remp.work.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Employee;

public interface MemberService {
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * 내부사용자의 정보를 가져오는 메소드
	 * @param id 해당 회원을 식별하기 위한 id
	 * @return dto 해당 회원의 정보가 담긴 dto객체를 반환
	 */
	public Employee getMemberInfoSub(String id);
	
	/**
	 * 내부사용자의 정보를 적용하는 메소드
	 * @param map 해당 회원의 정보를 mqp객체에 담아서 전달
	 * @return int 성공 시 1, 실패 시 0값을 반환
	 */
	public int setMemberInfoSub(Map<String, String> map);
	
	/* ======================================== by 이원호 ================================================= */
	public HashMap<String, String> getLogin(String id, String pw);
	public List<Map<String, String>> getMemberList(String searchId);
	public List<Map<String, String>> getAuthorityList();
	public Map<String, String> getMemberInfo(String memberId);
	public boolean addMemberJoin(Map<String, String> memberJoin);
	public boolean setMemberInfo(Map<String, String> memberInfo);
	public boolean setMemberInfoDetail(Map<String, String> memberInfo);
	public String getRandomEmpl();
}
