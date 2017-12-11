<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="uploadTest.do" id="fileTestForm" name="fileTestForm" method="post">
<div class="card border-dark mb-3" style="max-width: 20rem; float: left;">
  <div class="card-header">Header</div>
  <div class="card-body text-dark">
    <h4 class="card-title">Dark card title</h4>
    <input type="file" id="tb_file" name="tb_file">
  <!--   <input type="button" name="bt_a" value="a" onclick="uploadTest()"> --> <!-- FILE 업로드 -->
    <input type="button" name="bt_a" value="a" onclick="qrCreate()"> <!-- QR 생성 -->
  </div>
</div>
</form>
<div class="card border-dark mb-3" style="max-width: 20rem; float: left;" id="aaaa">
  <div class="card-header"><h3>GIST ?</h3></div>
  <div class="card-body text-dark">
    <h4 class="card-title">GIST는 2017/10/31에 설립되었습니다.</h4>
	<input type="color" name="tb_color" id="tb_color" onchange="changeColor(this.value)">
  </div>
</div>

</body>
</html>