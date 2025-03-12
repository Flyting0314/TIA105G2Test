<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.food.model.*"%>

<%-- 此頁使用 EL 來取值 --%>
<%
    FoodService foodSvc = new FoodService();
    List<FoodVO> list = foodSvc.getAll();
    pageContext.setAttribute("list", list);
%>

<html>
<head>
<title>所有餐點資料 - listAllFood.jsp</title>

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
	width: 800px;
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

<h4>此頁使用 EL 來取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有餐點資料 - listAllFood.jsp</h3>
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
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="foodVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${foodVO.foodId}</td>
			<td>${foodVO.storeId}</td>
			<td>${foodVO.name}</td>
			<td>${foodVO.createdTime}</td>
			<td>${foodVO.status}</td>
			<td>${foodVO.amount}</td>
			<td><img src="${pageContext.request.contextPath}/${foodVO.photo}" width="800"></td>
			<td>${foodVO.cost}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/food/food.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="foodId"  value="${foodVO.foodId}">
			     <input type="hidden" name="action" value="getOneForUpdate">
			  </FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/food/food.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="foodId"  value="${foodVO.foodId}">
			     <input type="hidden" name="action" value="delete">
			  </FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>
