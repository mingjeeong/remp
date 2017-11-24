<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.remp.work.model.dto.Item" %>

<!-- customer rental main -->
<!-- 검색바 -->
<form action="searchitem.do" method="post">
<div>
	<select name="sb_search" >
		<option value="제품명">제품명</option>
		<option value="제품코드">제품코드</option>
	</select>
	<input type="text" id="item" name="item" size="30">
	<input type="submit" value="검색" >
</div>
</form>
<span>${message}</span>
<!-- 제품리스트 -->
<div align="center" style="margin: 20px;">
	
 <table border="1" style="width: 900px">
 <% 
    ArrayList<Item> list = (ArrayList)request.getAttribute("list");
	 if(list != null){
	    int size = list.size();
	    int trMax = size / 3; 
	    int tmp = size % 3;
	    if (tmp > 0) { 
	      trMax += 1; 
	     } 
 %>

<% 
   int position = 0;
   for (int i=0; i < trMax;  i++) { 
%> 
   <tr>
<% 
 for (int j=0; j < 3;  j++) {
	 
%> 
      <td>
<%
  if (position < size) { 
%>
  <a href="#" data-code="<%= list.get(position).getId() %>" onclick="getItemId(this)"><img src="resources/images/<%= list.get(position).getImage() %>"></a>
  <div><%= list.get(position).getCode() %></div>
  <div><%= list.get(position).getName() %></div>
  <div>렌탈가격 <%= list.get(position).getPrice() %>원</div>
  <input type="button" value="구매하기">
  
      </td>
<% 
   position++;
  }  
 }
%>
 </tr>
<%
}
}
%> 
 </table> 
</div>