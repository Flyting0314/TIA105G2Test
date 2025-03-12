<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.orderfood.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
	OrderFoodVO ofVO = (OrderFoodVO) request.getAttribute("ofVO"); 
%>	

<html>
<head>
<title>訂單資料 - listOneOrderFood.jsp</title>

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
		 <h3>訂單資料 - listOneOrderFood.jsp</h3>
		 <h4><a href="/TIA105G2/front-end/orderfood/select_page_for_orderfood.jsp"><img src="/TIA105G2/resources/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
			<th>訂單ID</th>
			<th>店家會員ID</th>
			<th>受助人ID</th>
			<th>評分</th>
			<th>評論</th>
			<th>訂單狀態</th>
			<th>取餐狀態</th>
			<th>取餐時間</th>
			<th>訂單成立時間</th>
			
	</tr>
	<tr>
		<td><%=ofVO.getOrderId()%></td>
		<td><%=ofVO.getStoreId()%></td>
		<td><%=ofVO.getMemberId()%></td>
		<td><%=ofVO.getRate()%></td>
		<td><%=ofVO.getComment()%></td>
		<td><%=ofVO.getServeStat()%></td>
		<td><%=ofVO.getPickStat()%></td>
		<td><%=ofVO.getPickTime()%></td>
		<td><%=ofVO.getCreatedTime()%></td>
	</tr>
</table>

</body>
</html>