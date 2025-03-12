package com.orderfood.controller;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.sql.Timestamp;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.orderfood.model.*;
import com.store.model.*;
import com.member.model.*;
import com.orderdetail.model.OrderDetailVO;



@WebServlet("/OrderFood")
public class OrderFoodServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 首頁傳遞的資訊等於"getOne_For_Display"時十
			
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();// 錯誤存放
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("orderId");

			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.put("orderId", "請輸入訂單編號");
			}

			// 錯誤處理-輸入為空
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/orderfood/select_page_for_orderfood.jsp");
				failureView.forward(req, res);
				return;// 程式中斷

			}

			// 搜尋索引轉型以及錯誤型別處理。
			Integer orderId = null;
			try {
				orderId = Integer.valueOf(str);

			} catch (Exception e) {
				errorMsgs.put("orderId", "訂單編號不正確");
			}

			// 錯誤處理-型別錯誤
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/orderfood/select_page_for_orderfood.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			OrderFoodService ofSvc = new OrderFoodService();
			OrderFoodVO ofVO = ofSvc.getOneOF(orderId);// 取得查詢物件
			if (ofVO == null) {
				errorMsgs.put("orderId", "查無資料");
			}
			// 錯誤處理--查無資料
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/orderfood/select_page_for_orderfood.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("ofVO", ofVO); // 資料庫取出的odVO物件,存入req
			String url = "/back-end/orderfood/listOneOrderFood.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 轉交至listAllOrderDetail.jsp頁面
			successView.forward(req, res);

		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer orderId = Integer.valueOf(req.getParameter("orderId"));
			/*************************** 2.開始查詢資料 ****************************************/
			OrderFoodService ofSvc = new OrderFoodService();
			OrderFoodVO ofVO = ofSvc.getOneOF(orderId);
			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("ofVO", ofVO); // 資料庫取出的empVO物件,存入req
			String url = "/front-end/orderfood/update_OrderFood_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
		}

		
		

		
		
		
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer orderId = Integer.valueOf(req.getParameter("orderId").trim());
			
			String storeIdSt = req.getParameter("storeId");
			Integer storeId = null;
			if (storeIdSt == null || storeIdSt.trim().isEmpty()) {
			    errorMsgs.put("storeId", "店家編號請勿空白"); // 修正這裡
			} else {
			    try {
			        storeId = Integer.parseInt(storeIdSt.trim());
			        
			        if (storeId < 0) {
			            errorMsgs.put("storeId", "請輸入正確的店家編號");
			        }
			    } catch (NumberFormatException e) {
			        errorMsgs.put("storeId", "請輸入正確的數字");
			    }
			}

			String memberIdSt = req.getParameter("memberId");
			Integer memberId = null;
			if (memberIdSt == null || memberIdSt.trim().isEmpty()) {
			    errorMsgs.put("memberId", "受助人編號請勿空白");
			} else {
			    try {
			        memberId = Integer.parseInt(memberIdSt.trim());
			        if (memberId < 0) {
			            errorMsgs.put("memberId", "請輸入正確的受助人編號");
			        }
			    } catch (NumberFormatException e) {
			        errorMsgs.put("memberId", "請輸入正確的數字");
			    }
			}

			String rateSt = req.getParameter("rate");
			Integer rate = null;
			if (rateSt == null || rateSt.trim().isEmpty()) {
			    errorMsgs.put("rate", "評分請勿空白");
			} else {
			    try {
			        rate = Integer.parseInt(rateSt.trim());
			        if (rate < 0) {
			            errorMsgs.put("rate", "請輸入正確的評分");
			        }
			    } catch (NumberFormatException e) {
			        errorMsgs.put("rate", "請輸入正確的數字");
			    }
			}

			String comment = req.getParameter("comment");
			if (comment == null || comment.isEmpty()) {
			    errorMsgs.put("comment", "請輸入備註");
			}

			String serveStatSt = req.getParameter("serveStat");
			Integer serveStat = null;
			if (serveStatSt == null || serveStatSt.trim().isEmpty()) {
			    errorMsgs.put("serveStat", "訂單狀態請勿空白");
			} else {
			    try {
			        serveStat = Integer.parseInt(serveStatSt.trim());
			        if (serveStat < 0 || serveStat > 1) { // 修正這裡
			            errorMsgs.put("serveStat", "請輸入正確狀態");
			        }
			    } catch (NumberFormatException e) {
			        errorMsgs.put("serveStat", "請輸入正確的數字");
			    }
			}

			String pickStatSt = req.getParameter("pickStat");
			Integer pickStat = null;
			if (pickStatSt == null || pickStatSt.trim().isEmpty()) {
			    errorMsgs.put("pickStat", "取餐狀態請勿空白");
			} else {
			    try {
			        pickStat = Integer.parseInt(pickStatSt.trim());
			        if (pickStat < 0 || pickStat > 1) { 
			            errorMsgs.put("pickStat", "請輸入正確狀態");
			        }
			    } catch (NumberFormatException e) {
			        errorMsgs.put("pickStat", "請輸入正確的數字");
			    }
			}

			String pickTimeSt = req.getParameter("pickTime");
			Timestamp pickTime = null;
			if (pickTimeSt == null || pickTimeSt.trim().isEmpty()) {
			    errorMsgs.put("pickTime", "請輸入日期"); 
			} else {
			    try {
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			        Date utilDate = sdf.parse(pickTimeSt.trim());
			        pickTime = new Timestamp(utilDate.getTime());
			    } catch (Exception e) {
			        errorMsgs.put("pickTime", "日期格式不正確，請使用正確的格式 (yyyy-MM-dd'T'HH:mm)"); // 修正這裡
			    }
			}

			String createdTimeSt = req.getParameter("createdTime");
			Timestamp createdTime = null;
			if (createdTimeSt == null || createdTimeSt.trim().isEmpty()) {
			    errorMsgs.put("createdTime", "請輸入日期"); // 修正這裡
			} else {
			    try {
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			        Date utilDate = sdf.parse(createdTimeSt.trim());
			        createdTime = new Timestamp(utilDate.getTime());
			    } catch (Exception e) {
			        errorMsgs.put("createdTime", "日期格式不正確，請使用正確的格式 (yyyy-MM-dd'T'HH:mm)"); // 修正這裡
			    }
			}
			
			OrderFoodService ofSvc = new OrderFoodService();
			List<StoreVO> stVOTest = ofSvc.getAllFromStore() ;
			List<MemberVO> memVOTest = ofSvc.getAllFromMember() ;
			Set<Integer> stpk = new HashSet<Integer>();
			Set<Integer> mempk = new HashSet<Integer>();
			
			for(StoreVO it : stVOTest) {
				stpk.add(it.getStoreId());
			}
			for(MemberVO it : memVOTest) {
				mempk.add(it.getMemberId());
			}

			if(!stpk.contains(storeId)) {
				errorMsgs.put("storeId ", "請確認是否存在於父表。");

			}
			if(!mempk.contains(memberId)) {
				errorMsgs.put("memberId ", "請確認是否存在於父表。");
			}
			OrderFoodVO ofVO = new OrderFoodVO();
			ofVO.setStoreId(storeId);
			ofVO.setMemberId(memberId);
			ofVO.setRate(rate);
			ofVO.setComment(comment);
			ofVO.setServeStat(serveStat);
			ofVO.setPickStat(pickStat);
			ofVO.setPickTime(pickTime);
			ofVO.setCreatedTime(createdTime);
		
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("ofVO", ofVO);
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/orderfood/addOrderFood.jsp");
				failureView.forward(req, res);
			}
			
			
			/*************************** 2.開始修改資料 *****************************************/
