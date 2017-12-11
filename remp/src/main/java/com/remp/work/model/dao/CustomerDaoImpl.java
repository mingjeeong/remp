package com.remp.work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao{
	private static CustomerDaoImpl instance;
	private  CustomerDaoImpl() {
		
	}
	public static CustomerDaoImpl getInstance() {
		if(instance == null) {
			instance = new CustomerDaoImpl();
		}
		return instance;
	}
	
	private FactoryDao factory;
	@Autowired
	public void setFactoryDao(FactoryDao factory) {
		this.factory = factory;
	}

	/* ======================================== by 이동훈 ================================================= */
	@Override
	public String selectPw(String id, String name, String birth, String mobile) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select cu_id from customer where cu_id=? and cu_name=? and cu_birth=? and cu_mobile=?";
		String email = null;
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, birth);
			pstmt.setString(4, mobile);
			rs = pstmt.executeQuery();
			if(rs.next()) {
					email = rs.getString("cu_id");
			}
		} catch (SQLException e) {
			System.out.println("findPw SQL Error");
		} finally {
			factory.close(con, pstmt, rs);
		}
		return email;
	}
	
	@Override
	public int updateTmpPw(String id, String tmpPW) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update customer set cu_pw=? where cu_id=?";
		int result = 0;
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tmpPW);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("tmpPw SQL Error");
		} finally {
			factory.close(con, pstmt);;
		}
		return result;
	}
	
	@Override
	public List<Map<String, String>> selectItemInfo(String customerId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from buy where cu_id=? and buy_complete='Y'";
		List<Map<String, String>> list = new ArrayList<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("buyId", rs.getString("buy_id"));
				map.put("itId", rs.getString("it_id"));
				list.add(map);
			}
		} catch (SQLException e) {
			System.out.println("selectItemInfo SQL Error");
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	
	@Override
	public Map<String, String> selectChangeInfo(String sbValue) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from buy where buy_id=? and buy_complete='Y'";
		Map<String, String> map = new HashMap<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sbValue);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				map.put("buyStart", rs.getString("buy_start"));
				map.put("buyEnd", rs.getString("buy_end"));
			}
		} catch (SQLException e) {
			System.out.println("selectChangeInfo SQL Error");
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return map;
	}
	
	/* ======================================== by 김재림 ================================================= */
	public String selectCustomerId(HashMap<String, String> memberinfo) {
		StringBuilder returnValue = new StringBuilder();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="select cu_id from customer where cu_name = ? and cu_mobile = ?";
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberinfo.get("name"));
			pstmt.setString(2, memberinfo.get("phone"));
			rs = pstmt.executeQuery();
			rs.next();
			returnValue.append(rs.getString("cu_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue.toString();
	}
	
	/* ======================================== by 이원호 ================================================= */
	@Override
	public HashMap<String, String> selectLogin(String id, String pw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cu_id, cu_name FROM customer WHERE cu_id=? AND cu_pw=?";
		try {
			HashMap<String, String> hashmap = new HashMap<>();
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				hashmap.put("loginId", rs.getString("cu_id"));
				hashmap.put("loginName", rs.getString("cu_name"));
			}
			return hashmap;			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	
	//아이디 중복 확인
	@Override
	public boolean selectIdCheck(String customerId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cu_id FROM customer WHERE cu_id=?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId.trim());
			rs = pstmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return false;
	}

	//회원가입
	@Override
	public int insertJoin(Map<String, String> customerJoin) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?,?,?,?,to_char(systimestamp,'yyyy-mm-dd'),?,to_char(systimestamp,'yyyy-mm-dd'))";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerJoin.get("tb_id"));
			pstmt.setString(2, customerJoin.get("tb_pw"));
			pstmt.setString(3, customerJoin.get("tb_name"));
			pstmt.setString(4, customerJoin.get("tb_birth"));
			pstmt.setString(5, customerJoin.get("tb_mobile"));
			pstmt.setString(6, customerJoin.get("tb_post"));
			pstmt.setString(7, customerJoin.get("tb_addr"));
			pstmt.setString(8, customerJoin.get("tb_addD"));
			pstmt.setString(9, customerJoin.get("sel_card"));
			pstmt.setString(10, customerJoin.get("tb_card1")+customerJoin.get("tb_card2")+customerJoin.get("tb_card3")+customerJoin.get("tb_card4"));
			pstmt.setString(11, customerJoin.get("sel_account"));
			pstmt.setString(12, customerJoin.get("tb_account"));
			pstmt.setString(13, "u");
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return 0;
	}

	//관리자 회원 검색
	@Override
	public List<Map<String, String>> selectCustomerList(String searchId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cu_id, cu_name FROM customer WHERE cu_id like ?";
		try {
			List<Map<String, String>> list = new ArrayList<>();
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+searchId+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String, String> hashmap = new HashMap<>();
				hashmap.put("memId", rs.getString("cu_id"));
				hashmap.put("memName", rs.getString("cu_name"));
				list.add(hashmap);
			}
			return list;			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	
	//회원 정보
	@Override
	public Map<String, String> selectCustomerInfo(String customerId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM customer WHERE cu_id=?";
		try {
			Map<String, String> hashmap = new HashMap<>();
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				hashmap.put("memId", rs.getString("cu_id"));
				hashmap.put("memPw", rs.getString("cu_pw"));
				hashmap.put("memName", rs.getString("cu_name"));
				hashmap.put("memBirth", rs.getString("cu_birth"));
				hashmap.put("memMobile", rs.getString("cu_mobile"));
				hashmap.put("memPost", rs.getString("cu_post"));
				hashmap.put("memAddr", rs.getString("cu_addr"));
				hashmap.put("memAddD", rs.getString("cu_add_d"));
				hashmap.put("memCardSel", rs.getString("CU_CCOMPANY"));
				hashmap.put("memCard", rs.getString("cu_card"));
				hashmap.put("memAccountSel", rs.getString("CU_CBANK"));
				hashmap.put("memAccount", rs.getString("cu_account"));
				hashmap.put("memEntry", rs.getString("cu_entry_date"));
				hashmap.put("memState", rs.getString("cu_state"));
				hashmap.put("memUpdate", rs.getString("cu_update"));
			}
			return hashmap;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}

	//관리자 회원 정보 전체 변경
	@Override
	public int updateCustomerInfo(Map<String, String> customerInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE customer SET cu_pw=?, cu_name=?, cu_mobile=?, cu_post=?, cu_addr=?, cu_add_d=?, cu_card=?, cu_account=?, cu_state=?, cu_update=to_char(systimestamp,'yyyy-mm-dd'), cu_ccompany=?, cu_cbank=? WHERE cu_id=?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerInfo.get("tb_memPw"));
			pstmt.setString(2, customerInfo.get("tb_memName"));
			pstmt.setString(3, customerInfo.get("tb_memMobile"));
			pstmt.setString(4, customerInfo.get("tb_post"));
			pstmt.setString(5, customerInfo.get("tb_addr"));
			pstmt.setString(6, customerInfo.get("tb_addD"));
			pstmt.setString(7, customerInfo.get("tb_card1")+customerInfo.get("tb_card2")+customerInfo.get("tb_card3")+customerInfo.get("tb_card4"));
			pstmt.setString(8, customerInfo.get("tb_memAccount"));
			pstmt.setString(9, customerInfo.get("tb_memState"));
			pstmt.setString(10, customerInfo.get("sel_card"));
			pstmt.setString(11, customerInfo.get("sel_account"));
			pstmt.setString(12, customerInfo.get("tb_memId"));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return 0;
	}
	
	//관리자 회원 정보 부분 변경
	@Override
	public int updateCustomerInfoDetail(Map<String, String> customerInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = factory.getConnection();
			if (customerInfo.get("column").equals("1")) {
				sql = "UPDATE customer SET cu_pw=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_memPw"));
				pstmt.setString(2, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("2")) {
				sql = "UPDATE customer SET cu_name=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_memName"));
				pstmt.setString(2, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("3")) {
				sql = "UPDATE customer SET cu_mobile=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_memMobile"));
				pstmt.setString(2, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("5")) {
				sql = "UPDATE customer SET cu_post=?, cu_addr=?, cu_add_d=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_post"));
				pstmt.setString(2, customerInfo.get("tb_addr"));
				pstmt.setString(3, customerInfo.get("tb_addD"));
				pstmt.setString(4, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("4")) {
				sql = "UPDATE customer SET cu_card=?, cu_ccompany=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_card1")+customerInfo.get("tb_card2")+customerInfo.get("tb_card3")+customerInfo.get("tb_card4"));
				pstmt.setString(2, customerInfo.get("sel_card"));
				pstmt.setString(3, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("6")) {
				sql = "UPDATE customer SET cu_state=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_memState"));
				pstmt.setString(2, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("7")) {
				sql = "UPDATE customer SET cu_cbank=?, cu_account=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_card1")+customerInfo.get("tb_card2")+customerInfo.get("tb_card3")+customerInfo.get("tb_card4"));
				pstmt.setString(2, customerInfo.get("sel_account"));
				pstmt.setString(3, customerInfo.get("tb_memId"));
			} else {
				System.out.println("없단다");
				return 0;
			}
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return 0;
	}

	//임시 회원가입
	@Override
	public int insertTempCustomer(Map<String, String> tempInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO customer (cu_id, cu_pw, cu_name, cu_birth, cu_mobile, cu_entry_date, cu_state, cu_update) "
				+ "VALUES ('te'||trim(to_char(temcustomer_seq.nextval,'000000')),?,?,?,?,to_char(systimestamp,'yyyy-mm-dd'),'u',to_char(systimestamp,'yyyy-mm-dd'))";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tempInfo.get("memberMobile"));
			pstmt.setString(2, tempInfo.get("memberName"));
			pstmt.setString(3, tempInfo.get("memberBirth"));
			pstmt.setString(4, tempInfo.get("memberMobile"));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return 0;
	}

	//임시 회원 아이디 가져오기
	@Override
	public String selectTempInfo(Map<String, String> tempInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cu_id FROM customer WHERE cu_name=? AND cu_mobile=? AND cu_birth=?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tempInfo.get("memberName"));
			pstmt.setString(2, tempInfo.get("memberMobile"));
			pstmt.setString(3, tempInfo.get("memberBirth"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("cu_id");
			}
			System.out.println("No");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	
	/* ======================================== by 이민정 ================================================= */
	public boolean updatePassword(String id, String pw, String newPw) {
		Connection con = null;
		String sql = "update CUSTOMER set CU_PW=? where CU_PW=? and CU_ID=?";
		PreparedStatement pstmt = null;
		int result;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newPw);
			pstmt.setString(2, pw);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();
			
			if(result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt,null);
		}
		return false;
	}
}
