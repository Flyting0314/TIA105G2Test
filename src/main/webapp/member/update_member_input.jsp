<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>

<% //見com.member.controller.MemberServlet.java第163行存入req的memberVO物件 (此為從資料庫取出的memberVO, 也可以是輸入格式有錯誤時的memberVO物件)
MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>
--<%= memberVO==null %> --${memberVO.organizationId}--<!-- line 100 -->
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>受助者資料修改 - update_member_input.jsp</title>

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
		 <h3>受助者資料修改 - update_member_input.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
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

<FORM METHOD="post" ACTION="member.do" name="form1" enctype="multipart/form-data">
<table>
	<tr>
		<td>受助者編號:<font color=red><b>*</b></font></td>
		<td><%=memberVO.getMemberId()%></td>
	</tr>
	
	<tr>
		<td>姓名:</td>
		<td><input type="TEXT" name="name" value="<%=memberVO.getName()%>" size="45"/></td>
	</tr>
	
	<tr>
		<td>身分證字號:</td>
		<td><input type="TEXT" name="idNum" value="<%=memberVO.getIdNum()%>" size="45"/></td>
	</tr>
	
	<tr>
		<td>戶籍地址:</td>
		<td><input type="TEXT" name="permAddr"   value="<%=memberVO.getPermAddr()%>" size="45"/></td>
	</tr>
	
	<tr>
		<td>通訊地址:</td>
		<td><input type="TEXT" name="address"   value="<%=memberVO.getAddress()%>" size="45"/></td>
	</tr>
	
	<tr>
    <td style="visibility:hidden">註冊時間:</td>
    <td>
        <input type="hidden" name="regTime" value="${originalRegTime}" />
        ${originalRegTime}  <!-- 顯示原始註冊時間，避免修改 -->
    </td>
	</tr>
	
	<tr>
	    <td style="visibility:hidden">最後修改時間:</td>
	    <td>
	    <input type="hidden" name="regTime" value="${lastModifiedTime}" />
	    ${lastModifiedTime}
	    </td>  <!-- 這是資料庫更新後的時間 -->
	</tr>

	


	<tr>
    <td>證明文件:</td>
    <td>
        <input type="file" name="kycImage">
        <c:if test="${not empty memberVO.kycImage}">
            <br>
            <span>目前檔案: <a href="${pageContext.request.contextPath}/images_uploaded/${memberVO.kycImage}" target="_blank">${memberVO.kycImage}</a></span>
        </c:if>
    </td>
	</tr>
	<tr>
		<td>信箱:</td>
		<td><input type="email" name="email"  value="<%=memberVO.getEmail()%>" size="45"/></td>
	</tr>
	<tr>
		<td>電話:</td>
		<td><input type="TEXT" name="phone"  value="<%=memberVO.getPhone()%>" size="45"/></td>
	</tr>
	
	<tr>
		<td>帳號:</td>
		<td><input type="TEXT" name="account"  value="<%=memberVO.getAccount()%>" size="45"/></td>
	</tr>
	<tr>
		<td>密碼:</td>
		<td><input type="password" name="password"  value="<%=memberVO.getPassword()%>" size="45"/></td>
	</tr>
	<tr>
		<td>點數餘額:</td>
		<td><input type="TEXT" name="pointsBalance"  value="<%=memberVO.getPointsBalance()%>" size="45"/></td>
	</tr>
	<tr>
		<td>點數餘額:</td>
		<td><input type="TEXT" name="unclaimedMealCount"  value="<%=memberVO.getUnclaimedMealCount()%>" size="45"/></td>
	</tr>
	<tr>
		<td>帳號狀態:</td>
<%-- 		<td><input type="TEXT" name="accStat"  value="<%= (memberVO==null)? "0" : memberVO.getAccStat()%>" size="45"/></td> --%>
		<td>
		<input type="Radio" name="accStat"  value=0 checked="checked" size="45"/>停用
		<input type="Radio" name="accStat"  value=1 size="45"/>啟用
		</td>		
	</tr>
	<tr>
		<td>帳號審核狀態:</td>
<%-- 		<td><input type="TEXT" name="reviewed"  value="<%= (memberVO==null)? "0" : memberVO.getReviewed()%>" size="45"/></td> --%>
		<td>
		<input type="Radio" name="reviewed"  value=0 checked="checked" size="45"/>審核中
		<input type="Radio" name="reviewed"  value=1 size="45"/>已通過
		<input type="Radio" name="reviewed"  value=2 size="45"/>未通過
		<input type="Radio" name="reviewed"  value=3 size="45"/>未審核
		</td>
	</tr>

	<jsp:useBean id="organizationSvc" scope="page" class="com.organization.model.OrganizationService" />
	<tr>
		<td>在冊單位:<font color=red><b>*</b></font></td>
		<td><select size="1" name="organizationId">
			<c:forEach var="organizationVO" items="${organizationSvc.all}">
				<option value="${organizationVO.organizationId}" ${(memberVO.organizationId==organizationIdVO.organizationId)?'selected':'' } >${organizationVO.name}
			</c:forEach>
		</select></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="memberId" value="<%=memberVO.getMemberId()%>">
<input type="submit" value="送出修改"></FORM>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

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
 		   
 	       value:new Timestamp(System.currentTimeMillis()),//value:'<%=memberVO.getRegTime()%>',
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
//            maxDate:               '+1970-01-01'  // 去除今日(不含)之後
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