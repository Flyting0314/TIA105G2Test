<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM Food: Home</title>

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
   <tr><td><h3>IBM Food: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Food: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsg}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsg}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllFood.jsp'>List</a> all Food.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="food.do" >
        <b>輸入餐點編號:</b>
        <input type="text" name="foodId">
        <input type="hidden" name="action" value="getOneForDisplay">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="foodSvc" scope="page" class="com.food.model.FoodService" />
   
  <li>
     <FORM METHOD="post" ACTION="food.do" >
       <b>選擇餐點編號:</b>
       <select size="1" name="foodId">
         <c:forEach var="foodVO" items="${foodSvc.all}" > 
          <option value="${foodVO.foodId}">${foodVO.foodId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOneForDisplay">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="food.do" >
       <b>選擇餐點名稱:</b>
       <select size="1" name="foodId">
         <c:forEach var="foodVO" items="${foodSvc.all}" > 
          <option value="${foodVO.foodId}">${foodVO.name}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOneForDisplay">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>餐點管理</h3>

<ul>
  <li><a href='addFood.jsp'>Add</a> a new Food</li>
</ul>

</body>
</html>