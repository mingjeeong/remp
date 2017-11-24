<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>


<!-- customer rental detail -->
<div align="center">
<table style="width: 800px; height:400px" border="1">
	<tr>
		<td align="center">
			<img src="resources/images/${dto.getImage()}" width="400px">
		</td>
		<td>
			<div style="padding: 20px;">
				<div>${dto.getCode()}</div>
				<div>${dto.getName()}<hr></div>
				<div>렌탈가격 <span style="color: darkred;">월 ${dto.getPrice()}원</span><hr></div>
				<div>*3년 약정</div>
				<div align="right" style="margin: 50px;" >
					<input type="button" data-code="${dto.getId()}" id="bt_buy" value="구매하기" onclick="getId(this)">
					<input type="button" id="bt_golist"value="목록으로" onclick="location='rentalmain.do'">
				</div>
			</div>
		</td>
	</tr>
</table>
</div>