//			OrderFoodService ofSvc = new OrderFoodService();
			ofVO = ofSvc.updateOF(orderId,storeId, memberId, rate,comment, serveStat,
					pickStat,  pickTime, createdTime);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("ofVO", ofVO); // 資料庫update成功後,正確的odVO物件,存入req
			String url = "/back-end/orderfood/listOneOrderFood.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}

		
		
		
		
		
		
		if ("insert".equals(action)) { // 來自addOrderFood.jsp的請求
		
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String storeIdSt = req.getParameter("storeId");
			Integer storeId = null;
			if (storeIdSt == null || storeIdSt.trim().isEmpty()) {
			    errorMsgs.put("storeId", "店家編號請勿空白"); 
			} else {
			    try {
			        storeId = Integer.parseInt(storeIdSt.trim());
			        if (storeId < 0) {
			            errorMsgs.put("storeId", "請輸入正確的店家編號");
			        }
			    } catch (NumberFormatException e) {
			        errorMsgs.put("storeId", "請輸入正確的數字");
			    }
			}

			String memberIdSt = req.getParameter("memberId");
			Integer memberId = null;
			if (memberIdSt == null || memberIdSt.trim().isEmpty()) {
			    errorMsgs.put("memberId", "受助人編號請勿空白");
			} else {
			    try {
			        memberId = Integer.parseInt(memberIdSt.trim());
			        if (memberId < 0) {
			            errorMsgs.put("memberId", "請輸入正確的受助人編號");
			        }
			    } catch (NumberFormatException e) {
			        errorMsgs.put("memberId", "請輸入正確的數字");
			    }
			}

			String rateSt = req.getParameter("rate");
			Integer rate = null;
			if (rateSt == null || rateSt.trim().isEmpty()) {
			    errorMsgs.put("rate", "評分請勿空白");
			} else {
			    try {
			        rate = Integer.parseInt(rateSt.trim());
			        if (rate < 0) {
			            errorMsgs.put("rate", "請輸入正確的評分");
			        }
			    } catch (NumberFormatException e) {
			        errorMsgs.put("rate", "請輸入正確的數字");
			    }
			}

			String comment = req.getParameter("comment");
			if (comment == null || comment.isEmpty()) {
			    errorMsgs.put("comment", "請輸入備註");
			}

			String serveStatSt = req.getParameter("serveStat");
			Integer serveStat = null;
			if (serveStatSt == null || serveStatSt.trim().isEmpty()) {
			    errorMsgs.put("serveStat", "訂單狀態請勿空白");
			} else {
			    try {
			        serveStat = Integer.parseInt(serveStatSt.trim());
			        if (serveStat < 0 || serveStat > 1) { // 修正這裡
			            errorMsgs.put("serveStat", "請輸入正確狀態");
			        }
			    } catch (NumberFormatException e) {
			        errorMsgs.put("serveStat", "請輸入正確的數字");
			    }
			}

			String pickStatSt = req.getParameter("pickStat");
			Integer pickStat = null;
			if (pickStatSt == null || pickStatSt.trim().isEmpty()) {
			    errorMsgs.put("pickStat", "取餐狀態請勿空白");
			} else {
			    try {
			        pickStat = Integer.parseInt(pickStatSt.trim());
			        if (pickStat < 0 || pickStat > 1) { // 修正這裡
			            errorMsgs.put("pickStat", "請輸入正確狀態");
			        }
			    } catch (NumberFormatException e) {
			        errorMsgs.put("pickStat", "請輸入正確的數字");
			    }
			}

			String pickTimeSt = req.getParameter("pickTime");
			Timestamp pickTime = null;
			if (pickTimeSt == null || pickTimeSt.trim().isEmpty()) {
			    errorMsgs.put("pickTime", "請輸入日期"); // 修正這裡
			} else {
			    try {
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			        Date utilDate = sdf.parse(pickTimeSt.trim());
			        pickTime = new Timestamp(utilDate.getTime());
			    } catch (Exception e) {
			        errorMsgs.put("pickTime", "日期格式不正確，請使用正確的格式 (yyyy-MM-dd'T'HH:mm)"); // 修正這裡
			    }
			}

			String createdTimeSt = req.getParameter("createdTime");
			Timestamp createdTime = null;
			if (createdTimeSt == null || createdTimeSt.trim().isEmpty()) {
			    errorMsgs.put("createdTime", "請輸入日期");
			} else {
			    try {
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			        Date utilDate = sdf.parse(createdTimeSt.trim());
			        createdTime = new Timestamp(utilDate.getTime());
			    } catch (Exception e) {
			        errorMsgs.put("createdTime", "日期格式不正確，請使用正確的格式 (yyyy-MM-dd'T'HH:mm)"); // 修正這裡
			    }
			}
			
			//查詢父表，確認FK存在
			OrderFoodService ofSvc = new OrderFoodService();
			List<StoreVO> stVOTest = ofSvc.getAllFromStore() ;
			List<MemberVO> memVOTest = ofSvc.getAllFromMember() ;
			Set<Integer> stpk = new HashSet<Integer>();
			Set<Integer> mempk = new HashSet<Integer>();
			
			for(StoreVO it : stVOTest) {
				stpk.add(it.getStoreId());
			}
			for(MemberVO it : memVOTest) {
				mempk.add(it.getMemberId());
			}

			if(!stpk.contains(storeId)){
				errorMsgs.put("storeId ", "請確認是否存在於父表。");
			}
			if(!mempk.contains(memberId)) {
				errorMsgs.put("memberId ", "請確認是否存在於父表。");
			}
			

			OrderFoodVO ofVO = new OrderFoodVO();
			ofVO.setStoreId(storeId);
			ofVO.setMemberId(memberId);
			ofVO.setRate(rate);
			ofVO.setComment(comment);
			ofVO.setServeStat(serveStat);
			ofVO.setPickStat(pickStat);
			ofVO.setPickTime(pickTime);
			ofVO.setCreatedTime(createdTime);
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				System.out.println();
				req.setAttribute("ofVO", ofVO);
				System.out.println(req.getAttribute("ofVO")+"ahahaha");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/orderfood/addOrderFood.jsp");
				failureView.forward(req, res);
				System.out.println(req.getAttribute("ofVO")+"bababa");
				return; // 程式中斷
			}

			/*************************** 2.開始新增資料 ***************************************/
