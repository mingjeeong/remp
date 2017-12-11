<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<script src="resources/js/jquery-3.2.1.js"></script>
<script src="resources/js/core.js"></script>
<!-- head -->
<form name="loginForm" method="post" autocomplete="off">
<table class="tableLoc" align="center" style="margin-top: 10%" cellpadding="5px" width="20%">
	<tr>
		<td align="center">
			<input type="radio" id="rb_user" name="rb_user" value="customer" checked="checked" onclick="getLoginTab(this.value)"><label style="margin-right: 5%">고객</label>
			<input type="radio" id="rb_user" name="rb_user" value="employee" onclick="getLoginTab(this.value)"><label>직원</label>
		</td>
	</tr>
	<tr>
		<td>
			<input class="form-control" type="text" name="tb_inputId" id="tb_inputId" placeholder="아이디">
		</td>
	</tr>
	<tr>
		<td>
			<input class="form-control" type="password" name="tb_inputPw" id="tb_inputPw" pattern="[0-9a-zA-z!@#$%^&*]{8,30}$" maxlength="30" placeholder="비밀번호">
		</td>
	</tr>
	<tr>
		<td align="center">
			<input class="btn btn-secondary" type="button" value="로그인" onclick="getLogin()" style="width: 100%;">
		</td>
	</tr>
	<tr id="employee">
		<td align="center">
			<a href="#">아이디 찾기</a>
			<span>|</span>
			<a href="#">비밀번호 찾기</a>
			<span>|</span>
			<a href="goJoin.do">회원가입</a>
		</td>
	</tr>
</table>
</form>