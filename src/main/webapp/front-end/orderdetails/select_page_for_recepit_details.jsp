<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<title>Receipt details for Search: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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
   <tr><td><h3> Receipt details search</h3><h4>( Project 1 )</h4></td></tr>
</table>

<p>This is the Home page for receipt details search: Home</p>

<h3>資料查詢:</h3>

<!-- 錯誤表列 -->
<%-- <c:if test="${not empty errorMsgs}"> --%>
<!-- 	<font style="color:red">請修正以下錯誤:</font> -->
<!-- 	<ul> -->
<%-- 		<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 			<li style="color:red">${message.value}</li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->
<%-- </c:if> --%>

<ul>
  <li><a href='/TIA105G2/back-end/orderdetails/listAllOrderDetail.jsp'>List</a> All search results <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="/TIA105G2/OrderDetail" >
        <b>請輸入訂單編號:</b>
        <input type="text" name="orderId" value="${param.orderId}"><font color=red>${errorMsgs.orderId}</font>
        <br>
        <b>請輸入餐點編號:</b>
        <input type="text" name="foodId" value="${param.foodId}"><font color=red>${errorMsgs.foodId}</font>
        <input type="hidden" name="action" value="getOne_For_Display">
        <br>
        <input type="submit" value="送出">
    </FORM>
  </li>
  

   
 <jsp:useBean id="odSvc" scope="page" class="com.orderdetail.model.OrderDetailService" />

<li>
   <FORM METHOD="post" ACTION="/TIA105G2/OrderDetail">
     <b>請選擇訂單編號:</b>
     <select size="1" name="orderId">
       <c:forEach var="odVO" items="${odSvc.all}">
         <option value="${odVO.orderId}">${odVO.orderId}</option>
       </c:forEach>
     </select>

     <b>/餐點編號:</b>
     <select size="1" name="foodId">
       <c:forEach var="odVO" items="${odSvc.all}">
         <option value="${odVO.foodId}">${odVO.foodId}</option>
       </c:forEach>
     </select>

     <input type="hidden" name="action" value="getOne_For_Display">
     <input type="submit" value="送出">
  </FORM>
</li>
  

<h3>明細管理</h3>

<ul>
  <li><a href='/TIA105G2/front-end/orderdetails/addOrderDetail.jsp'>Add:</a>Receipt details</li>
</ul>

</body>
</html>