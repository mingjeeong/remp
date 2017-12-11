<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div style="margin: 0 auto; width: 600px;">
	<form name="joinForm" action="join.do" method="post" autocomplete="off">
		<!-- 아이디 -->
		<div class="item_wrapper">
			<div>
				<label>아이디</label>
			</div>
			<div>
				<input class="form-control" id="tb_id" name="tb_id" type="email" pattern="^[a-zA-Z0-9]{4,20}@([a-z0-9]+\.)+[a-z0-9]{2,4}$" placeholder="아이디" disabled>
			</div>
		</div>
		
		<!-- 비밀번호 -->
		<div class="item_wrapper">
			<div>
				<label>비밀번호</label>
			</div>
			<div>
				<input class="form-control" id="tb_pw"  name="tb_pw" type="password" pattern="[0-9a-zA-z!@#$%^&*]{8,30}$" placeholder="비밀번호" value="**********" disabled>
			</div>
		</div>
		
		<!-- 비밀번호 확인 -->
		<div class="item_wrapper">
			<div>
				<label>비밀번호 확인</label>
			</div>
			<div>
				<input class="form-control" id="tb_rePw" name="tb_rePw" type="password" onkeyup="setPwCheck()" pattern="[0-9a-zA-z!@#$%^&*]{8,30}$" placeholder="비밀번호 확인" maxlength="30">
			</div>
		</div>
		
		<!-- 성명 -->
		<div class="item_wrapper">
			<div>
				<label>성명</label>
			</div>
			<div>
				<input class="form-control" name="tb_name" type="text" pattern="[가-힣]{2,}" title="한글을 입력하세요." placeholder="이름" disabled>
			</div>
		</div>
		
		<!-- 생년월일 -->
		<div class="item_wrapper">
			<div>
				<label>생년월일</label>
			</div>
			<div>
				<input class="form-control" type="date" name="tb_birth" disabled>
			</div>
		</div>
		
		<!-- 전화번호 -->
		<div class="item_wrapper">
			<div>
				<label>전화번호</label>
			</div>
			<div>
				<input class="form-control" name="tb_mobile" type="tel" pattern="[0-9]{3}-[0-9]{3,4}-[0-9]{4}" title="'-'를 포함해주세요." placeholder="'-'를 포함해주세요.">
			</div>
		</div>
		
		<!-- 우편번호 -->
		<div class="item_wrapper">
			<div>
				<label>우편번호</label>
			</div>
			<div>
				<input class="form-control" id="tb_post" name="tb_post" type="number" placeholder="우편번호" readonly>
				<input class="btn btn-success" type="button" value="우편번호" onclick="getPost()">
			</div>
		</div>
		
		<!-- 주소 -->
		<div class="item_wrapper">
			<div>
				<label>주소</label>
			</div>
			<div>
				<input class="form-control" id="tb_addr" name="tb_addr" type="text" placeholder="주소" readonly>
			</div>
		</div>
		
		<!-- 상세주소 -->
		<div class="item_wrapper">
			<div>
				<label>상세주소</label>
			</div>
			<div>
				<input class="form-control" id="tb_addD" name="tb_addD" type="text" placeholder="상세주소" onfocus="getAddressCheck()">
			</div>
		</div>
		
		<!-- 카드번호 -->
		<div class="item_wrapper">
			<div>
				<label>카드번호</label>
			</div>
			<div>
				<select id="sel_card" name="sel_card" class="form-control" style="width: 20%;">
					<option value="">=카드선택=</option>
					<option value="현대카드">현대카드</option>
					<option value="KB국민카드">KB국민카드</option>
					<option value="신한카드">신한카드</option>
					<option value="삼성카드">삼성카드</option>
					<option value="롯데카드">롯데카드</option>
					<option value="우리카드">우리카드</option>
					<option value="하나카드">하나카드</option>
					<option value="비씨카드">비씨카드</option>
					<option value="NH농협카드">NH농협카드</option>
					<option value="씨티카드">씨티카드</option>
					<option value="카카오뱅크">카카오뱅크</option>
				</select>
				<input class="form-control form-control-sm" name="tb_card1" placeholder="카" type="text" maxlength="4" pattern="[0-9]{4}" style="width: 20%; display: inline;">
				<input class="form-control form-control-sm" name="tb_card2" placeholder="드" type="text" maxlength="4" pattern="[0-9]{4}" style="width: 20%; display: inline;">
				<input class="form-control form-control-sm" name="tb_card3" placeholder="번" type="text" maxlength="4" pattern="[0-9]{4}" style="width: 20%; display: inline;">
				<input class="form-control form-control-sm" name="tb_card4" placeholder="호" type="text" maxlength="4" pattern="[0-9]{4}" style="width: 20%; display: inline;">
			</div>
		</div>
		
		<!-- 계좌번호 -->
		<div class="item_wrapper">
			<div>
				<label>계좌번호</label>
			</div>
			<div>
				<select id="sel_account" name="sel_account" class="form-control"
					style="width: 20%;">
					<option value="">은행을 선택해주세요</option>
					<option value="농협">농협</option>
					<option value="우리은행">우리은행</option>
					<option value="신한은행">신한은행</option>
					<option value="국민은행">국민은행</option>
					<option value="하나은행">하나은행</option>
					<option value="외환은행">외환은행</option>
					<option value="씨티은행">씨티은행</option>
					<option value="기업은행">기업은행</option>
					<option value="우체국">우체국</option>
					<option value="부산은행">부산은행</option>
					<option value="SC은행">SC은행</option>
				</select>
				<input class="form-control" name="tb_account" type="text" placeholder="계좌번호" pattern="[0-9-]{11,15}">
			</div>
		</div>
	</form>
</div>