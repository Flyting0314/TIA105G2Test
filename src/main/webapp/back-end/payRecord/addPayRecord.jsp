<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.payRecord.model.*"%>

<%
   // 取得可能的錯誤資料
   PayRecordVO payRecordVO = (PayRecordVO) request.getAttribute("payRecordVO");
%>

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>點數發放資料新增</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <style>
        .xdsoft_datetimepicker .xdsoft_datepicker {
            width:  300px;
        }
        .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
            height: 151px;
        }
    </style>
</head>

<body>
    <div class="container py-4">
        <!-- 頁頭 -->
        <div class="card mb-4">
            <div class="card-header bg-success text-white">
                <h3 class="mb-0">新增點數發放資料</h3>
            </div>
            <div class="card-body">
                <h4>
                    <a href="<%=request.getContextPath()%>/payRecord/listAllPayRecord.jsp" class="btn btn-secondary">
                        <i class="bi bi-arrow-left"></i> 返回點數發放管理
                    </a>
                </h4>
            </div>
        </div>
        
        <!-- 錯誤訊息顯示 -->
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

        <!-- 新增表單 -->
        <div class="card">
            <div class="card-body">
                <form method="post" action="<%=request.getContextPath()%>/payRecord/payRecord.do" name="form1">
                    <div class="row mb-3">
                        <label for="totalPoint" class="col-sm-2 col-form-label">發放總點數:</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" id="totalPoint" name="totalPoint" 
                                   value="<%= (payRecordVO==null)? "100" : payRecordVO.getTotalPoint()%>" min="0" required>
                        </div>
                    </div>
                    
                    <div class="row mb-3">
                        <label for="payoutDate" class="col-sm-2 col-form-label">發放時間:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="f_date1" name="payoutDate">
                        </div>
                    </div>
                    
                    <div class="row mb-3">
                        <label for="status" class="col-sm-2 col-form-label">發放狀態:</label>
                        <div class="col-sm-10">
                            <select class="form-select" id="status" name="status">
                                <option value="1" <%= (payRecordVO!=null && payRecordVO.getStatus() == 1) ? "selected" : ""%>>已發放</option>
                                <option value="0" <%= (payRecordVO!=null && payRecordVO.getStatus() == 0) ? "selected" : ""%>>未發放</option>
                            </select>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-12 text-center">
                            <input type="hidden" name="action" value="insert">
                            <input type="submit" class="btn btn-primary" value="確認新增">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- 日期時間選擇器設定 -->
    <!-- 設定有點奇怪要重寫！！ -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

    <script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
            theme: '',
            timepicker: true,
            step: 1,
            format: 'Y-m-d H:i:s',
            value: new Date(),
        });
    </script>
</body>
</html>