<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<title>Order for Search: Home</title>

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
   <tr><td><h3> Order search:Home</h3><h4>( Project 1 )</h4></td></tr>
</table>

<p>This is the Home page for Order search: Home</p>

<h3>資料查詢:</h3>

錯誤表列
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message.value}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='${pageContext.request.contextPath}/back-end/orderfood/listAllOrderFood.jsp'>List</a> All search results <br><br></li>
    
  <li>
    <FORM METHOD="post" ACTION="${pageContext.request.contextPath}/OrderFood" >
        <b>請輸入訂單編號:</b>
        <input type="text" name="orderId">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>
  

   
 <jsp:useBean id="ofSvc" scope="page" class="com.orderfood.model.OrderFoodService" />

<li>
   <FORM METHOD="post" ACTION="${pageContext.request.contextPath}/OrderFood">
     <b>請選擇訂單編號:</b>
     <select size="1" name="orderId">
       <c:forEach var="ofVO" items="${ofSvc.all}">
         <option value="${ofVO.orderId}">${ofVO.orderId}</option>
       </c:forEach>
     </select>

<!--      <b>/餐點編號:</b> -->
<!--      <select size="1" name="foodId"> -->
<%--        <c:forEach var="odVO" items="${odSvc.all}"> --%>
<%--          <option value="${odVO.foodId}">${odVO.foodId}</option> --%>
<%--        </c:forEach> --%>
<!--      </select> -->

     <input type="hidden" name="action" value="getOne_For_Display">
     <input type="submit" value="送出">
  </FORM>
</li>
</ul>  

<h3>訂單管理</h3>

<ul>
  <li><a href='${pageContext.request.contextPath}/back-end/orderfood/addOrderFood.jsp'>Add:</a>Receipt details</li>
</ul>

</body>
</html>