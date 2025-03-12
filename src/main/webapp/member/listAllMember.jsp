<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	MemberService memberSvc = new MemberService();
    List<MemberVO> list = memberSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有受助者資料 - listAllMember.jsp</title>

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
	width: AUTO;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
	
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有受助者資料 - listAllMember.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="<%= request.getContextPath() %>/member/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>受助者編號</th>
		<th>在冊單位</th>
		<th>姓名</th>
		<th>身分證字號</th>
		<th>戶籍地址</th>
		<th>通訊地址</th>
		<th>註冊時間</th>
		<th>證明照片</th>
		<th>email</th>
		<th>電話</th>
		<th>會員帳號</th>
		<th>會員密碼</th>
		<th>點數餘額</th>
		<th>未領餐累計次數</th>
		<th>受助者帳號狀態</th>
		<th>受助者帳號狀態受助者審核狀態(後台用)</th>
		
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="memberVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${memberVO.memberId}</td>
			<td>${memberVO.organizationId}</td>
			<td>${memberVO.name}</td>
			<td>${memberVO.idNum}</td>
			<td>${memberVO.permAddr}</td>
			<td>${memberVO.address}</td>
			<td>${memberVO.regTime}</td>

<td ><img src="${pageContext.request.contextPath}/images_uploaded/${memberVO.kycImage}" width="100" >${memberVO.kycImage} </td>
    
			<td>${memberVO.email}</td>
			<td>${memberVO.phone}</td>
			<td>${memberVO.account}</td>
			<td>${memberVO.password}</td>
			<td>${memberVO.pointsBalance}</td>
			<td>${memberVO.unclaimedMealCount}</td>
			<td>${memberVO.accStat}</td>
			<td>${memberVO.reviewed}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="memberId"  value="${memberVO.memberId}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="memberId"  value="${memberVO.memberId}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>