package com.payRecord.controller;

public class payRecordServlet {

	
	
	
	
	
	
	
	
	
	 //==========這裡是以發放狀態和以月份搜尋的功能的action============
	
//	if ("listByStatus".equals(action)) {
//	    List<String> errorMsgs = new LinkedList<String>();
//	    req.setAttribute("errorMsgs", errorMsgs);
//
//	    try {
//	        Integer status = Integer.valueOf(req.getParameter("status"));
//	        
//	        PayRecordService payRecordSvc = new PayRecordService();
//	        List<PayRecordVO> list = payRecordSvc.getPayRecordsByStatus(status);
//	        
//	        if (list.isEmpty()) {
//	            errorMsgs.add("查無此狀態的資料");
//	        }
//	        
//	        if (!errorMsgs.isEmpty()) {
//	            RequestDispatcher failureView = req.getRequestDispatcher("/payRecord/listAllPayRecord.jsp");
//	            failureView.forward(req, res);
//	            return;
//	        }
//	        
//	        req.setAttribute("listByStatus", list); // 存入request
//	        RequestDispatcher successView = req.getRequestDispatcher("/payRecord/listPayRecordByStatus.jsp");
//	        successView.forward(req, res);
//	        
//	    } catch (Exception e) {
//	        errorMsgs.add("無法取得資料: " + e.getMessage());
//	        RequestDispatcher failureView = req.getRequestDispatcher("/payRecord/listAllPayRecord.jsp");
//	        failureView.forward(req, res);
//	    }
//	}
//
//	if ("listByMonth".equals(action)) {
//	    List<String> errorMsgs = new LinkedList<String>();
//	    req.setAttribute("errorMsgs", errorMsgs);
//
//	    try {
//	        String queryMonth = req.getParameter("queryMonth");
//	        
//	        if (queryMonth == null || queryMonth.trim().length() == 0) {
//	            errorMsgs.add("請選擇查詢月份");
//	        }
//	        
//	        if (!errorMsgs.isEmpty()) {
//	            RequestDispatcher failureView = req.getRequestDispatcher("/payRecord/listAllPayRecord.jsp");
//	            failureView.forward(req, res);
//	            return;
//	        }
//	        
//	        PayRecordService payRecordSvc = new PayRecordService();
//	        List<PayRecordVO> list = payRecordSvc.getPayRecordsByMonth(queryMonth);
//	        
//	        if (list.isEmpty()) {
//	            errorMsgs.add("查無此月份的資料");
//	        }
//	        
//	        if (!errorMsgs.isEmpty()) {
//	            RequestDispatcher failureView = req.getRequestDispatcher("/payRecord/listAllPayRecord.jsp");
//	            failureView.forward(req, res);
//	            return;
//	        }
//	        
//	        req.setAttribute("listByMonth", list); // 存入request
//	        RequestDispatcher successView = req.getRequestDispatcher("/payRecord/listPayRecordByMonth.jsp");
//	        successView.forward(req, res);
//	        
//	    } catch (Exception e) {
//	        errorMsgs.add("無法取得資料: " + e.getMessage());
//	        RequestDispatcher failureView = req.getRequestDispatcher("/payRecord/listAllPayRecord.jsp");
//	        failureView.forward(req, res);
//	    }
//	}
}
