<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>

<% //見com.member.controller.MemberServlet.java第238行存入req的memberVO物件 (此為輸入格式有錯誤時的memberVO物件)
MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>
--<%= memberVO==null %> --${memberVO.organizationId}--<!-- line 100 -->
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>受助者資料新增 - addMember.jsp</title>

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
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
    width:fit-content;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>受助者資料新增 - addMember.jsp</h3></td><td>
		 <h4><a href="select_page.jsp"><img src="images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
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

<FORM METHOD="post" ACTION="member.do" name="form1" enctype="multipart/form-data" >
<table>
		
	<tr>
		<td>姓名:</td>
		<td><input type="TEXT" name="name" value="<%= (memberVO==null)? "" : memberVO.getName()%>" size="45"/></td>
	</tr>
	<tr>
		<td>身分證字號:</td>
		<td><input type="TEXT" name="idNum"   value="<%= (memberVO==null)? "" : memberVO.getIdNum()%>" size="45"/></td>
	</tr>
	<tr>
		<td>戶籍地址:</td>
		<td><input type="TEXT" name="permAddr"  value="<%= (memberVO==null)? "" : memberVO.getPermAddr()%>" size="45"/></td>
	</tr>
	
	<tr>
		<td>通訊地址:</td>
		<td><input type="TEXT" name="address"   value="<%= (memberVO==null)? "" : memberVO.getAddress()%>" size="45"/></td>
	</tr>
	
	<tr>
    <td style="visibility:hidden">註冊日期:</td>
    <td>
        <input name="regTime" id="f_date1" type="TEXT" 
            value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()) %>" 
            style="visibility:hidden;">
    </td>
</tr>
	


	<tr>
		<td>證明文件:</td>
		<td><input type="file" name="kycImage"  /></td>
	</tr>


	<tr>
		<td>信箱:</td>
		<td><input type="email" name="email"  value="<%= (memberVO==null)? "" : memberVO.getEmail()%>" size="45"/></td>
	</tr>
	<tr>
		<td>電話:</td>
		<td><input type="TEXT" name="phone"  value="<%= (memberVO==null)? "" : memberVO.getPhone()%>" size="45"/></td>
	</tr>
	<tr>
		<td>帳號:</td>
		<td><input type="TEXT" name="account"  value="<%= (memberVO==null)? "" : memberVO.getAccount()%>" size="45"/></td>
	</tr>
	<tr>
		<td>密碼:</td>
		<td><input type="password" name="password"  value="<%= (memberVO==null)? "" : memberVO.getPassword()%>" size="45"/></td>
	</tr>
	
	<tr>
		<td style="visibility:hidden">點數餘額:</td>
		<td><input style="visibility:hidden" type="TEXT" name="pointsBalance"  value="<%= (memberVO==null)? "0" : memberVO.getPointsBalance()%>" size="45"/></td>
	</tr>
	
	<tr>
		<td style="visibility:hidden">未領餐累計次數:</td>
		<td><input style="visibility:hidden" type="TEXT" name="unclaimedMealCount"  value="<%= (memberVO==null)? "0" : memberVO.getUnclaimedMealCount()%>" size="45"/></td>
	</tr>
	
	<tr>
		<td style="visibility:hidden">帳號狀態:</td>
		<td><input style="visibility:hidden" type="TEXT" name="accStat"  value="<%= (memberVO==null)? "0" : memberVO.getAccStat()%>" size="45"/></td>
				
	</tr>
	
	<tr>
		<td style="visibility:hidden">帳號審核狀態:</td>
		<td><input style="visibility:hidden" type="TEXT" name="reviewed"  value="<%= (memberVO==null)? "0" : memberVO.getReviewed()%>" size="45"/></td>
	</tr>

	<jsp:useBean id="organizationSvc" scope="page" class="com.organization.model.OrganizationService" />
	
	<tr>
		<td>在冊單位:<font color=red><b>*</b></font></td>
		<td><select size="1" name="organizationId">
			<c:forEach var="organizationVO" items="${organizationSvc.all}">
				<option value="${organizationVO.organizationId}" ${(memberVO.organizationId==organizationVO.organizationId)? 'selected':'' } >${organizationVO.name}
			</c:forEach>
		</select></td>
	</tr>
	
	
	
	
	

</table>


<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>

</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  java.sql.Timestamp regTime = null;
  try {
	  regTime = memberVO.getRegTime();
   } catch (Exception e) {
	   regTime = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: '<%=regTime%>',  // value:   new Timestamp(System.currentTimeMillis()),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
        //      var somedate1 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});

        
        //      2.以下為某一天之後的日期無法選擇
        //      var somedate2 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});


        //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
        
</script>
</html>