<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.payRecord.model.*"%>

<!-- 這頁把 listAll 和 select page 結合 -->

<!-- 使用bean取代scriptlet -->
<jsp:useBean id="payRecordSvc" scope="page" class="com.payRecord.model.PayRecordService" />
<%
    List<PayRecordVO> list = payRecordSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
    <!-- ///// 引入外部前端工具 ///// -->
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

    <title>受助者點數發放</title>

    <!-- ////////以下css接尚未調整！！//////// -->
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
    </style>

    <style>
        table {
            width: 800px;
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
    <!-- ////////以上css接尚未調整！！//////// -->
</head>


<body>
    <div class="container py-4">
        <!-- /////// 以下為查詢功能 //////// -->
        <!-- 錯誤訊息顯示-->
        <c:if test="${not empty errorMsgs}">
            <div class="alert alert-danger">
                <strong>請修正以下錯誤:</strong>
                <ul>
                    <c:forEach var="message" items="${errorMsgs}">
                        <li>${message}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
        
        <div class="d-flex flex-wrap align-items-center gap-4 my-3">
            <!-- 輸入發放ID -->
            <FORM METHOD="post" ACTION="payRecord.do" class="d-flex flex-column align-items-start">
                <b>以點數發放ID查詢</b>
                <div class="d-flex mt-2">
                    <input type="text" name="payoutId" class="form-control" placeholder="如1234">
                    <input type="submit" value="送出" class="btn btn-primary ms-2">
                    <input type="hidden" name="action" value="getOne_For_Display">
                </div>
            </FORM>

            <!-- 選擇點數發放ID -->
            <FORM METHOD="post" ACTION="payRecord.do" class="d-flex flex-column align-items-start">
                <b>選擇點數發放ID</b>
                <div class="d-flex align-items-center mt-2">
                    <select class="form-select me-2" name="payoutId">
                        <c:forEach var="payRecordVO" items="${payRecordSvc.all}">
                            <option value="${payRecordVO.payoutId}">${payRecordVO.payoutId}
                        </c:forEach>
                    </select>
                    <input type="hidden" name="action" value="getOne_For_Display">
                    <input type="submit" value="送出" class="btn btn-primary">
                </div>
            </FORM>
        </div>
        
        <div class="d-flex flex-wrap align-items-center gap-4 my-3">
   
    
	    <!-- 依發放狀態查詢 -->
	    <FORM METHOD="post" ACTION="payRecord.do" class="d-flex flex-column align-items-start">
	        <b>依發放狀態查詢</b>
	        <div class="d-flex align-items-center mt-2">
	            <select class="form-select me-2" name="status">
	                <option value="1">已發放</option>
	                <option value="0">未發放</option>
	            </select>
	            <input type="hidden" name="action" value="listByStatus">
	            <input type="submit" value="送出" class="btn btn-primary">
	        </div>
	    </FORM>
	    
	    <!-- 依月份查詢 -->
	    <FORM METHOD="post" ACTION="payRecord.do" class="d-flex flex-column align-items-start">
	        <b>依月份查詢</b>
	        <div class="d-flex align-items-center mt-2">
	            <input type="month" name="queryMonth" class="form-control me-2">
	            <input type="hidden" name="action" value="listByMonth">
	            <input type="submit" value="送出" class="btn btn-primary">
	        </div>
	    </FORM>
	</div>







        <!--  //////以下為 ListAll 內容，套用 Bootstrap 樣式 //////-->
        <div class="card mt-4">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">點數發放紀錄</h4>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>點數發放ID</th>
                                <th>發放總點數</th>
                                <th>發放時間</th>
                                <th>發放狀態</th>
                                <th>修改</th>
                                <th>刪除</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- page1 記得要寫自己的版本！ -->
                            <%@ include file="page1.file" %> 
                            <c:forEach var="payRecordVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                                <tr>
                                    <td>${payRecordVO.payoutId}</td>
                                    <td>${payRecordVO.totalPoint}</td>
                                    <td>${payRecordVO.payoutDate}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${payRecordVO.status == 1}">
                                                <span class="badge bg-success">有效</span>
                                            </c:when>
                                            <c:when test="${payRecordVO.status == 0}">
                                                <span class="badge bg-danger">無效</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">${payRecordVO.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/payRecord/payRecord.do">
                                            <input type="submit" value="修改" class="btn btn-warning btn-sm">
                                            <input type="hidden" name="payoutId" value="${payRecordVO.payoutId}">
                                            <input type="hidden" name="action" value="getOne_For_Update">
                                        </FORM>
                                    </td>
                                    <td>
                                        <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/payRecord/payRecord.do">
                                            <input type="submit" value="刪除" class="btn btn-danger btn-sm" onclick="return confirm('確定要刪除這筆記錄?');">
                                            <input type="hidden" name="payoutId" value="${payRecordVO.payoutId}">
                                            <input type="hidden" name="action" value="delete">
                                        </FORM>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- 記得寫 page 2 ! -->
                <div class="mt-3">
                    <%@ include file="page2.file" %>
                </div>
            </div>
        </div>
        <!--  //////以上為 ListAll 內容 //////-->
    </div>
    
    
    
    <!-- ///// 新增功能 ////// -->
        <div class="text-center mt-4">
        <a href="<%=request.getContextPath()%>/payRecord/addPayRecord.jsp" class="btn btn-success">
            <i class="bi bi-plus-circle me-1"></i>新增一筆發放紀錄
        </a>
    </div>
</body>
</html>