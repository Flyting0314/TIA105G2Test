<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.food.model.*"%>

<%
// 從 FoodServlet 取得 foodVO 物件 (若輸入錯誤則保留輸入值)
FoodVO foodVO = (FoodVO) request.getAttribute("foodVO");
%>
--<%=foodVO == null%>--${foodVO.storeId}--
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>餐點資料新增 - addFood.jsp</title>

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
</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>餐點資料新增 - addFood.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="selectPage.jsp"><img src="images/tomcat.png"
						width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsg}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsg}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="food.do" name="form1"
		enctype="multipart/form-data">
		<table>
			<jsp:useBean id="storeSvc" scope="page"
				class="com.store.model.StoreService" />
			<tr>
				<td>店家名稱:</td>
				<td><select name="storeId" id="storeId">
						<option value="">請選擇店家</option>
						<c:forEach var="storeVO" items="${storeSvc.all}">
							<option value="${storeVO.storeId}">${storeVO.name}</option>
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td>餐點名稱:</td>
				<td><input type="TEXT" name="name"
					value="<%=(foodVO == null) ? "" : foodVO.getName()%>" size="45" /></td>
			</tr>
			<tr>
				<td>狀態:</td>
				<td><input type="TEXT" name="status"
					value="<%=(foodVO == null) ? "1" : foodVO.getStatus()%>" size="10" /></td>
			</tr>
			<tr>
				<td>數量:</td>
				<td><input type="TEXT" name="amount"
					value="<%=(foodVO == null) ? "1" : foodVO.getAmount()%>" size="10" /></td>
			</tr>
			<tr>
				<td>消耗點數:</td>
				<td><input type="TEXT" name="cost"
					value="<%=(foodVO == null) ? "10" : foodVO.getCost()%>" size="10" /></td>
			</tr>
			<tr>
				<td>上傳圖片:</td>
				<td><input type="file" name="photo"></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</FORM>

</body>

<!-- 日期選擇器設定 -->
<%
java.sql.Timestamp createdTime = null;
try {
	createdTime = foodVO.getCreatedTime();
} catch (Exception e) {
	createdTime = new java.sql.Timestamp(System.currentTimeMillis());
}
%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<script>
    $.datetimepicker.setLocale('zh');
    $('#f_date1').datetimepicker({
       timepicker:false,
       format:'Y-m-d',
       value: '<%=createdTime%>
	'
	});
</script>
</html>
