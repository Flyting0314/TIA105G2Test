<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.orderdetail.model.*"%>
<%@ page import="java.text.SimpleDateFormat" %>

<%
	OrderDetailVO odVO = (OrderDetailVO) request.getAttribute("odVO");
%>
--<%=odVO==null %>--${odVO.orderId}--<!-- line 100 -->
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>訂單明細修改 - update_OrderDetail_input.jsp</title>

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
		 <h3>訂單資料明細修改 - update_OrderDetail_input.jsp</h3>
		 <h4><a href="/TIA105G2/front-end/orderdetails/select_page_for_recepit_details.jsp"><img src="/TIA105G2/resources/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>>
</table>

<h3>資料修改:</h3>

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
		<td>訂單編號:<font color=red><b>*</b></font></td>
		<td><%= (odVO == null ? "" : odVO.getOrderId()) %></td>
	</tr>
	<tr>
		<td>餐點編號:</td>
		<td><%= (odVO == null ? "" : odVO.getFoodId())%></td>
	</tr>
	<tr>
	
		<td>訂單成立時間:</td>
		<%
   			 String fd = "";
    	if (odVO != null && odVO.getCreatedTime() != null) {
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
       			 fd = sdf.format(odVO.getCreatedTime());
    	} else {
       				 fd = "";  
    	}
%>
		<td><input type="datetime-local" name="createdTime" value="<%=fd%>" size="45"/></td>
	</tr>
	<tr>
		<td>餐點數量:</td>
		<td><input type="TEXT" name="amount" value="<%= (odVO != null ? odVO.getAmount() : "") %>" size="45"/></td>
	</tr>
	<tr>
		<td>使用點數小計:</td>
		<td><input type="TEXT" name="pointsCost"   value="<%= (odVO != null ? odVO.getPointsCost() : "") %>" size="45"/></td>
	</tr>
	<tr>
		<td>備註:</td>
		<td><input type="TEXT" name="note"  value="<%= (odVO != null ? odVO.getNote() :"") %>" size="45"/></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="orderId" value="<%= (odVO != null ? odVO.getOrderId() : "N/A") %>">
<input type="hidden" name="foodId" value="<%= (odVO != null ? odVO.getFoodId() : "N/A") %>">
<input type="submit" value="送出修改"></FORM>
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