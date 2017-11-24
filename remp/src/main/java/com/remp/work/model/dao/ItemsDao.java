package com.remp.work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.remp.work.model.dto.Item;

@Repository
public class ItemsDao {
	@Autowired
	@Qualifier("factory")
	private FactoryDao factory;
	/**
	 * 고객렌탈 메인 화면 품목리스트 보여주기
	 * @return
	 */
	public ArrayList<Item> getItemList(){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql ="select * from item order by IT_NAME";
		ResultSet rs = null;
		ArrayList<Item> list = new ArrayList<Item>();
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int id = 0;
			String name = null;
			String code = null;
			int price = 0;
			int acquisition = 0;
			String image = null;
			
			while(rs.next()) {
				id = rs.getInt("IT_ID");
				name = rs.getString("IT_NAME");
				code = rs.getString("IT_CODE");
				price = rs.getInt("IT_PRICE");
				acquisition = rs.getInt("IT_ACQUISITION");
				image = rs.getString("IT_IMAGE");
				list.add(new Item(id, name, code, price, acquisition, image));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				factory.close(con, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 제품 검색
	 * @param sb_search
	 * @param item
	 * @return
	 */
	public ArrayList<Item> getSearchList(String sb_search, String item){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Item> list = new ArrayList<Item>();
		String sql= "";
		if(sb_search.equals("제품명")) {
			sql = "select * from item where IT_NAME like ? order by IT_NAME";
		}else if(sb_search.equals("제품코드")) {
			sql = "select * from item where IT_CODE like ? order by IT_NAME";
		}
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+item.trim()+"%");
			rs = pstmt.executeQuery();
			
			int id = 0;
			String name = null;
			String code = null;
			int price = 0;
			int acquisition = 0;
			String image = null;
			
			while(rs.next()) {
				id = rs.getInt("IT_ID");
				name = rs.getString("IT_NAME");
				code = rs.getString("IT_CODE");
				price = rs.getInt("IT_PRICE");
				acquisition = rs.getInt("IT_ACQUISITION");
				image = rs.getString("IT_IMAGE");
				list.add(new Item(id, name, code, price, acquisition, image));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				factory.close(con, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 렌탈 제품 상세보기
	 * @param itemId
	 * @return
	 */
	public Item getItem(int itemId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from item where IT_ID = ?";
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			rs = pstmt.executeQuery();
			
			int id = 0;
			String name = null;
			String code = null;
			int price = 0;
			int acquisition = 0;
			String image = null;
			
			while(rs.next()) {
				id = rs.getInt("IT_ID");
				name = rs.getString("IT_NAME");
				code = rs.getString("IT_CODE");
				price = rs.getInt("IT_PRICE");
				acquisition = rs.getInt("IT_ACQUISITION");
				image = rs.getString("IT_IMAGE");
				return new Item(id, name, code, price, acquisition, image);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				factory.close(con, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
