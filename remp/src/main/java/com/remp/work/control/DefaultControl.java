package com.remp.work.control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.remp.work.model.dto.Deprive;
import com.remp.work.model.dto.Employee;
import com.remp.work.model.dto.Item;
import com.remp.work.model.dto.Product;
import com.remp.work.model.service.AssetService;
import com.remp.work.model.service.CustomerService;
import com.remp.work.model.service.MemberService;
import com.remp.work.model.service.RentalService;
import com.remp.work.model.service.SMTPAuthenticatior;
import com.remp.work.model.service.SmsService;
import com.remp.work.util.RempUtility;

@Controller
public class DefaultControl extends ControllerAdapter {

	private CustomerService customerService;
	private MemberService memberService;
	private AssetService assetService;
	private RentalService rentalService;
	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	@Autowired
	public void setAssetService(AssetService assetService) {
		this.assetService = assetService;
	}
	@Autowired
	public void setRentalService(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@Override
	public ModelAndView home() {
		return getPlainPage("gistMain.jsp");
	}
	
	/* ============================================== by 이동훈 ============================================ */
	/* 비밀번호 찾기로 이동하는 메소드 */
	@RequestMapping("goFindPw.do")
	public ModelAndView goSelectPw() {
		return getPlainRedPage("findPw.jsp");
	}
	
	/*	상담페이지로 이동하는 메소드 */ 
	@RequestMapping("goAdvice.do")
	public ModelAndView goAdvice() {
		return getPlainPage("rentAdvice.jsp");
	}
	
	/*	자산관리 페이지 이동 */ 
	@RequestMapping("goProductCare.do")
	public ModelAndView goProductCare() {
		return getPlainPage("productCare.jsp");
	}
	
	/*	자산등록 페이지 이동 */ 
	@RequestMapping("goProductInsert.do")
	public ModelAndView goProductInsert() {
		return getHeadDetailPage("productRequestList.jsp", "productInsert.jsp");
	}
	
	/* 회원의 비밀번호를 찾는 메소드. 이메일을 검색 후 해당 이메일로 임시비밀번호 발급 */
	@RequestMapping("selectPw.do")
	public ModelAndView getPw(String tb_id, String tb_name, String tb_birth, String tb_mobile, HttpSession session) { // 세션유무?
		ModelAndView mav = new ModelAndView();	
		String tmpPw = "";
		String from = "leedh93@hanmail.net";
		String result = customerService.getPw(tb_id, tb_name, tb_birth, tb_mobile);
		if(result != null) {
			String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
		    for( int i=0; i < 8; i++ ) {
		    	tmpPw += possible.charAt((int) Math.floor(Math.random() * possible.length()));
		    }
			customerService.setTmpPw(tb_id, tmpPw);
			mav.addObject("tmpPw", tmpPw);
			mav.addObject("to", result);
			mav.addObject("from", from);
		}else {
			return getErrorPlainPage(mav, "requestError.jsp");
		}
		return getPlainRedPage(mav, "tmpPw.jsp");
	}
	
	/* 내부사용자의 정보를 가져오는 메소드 */
	@RequestMapping("goSetMemberInfo.do")
	public ModelAndView getMemberInfo(String id, HttpSession session) { // 세션처리에 따라서 id를 매개변수로 받을지 말지 결정
		ModelAndView mav = new ModelAndView();
		id = (String)session.getAttribute("loginId");  //로그인 시 해당 아이디를 세션에 저장
		Employee emp = new Employee();
		emp = memberService.getMemberInfoSub(id);
		if(emp != null) {
			mav.addObject("emp", emp);	
		}else {
			return getErrorPlainPage(mav, "requestError.jsp");
		}
		return getPlainPage(mav, "memberInfo.jsp");
	}
	
	/* 내부사용자의 정보를 수정하는 메소드 */
	@RequestMapping("updateMemberInfo.do")
	public ModelAndView setMemeberInfo(@RequestParam Map<String, String> map, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String id = map.get("tb_id");
		int result = 0;
		result = memberService.setMemberInfoSub(map);
		if(result == 1) {
			return getMemberInfo(id, session);
		}
		mav.addObject("error", "내부사용자 정보수정");
		return getErrorPlainPage(mav, "error.jsp"); // 에러페이지는 좀 더 수정이 필요
	}
	
	// AS/회수 목록 조회
	@RequestMapping(value="getVisitRequest.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getVisitRequest(@RequestBody String jsonObjectString) {
		Map<String, String> map = jsonToMap(jsonObjectString);
		return rentalService.getVisitSearchList(map.get("pagetype"), map.get("select"), map.get("keyword"));         // 방문리스트 전제조회
	}
	
	/* as결과를 등록 및 수정하는 메소드 */
	@RequestMapping(value="setFixInfo.do", method=RequestMethod.POST)
	public @ResponseBody String addFixInfo(@RequestBody String jsonObjectString, HttpSession session) {
		String total = null;
		Map<String, String> map = jsonToMap(jsonObjectString);
		String engineerId = (String)session.getAttribute("loginId");
		String engineerName = (String)session.getAttribute("loginName"); // 수리기사 아이디 및 이름
		String viDay = "";
		if (map.get("tb_viDay") != null && map.get("tb_viDay").trim().length() > 0) {
			viDay = map.get("tb_viDay").trim().substring(0,10) + map.get("tb_viDay").trim().substring(11, 16);
		}
		map.put("hi_outDay", viDay);
		map.put("engineerId", engineerId);
		map.put("engineerName", engineerName);
		int result = 0;
		if(map.get("hi_outId") == null || map.get("hi_outId").trim().equals("")) {
			result = assetService.addFix(map);
			System.out.println("insert다");
		} else {
			result = assetService.setFix(map);
			System.out.println("update다");
		}
		if(result != 0) {
			int update = setPrState(map.get("hi_outState"), map.get("hi_buyId"));
			if(update !=0) {
				System.out.println("자산테이블 갱신성공");
				if(map.get("hi_outState").equals("re_return")) {
					int inUpdate = setInState(map);
					if(inUpdate !=0) {
						System.out.println("입고테이블 갱신성공");
					}else {
						System.out.println("입고테이블 갱신실패");
					}
					total = "success";
				}
			} else {
				System.out.println("자산테이블 갱신오류");
			}
			return total;
		} else {
			return total;
		}
	}
	
	/* as상태 갱신 시 자산테이블 상태 갱신하는 메소드 */
	public int setPrState(String state, String prId) {
		int result = assetService.setState(state, prId);
		return result;
	}
	
	/* as상태 갱신 시 입고테이블 갱신하는 메소드 */
	public int setInState(Map<String, String> map) {
		int result = assetService.setInState(map);
		return result;
	}
	
	/* as페이지 이동 */
	@RequestMapping("goAsRequest.do")
	public ModelAndView goAsRequest() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("state", "As");
		return getHeadDetailPage(mav, "rentalVisitList.jsp", "rentalAsVisit.jsp");
	}

