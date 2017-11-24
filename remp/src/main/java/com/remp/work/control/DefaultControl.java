package com.remp.work.control;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.remp.work.model.dto.Item;

@Controller
public class DefaultControl extends ControllerAdapter {
	/**
	 * 비밀번호 변경
	 * @param session
	 * @param presentPw
	 * @param newPw
	 * @return
	 */
	@RequestMapping("changepw.do")
	public ModelAndView setPassword(HttpSession session, String presentPw, String newPw) {
		ModelAndView mv = new ModelAndView();
		String id = (String) session.getAttribute("id");
		boolean result = customerService.setPassword(id, presentPw, newPw);
		if(result) {
			mv.setViewName("home");
		}
		return mv;
	}
	
	/**
	 * 비밀번호 변경 페이지로 가기
	 */
	@RequestMapping("gochangepw.do")
	public ModelAndView goSetPassword() {
		return getPlainPage("changepw.jsp");
	}
	
	/**
	 * 렌탈 메인 페이지로 가기
	 * @return
	 */
	@RequestMapping("gorentalmain.do")
	public ModelAndView goRentalMain() {
		return getPlainPage("rentalmain.jsp");
	}
	/**
	 * 렌탈 메인
	 * @return
	 */
	@RequestMapping("rentalmain.do")
	public ModelAndView rentalMain() {
		ModelAndView mv = getPlainPage("rentalmain.jsp");
		ArrayList<Item> list = assetService.getItemList();
		if(list != null) {
			mv.addObject("list", list);
		}
		return mv;
	}
	
	/**
	 * 렌탈 제품 검색하기
	 * @param sb_search
	 * @param item
	 * @return
	 */
	@RequestMapping("searchitem.do")
	public ModelAndView getSearchList(String sb_search, String item) {
		ModelAndView mv = getPlainPage("rentalmain.jsp");
		ArrayList<Item> list = assetService.getSearchList(sb_search, item);
		if((item.trim().length() == 0) || (item.equals("")) || (list.size() == 0)) {
			StringBuffer message = new  StringBuffer();
			message.append("'").append(item);
			message.append("'에 대한 검색 결과가 없습니다.");
			mv.addObject("message", message);
		}
		else if((list != null) && (!list.isEmpty())) {
			StringBuffer message = new  StringBuffer();
			message.append("'").append(item);
			message.append("'에 대한 검색 결과가 ").append(list.size());
			message.append("건이 있습니다.");
			mv.addObject("list", list);
			mv.addObject("message", message);
			
		}
		return mv;
	}
	
	/**
	 * 렌탈 제품 상세보기로 가기
	 * @return
	 */
	@RequestMapping("gorentaldetail.do")
	public ModelAndView goRentalDetail() {
		return getPlainPage("rentaldetail.jsp");
		
	}
	/**
	 *  렌탈 제품 상세보기
	 * @param id
	 * @return
	 */
	@RequestMapping("rentaldetail.do")
	public ModelAndView rentalDetail(@RequestParam int itemId) {
		ModelAndView mv = getPlainPage("rentaldetail.jsp");
		Item dto = assetService.getItem(itemId);
		if(dto != null) {
			mv.addObject("dto", dto);
		}
		return mv;
	}
	@RequestMapping("rental.do")
	public ModelAndView rental(@RequestParam int itemId) {
		ModelAndView mv = getPlainPage("rental.jsp");;
		Item dto = assetService.getItem(itemId);
		if(dto != null) {
			mv.addObject("dto", dto);
		}
		return mv;
	}

	@Override
	public ModelAndView home() {
		return getHeadDetailPage("head.jsp", "detail.jsp");
	}
}
