package com.member.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




public class MemberService {
	
	

	private MemberDAOinterface dao;

	public MemberService() {
		dao = new MemberDAO();
	}
	

	
	public MemberVO addMember(Integer organizationId,String name,String idNum,String permAddr,String address,java.sql.Timestamp regTime,String  kycImage,String email,String phone,String account,String password,Integer pointsBalance,Integer unclaimedMealCount,Integer accStat,Integer reviewed ) {
		
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
		System.out.println("Before insert: " + memberVO.getPhone()); // 輸出檢查
		System.out.println("Before insert: " + memberVO.getRegTime()); // 輸出檢查
		dao.insert(memberVO);
		System.out.println("After insert: " + memberVO.getRegTime()); // 輸出檢查
		System.out.println("After insert: " + memberVO.getPhone()); // 輸出檢查
		return memberVO;
	}

	public MemberVO updateMember(Integer memberId,Integer organizationId,String name,String idNum,String permAddr,String address,java.sql.Timestamp regTime,String kycImage,String email,String phone,String account,String password,Integer pointsBalance,Integer unclaimedMealCount,Integer accStat,Integer reviewed ) {
		
		
		MemberVO memberVO = new MemberVO(); 
	   
		memberVO.setMemberId(memberId);
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
		System.out.println("Before update: " + memberVO.getPhone()); // 輸出檢查

		System.out.println("Before update: " + memberVO.getRegTime()); // 日誌檢查
		dao.update(memberVO);
		System.out.println("After update: " + memberVO.getRegTime()); // 日誌檢查
		System.out.println("After update: " + memberVO.getPhone()); // 輸出檢查

		return memberVO;
	}

	public void deleteMember(Integer memberId) {
		dao.delete(memberId);
	}

	public MemberVO getOneMember(Integer memberId) {
		return dao.findByPrimaryKey(memberId);
	}

	public List<MemberVO> getAll() {
		return dao.getAll();
	}



	public void download(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	        String fileName  = req.getParameter("kycImage");
	        if (fileName == null || fileName.trim().isEmpty()) {
	            res.getWriter().write("檔案名稱無效！");
	            return;
	        }
	        String dir = req.getServletContext().getRealPath("/images_uploaded/");  
	        File file = new File(dir + fileName);
	        
	        res.setContentType(Files.probeContentType(file.toPath())); 
	        res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	        res.setContentLengthLong(file.length());

	        try (FileInputStream fileInputStream = new FileInputStream(file);
	             OutputStream outputStream = res.getOutputStream()) {

	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	            System.out.println(fileName);
	        }
	    
	       


	    }



	public MemberVO updateMember(Integer memberId, Integer organizationId, String name, String idNum, String permAddr,
			String address, Timestamp originalRegTime, String kycImage, String email, String phone, String account,
			String password, Integer pointsBalance, Integer unclaimedMealCount, Integer accStat, Integer reviewed,
			Timestamp lastModifiedTime) {
		// TODO Auto-generated method stub
		return null;
	}
		
	}

