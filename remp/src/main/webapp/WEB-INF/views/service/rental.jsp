<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>


<!-- rental  -->
<!-- 주문상품 정보 -->
<div>
	<div style="font-size: 20px;">렌탈상품 정보</div>
	<div align="center">
		<table border="1">
			<tr>
				<th>상품정보</th>
				<th>상품금액</th>
			</tr>
			<tr>
				<td><img src="resources/images/${dto.getImage()}" width="120px">${dto.getCode()}${dto.getName()}</td>
				<td>매월 ${dto.getPrice()}원</td>
			</tr>
		</table>
	</div>
</div>
<!-- 렌탈 기간 설정 -->
<div>
<div style="font-size: 20px;">렌탈기간 설정</div>
	시작일<input type="date" id="start" onchange="setDate(this)">
	종료일<input type="date" id="end" readonly>
</div>
<!-- 배송지 정보 -->
<div align="center">
	<div style="font-size: 20px;">배송지 정보</div>
	<table>
		<tr>
			<td>받는 분 </td>
			<td><input type="text" id="recipient" size="25"></td>
		</tr>
		<tr>
			<td>휴대전화</td>
			<td>
				<select name="sb_mobile1">
					<option value="010">010</option>
					<option value="011">011</option>
					<option value="016">016</option>
					<option value="017">017</option>
					<option value="018">018</option>
					<option value="019">019</option>
				</select>-
				<input type="text" name="mobile2" id="mobile2" size="5">-
				<input type="text" name="mobile3" id="mobile3" size="5">
			</td>
		</tr>
		<tr>
			<td>주소</td>
			<td><input type="button" id="findAddress" value="주소찾기"><input type="text" id="post" size="8" readonly><br>
				<input type="text" id="address" size="40" readonly><br>
				<input type="text" id="addressDetail" size="40">
			</td>
		</tr>
	</table>
</div>

<!-- 결제 수단  -->
<div >
	<div style="font-size: 20px;">결제 수단</div>
	<div>
		<input type="radio" name="rb_payment" onclick="showCard()">신용카드/체크카드 결제
		<input type="radio" name="rb_payment" onclick="showCash()">무통장입금
	</div>
	<div class="sb_payment" id="paymentCard">카드종류 
		<select name="sb_card">
			<option value="">카드를 선택해주세요</option>
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
		<input type="text" id="cardNum" placeholder="카드번호 입력">
	</div>
	<div class="sb_payment" id="paymentCash">은행선택
		<select name="sb_bank">
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
		<input type="text" id="accountNum" placeholder="계좌번호 입력">
	</div>
	<input type="button" value="결제하기"> 
</div>