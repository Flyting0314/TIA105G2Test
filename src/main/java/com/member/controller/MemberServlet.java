package com.member.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.member.model.MemberService;
import com.member.model.MemberVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("memberId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入受助者編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer memberId = null;
			try {
				memberId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("受助者編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.getOneMember(memberId);
			if (memberVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("memberVO", memberVO); // 資料庫取出的memberVO物件,存入req
			String url = "/member/listOneMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMember.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllMember.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer memberId = Integer.valueOf(req.getParameter("memberId"));

			/*************************** 2.開始查詢資料 ****************************************/
			MemberService memberSvc = new MemberService();
			
			MemberVO memberVO = memberSvc.getOneMember(memberId);
			

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("memberVO", memberVO); // 資料庫取出的memberVO物件,存入req
			String url = "/member/update_member_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_member_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_member_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			MemberService memberSvc = new MemberService();
		    
		    

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer memberId = Integer.valueOf(req.getParameter("memberId").trim());
			MemberVO originalMemberVO = memberSvc.getOneMember(memberId);
			java.sql.Timestamp originalRegTime = originalMemberVO.getRegTime();
			java.sql.Timestamp lastModifiedTime = new java.sql.Timestamp(System.currentTimeMillis());
			java.sql.Timestamp regTime = null;
			regTime = new java.sql.Timestamp(System.currentTimeMillis());
			Integer organizationId = Integer.valueOf(req.getParameter("organizationId").trim());

			String name = req.getParameter("name");
			String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (name == null || name.trim().length() == 0) {
				errorMsgs.add("受助者姓名: 請勿空白");
			} else if (!name.trim().matches(nameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("受助者姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			String idNum = req.getParameter("idNum").trim();
			String idNumReg = "^[A-Z]{1}[1-2]{1}[0-9]{8}$";
			if (idNum == null || idNum.trim().length() == 0) {
				errorMsgs.add("身分證字號請勿空白");
			} else if (!idNum.toUpperCase().trim().matches(idNumReg.toUpperCase())) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("身分證格式錯誤");
			}

			String permAddr = req.getParameter("permAddr").trim();
			String permAddrReg = "^\\D+[縣市]\\D+?[鄉鎮市區].+$";
			if (permAddr == null || permAddr.trim().length() == 0) {
				errorMsgs.add("戶籍地址請勿空白");
			} else if (!permAddr.trim().matches(permAddrReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("戶籍地址格式錯誤");
			}

			String address = req.getParameter("address").trim();
			String addressReg = "^\\D+[縣市]\\D+?[鄉鎮市區].+$";
			if (address == null || address.trim().length() == 0) {
				errorMsgs.add("通訊地址請勿空白");
			} else if (!address.trim().matches(addressReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("通訊地址格式錯誤");
			}

		    	
			
			String kycImage = originalMemberVO.getKycImage();
			Part part = req.getPart("kycImage");
			if (part != null && part.getSize() > 0) {
		        String timename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		        kycImage = timename + fileName;
		        String dir = getServletContext().getRealPath("/images_uploaded");
		        File uploadDir = new File(dir);
		        if (!uploadDir.exists()) {
		            uploadDir.mkdirs();
		        }
		        part.write(dir + "/" + kycImage);
		    } else {
		        errorMsgs.add("請上傳證明文件");
		    }


			String email = req.getParameter("email").trim();
			String emailReg = "^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
			if (email == null || email.trim().length() == 0) {
				errorMsgs.add("信箱請勿空白");
			} else if (!email.trim().matches(emailReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("信箱格式錯誤");
			}

			String phone = req.getParameter("phone").trim();
			String phoneReg = "^(0)([0-9]{1})([-]?)([0-9]{6,8})$";// 市話
			String phoneReg2 = "^(09)([0-9]{2})([-]?)([0-9]{6})$";// 手機
			if (phone == null || phone.trim().length() == 0) {
				errorMsgs.add("電話請勿空白");
			} else if (!phone.trim().matches(phoneReg) && !phone.trim().matches(phoneReg2)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("電話格式錯誤");
			}

			String account = req.getParameter("account").trim();
			String accountReg = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,}$";// 至少1大寫+1小寫英文+8字符
			if (account == null || account.trim().length() == 0) {
				errorMsgs.add("帳號請勿空白");
			} else if (!account.trim().matches(accountReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("帳號格式錯誤");
			}

			String password = req.getParameter("password").trim();
			String passwordReg = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,}$";// 至少1大寫+1小寫英文+8字符
			if (password == null || password.trim().length() == 0) {
				errorMsgs.add("密碼請勿空白");
			} else if (!password.trim().matches(passwordReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("密碼格式錯誤");
			}

			Integer pointsBalance = null;
			try {
				pointsBalance = Integer.valueOf(req.getParameter("pointsBalance").trim());
			} catch (NumberFormatException e) {
				pointsBalance = 0;
				errorMsgs.add("點數餘額請填數字");
			}

			Integer unclaimedMealCount = null;
			try {
				unclaimedMealCount = Integer.valueOf(req.getParameter("unclaimedMealCount").trim());
			} catch (NumberFormatException e) {
				unclaimedMealCount = 0;
				errorMsgs.add("未領餐累計次數請填數字");
			}

			Integer accStat = null;
			try {
				Integer n1 = 1;
				Integer n0 = 0;

				accStat = Integer.valueOf(req.getParameter("accStat").trim());

				if (accStat.equals(n1) || accStat.equals(n0)) {

				} else {
					errorMsgs.add("帳號狀態請勿空白並填寫數字:0=停用;1=啟用");
				}

			} catch (NumberFormatException e) {
				accStat = 0;
				errorMsgs.add("帳號狀態請填數字");
			}

			Integer reviewed = null;

			try {
				Integer n1 = 1;
				Integer n0 = 0;

				reviewed = Integer.valueOf(req.getParameter("reviewed").trim());
				if (reviewed.equals(n1) || reviewed.equals(n0)) {

				} else {
					errorMsgs.add("帳號審核狀態請填數字:0=審核中;1=已通過;2=未通過;3=未審核");
				}

			} catch (NumberFormatException e) {
				reviewed = 0;
				errorMsgs.add("帳號審核狀態請填數字");
			}

			MemberVO memberVO = new MemberVO();
			memberVO.setMemberId(memberId);
			memberVO.setOrganizationId(organizationId);
			memberVO.setName(name);
			memberVO.setIdNum(idNum);
			memberVO.setPermAddr(permAddr);
			memberVO.setAddress(address);
			memberVO.setRegTime(originalRegTime);
			System.out.println("MemberVO regTime set to: " + memberVO.getRegTime());
			memberVO.setKycImage(kycImage);
			memberVO.setEmail(email);
			memberVO.setPhone(phone);
			memberVO.setAccount(account);
			memberVO.setPassword(password);
			memberVO.setPointsBalance(pointsBalance);
			memberVO.setUnclaimedMealCount(unclaimedMealCount);
			memberVO.setAccStat(accStat);
			memberVO.setReviewed(reviewed);
			System.out.println("MemberVO after setting all values: " + memberVO);
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的memberVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/member/update_member_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			 memberSvc = new MemberService();		
			memberVO = memberSvc.updateMember(memberId, organizationId, name, idNum, permAddr, address,originalRegTime,kycImage, email, phone, account, password, pointsBalance, unclaimedMealCount, accStat, reviewed);
			
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/

			req.setAttribute("memberVO", memberVO); // 資料庫update成功後,正確的的memberVO物件,存入req
//			req.setAttribute("regTime", regTime);

			String url = "/member/listAllMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneMember.jsp
			successView.forward(req, res);

		}

		if ("insert".equals(action)) { // 來自addMember.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			Integer organizationId = Integer.valueOf(req.getParameter("organizationId").trim());

			String name = req.getParameter("name");
			String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (name == null || name.trim().length() == 0) {
				errorMsgs.add("受助者姓名: 請勿空白");
			} else if (!name.trim().matches(nameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("受助者姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			String idNum = req.getParameter("idNum").trim();
			String idNumReg = "^[A-Z]{1}[1-2]{1}[0-9]{8}$";
			if (idNum == null || idNum.trim().length() == 0) {
				errorMsgs.add("身分證字號請勿空白");
			} else if (!idNum.toUpperCase().trim().matches(idNumReg.toUpperCase())) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("身分證格式錯誤");
			}

			String permAddr = req.getParameter("permAddr").trim();
			String permAddrReg = "^\\D+[縣市]\\D+?[鄉鎮市區].+$";
			if (permAddr == null || permAddr.trim().length() == 0) {
				errorMsgs.add("戶籍地址請勿空白");
			} else if (!permAddr.trim().matches(permAddrReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("戶籍地址格式錯誤");
			}

			String address = req.getParameter("address").trim();
			String addressReg = "^\\D+[縣市]\\D+?[鄉鎮市區].+$";
			if (address == null || address.trim().length() == 0) {
				errorMsgs.add("通訊地址請勿空白");
			} else if (!address.trim().matches(addressReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("通訊地址格式錯誤");
			}

			java.sql.Timestamp regTime = null;
			String regTimeStr = req.getParameter("regTime");

			if (regTimeStr == null || regTimeStr.trim().isEmpty()) {
			    regTime = new java.sql.Timestamp(System.currentTimeMillis()); // 設定為當前時間
			} else {
			    try {
			        regTime = java.sql.Timestamp.valueOf(regTimeStr.trim());
			    } catch (IllegalArgumentException e) {
			        regTime = new java.sql.Timestamp(System.currentTimeMillis());
			        errorMsgs.add("日期格式錯誤，已自動填入系統時間!");
			    }
			}
			

			
			
			String kycImage = null;
			Part part = req.getPart("kycImage");
			if (part != null && part.getSize() > 0) {
		        String timename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		        kycImage = timename + fileName;
		        String dir = getServletContext().getRealPath("/images_uploaded");
		        File uploadDir = new File(dir);
		        if (!uploadDir.exists()) {
		            uploadDir.mkdirs();
		        }
		        part.write(dir + "/" + kycImage);
		    } else {
		        errorMsgs.add("請上傳證明文件");
		    }

			String email = req.getParameter("email").trim();
			String emailReg = "^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
			if (email == null || email.trim().length() == 0) {
				errorMsgs.add("信箱請勿空白");
			} else if (!email.trim().matches(emailReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("信箱格式錯誤");
			}

			String phone = req.getParameter("phone").trim();
			String phoneReg = "^(0)([0-9]{1})([-]?)([0-9]{6,8})$";// 市話
			String phoneReg2 = "^(09)([0-9]{2})([-]?)([0-9]{6})$";// 手機
			if (phone == null || phone.trim().length() == 0) {
				errorMsgs.add("電話請勿空白");
			} else if (!phone.trim().matches(phoneReg) && !phone.trim().matches(phoneReg2)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("電話格式錯誤");
			}

			String account = req.getParameter("account").trim();
			String accountReg = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,}$";// 至少1大寫+1小寫英文+8字符
			if (account == null || account.trim().length() == 0) {
				errorMsgs.add("帳號請勿空白");
			} else if (!account.trim().matches(accountReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("帳號格式錯誤");
			}

			String password = req.getParameter("password").trim();
			String passwordReg = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,}$";// 至少1大寫+1小寫英文+8字符
			if (password == null || password.trim().length() == 0) {
				errorMsgs.add("密碼請勿空白");
			} else if (!password.trim().matches(passwordReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("密碼格式錯誤");
			}

			Integer pointsBalance = null;
			try {
				pointsBalance = Integer.valueOf(req.getParameter("pointsBalance").trim());
			} catch (NumberFormatException e) {
				pointsBalance = 0;
				errorMsgs.add("點數餘額請填數字");
			}

			Integer unclaimedMealCount = null;
			try {
				unclaimedMealCount = Integer.valueOf(req.getParameter("unclaimedMealCount").trim());
			} catch (NumberFormatException e) {
				unclaimedMealCount = 0;
				errorMsgs.add("未領餐累計次數請填數字");
			}

			Integer accStat = null;
			try {
				Integer n1 = 1;
				Integer n0 = 0;

				accStat = Integer.valueOf(req.getParameter("accStat").trim());

				if (accStat.equals(n1) || accStat.equals(n0)) {

				} else {
					errorMsgs.add("帳號狀態請勿空白並填寫數字:0=停用;1=啟用");
				}

			} catch (NumberFormatException e) {
				accStat = 0;
				errorMsgs.add("帳號狀態請填數字");
			}

			Integer reviewed = null;

			try {
				Integer n1 = 1;
				Integer n0 = 0;

				reviewed = Integer.valueOf(req.getParameter("reviewed").trim());
				if (reviewed.equals(n1) || reviewed.equals(n0)) {

				} else {
					errorMsgs.add("帳號審核狀態請填數字:0=審核中;1=已通過;2=未通過;3=未審核");
				}

			} catch (NumberFormatException e) {
				reviewed = 0;
				errorMsgs.add("帳號審核狀態請填數字");
			}

			MemberVO memberVO = new MemberVO();
			memberVO.setOrganizationId(organizationId);
			memberVO.setName(name);
			memberVO.setIdNum(idNum);
			memberVO.setPermAddr(permAddr);
			memberVO.setAddress(address);
			memberVO.setRegTime(regTime);
			memberVO.setKycImage(kycImage);
			memberVO.setEmail(email);
			memberVO.setPhone(phone);
			memberVO.setAccount(account);
			memberVO.setPassword(password);
			memberVO.setPointsBalance(pointsBalance);
			memberVO.setUnclaimedMealCount(unclaimedMealCount);
			memberVO.setAccStat(accStat);
			memberVO.setReviewed(reviewed);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的memberVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/member/addMember.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			MemberService memberSvc = new MemberService();
			memberVO = memberSvc.addMember(organizationId, name, idNum, permAddr, address, regTime, kycImage, email,
					phone, account, password, pointsBalance, unclaimedMealCount, accStat, reviewed);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/member/listAllMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMember.jsp
			successView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllMember.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer memberId = Integer.valueOf(req.getParameter("memberId"));

			/*************************** 2.開始刪除資料 ***************************************/
			MemberService memberSvc = new MemberService();
			memberSvc.deleteMember(memberId);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/member/listAllMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
		
		if ("download".equals(action)) { // 來自listAllMember.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			String kycImage = null;
			Part part = req.getPart("kycImage");
			if (part != null || part.getSize() > 0) {
				PrintWriter out = res.getWriter();
				String timename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				kycImage = timename + fileName;

				String dir = getServletContext().getRealPath("/images_uploaded");
				File uploadDir = new File(dir);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				part.write(dir + "/" + kycImage);

			} else {
				errorMsgs.add("請上傳證明文件");
			}

			/*************************** 2.開始下載資料 ***************************************/
			MemberService memberSvc = new MemberService();
			memberSvc.download(req, res);

			/*************************** 3.下載完成,準備轉交(Send the Success view) ***********/
			String url = "/member/listAllMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}
}
