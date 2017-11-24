<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<!-- change Password -->
<form action="changepw.do" method="post" name="changepwform">
	<div style="width: 500px; " align="center">
		<input id="presentPw" name="presentPw" type="password" placeholder="현재 비밀번호" class="form-control">
		<div style="margin-top : 10px;">
			<input id="newPw" name="newPw" type="password" placeholder="새 비밀번호" class="form-control">
			<input id="checkPw" name="checkPw" type="password" placeholder="새 비밀번호 확인" class="form-control">
		</div>
		<input type="button" class="btn btn-primary" value="비밀번호 번경" onclick="return checkPw()">
	</div>
</form>