<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.food.model.*"%>

<%
   FoodVO foodVO = (FoodVO) request.getAttribute("foodVO"); // FoodServlet.java 存入 req 的 foodVO 物件
%>
--<%= foodVO == null %>--${foodVO.storeId}--
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>餐點資料修改 - updateFoodInput.jsp</title>

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
	<tr><td>
		 <h3>餐點資料修改 - updateFoodInput.jsp</h3>
		 <h4><a href="selectPage.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<c:if test="${not empty errorMsg}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsg}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="food.do" name="form1" enctype="multipart/form-data">
<table>
	<tr>
		<td>餐點編號:</td>
		<td><%=foodVO.getFoodId()%></td>
	</tr>
	<tr>
		<td>餐點名稱:</td>
		<td><input type="TEXT" name="name" value="<%=foodVO.getName()%>" size="45"/></td>
	</tr>
	<tr>
		<td><input type="hidden" name="storeId" value="<%=foodVO.getStoreId()%>" size="45"/></td>
	</tr>
	<tr>
		<td>狀態:</td>
		<td><input type="TEXT" name="status" value="<%=foodVO.getStatus()%>" size="10"/></td>
	</tr>
	<tr>
		<td>數量:</td>
		<td><input type="TEXT" name="amount" value="<%=foodVO.getAmount()%>" size="10"/></td>
	</tr>
	<tr>
		<td>消耗點數:</td>
		<td><input type="TEXT" name="cost" value="<%=foodVO.getCost()%>" size="10"/></td>
	</tr>
	<tr>
		<td>上傳新圖片:</td>
		<td><img src="${pageContext.request.contextPath}/${foodVO.photo}" width="100"></td>
		<td><input type="file" name="photo">
		</td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="foodId" value="<%=foodVO.getFoodId()%>">
<input type="submit" value="送出修改">
</FORM>

</body>
</html>