	/* Detail List 가져오기 */
	@RequestMapping(value="getVisitList.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> getVisitList(@RequestBody String jsonObjectString) {
		return rentalService.getBuyList(jsonToMap(jsonObjectString).get("productId"), jsonToMap(jsonObjectString).get("visitId"));
	}

	/* 자산관리 전체 및 검색조회 */
	@RequestMapping(value="getProductRequest.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getProductRequest(@RequestBody String jsonObjectString) {
		return assetService.getProduct(jsonToMap(jsonObjectString).get("keyword"));
	}
	
	/* 자산관리 갱신정보 가져오기 */
	@RequestMapping(value="getProductUpdate.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> getProductUpdate(@RequestBody String jsonObjectString) {
		return assetService.getProductUpdate(jsonToMap(jsonObjectString).get("prId"), jsonToMap(jsonObjectString).get("itId"));
	}
	
	/* 자산관리 정보변경 */
	@RequestMapping(value="setProductUpdate.do", method=RequestMethod.POST)
	public @ResponseBody String setProductUpdate(@RequestBody String jsonObjectString) {
    	Map<String, String> map = jsonToMap(jsonObjectString);
    	String prInDay = "";
		if (map.get("tb_prInDay") != null && map.get("tb_prInDay").trim().length() > 0) {
			prInDay = map.get("tb_prInDay").trim().substring(0,10) + map.get("tb_prInDay").trim().substring(11, 16);
		}
		String prOutDay = "";
		if (map.get("tb_prOutDay") != null && map.get("tb_prOutDay").trim().length() > 0) {
			prOutDay = map.get("tb_prOutDay").trim().substring(0,10) + map.get("tb_prOutDay").trim().substring(11, 16);
		}
		map.put("tb_prInDay", prInDay);
		map.put("tb_prOutDay", prOutDay);
		return assetService.setProductUpdate(map);
	}
	
	/* 자산관리 정보변경(전체) */
	@RequestMapping(value="setProductUpdateAll.do", method=RequestMethod.POST)
	public @ResponseBody String setProductUpdateAll(@RequestBody String jsonObjectString) {
    	Map<String, String> map = jsonToMap(jsonObjectString);
		String prInDay = "";
		if (map.get("tb_prInDay") != null && map.get("tb_prInDay").trim().length() > 0) {
			prInDay = map.get("tb_prInDay").trim().substring(0,10) + map.get("tb_prInDay").trim().substring(11, 16);
		}
		String prOutDay = "";
		if (map.get("tb_prOutDay") != null && map.get("tb_prOutDay").trim().length() > 0) {
			prOutDay = map.get("tb_prOutDay").trim().substring(0,10) + map.get("tb_prOutDay").trim().substring(11, 16);
		}
		map.put("tb_prInDay", prInDay);
		map.put("tb_prOutDay", prOutDay);
		return assetService.setProductUpdateAll(map);
	}

