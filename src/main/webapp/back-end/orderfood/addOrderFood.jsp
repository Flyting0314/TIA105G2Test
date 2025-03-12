<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.orderfood.model.*"%>
<%@ page import="java.text.SimpleDateFormat" %>

<% //見com.emp.controller.EmpServlet.java第238行存入req的empVO物件 (此為輸入格式有錯誤時的empVO物件)
	OrderFoodVO ofVO = (OrderFoodVO) request.getAttribute("ofVO");
%>
--<%=ofVO==null %>--${ofVO.storeId}--<!-- line 100 -->
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>訂單資料新增 - addOrderDetail.jsp</title>

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
		 <h3>訂單資料新增 <br> Add OrderFood.jsp</h3></td><td>
		 <h4><a href="/TIA105G2/front-end/orderfood/select_page_for_orderfood.jsp">
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

<FORM METHOD="post" ACTION="/TIA105G2/OrderFood" name="form1">
<table>
	
	<tr>
		<td>店家會員ID:</td>
		<td><input type="TEXT" name="storeId" value="<%= (ofVO==null)? "Nan" : ofVO.getStoreId()%>" size="45"/></td>
	</tr>
	<tr>
		<td>受助人ID:</td>
		<td><input type="TEXT" name="memberId"   value="<%= (ofVO==null)? "Nan" : ofVO.getMemberId()%>" size="45"/></td>
	</tr>
	
	<tr>
    <td>評分:</td>
    <td>
        <select name="rate">
            <option value="1" <%= (ofVO != null && ofVO.getRate() == 1) ? "selected" : "" %>>1</option>
            <option value="2" <%= (ofVO != null && ofVO.getRate() == 2) ? "selected" : "" %>>2</option>
            <option value="3" <%= (ofVO != null && ofVO.getRate() == 3) ? "selected" : "" %>>3</option>
            <option value="4" <%= (ofVO != null && ofVO.getRate() == 4) ? "selected" : "" %>>4</option>
            <option value="5" <%= (ofVO != null && ofVO.getRate() == 5) ? "selected" : "" %>>5</option>
        </select>
    </td>
</tr>
	<tr>
		<td>評論:</td>
		<td><input type="TEXT" name="comment"   value="<%= (ofVO==null)? "Nan" : ofVO.getComment()%>" size="45"/></td>
	</tr>
	<tr>
    <td>訂單狀態:</td>
    <td>
        <select name="serveStat">
            <option value="0" <%= (ofVO != null && ofVO.getServeStat() == 0) ? "selected" : "" %>>0</option>
            <option value="1" <%= (ofVO != null && ofVO.getServeStat() == 1) ? "selected" : "" %>>1</option>
        </select>
    </td>
</tr>
<tr>
    <td>取餐狀態:</td>
    <td>
        <select name="pickStat">
            <option value="0" <%= (ofVO != null && ofVO.getPickStat() == 0) ? "selected" : "" %>>0</option>
            <option value="1" <%= (ofVO != null && ofVO.getPickStat() == 1) ? "selected" : "" %>>1</option>
        </select>
    </td>
</tr>
	
	
	<tr>
		<td>取餐時間:</td>

		<%
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
         String fd = (ofVO == null || ofVO.getPickTime() == null) 
         ? sdf.format(new java.util.Date())  
         : sdf.format(ofVO.getCreatedTime()); 
		%>
		<td><input type="datetime-local" name="pickTime" value="<%=fd%>" size="45"/></td>
	</tr>
	
	<tr>
		<td>取餐時間:</td>

		<%
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
         String fd1 = (ofVO == null || ofVO.getCreatedTime() == null) 
         ? sdf.format(new java.util.Date())  
         : sdf.format(ofVO.getCreatedTime()); 
		%>
		<td><input type="datetime-local" name="createdTime" value="<%=fd1%>" size="45"/></td>
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