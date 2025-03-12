<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.orderdetail.model.*"%>
<%@ page import="java.text.SimpleDateFormat" %>

<% //見com.emp.controller.EmpServlet.java第238行存入req的empVO物件 (此為輸入格式有錯誤時的empVO物件)
	OrderDetailVO odVO = (OrderDetailVO) request.getAttribute("odVO");
%>
--<%=odVO==null %>--${odVO.orderId}--<!-- line 100 -->
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>訂單明細資料新增 - addOrderDetail.jsp</title>

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
	<tr><td>
		 <h3>訂單明細資料新增 <br> Add Order Detail.jsp</h3></td><td>
		 <h4><a href="/TIA105G2/front-end/orderdetails/select_page_for_recepit_details.jsp">
		 <img src="/TIA105G2/resources/images/tomcat.png" width="110" height="100" border="0"><br>回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="/TIA105G2/OrderDetail" name="form1">
<table>
	
	
	
	
	<tr>
		<td>訂單編號:</td>
		<td><input type="TEXT" name="orderId" value="<%= (odVO==null)? "nan" : odVO.getOrderId()%>" size="45"/></td>
	</tr>
	<tr>
		<td>餐點編號:</td>
		<td><input type="TEXT" name="foodId"   value="<%= (odVO==null)? "nan" : odVO.getFoodId()%>" size="45"/></td>
	</tr>
	<tr>
		<td>訂單成立時間:</td>

		<%
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
         String fd = (odVO == null || odVO.getCreatedTime() == null) 
         ? sdf.format(new java.util.Date())  // 如果 odVO 為 null 或 getCreatedTime() 為 null，使用當前時間
         : sdf.format(odVO.getCreatedTime());  // 否則使用 odVO.getCreatedTime() 的時間
		%>
		<td><input type="datetime-local" name="createdTime" value="<%=fd%>" size="45"/></td>
	</tr>
	<tr>
		<td>餐點數量:</td>
		<td><input type="TEXT" name="amount"   value="<%= (odVO==null)? "0" : odVO.getAmount()%>" size="45"/></td>
	</tr>
	<tr>
		<td>使用點數小計:</td>
		<td><input type="TEXT" name="pointsCost"  value="<%= (odVO==null)? "0" : odVO.getPointsCost()%>" size="45"/></td>
	</tr>
	<tr>
		<td>備註:</td>
		<td><input type="TEXT" name="note"  value="<%= (odVO==null)? "無" : odVO.getNote()%>" size="45"/></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>

</body>



<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>


</html>