	/* 자산등록 요청조회 */
	@RequestMapping(value="getProductInsertRequest.do", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getProductInsertRequest(@RequestBody String jsonObjectString) {
		return assetService.getProductInsertRequest();
	}
	
	/* 자산등록 정보 가져오기 */
	@RequestMapping(value="getProductInsert.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> getProductInsert(@RequestBody String jsonObjectString) {
		return assetService.getProductInsert(jsonToMap(jsonObjectString).get("productId"));
	}
	
	/* 자산등록하기 */
	@RequestMapping(value="setProductInsert.do", method=RequestMethod.POST)
	public @ResponseBody String setProductInsert(@RequestBody String jsonObjectString) {
    	Map<String, String> map = jsonToMap(jsonObjectString);
    	
    	System.out.println("자산등록 맵 : " + map);
    	
    	String prFirstDay = "";
		if (map.get("tb_prFirstDay") != null && map.get("tb_prFirstDay").trim().length() > 0) {
			prFirstDay = map.get("tb_prFirstDay").trim().substring(0,10) + map.get("tb_prFirstDay").trim().substring(11, 16);
		}
		String prInDay = "";
		if (map.get("tb_prInDay") != null && map.get("tb_prInDay").trim().length() > 0) {
			prInDay = map.get("tb_prInDay").trim().substring(0,10) + map.get("tb_prInDay").trim().substring(11, 16);
		}
		String prOutDay = "";
		if (map.get("tb_prOutDay") != null && map.get("tb_prOutDay").trim().length() > 0) {
			prOutDay = map.get("tb_prOutDay").trim().substring(0,10) + map.get("tb_prOutDay").trim().substring(11, 16);
		}
		map.put("tb_prFirstDay", prFirstDay);
		map.put("tb_prInDay", prInDay);
		map.put("tb_prOutDay", prOutDay);
		return assetService.setProductInsert(map);
	}
	   
    /* 셀렉트박스 변경 시 기간 값 동적할당 */
    @RequestMapping(value="getChangeInfo.do", method=RequestMethod.POST)
    public @ResponseBody Map<String, String> getChangeInfo(@RequestBody String jsonObjectString) {
    	return customerService.getChangeInfo(jsonToMap(jsonObjectString).get("optionValue"));  // Dao에서 리스트 가져오기
    }
    
	   /* 상담 자산정보 가져오기 */
    @RequestMapping(value="getItemInfo.do", method=RequestMethod.POST)
	 public @ResponseBody List<Map<String, String>> getItemInfo(@RequestBody String jsonObjectString) {
    	
    	System.out.println("상담 자산정보 가져오기 memberId : " + jsonToMap(jsonObjectString).get("memberId"));
    	
	     return customerService.getItemInfo(jsonToMap(jsonObjectString).get("memberId"));  // Dao에서 리스트 가져오기
	 }
	   
	 /* as상담 등록하기 */
    @RequestMapping(value="setAsAdvice.do", method=RequestMethod.POST)
	 public @ResponseBody String setAsAdvice(@RequestBody String jsonObjectString, HttpSession session) {
    	Map<String, String> map = jsonToMap(jsonObjectString);
    	String empId = (String) session.getAttribute("loginId");
    	String date = map.get("tb_reDay").trim().substring(0,10) + map.get("tb_reDay").trim().substring(12);
    	map.put("tb_reDay", date);
    	map.put("emId", empId);
        map.put("adSort", "c");
        if(map.get("tb_cuResult").equals("비회원입니다.")) {
        	return null;
        } else {
        	return rentalService.setAdvice(map);
        }
	 }
	   
	   /* 회수상담 등록하기 */
	   @RequestMapping(value="setRefundAdvice.do", method=RequestMethod.POST)
	 public @ResponseBody String setRefundAdvice(@RequestBody String jsonObjectString, HttpSession session) {
	    Map<String, String> map = jsonToMap(jsonObjectString);
	    String empId = (String) session.getAttribute("id");
    	String date = map.get("tb_reDay").trim().substring(0,10) + map.get("tb_reDay").trim().substring(11);
    	map.put("tb_reDay", date);
    	map.put("emId", empId);
    	map.put("adSort", "d");
	   if(map.get("tb_cuResult").equals("비회원입니다.")) {
           	return null;
       } else {
	    return rentalService.setAdvice(map);
       }
	 }
	   
	   /* 이미지 업로드 */
	   @RequestMapping(value="fileUpload.do", method=RequestMethod.POST)
	   public @ResponseBody String fileUpload(MultipartHttpServletRequest request, MultipartFile uploadFile, Object obj){
		  String path = "";
	      String fileName = "";
	      String prId = request.getParameter("fi_prId");
	       OutputStream out = null;
	       PrintWriter printWriter = null;
	        try {
	           uploadFile = request.getFile("tb_file");
	            fileName = uploadFile.getOriginalFilename();
	            byte[] bytes = uploadFile.getBytes();
	            path = getSaveLocation(request, obj);
	            File file = new File(path);
	            if ((fileName != null) && (!fileName.equals(""))) {
	                if (file.exists()) {
	                    fileName = prId + "_" + fileName;
	                    file = new File(path + fileName);
	                }
	            }
	            out = new FileOutputStream(file);
	            out.write(bytes);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (out != null) {
	                    out.close();
	                }
	                if (printWriter != null) {
	                    printWriter.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	      return path+fileName;
	   }
	   
	   private String getSaveLocation(MultipartHttpServletRequest request, Object obj) {
		   String root_path = request.getSession().getServletContext().getRealPath("/");  
	       String attach_path = "resources/images/";
//	       String uploadPath = "I:\\Donghun\\Skill\\java\\workspace_FinalProject\\remp\\src\\main\\webapp\\resources\\images\\";
	       String uploadPath = root_path + attach_path;
	        System.out.println("UtilFile getSaveLocation path : " + uploadPath);
	        return uploadPath;
	    }
	
	   /* ======================================== by 김재림 ================================================= */
		@RequestMapping("gofindid.do")
		public ModelAndView gofindId() {
			return  getPlainRedPage("findid.jsp");
		}

		@RequestMapping(value="findid.do", method=RequestMethod.POST)
		public ModelAndView findId(@RequestParam HashMap<String, String> memberinfo) {
			ModelAndView returnValue = new ModelAndView();
			String customerId = customerService.getCustomerId(memberinfo);
			returnValue.addObject("customerId", customerId);
			return getPlainRedPage(returnValue, "findidresult.jsp");
		}
		
		@RequestMapping("gouserchange.do")
		public ModelAndView goUserChange(HttpSession session) {
			ModelAndView mav = new ModelAndView();
			String id = session.getAttribute("loginId").toString();
			Map<String, String> userinfo = customerService.getUserInfo(id);
			mav.addObject("userInfo", userinfo);
			return getPlainRedPage(mav, "user_change.jsp");
		}
		
		@RequestMapping("gorentalasset.do")
		public ModelAndView goRentalAsset() {
			return getHeadDetailPage("rental_request.jsp", "rental_asset_info.jsp");
		}
		
		@RequestMapping(value="getrentalrequest.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getRentalRequestList(@RequestBody String jsonObjectString) {
			return assetService.getRentalRequestList(jsonToMap(jsonObjectString).get("keyword"));
		}
		
		@RequestMapping(value="getassetlist.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getAssetList(@RequestBody String jsonObjectString) {
			return assetService.getAssetList(jsonToMap(jsonObjectString).get("productId"));
		}
		
		@RequestMapping("gounstoreconfirm.do")
		public ModelAndView goUnstoreConfirm() {
			return getHeadDetailPage("out_request.jsp", "out_asset_info.jsp");
		}

		@RequestMapping(value="getrequestassetlist.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getRequestAssetList(@RequestBody String jsonObjectString) {
			return assetService.getRequestAssetList(jsonToMap(jsonObjectString).get("assetState"));
		}
		
		@RequestMapping(value="rentalassetconfirm.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> rentalAssetConfirm(@RequestBody String jsonObjectString) {
			int result = assetService.setAssetRentalOut(jsonToMap(jsonObjectString));
			Map<String, String> returnValue = isUpdatedToMap(result);
			returnValue.put("id", jsonToMap(jsonObjectString).get("id"));
			return returnValue;
		}
		
		@RequestMapping(value="setunstore.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> setUnstore(@RequestBody String jsonObjectString) {
			String id = jsonToMap(jsonObjectString).get("productId");
			Map<String, String> returnValue = isUpdatedToMap(assetService.setUnstore(jsonToMap(jsonObjectString)));
			returnValue.put("id", id);
			return returnValue;
		}
		
		@RequestMapping(value="getrequestsearchassetlist.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getRequestSearchAssetList(@RequestBody String jsonObjectString) {
			Map<String, String> json = jsonToMap(jsonObjectString);
			return assetService.getRequestSearchAssetList(json.get("assetState"), json.get("keyword"));
		}
		
		@RequestMapping("goduediligence.do")
		public ModelAndView goDueDiligence() {
			return getPlainPage("due_diligence_inputform.jsp");
		}
		
		@RequestMapping("gochangeduediligence.do")
		public ModelAndView goChangeDueDiligence() {
			return getHeadDetailPage("due_diligence_list.jsp", "due_diligence_form.jsp");
		}
		
		@RequestMapping("goduediligenceresult.do")
		public ModelAndView goDueDiligenceResult() {
			return getHeadDetailPage("due_diligence_list.jsp", "detail.jsp");
		}
		
		@RequestMapping("gochangeduediligenceresult.do")
		public ModelAndView goChangeDueDiligenceResult() {
			return getHeadDetailPage("due_diligence_list.jsp", "detail.jsp");
		}
		
		@RequestMapping("newduediligenceplan.do")
		public @ResponseBody Map<String, String> newDueDiligencePlan(@RequestBody String jsonObjectString) {
			int result = assetService.newDueDiligencePlan(jsonToMap(jsonObjectString));
			return isUpdatedToMap(result);
		}
		
		@RequestMapping("setduediligenceplan.do")
		public @ResponseBody Map<String, String> setDueDiligencePlan(@RequestBody String jsonObjectString) {
			int result = assetService.setDueDiligencePlan(jsonToMap(jsonObjectString));
			return isUpdatedToMap(result);
		}
		
		@RequestMapping("getduediligenceplanlist.do")
		public @ResponseBody List<Map<String, String>> getDueDiligencePlanList(@RequestBody String jsonObjectString) {
			return assetService.getDueDiligencePlanList(jsonToMap(jsonObjectString).get("keyword").replaceAll(" ", "|"));
		}
		
		@RequestMapping(value="setNewPassword.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> setNewPassword(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			System.out.println(session.getAttribute("loginId").toString());
			map.put("id", session.getAttribute("loginId").toString());
			String password = map.get("password");
			String newPass = map.get("new_pw");
			if (password == null || password.trim().isEmpty() || newPass == null || newPass.trim().isEmpty()) {
				return isUpdatedToMap(0);
			}
			int result = customerService.setNewPassword(map);
			return isUpdatedToMap(result);
		}
		
		@RequestMapping(value="setNewMobile.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> setNewMobile(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			map.put("id", session.getAttribute("loginId").toString());
			String password = map.get("password");
			String mobile = map.get("new_mobile");
			if (password == null || password.trim().isEmpty() || mobile == null || mobile.trim().isEmpty()) {
				return isUpdatedToMap(0);
			}
			int result = customerService.setNewMobile(map);
			return isUpdatedToMap(result);
		}
		
		@RequestMapping(value="setNewAddress.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> setNewAddress(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			map.put("id", session.getAttribute("loginId").toString());
			String password = map.get("password");
			String post = map.get("new_post");
			String addr = map.get("new_addr");
			String addrd = map.get("new_addr_d");
			if (password == null || password.trim().isEmpty() ||
					post == null || post.trim().isEmpty() ||
					addr == null || addr.trim().isEmpty() ||
					addrd == null || addrd.trim().isEmpty()) {
				return isUpdatedToMap(0);
			}
			int result = customerService.setNewAddress(map);
			return isUpdatedToMap(result);
		}
		
		@RequestMapping(value="setNewCard.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> setNewCard(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			map.put("id", session.getAttribute("loginId").toString());
			String password = map.get("password");
			String company = map.get("new_c_company");
			String card = map.get("new_card1")+map.get("new_card2")+map.get("new_card3")+map.get("new_card4");
			if (password == null || password.trim().isEmpty() || company == null || company.trim().isEmpty() || card.trim().length() != 19 || !card.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")) {
				return isUpdatedToMap(0);
			}
			map.put("new_card", card);
			int result = customerService.setNewCard(map);
			return isUpdatedToMap(result);
		}
		
		@RequestMapping(value="setNewAccount.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> setNewAccount(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			map.put("id", session.getAttribute("loginId").toString());
			String password = map.get("password");
			String company = map.get("new_a_company");
			String account = map.get("new_account");
			if (password == null || password.trim().isEmpty() || company == null || company.trim().isEmpty() || account == null || account.trim().isEmpty()) {
				return isUpdatedToMap(0);
			}
			int result = customerService.setNewAccount(map);
			return isUpdatedToMap(result);
		}
		
		public Map<String, String> jsonToMap(String jsonObjectString) {
			Map<String, String> returnValue = new HashMap<>();
			ObjectMapper om = new ObjectMapper();
			try {
				returnValue = om.readValue(jsonObjectString.getBytes(), HashMap.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return returnValue;
		}
		
		public Map<String, Object> jsonToOMap(String jsonObjectString) {
			Map<String, Object> returnValue = new HashMap<>();
			ObjectMapper om = new ObjectMapper();
			try {
				returnValue = om.readValue(jsonObjectString.getBytes(), HashMap.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return returnValue;
		}
		
		private void authorityCheck(HttpSession session) {
		}
	
	/* ======================================== by 이민정 ================================================= */
		/**
		 * 비밀번호 변경
		 * @param session 세션
		 * @param presentPw 현재 비밀번호
		 * @param newPw 새 비밀번호
		 * @return 성공시 메인화면으로 이동
		 */
		@RequestMapping("changepw.do")
		public ModelAndView setPassword(HttpSession session, String presentPw, String newPw) {
			ModelAndView mv = new ModelAndView();
			String id = (String) session.getAttribute("loginId");
			boolean result = customerService.setPassword(id, presentPw, newPw);
			if(result) {
				return getPlainPage("gistMain.jsp");
			}else {
				//일치 하지 않는 정보 입니다 알림
			}
			return mv;
		}
		

		/**
		 * 비밀번호 변경 페이지로 가기
		 * @return 비밀번호 변경 페이지
		 */
		@RequestMapping("gochangepw.do")
		public ModelAndView goSetPassword() {
			return getPlainRedPage("changepw.jsp");
		}
		
		/**
		 * 렌탈 메인 페이지로 가기
		 * @return 렌탈 메인 페이지
		 */
		@RequestMapping("rentalmain.do")
		public ModelAndView rentalMain() {
			ModelAndView mv = getPlainRedPage("rentalmain.jsp");
			ArrayList<Item> list = rentalService.getItemList();
			if(list != null) {
				mv.addObject("list", list);
			}
			return mv;
		}
		
		/**
		 * 렌탈 제품 검색하기
		 * @param sb_search 셀렉트 박스의 검색 카테고리
		 * @param item 검색어
		 * @return 검색 내역 출력
		 */
		@RequestMapping("searchitem.do")
		public ModelAndView getSearchList(String sb_search, String item) {
			ModelAndView mv = getPlainRedPage("rentalmain.jsp");
			ArrayList<Item> list = rentalService.getSearchList(sb_search, item);
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
		 * 렌탈 상세보기 페이지로 가기
		 * @param itemId 품목 아이디
		 * @return 렌탈 상세보기 페이지
		 */
		@RequestMapping("rentaldetail.do")
		public ModelAndView rentalDetail(@RequestParam String itemId) {
			ModelAndView mv = getPlainRedPage("rentaldetail.jsp");
			Item dto = rentalService.getItem(itemId);
			if(dto != null) {
				mv.addObject("dto", dto);
				RempUtility ru = new RempUtility();
				mv.addObject("price", ru.numMoney(dto.getPrice()));
			}
			return mv;
		}
		
		/**
		 * 렌탈 구매하기
		 * @param itemId 품목아이디
		 * @return 렌탈 구매 페이지
		 */
		@RequestMapping("rental.do")
		public ModelAndView rental(@RequestParam String itemId) {
			ModelAndView mv = getPlainRedPage("rental.jsp");;
			Item dto = rentalService.getItem(itemId);
			if(dto != null) {
				mv.addObject("dto", dto);
				RempUtility ru = new RempUtility();
				mv.addObject("price", ru.numMoney(dto.getPrice()));
			}
			return mv;
		}
		
		/**
		 * 입고 요청자산 조회로 이동
		 * @return 입고요청 자산 조회 페이지
		 */
		@RequestMapping("goinputrequest.do")
		public ModelAndView goInputRequest() {
			return getHeadDetailPage("searchinputrequesthead.jsp", "searchinputrequestdetail.jsp");
		}
		
		/**
		 * 입고 요청자산 조회
		 * @param jsonObjectString 입고 요청자산 상태 (inputState)
		 * @return 입고 요청자산 상태에 맞는 입고 요청 자산 리스트
		 */
		@RequestMapping(value="getinputrequest.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getInputRequeat(@RequestBody String jsonObjectString) {
			return assetService.getInputRequest(jsonToMap(jsonObjectString).get("inputState"));
		}
		
		/**
		 * 입고요청자산 입고 등록
		 * @param jsonObjectString 입고아이디(id), 입고자산상태(state)
		 * @return 입고등록 성공,실패시 메시지 map
		 */
		@RequestMapping(value="setinputstate.do", method=RequestMethod.POST)
		public  @ResponseBody Map<String, String> setInputState(@RequestBody String jsonObjectString) {
			List<Map<String, String>> list = new ArrayList<>();
			Map<String, String> map = new HashMap<>();
			int result = assetService.setInputState(jsonToMap(jsonObjectString).get("id"),jsonToMap(jsonObjectString).get("state"));
			if(result == 1) {
				map.put("result", "성공적으로 입고 처리되었습니다.");
			}else {
				map.put("result", "입고처리를 실패하였습니다.");
			}
			return map;
		}
		
		/**
		 * 요청자산 검색 조회
		 * @param jsonObjectString 요청자산 상태(state), 검색어(name)
		 * @return 요청자산 상태,검색어에 맞는 검색결과 리스트
		 */
		@RequestMapping(value="searchinputrequest.do", method=RequestMethod.POST) 
		public @ResponseBody List<Map<String, String>> searchInputRequest(@RequestBody String jsonObjectString) {
			return assetService.searchInputRequest(jsonToMap(jsonObjectString).get("state"),jsonToMap(jsonObjectString).get("name").trim());
		}
		
		/**
		 * 입고 조회로 이동
		 * @return 입고 조회 페이지
		 */
		@RequestMapping("goinput.do")
		public ModelAndView goInput() {
			return getHeadDetailPage("searchinputhead.jsp", "searchinputdetail.jsp");
		}
		
		/**
		 * 입고조회
		 * @param jsonObjectString 입고자산상태(state)
		 * @return 입고 자산상태를 조회한 입고 리스트
		 */
		@RequestMapping("getinput.do") 
		public @ResponseBody List<Map<String, String>> getInput(@RequestBody String jsonObjectString) {
			System.out.println(jsonToMap(jsonObjectString).get("state"));
			List<Map<String, String>> result = assetService.getInput(jsonToMap(jsonObjectString).get("state"));
			System.out.println(result);
			return result;
		}
		
		/**
		 * 입고조회 검색
		 * @param jsonObjectString 입고상태(state),검색어(name)
		 * @return 입고자산 검색 후 결과 리스트
		 */
		@RequestMapping(value="searchinput.do", method=RequestMethod.POST) 
		public @ResponseBody List<Map<String, String>> searchInput(@RequestBody String jsonObjectString) {
			return assetService.searchInput(jsonToMap(jsonObjectString).get("state"),jsonToMap(jsonObjectString).get("name").trim());
		}

		/**
		 * 내부수리기사 점검등록 페이지로 이동
		 * @param session 세션
		 * @return 점검대기인 자산과 세션에 저장되어있는 아이디와 이름을 출력한 페이지
		 */
		@RequestMapping("gorepairlist.do")
		public ModelAndView goRepairList(HttpSession session) {
			ModelAndView mv = getHeadDetailPage("addrepairresulthead.jsp", "addrepairresultdetail.jsp");
			ArrayList<Product> list = assetService.getRepairList();
			
			if(list != null) {
				mv.addObject("list", list);
			}
			String id = (String) session.getAttribute("loginId");
			String name = (String) session.getAttribute("loginName");
			mv.addObject("id", id);
			mv.addObject("name", name);
			return mv;
		}
		
		/**
		 * 내부수리기사 점검대기 데이터 검색조회
		 * @param jsonObjectString 검색어(keyword), 검색카테고리(select)
		 * @return 점검대기인 상태인 자산을 검색어와 검색카테고리로 검색한 결과 리스트
		 */
		@RequestMapping(value="getrepairlist.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getRepairList(@RequestBody String jsonObjectString) {
			return assetService.searchRepairList(jsonToMap(jsonObjectString).get("keyword"), jsonToMap(jsonObjectString).get("select"));
		}
		
		/**
		 * 헤드에서 항목 클릭하면 디테일영역에 데이터 가져오기
		 * @param jsonObjectString 자산아이디(id),자산상태(state)
		 * @return 헤드에서 선택한 정보 map
		 */
		@RequestMapping(value="getrepairform.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> getRepairForm(@RequestBody String jsonObjectString ) {
			return assetService.getRepairForm(jsonToMap(jsonObjectString).get("id"), jsonToMap(jsonObjectString).get("state"));
		}
		
		/**
		 * 내부수리기사 점검내역 등록할 때 내부수리시 수리하는 품목에 맞는 부품리스트 보여주기
		 * @param jsonObjectString 품목아이디(id)
		 * @return 품목아이디에 사용되는 부품리스트
		 */
		@RequestMapping(value="getpartslist.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getPartsList(@RequestBody String jsonObjectString ) {
			return assetService.getRepairPartsList(jsonToMap(jsonObjectString).get("id"));
		}
		
		/**
	       * 내부수리기사 점검내역 등록
	       * @param jsonObjectString
	       * @return 점검내역 등록결과 map
	       */
	      @RequestMapping(value="addrepairresult.do", method=RequestMethod.POST)
	      public  @ResponseBody Map<String, String> addRepairResult(@RequestBody String jsonObjectString) {
	         Map<String, Object> map = jsonToOMap(jsonObjectString);
	         Map<String, String> parts = (Map<String, String>) map.get("list");
	         int result = 0;
	         if(map.get("repairSort").toString().length() != 0 && map.get("repairContent").toString().trim().length() != 0) {
	            result = assetService.addRepairResult(map, parts);
	         }
	         return areUpdatedToMap(result);
	      }
		
		/**
		 * 부품조회 페이지로 이동
		 * @return 부품조회 페이지
		 */
		@RequestMapping("gosearchparts.do")
		public ModelAndView goSearchPart() {
			return getPlainPage("searchparts.jsp");
		}
		
		/**
		 * 모든 부품 리스트 보여주기(부품조회 초기페이지의 데이터)
		 * @return 모든 부품 조회한 리스트
		 */
		@RequestMapping(value="getallpartslist.do", method=RequestMethod.POST) 
		public @ResponseBody List<Map<String, String>> getAllParts() {
			return assetService.getAllParts();
		}
		
		/**
		 * 내부수리기사 점검결과보기 페이지로 이동
		 * @return 내부수리결과 페이지
		 */
		@RequestMapping("gorepairresult.do")
		public ModelAndView goRepairResult() {
			return getPlainPage("searchrepairresult.jsp");
		}
		
		/**
		 * 내부수리기사 모든 점검결과  보여주기 
		 * @param session 세션
		 * @param jsonObjectString 
		 * @return 모든 점검결과 list
		 */
		@RequestMapping(value="getallrepairresultlist.do", method=RequestMethod.POST) 
		public @ResponseBody List<Map<String, String>> getAllRepairResult(HttpSession session, @RequestBody String jsonObjectString) {
			String id = (String) session.getAttribute("loginId");
			return assetService.getAllRepairResult(id);
		}
		
		/**
		 * 렌탈 구매, 구매내역확인으로 이동
		 * @param session 세션
		 * @param map 
		 * @return 렌탈 구매 후 구매내역 확인 페이지
		 */
		@RequestMapping(value="rentalpayment.do", method=RequestMethod.POST) 
		public ModelAndView rentalPayment(HttpSession session, @RequestParam Map<String, String> map) {
			ModelAndView mv = getPlainRedPage("complitedpayment.jsp");
			String customerId = (String) session.getAttribute("loginId");
			int buyResult = assetService.insertCustomerBuy(customerId, map);
			if(buyResult != 0) {
				map.put("tb_itNumber", "1");
				int outputResult = assetService.insertOutput(map); 
				mv.addObject("map", map);
				RempUtility ru = new RempUtility();
				mv.addObject("price", ru.numMoney(Integer.parseInt(map.get("price"))));
			}
			return mv;
		}
		
		/**
		 * 부속품 검색조회
		 * @param jsonObjectString 검색 카테고리(searchType), 검색어(searchKeyword)
		 * @return 부속품 검색한 리스트
		 */
		@RequestMapping(value="getsearchpartslist.do", method=RequestMethod.POST) 
		public @ResponseBody List<Map<String, String>> getSearchPartsList(@RequestBody String jsonObjectString) {
			return assetService.getRepairResult(jsonToMap(jsonObjectString).get("searchType"),jsonToMap(jsonObjectString).get("searchKeyword"));
		}
		
		/**
		 * 수리기사 점검내역 결과 검색조회
		 * @param session 세션
		 * @param jsonObjectString 검색기간 시작일(startDate), 검색기간 마지막일(endDate), 점검분류(repairSort)
		 * @return 내부수리결과 검색한 리스트
		 */
		@RequestMapping(value="getrepairresultlist.do", method=RequestMethod.POST) 
		public @ResponseBody List<Map<String, String>> getRepairResult(HttpSession session,@RequestBody String jsonObjectString) {
			String id = (String) session.getAttribute("loginId");
			List<Map<String, String>> result = assetService.getRepairResult(id, jsonToMap(jsonObjectString).get("startDate"), jsonToMap(jsonObjectString).get("endDate"), jsonToMap(jsonObjectString).get("repairSort"));
			return result;
		}
		
	/* ======================================== by 이원호 ================================================= */
		// 직원 메인화면
		@Override
		public ModelAndView goMain() {
			return getPlainPage("gistMain.jsp");
		}
		/*직원 ERROR 페이지 */
		@Override
		public ModelAndView goFoundError() {
			return getErrorPage("foundError.jsp");
		}
		@Override
		public ModelAndView goRequestError() {
			return getErrorPage("requestError.jsp");
		}
		@Override
		public ModelAndView goEtcError() {
			return getErrorPage("etcError.jsp");
		}
		/*사용자 ERROR 페이지 */
		@Override
		public ModelAndView goUserFoundError() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("employeeAuth", "aa");
			return getErrorRedPage(mv, "foundError.jsp");
		}
		@Override
		public ModelAndView goUserRequestError() {
			return getErrorRedPage("requestError.jsp");
		}
		@Override
		public ModelAndView goUserEtcError() {
			return getErrorRedPage("etcError.jsp");
		}
		
		/* 로그인 */
		@Override
		public ModelAndView goLogin() {
			donghunCode();
			return getPlainPage("login.jsp");
		}
		
		/* 회원가입  */
		@Override
		public ModelAndView goJoin() {
			return getPlainPage("join.jsp");
		}

		/*관리자 직원 등록 */
		@Override
		public ModelAndView goCompanionJoin() {
			List<Map<String, String>> auList = memberService.getAuthorityList();
			ModelAndView mv = new ModelAndView();
			mv.addObject("target", "Employee");
			if ((auList != null) && (!auList.isEmpty())) {
				mv.addObject("employeeAuth", auList);
			}
			return getHeadDetailPage(mv, "memberList.jsp", "companionJoin.jsp");
		}
		
		/*관리자 직원 수정 */
		@Override
		public ModelAndView goCompanionInfo() {
			List<Map<String, String>> auList = memberService.getAuthorityList();
			ModelAndView mv = new ModelAndView();
			mv.addObject("target", "Employee");
			if ((auList != null) && (!auList.isEmpty())) {
				mv.addObject("employeeAuth", auList);
			}
			return getHeadDetailPage(mv, "memberList.jsp", "companionInfo.jsp");
		}

		/*관리자 고객 수정 */
		@Override
		public ModelAndView goCusInfo() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("target", "Customer");
			return getHeadDetailPage(mv, "memberList.jsp", "customerInfo.jsp");
		}
		
		/* 회수페이지 이동 */
		@Override
		public ModelAndView goRefundRequest() {
			ModelAndView mav = new ModelAndView();
			mav.addObject("state", "Refund");
			return getHeadDetailPage(mav, "rentalVisitList.jsp", "rentalVisit.jsp");
		}
		
		/* 회원가입  */
		@RequestMapping("join.do")
		public ModelAndView join(@RequestParam Map<String, String> customerJoin) {
			if (customerService.addJoin(customerJoin)) {
				return getPlainPage("login.jsp");
			}
			return getPlainPage("join.jsp");
		}
		
		/* 고객 로그인 */
		@RequestMapping("customerLogin.do")
		public ModelAndView customerLogin(String tb_inputId, String tb_inputPw, HttpSession session) {
			HashMap<String, String> hashmap = customerService.getLogin(tb_inputId, tb_inputPw);
			if ((hashmap!=null) && (!hashmap.isEmpty())) {
				session.setAttribute("loginId", hashmap.get("loginId"));
				session.setAttribute("loginName", hashmap.get("loginName"));
				session.setAttribute("authority", "customer");
				return getPlainRedPage("rentalmain.jsp");
			}
			return getPlainPage("login.jsp");
		}
		
		/* 직원 로그인 */
		@RequestMapping("employeeLogin.do")
		public ModelAndView employeeLogin(String tb_inputId, String tb_inputPw, HttpSession session) {
			HashMap<String, String> hashmap = memberService.getLogin(tb_inputId, tb_inputPw);
			if ((hashmap!=null) && (!hashmap.isEmpty())) {
				session.setAttribute("loginId", hashmap.get("loginId"));
				session.setAttribute("loginName", hashmap.get("loginName"));
				session.setAttribute("authority", hashmap.get("authority"));
				return getPlainPage("gistMain.jsp");
			}
			return getPlainPage("login.jsp");
		}
		
		/* 아이디 중복확인 */
		@RequestMapping(value="getIdCheck.do", method=RequestMethod.POST)
		public @ResponseBody boolean idCheck(@RequestBody String jsonObjectString) {
			return customerService.getIdCheck(jsonToMap(jsonObjectString).get("customerId"));
		}
		
		/* 회수 요청 결과 등록 및 수정 */
		@RequestMapping(value="setRentalRefundResult.do", method=RequestMethod.POST)
		public @ResponseBody Boolean refundResult(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			Deprive depriveDto = new Deprive();
			String date = "";
			if (map.get("tb_viDay").trim().length() > 0) {
				date = map.get("tb_viDay").trim().substring(0,10) + map.get("tb_viDay").trim().substring(11);
			}
			depriveDto.setId(map.get("tb_depId"));
			depriveDto.setViId(map.get("hi_viId"));
			depriveDto.setDay(date);
			depriveDto.setCuId(map.get("hi_cuId"));
			depriveDto.setCuName(map.get("tb_cuName"));
			depriveDto.setPrId(map.get("tb_prId"));
			depriveDto.setState("re_return");
			depriveDto.setEngineerId((String)session.getAttribute("loginId"));
			depriveDto.setEngineerName((String)session.getAttribute("loginName"));
			depriveDto.setContent(map.get("te_content"));
			
			if (map.get("tb_depId").trim().length() > 0) {
				return rentalService.setRefundResult(depriveDto, map.get("hi_buyId"));
			} else {
				return rentalService.addRefundResult(depriveDto, map.get("hi_buyId"));
			}
		}
		
		/*관리자 고객 리스트 */
		@RequestMapping(value="getCustomerList.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getCustomereList(@RequestBody String jsonObjectString) {
			return customerService.getCustomerList(jsonToMap(jsonObjectString).get("keyword"));
		}
		
		/*관리자 직원 등록 */
		@RequestMapping(value="addCompanionJoin.do", method=RequestMethod.POST)
		public @ResponseBody Boolean addCompanionJoin(@RequestBody String jsonObjectString) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			SmsService sms = new SmsService();
			sms.sendSms(map.get("tb_memMobile"), map.get("tb_memId"), map.get("tb_memPw"));
			return memberService.addMemberJoin(map);
		}
		
		/*관리자 직원 리스트 */
		@RequestMapping(value="getEmployeeList.do", method=RequestMethod.POST)
		public @ResponseBody List<Map<String, String>> getEmployeeList(@RequestBody String jsonObjectString) {
			return memberService.getMemberList(jsonToMap(jsonObjectString).get("keyword"));
		}
		
		/*관리자 고객 정보 */
		@RequestMapping(value="getCustomerInfo.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> getCustomereInfo(@RequestBody String jsonObjectString) {
			return customerService.getCustomerInfo(jsonToMap(jsonObjectString).get("memberId"));
		}
		
		/*관리자 직원 정보 */
		@RequestMapping(value="getEmployeeInfo.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> getEmployeeInfo(@RequestBody String jsonObjectString) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			return memberService.getMemberInfo(map.get("memberId"));
		}
		
		/*관리자 고객, 직원 정보 일괄변경 */
		@RequestMapping(value="setMemberChange.do", method=RequestMethod.POST)
		public @ResponseBody boolean setMemberChange(@RequestBody String jsonObjectString) {
			Map<String, String> memberInfo = jsonToMap(jsonObjectString);
			if (memberInfo.get("target").trim().equals("Customer")) {
				return customerService.setCustomerInfo(memberInfo);
			} else if(memberInfo.get("target").trim().equals("Employee")) {
				if(memberService.setMemberInfo(memberInfo)) {
					SmsService sms = new SmsService();
					return sms.sendSms(memberInfo.get("tb_memMobile"), memberInfo.get("tb_memId"), memberInfo.get("tb_memPw"));
				}
			}
			return false;
		}
		
		/*관리자 고객, 직원 정보 각각 변경 */
		@RequestMapping(value="setMemberItemChange.do", method=RequestMethod.POST)
		public @ResponseBody boolean setMemberItemChange(@RequestBody String jsonObjectString) {
			Map<String, String> memberInfo = jsonToMap(jsonObjectString);
			if (memberInfo.get("hi_target").trim().equals("Customer")) {
				if(memberInfo.get("column").equals("1")) {
					if(donghun(memberInfo.get("tb_memId"), memberInfo.get("tb_memPw"))) {
						System.out.println("전송성공");
					} else {
						System.out.println("실패");
					}
				}
				return customerService.setCustomerInfoDetail(memberInfo);
			} else if(memberInfo.get("hi_target").trim().equals("Employee")) {
				return memberService.setMemberInfoDetail(memberInfo);
			}
			return false;
		}
		
		/*관리자 직원 아이디 자동 부여 */
		@RequestMapping(value="getNewId.do", method=RequestMethod.POST)
		public @ResponseBody String getNewId(@RequestBody String jsonObjectString) {
			return memberService.getRandomEmpl();
		}
		
		/*상담사 물품 수량 조회 */
		@RequestMapping(value="getProductCount.do", method=RequestMethod.POST)
		public @ResponseBody Map<String, String> getProductCount(@RequestBody String jsonObjectString) {
			Map<String, String> item = jsonToMap(jsonObjectString);
			return assetService.getProductCount(item.get("itId"), item.get("itName"));
		}
		
		/*상담사 물품 수량 조회 */
		@RequestMapping(value="addTempCustomer.do", method=RequestMethod.POST)
		public @ResponseBody String addTempCustomer(@RequestBody String jsonObjectString) {
			return customerService.addTempCustomer(jsonToMap(jsonObjectString));
		}
		
		/*상담사 상담 아이디 생성 */
		@RequestMapping(value="addAdviceId.do", method=RequestMethod.POST)
		public @ResponseBody String addAdviceId(HttpSession session) {
			return rentalService.getAdviceId((String)session.getAttribute("loginId"));
		}
		
		/*상담사 일반 상담 */
		@RequestMapping(value="addNomalAdvice.do", method=RequestMethod.POST)
		public @ResponseBody String addNomalAdvice(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			System.out.println(map + " : 상담");
			map.put("adSort", "a");
			map.put("sb_item", "");
			map.put("emId", (String)session.getAttribute("loginId"));
			int result = rentalService.addNomalAdvice(map);
			if (result == 1) {
				return "1";
			}
			return "0";
		}
		
		/*상담사 일반 상담 */
		@RequestMapping(value="addRentalAdvice.do", method=RequestMethod.POST)
		public @ResponseBody String addRentalAdvice(@RequestBody String jsonObjectString, HttpSession session) {
			Map<String, String> map = jsonToMap(jsonObjectString);
			System.out.println("렌탈 상담 : " + map);
			map.put("adSort", "b");
			map.put("emId", (String)session.getAttribute("loginId"));
			int result = rentalService.addRentalAdvice(map);
			if (result == 1) {
				return "1";
			}
			return "0";
		}
		
		/* 관리자 고객 비밀번호 변경 시 메일 전송 */
		public boolean donghun(String customerId, String newPw) {
			String to = customerId;
			String from = "leedh93@hanmail.net";
			
			Properties p = new Properties();
			p.put("mail.smtp.host","smtp.daum.net");
			p.put("mail.smtp.port", "465");
			p.put("mail.smtp.starttls.enable", "true");
			p.put("mail.smtp.auth", "true");
			p.put("mail.smtp.debug", "true");
			p.put("mail.smtp.socketFactory.port", "465");
			p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			p.put("mail.smtp.socketFactory.fallback", "false");
			
			try{
		    	Authenticator auth = new SMTPAuthenticatior();
		        Session ses = Session.getInstance(p, auth);
		        MimeMessage msg = new MimeMessage(ses);
		     
		        msg.setSubject("[GIST] 개인정보 이용 안내");
		        StringBuilder buffer = new StringBuilder();
		        buffer.append("고객님의 요청으로 인하여 개인 정보가 변경되었습니다.<br><br>");
		        buffer.append("변경된 회원님의 임시비밀번호는   <font color=\"red\"  style=\"font-size: 20px; font-weight: bold;\">"+ newPw +"</font>  입니다.<br><br>");
		        buffer.append("변경한 내역이 존재하지 않으실 경우 고객센터에 문의하여 주세요.");
		        Address fromAddr = new InternetAddress(from);
		        msg.setFrom(fromAddr); 
		     
		        Address toAddr = new InternetAddress(to);
		        msg.addRecipient(Message.RecipientType.TO, toAddr);
		        msg.setContent(buffer.toString(), "text/html;charset=UTF-8");
		        Transport.send(msg);
		    } catch(Exception e){
		        e.printStackTrace();
		        return false;
		    }
			return true;
		}
		
		//ZXING
		@RequestMapping(value="addQrCode.do", method=RequestMethod.POST)
		public @ResponseBody void donghunCode() {
			try {
	            File file = null;
	            // 큐알이미지를 저장할 디렉토리 지정
	            file = new File("I:\\ho\\eclipseall\\ReMPP\\remp\\src\\main\\webapp\\resources\\images");
	            if(!file.exists()) {
	                file.mkdirs();
	            }
	            // 코드인식시 링크걸 URL주소
	            String codeurl = new String("http://192.168.0.110:8093/work/".getBytes("UTF-8"), "ISO-8859-1");
	            // 큐알코드 바코드 생상값
	            int qrcodeColor = 0xFF00FF00;
	            // 큐알코드 배경색상값
	            int backgroundColor = 0x00FF00FF;
	             
	            QRCodeWriter qrCodeWriter = new QRCodeWriter();
	            // 3,4번째 parameter값 : width/height값 지정
	            BitMatrix bitMatrix = qrCodeWriter.encode(codeurl, BarcodeFormat.QR_CODE,200, 200);
	            //
	            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor,backgroundColor);
	            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
	            // ImageIO를 사용한 바코드 파일쓰기
	            ImageIO.write(bufferedImage, "png", new File("I:\\ho\\eclipseall\\ReMPP\\remp\\src\\main\\webapp\\resources\\images\\qrcode.png"));
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}