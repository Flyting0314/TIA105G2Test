<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>All i Eat Member: Home</title>

<style>
  table#table-1 {
	width: AUTO;
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
   <tr><td><h3>All i Eat Member: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for All i Eat Member: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllMember.jsp'>List</a> all Members.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="member.do" >
        <b>輸入受助者編號 (如1):</b>
        <input type="text" name="memberId">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />
  
  


  
  <li>
     <FORM METHOD="post" ACTION="member.do" >
       <b>選擇受助者姓名:</b>
       <select size="1" name="memberId">
       <option value="" selected>請選擇</option>
         <c:forEach var="memberVO" items="${memberSvc.all}" > 
<%--           <option value="${memberVO.memberId}">${memberVO.name} --%>
          <option value="${memberVO.memberId}">${memberVO.name}</option>
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>受助者管理</h3>

<ul>
  <li><a href='addMember.jsp'>Add</a> a new Member.</li>
</ul>

</body>
</html>