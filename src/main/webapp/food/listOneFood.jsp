<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.food.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  FoodVO foodVO = (FoodVO) request.getAttribute("foodVO"); // FoodServlet.java 存入 req 的 foodVO 物件
%>

<html>
<head>
<title>餐點資料 - listOneFood.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>餐點資料 - listOneFood.jsp</h3>
		 <h4><a href="selectPage.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>餐點編號</th>
		<th>店家編號</th>
		<th>餐點名稱</th>
		<th>創建日期</th>
		<th>狀態</th>
		<th>數量</th>
		<th>照片</th>
		<th>消耗點數</th>
	</tr>
	<tr>
		<td><%=foodVO.getFoodId()%></td>
		<td><%=foodVO.getStoreId()%></td>
		<td><%=foodVO.getName()%></td>
		<td><%=foodVO.getCreatedTime()%></td>
		<td><%=foodVO.getStatus()%></td>
		<td><%=foodVO.getAmount()%></td>
		<td><img src="${pageContext.request.contextPath}/<%=foodVO.getPhoto()%>" width="100"></td>
		<td><%=foodVO.getCost()%></td>
	</tr>
</table>

</body>
</html>
