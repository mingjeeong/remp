package com.remp.work.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.remp.work.model.dto.Employee;

public interface MemberDao {
	/* ======================================== by 이동훈 ================================================= */
	/**
	 * 내부사용자의 정보를 가져오는 메소드
	 * @param id 해당 회원을 식별하기 위한 id
	 * @return dto 해당 회원의 정보가 담긴 dto객체를 반환
	 */
	public Employee selectMemberInfoSub(String id);
	
	/**
	 * 내부사용자의 정보를 적용하는 메소드
	 * @param map 해당 회원의 정보를 mqp객체에 담아서 전달
	 * @return int 성공 시 1, 실패 시 0값을 반환
	 */
	public int updateMemberInfoSub(Map<String, String> map);
	
	/* ======================================== by 이원호 ================================================= */
	public String selectRandomEmpl();
	public HashMap<String, String> selectLogin(String id, String pw);
	public List<Map<String, String>> selectMemberList(String searchId);
	public List<Map<String, String>> selectAuthorityList();
	public Map<String, String> selectMemberInfo(String memberId);
	public int insertMemberJoin(Map<String, String> memberJoin);
	public int updateMemberInfo(Map<String, String> memberInfo);
	public int updateMemberInfoDetail(Map<String, String> memberInfo);
}
