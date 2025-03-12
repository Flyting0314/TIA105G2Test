<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.orderfood.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
OrderFoodService ofSvc = new OrderFoodService();
List<OrderFoodVO> list = ofSvc.getAll();
pageContext.setAttribute("list", list);
%>


<html>
<head>
<title>所有訂單資料 - listAllOrderFood.jsp</title>

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

	<h4>此頁練習採用 EL 的寫法取值:</h4>


	<table id="table-1">
		<tr>
			<td>
				<h3>所有訂單資料 - listAllOrderFood.jsp</h3>
				<h4>
					<a
						href="/TIA105G2/front-end/orderfood/select_page_for_orderfood.jsp">
						<img src="/TIA105G2/resources/images/back1.gif" width="100"
						height="32" border="0">回首頁
					</a>
				</h4>
			</td>
		</tr>
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
			<th>修改</th>
			<th>刪除</th>
		</tr>
		<%@ include file="/back-end/orderfood/page1.file"%>
		<c:forEach var="ofVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${ofVO.orderId}</td>
				<td>${ofVO.storeId}</td>
				<td>${ofVO.memberId}</td>
				<td>${ofVO.rate}</td>
				<td>${ofVO.comment}</td>
				<td>${ofVO.serveStat}</td>
				<td>${ofVO.pickStat}</td>
				<td>${ofVO.pickTime}</td>
				<td>${ofVO.createdTime}</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/OrderFood"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> 
						<input type="hidden"name="orderId" value="${ofVO.orderId}">  
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/OrderFood"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> 
						<input type="hidden"name="orderId" value="${ofVO.orderId}">
						<input type="hidden"name="storeId" value="${ofVO.storeId}"> 
						<input type="hidden"name="memberId" value="${ofVO.memberId}">
						<input type="hidden"name="rate" value="${ofVO.rate}">
						<input type="hidden"name="comment" value="${ofVO.comment}">
						<input type="hidden"name="serveStat" value="${ofVO.serveStat}">
						<input type="hidden"name="pickStat" value="${ofVO.pickStat}">
						<input type="hidden"name="pickTime" value="${ofVO.pickTime}">
						<input type="hidden"name="createdTime" value="${ofVO.createdTime}">
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>


		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>



	</table>
	<%@ include file="page2.file"%>

</body>
</html>