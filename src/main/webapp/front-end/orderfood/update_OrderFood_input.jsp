<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.orderfood.model.*"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
OrderFoodVO ofVO = (OrderFoodVO) request.getAttribute("ofVO");
%>
--<%=ofVO == null%>--${ofVO.orderId}--
<!-- line 100 -->
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>訂單修改 - update_OrderFood_input.jsp</title>

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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>訂單資料修改 - update_OrderFood_input.jsp</h3>
				<h4>
					<a
						href="${pageContext.request.contextPath}/front-end/orderfood/select_page_for_orderfood.jsp"><img
						src="${pageContext.request.contextPath}/resources/images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>

	</table>

	<h3>資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post"
		ACTION="${pageContext.request.contextPath}/OrderFood" name="form1">
		<table>
			<!-- 			<tr> -->
			<!-- 				<td>訂單ID:<font color=red><b>*</b></font></td> -->
			<%-- 				<td><%=ofVO.getOrderId()%></td> --%>
			<!-- 			</tr> -->
			<tr>
				<td>店家會員ID:</td>
				<td><input type="TEXT" name="storeId"
					value="<%=ofVO.getStoreId()%>" size="45" /></td>
			</tr>
			<tr>
				<td>受助人ID:</td>
				<td><input type="TEXT" name="memberId"
					value="<%=ofVO.getMemberId()%>" size="45" /></td>
			</tr>
			<tr>
				<td>評分:</td>
				<td><select name="rate">
						<option value="1" <%=ofVO.getRate() == 1 ? "selected" : ""%>>1</option>
						<option value="2" <%=ofVO.getRate() == 2 ? "selected" : ""%>>2</option>
						<option value="3" <%=ofVO.getRate() == 3 ? "selected" : ""%>>3</option>
						<option value="4" <%=ofVO.getRate() == 4 ? "selected" : ""%>>4</option>
						<option value="5" <%=ofVO.getRate() == 5 ? "selected" : ""%>>5</option>
				</select></td>
			</tr>
			<tr>
				<td>備註:</td>
				<td><input type="TEXT" name="comment"
					value="<%=ofVO.getComment()%>" size="45" /></td>
			</tr>
			<tr>
				<td>訂單狀態:</td>
				<td><select name="serveStat">
						<option value="0"
							<%="0".equals(String.valueOf(ofVO.getServeStat())) ? "selected" : ""%>>0</option>
						<option value="1"
							<%="1".equals(String.valueOf(ofVO.getServeStat())) ? "selected" : ""%>>1</option>
				</select></td>
			</tr>
			<tr>
				<td>取餐狀態:</td>
				<td><select name="pickStat">
						<option value="0"
							<%="0".equals(String.valueOf(ofVO.getPickStat())) ? "selected" : ""%>>0</option>
						<option value="1"
							<%="1".equals(String.valueOf(ofVO.getPickStat())) ? "selected" : ""%>>1</option>
				</select></td>
			</tr>
			<tr>
				<td>取餐時間:</td>
				<%
				String fd = "";
				if (ofVO != null && ofVO.getPickTime() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
					fd = sdf.format(ofVO.getPickTime());
				} else {
					fd = "";
				}
				%>
				<td><input type="datetime-local" name="pickTime"
					value="<%=fd%>" size="45" /></td>
			</tr>
			<tr>
				<td>訂單成立時間:</td>
				<%
				String fd1 = "";
				if (ofVO != null && ofVO.getCreatedTime() != null) {
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
					fd1 = sdf1.format(ofVO.getCreatedTime());
				} else {
					fd1 = "";
				}
				%>
				<td><input type="datetime-local" name="createdTime"
					value="<%=fd1%>" size="45" /></td>
			</tr>
		</table>
		<br>
		<input type="hidden" name="action" value="update"> <input
			type="hidden" name="orderId"
			value="<%=(ofVO != null ? ofVO.getOrderId() : "N/A")%>"> <input
			type="submit" value="送出修改">
	</FORM>
</body>


<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>


</html>