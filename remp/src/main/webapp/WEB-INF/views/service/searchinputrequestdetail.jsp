<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!-- 입고요청조회 -->

<div class="item_wrapper">
	<div class="left-w20">
		<span class="btn btn-secondary">입고요청조회</span>
	</div>
	<div class="left-w70">
		<input type="text" id="searchInput" class="form-control" placeholder="통합검색">
	</div>
	<div class="left-w10">
		<input type="button" class="btn btn-primary" value="검색" onclick="searchInputRequest()">
	</div>
</div>

<!-- 조회리스트 -->
<div style="width: 100%; height: 80%; overflow: scroll;">
	<table class="table table-striped table-hover table-bordered table-sm">
		<thead class="thead-dark">
			<tr>
				<th>입고번호</th>
				<th>제품명</th>
				<th>수량</th>
				<th>요청일자</th>
				<th>인계자</th>
				<th>입고</th>
			</tr>
		</thead>
		<tbody id="inputList">
		</tbody>
</table>
</div>
 