<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>

<body onload="getProductInsertRequest()">
	<div style="width: 100%; height: 90%; overflow: scroll;">
	   <table id="procutRequestList" class="table table-striped table-hover table-bordered table-sm">
	      <thead class="thead-dark">
	         <tr>
		         <th>품목명</th>
		         <th>상태</th>
		         <th>입고일자</th>
		         <th>완료여부</th>
	         </tr>
	      <thead>
	      <tbody id="productReqList">
	      </tbody>
	   </table>
	</div>
</body>