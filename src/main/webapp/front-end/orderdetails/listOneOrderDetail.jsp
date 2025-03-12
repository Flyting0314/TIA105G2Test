<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.orderdetail.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
	OrderDetailVO odVO = (OrderDetailVO) request.getAttribute("odVO"); 
%>	

<html>
<head>
<title>員工資料 - listOneEmp.jsp</title>

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
		 <h3>訂單明細資料 - listOneEmp.jsp</h3>
		 <h4><a href="/TIA105G2/front-end/orderdetails/select_page_for_recepit_details.jsp"><img src="/TIA105G2/resources/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>訂單編號</th>
		<th>餐點編號</th>
		<th>訂單成立時間</th>
		<th>餐點數量</th>
		<th>使用點數小計</th>
		<th>備註</th>
	</tr>
	<tr>
		<td><%=odVO.getOrderId()%></td>
		<td><%=odVO.getFoodId()%></td>
		<td><%=odVO.getCreatedTime()%></td>
		<td><%=odVO.getAmount()%></td>
		<td><%=odVO.getPointsCost()%></td>
		<td><%=odVO.getNote()%></td>
	</tr>
</table>

</body>
</html>