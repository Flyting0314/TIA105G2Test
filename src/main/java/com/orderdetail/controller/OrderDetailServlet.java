package com.orderdetail.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import java.sql.Timestamp;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.food.model.*;
import com.orderdetail.model.OrderDetailService;
import com.orderdetail.model.OrderDetailVO;
import com.orderfood.model.*;



@WebServlet("/OrderDetail")
public class OrderDetailServlet extends HttpServlet {

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
			String str1 = req.getParameter("foodId");

			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.put("orderId", "請輸入訂單編號");
			}
			if (str1 == null || (str1.trim()).length() == 0) {
				errorMsgs.put("foodId", "請輸入餐點編號");
			}

			// 錯誤處理-輸入為空
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/orderdetails/select_page_for_recepit_details.jsp");
				failureView.forward(req, res);
				return;// 程式中斷

			}

			// 搜尋索引轉型以及錯誤型別處理。
			Integer orderId = null, foodId = null;
			try {
				orderId = Integer.valueOf(str);

			} catch (Exception e) {
				errorMsgs.put("orderId", "訂單編號不正確");
			}

			try {
				foodId = Integer.valueOf(str1);

			} catch (Exception e) {
				errorMsgs.put("foodId", "餐點編號不正確");
			}

			// 錯誤處理-型別錯誤
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/orderdetails/select_page_for_recepit_details.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			OrderDetailService odSvc = new OrderDetailService();
			OrderDetailVO odVO = odSvc.getOneOD(orderId, foodId);// 取得查詢物件
			if (odVO == null) {
				errorMsgs.put("orderId", "查無資料");
				errorMsgs.put("foodId", "查無資料");
			}
			// 錯誤處理--查無資料
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/orderdetails/select_page_for_recepit_details.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("odVO", odVO); // 資料庫取出的odVO物件,存入req
			String url = "/front-end/orderdetails/listOneOrderDetail.jsp";
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
			Integer foodId = Integer.valueOf(req.getParameter("foodId"));
			/*************************** 2.開始查詢資料 ****************************************/
			OrderDetailService odSvc = new OrderDetailService();
			OrderDetailVO odVO = odSvc.getOneOD(orderId, foodId);
			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("odVO", odVO); // 資料庫取出的empVO物件,存入req
			String url = "/back-end/orderdetails/update_OrderDetail_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer orderId = Integer.valueOf(req.getParameter("orderId").trim());
			Integer foodId = Integer.valueOf(req.getParameter("foodId").trim());

			/** 創建時間例外處理 **/
			String createdTimeSt = req.getParameter("createdTime");
			Timestamp createdTime = null;
			// 確認回傳的時間格式字串是不是空值或null
			if (createdTimeSt == null || createdTimeSt.trim().isEmpty()) {
				errorMsgs.put("Created time", "請輸入日期");
			} else {
				try {// 處理字串格式轉成java.util.Date格式並利用getTime()以及java.sql.Date變成正確型別
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
					Date utilDate = sdf.parse(createdTimeSt.trim());
					createdTime = new Timestamp(utilDate.getTime());
				} catch (Exception e) {
					errorMsgs.put("Created time", "日期格式不正確，請使用正確的格式 (yyyy-MM-dd'T'HH:mm)");
				}
			}

			/** 餐點數目例外處理 **/
			String amountSt = req.getParameter("amount");
			Integer amount = null;
			// 確認餐點字串非空
			if (amountSt == null || amountSt.trim().isEmpty()) {
				errorMsgs.put("amount", "餐點數量請勿空白");
			} else {
				try {
					amount = Integer.parseInt(amountSt.trim());
					if (amount < 0) {
						errorMsgs.put("amount", "餐點份數不能小於0");
					}
				} catch (NumberFormatException e) {
					errorMsgs.put("amount", "請輸入正確的數字");
				}
			}

			/** 使用點數例外處理 **/
			String pointsCostSt = req.getParameter("pointsCost");
			Integer pointsCost = null;
			// 確認餐點字串非空
			if (pointsCostSt == null || pointsCostSt.trim().isEmpty()) {
				errorMsgs.put("Points Cost", "使用點數請勿空白");
			} else {
				try {
					pointsCost = Integer.parseInt(pointsCostSt.trim());

					if (pointsCost < 0) {
						errorMsgs.put("Points Cost", "使用點數不能為負值");
					}
				} catch (NumberFormatException e) {
					errorMsgs.put("Points Cost", "請輸入正確的數字");
				}
			}

			String note = req.getParameter("note");
			if (note == null || note.isEmpty()) {
				errorMsgs.put("note", "請輸入備註");
			}

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/orderdetails/update_OrderDetail_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷

			}

			/*************************** 2.開始修改資料 *****************************************/
			OrderDetailService odpSvc = new OrderDetailService();
			OrderDetailVO odVO = odpSvc.updateOD(createdTime, amount, pointsCost, note, orderId, foodId);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("odVO", odVO); // 資料庫update成功後,正確的odVO物件,存入req
			String url = "/front-end/orderdetails/listOneOrderDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("insert".equals(action)) { // 來自addOrderDetail.jsp的請求
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

			String orderIdSt = req.getParameter("orderId");
			Integer orderId = null;
			if (orderIdSt == null || orderIdSt.trim().isEmpty()) {
				errorMsgs.put("orderId", "訂單編號請勿空白");
			} else {
				try {
					orderId = Integer.parseInt(orderIdSt.trim());
					if (orderId < 0) {
						errorMsgs.put("orderId", "請輸入正確的訂單編號");
					}
				} catch (NumberFormatException e) {
					errorMsgs.put("orderId", "請輸入正確的數字");
				}
			}

			String foodIdSt = req.getParameter("foodId");
			Integer foodId = null;
			if (foodIdSt == null || foodIdSt.trim().isEmpty()) {
				errorMsgs.put("foodId", "餐點請勿空白");
			} else {
				try {
					foodId = Integer.parseInt(foodIdSt.trim());
					if (foodId < 0) {
						errorMsgs.put("foodId", "請輸入正確的餐點編號");
					}
				} catch (NumberFormatException e) {
					errorMsgs.put("foodId", "請輸入正確的數字");
				}
			}

			/** 創建時間例外處理 **/
			String createdTimeSt = req.getParameter("createdTime");
			Timestamp createdTime = null;
			// 確認回傳的時間格式字串是不是空值或null
			if (createdTimeSt == null || createdTimeSt.trim().isEmpty()) {
				errorMsgs.put("Created time", "請輸入日期");
			} else {
				try {// 處理字串格式轉成java.util.Date格式並利用getTime()以及java.sql.Date變成正確型別
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
					Date utilDate = sdf.parse(createdTimeSt.trim());
					createdTime = new Timestamp(utilDate.getTime());
				} catch (Exception e) {
					errorMsgs.put("Created time", "日期格式不正確，請使用正確的格式 (yyyy-MM-dd'T'HH:mm)");
				}
			}

			/** 餐點數目例外處理 **/
			String amountSt = req.getParameter("amount");
			Integer amount = null;
			// 確認餐點字串非空
			if (amountSt == null || amountSt.trim().isEmpty()) {
				errorMsgs.put("amount", "餐點數量請勿空白");
			} else {
				try {
					amount = Integer.parseInt(amountSt.trim());
					if (amount < 0) {
						errorMsgs.put("amount", "餐點份數不能小於0");
					}
				} catch (NumberFormatException e) {
					errorMsgs.put("amount", "請輸入正確的數字");
				}
			}

			/** 使用點數例外處理 **/
			String pointsCostSt = req.getParameter("pointsCost");
			Integer pointsCost = null;
			// 確認餐點字串非空
			if (pointsCostSt == null || pointsCostSt.trim().isEmpty()) {
				errorMsgs.put("Points Cost", "使用點數請勿空白");
			} else {
				try {
					pointsCost = Integer.parseInt(pointsCostSt.trim());

					if (pointsCost < 0) {
						errorMsgs.put("Points Cost", "使用點數不能為負值");
					}
				} catch (NumberFormatException e) {
					errorMsgs.put("Points Cost", "請輸入正確的數字");
				}
			}

			String note = req.getParameter("note");
			if (note == null || note.isEmpty()) {
				errorMsgs.put("note", "請輸入備註");
			}

			// 檢查新增的資料是否與舊的資料有一樣的組合
			OrderDetailService odpSvcTest = new OrderDetailService();
			List<OrderDetailVO> odVOTest = odpSvcTest.getAll();
			
			for (OrderDetailVO it : odVOTest) {
				try {
					if (orderId.equals(it.getOrderId()) && foodId.equals(it.getFoodId())) {
						errorMsgs.put("orderId與foodId", "組合已經存在，無法新增。");
					}
				}catch(Exception e) {
					if(errorMsgs.size()==0) {
						errorMsgs.put("orderId與foodId", "請輸入正確的資料。");
					}
					
				}
			}
			
			//檢查orderId與foodId是否存在其他兩張表中
			OrderFoodJDBCDAO temp1 = new OrderFoodJDBCDAO();
			FoodJDBCDAO temp2 = new FoodJDBCDAO();
			List<OrderFoodVO> ofVOTest =temp1.getAll();
			List<FoodVO> fdVOTest = temp2.getAll();
			Set<Integer> pk1 = new HashSet<Integer>();
			Set<Integer> pk2 = new HashSet<Integer>();
			
			//取得OrderId
			for(OrderFoodVO it : ofVOTest) {
				pk1.add(it.getStoreId());
			}
			for(FoodVO it : fdVOTest){
				pk2.add(it.getFoodId());
			}
			if(!pk1.contains(orderId) && !pk2.contains(foodId)) {
				errorMsgs.put("orderId 與 foodId", "都不存在表中無法新增。");
			}
			if(!pk1.contains(orderId) && errorMsgs.size()==0) {
				errorMsgs.put("orderId", "不存在order表中，無法新增。");
			}
			if(!pk2.contains(foodId) && errorMsgs.size()==0) {
				errorMsgs.put("foodId", "不存在food表中，無法新增。");
			}
			
			OrderDetailVO odVO = new OrderDetailVO();
			odVO.setOrderId(orderId);
			odVO.setFoodId(foodId);
			odVO.setCreatedTime(createdTime);
			odVO.setAmount(amount);
			odVO.setPointsCost(pointsCost);
			odVO.setNote(note);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("odVO", odVO);
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/orderdetails/listAllOrderDetail.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始新增資料 ***************************************/
			OrderDetailService odpSvc = new OrderDetailService();
			odVO = odpSvc.addOD(orderId, foodId, createdTime, amount, pointsCost, note);
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			req.setAttribute("odVO", odVO); // 資料庫update成功後,正確的odVO物件,存入req
			String url = "/front-end/orderdetails/listAllOrderDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("delete".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer orderId = Integer.valueOf(req.getParameter("orderId"));
			Integer foodId = Integer.valueOf(req.getParameter("foodId"));

			/*************************** 2.開始刪除資料 ***************************************/
			OrderDetailService odSvc = new OrderDetailService();
			odSvc.deleteOD(orderId, foodId);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/back-end/orderdetails/listAllOrderDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}
}
