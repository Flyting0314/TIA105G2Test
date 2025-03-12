<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.orderdetail.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
OrderDetailService odSvc = new OrderDetailService();
List<OrderDetailVO> list = odSvc.getAll();
pageContext.setAttribute("list", list);
%>


<html>
<head>
<title>所有訂單明細資料 - listAllOrderDetails.jsp</title>

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
				<h3>所有訂單明細資料 - listAllOrderDetails.jsp</h3>
				<h4>
					<a
						href="/TIA105G2/front-end/orderdetails/select_page_for_recepit_details.jsp">
						<img src="/TIA105G2/resources/images/back1.gif" width="100"
						height="32" border="0">回首頁
					</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>訂單編號</th>
			<th>餐點編號</th>
			<th>訂單成立時間</th>
			<th>餐點數量</th>
			<th>使用點數小計</th>
			<th>備註</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>
		<%@ include file="/back-end/orderdetails/page1.file"%>
		<c:forEach var="odVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${odVO.orderId}</td>
				<td>${odVO.foodId}</td>
				<td>${odVO.createdTime}</td>
				<td>${odVO.amount}</td>
				<td>${odVO.pointsCost}</td>
				<td>${odVO.note}</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/OrderDetail"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> <input type="hidden"
							name="orderId" value="${odVO.orderId}"> <input
							type="hidden" name="foodId" value="${odVO.foodId}"> <input
							type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/OrderDetail"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="orderId" value="${odVO.orderId}"> <input
							type="hidden" name="foodId" value="${odVO.foodId}"> <input
							type="hidden" name="action" value="delete">
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