//			OrderFoodService ofSvc = new OrderFoodService();
			ofVO = ofSvc.addOF(storeId, memberId, rate,comment, serveStat,
					pickStat,  pickTime, createdTime);
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			req.setAttribute("ofVO", ofVO); // 資料庫update成功後,正確的odVO物件,存入req
			String url = "/back-end/orderfood/listAllOrderFood.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneOrderFood.jsp
			successView.forward(req, res);
		}


		if ("delete".equals(action)) {
		
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer orderId = Integer.valueOf(req.getParameter("orderId"));
			Integer storeId = Integer.valueOf(req.getParameter("storeId"));
			Integer memberId = Integer.valueOf(req.getParameter("memberId"));
			Integer rate = Integer.valueOf(req.getParameter("rate"));
			String comment = req.getParameter("comment");
			Integer serveStat = Integer.valueOf(req.getParameter("serveStat"));
			Integer pickStat = Integer.valueOf(req.getParameter("pickStat"));
			
			String pickTimeSt = req.getParameter("pickTime");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date utilDate;
			Timestamp  pickTime = null;
			try {
				utilDate = sdf.parse(pickTimeSt.trim());
				pickTime = new Timestamp(utilDate.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String createdTimeSt = req.getParameter("createdTime");
	
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date utilDate1;
			Timestamp createdTime= null;
			try {
				utilDate1 = sdf1.parse(createdTimeSt.trim());
				createdTime = new Timestamp(utilDate1.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
//			//查詢父表有無關聯
//			
//			
			OrderFoodService ofSvc = new OrderFoodService();
			List<OrderDetailVO> stVOTest = ofSvc.getAllOD() ;
			Set<Integer> odpk = new HashSet<Integer>();
			
			for(OrderDetailVO it : stVOTest) {
				odpk.add(it.getOrderId());
			}

			if(odpk.contains(orderId)) {
				errorMsgs.put("orderId ", "子表存在相關資訊,請先刪除子表相關資訊。");

			}
//			
			
			OrderFoodVO ofVO = new OrderFoodVO();
			ofVO.setStoreId(storeId);
			ofVO.setMemberId(memberId);
			ofVO.setRate(rate);
			ofVO.setComment(comment);
			ofVO.setServeStat(serveStat);
			ofVO.setPickStat(pickStat);
			ofVO.setPickTime(pickTime);
			ofVO.setCreatedTime(createdTime);
		
			
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("ofVO", ofVO);
				RequestDispatcher failureView = req
						.getRequestDispatcher("back-end/orderfood/listAllOrderFood.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
			


			/*************************** 2.開始刪除資料 ***************************************/
//			OrderFoodService ofSvc = new OrderFoodService();
			ofSvc.deleteOF(orderId);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/back-end/orderfood/listAllOrderFood.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}
}
