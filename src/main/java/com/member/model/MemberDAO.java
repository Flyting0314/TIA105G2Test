package com.member.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.*;
import java.sql.*;

public class MemberDAO implements MemberDAOinterface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
	        "INSERT INTO member (organizationId, name, idNum, permAddr, address, regTime, kycImage, email, phone, account, password, pointsBalance, unclaimedMealCount, accStat, reviewed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    private static final String GET_ALL_STMT = 
	        "SELECT memberid, organizationId, name, idNum, permAddr, address, regTime, kycImage, email, phone, account, password, pointsBalance, unclaimedMealCount, accStat, reviewed FROM member ORDER BY memberId";
	   
	    private static final String GET_ONE_STMT = 
	        "SELECT memberid, organizationId, name, idNum, permAddr, address, regTime, kycImage, email, phone, account, password, pointsBalance, unclaimedMealCount, accStat, reviewed FROM member WHERE memberId = ?";
	    
	    private static final String DELETE = 
	        "DELETE FROM member WHERE memberId = ?";
	    
	    private static final String UPDATE = 
	        "UPDATE member SET  organizationId=?, name=?, idNum=?, permAddr=?, address=?, regTime=?, kycImage=?, email=?, phone=?, account=?, password=?, pointsBalance=?, unclaimedMealCount=?, accStat=?, reviewed=? WHERE memberId = ?";




		

	

	@Override
	public void insert(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, memberVO.getOrganizationId());
            pstmt.setString(2, memberVO.getName());
            pstmt.setString(3, memberVO.getIdNum());
            pstmt.setString(4, memberVO.getPermAddr());
            pstmt.setString(5, memberVO.getAddress());
            pstmt.setTimestamp(6, memberVO.getRegTime());
            pstmt.setString (7, memberVO.getKycImage());
            pstmt.setString(8, memberVO.getEmail());
            pstmt.setString(9, memberVO.getPhone());
            pstmt.setString(10, memberVO.getAccount());
            pstmt.setString(11, memberVO.getPassword());
            pstmt.setInt(12, memberVO.getPointsBalance());
            pstmt.setInt(13, memberVO.getUnclaimedMealCount());
            pstmt.setInt(14, memberVO.getAccStat());
            pstmt.setInt(15, memberVO.getReviewed());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, memberVO.getOrganizationId());
	        pstmt.setString(2, memberVO.getName());
	        pstmt.setString(3, memberVO.getIdNum());
	        pstmt.setString(4, memberVO.getPermAddr());
	        pstmt.setString(5, memberVO.getAddress());
	        pstmt.setTimestamp(6, memberVO.getRegTime()); // 確保設置了 regTime
	        pstmt.setString(7, memberVO.getKycImage());
	        pstmt.setString(8, memberVO.getEmail());
	        pstmt.setString(9, memberVO.getPhone());
	        pstmt.setString(10, memberVO.getAccount());
	        pstmt.setString(11, memberVO.getPassword());
	        pstmt.setInt(12, memberVO.getPointsBalance());
	        pstmt.setInt(13, memberVO.getUnclaimedMealCount());
	        pstmt.setInt(14, memberVO.getAccStat());
	        pstmt.setInt(15, memberVO.getReviewed());
	        pstmt.setInt(16, memberVO.getMemberId());

	        System.out.println("Executing update for memberId: " + memberVO.getMemberId() + " with regTime: " + memberVO.getRegTime()); // 日誌檢查

	        pstmt.executeUpdate();
	        

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	
	@Override
	public void delete(Integer memberId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memberId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public MemberVO findByPrimaryKey(Integer memberId) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
                memberVO.setMemberId(rs.getInt("memberId"));
                memberVO.setOrganizationId(rs.getInt("organizationId"));
                memberVO.setName(rs.getString("name"));
                memberVO.setIdNum(rs.getString("idNum"));
                memberVO.setPermAddr(rs.getString("permAddr"));
                memberVO.setAddress(rs.getString("address"));
                memberVO.setRegTime(rs.getTimestamp("regTime"));
                memberVO.setKycImage(rs.getString ("kycImage"));
                memberVO.setEmail(rs.getString("email"));
                memberVO.setPhone(rs.getString("phone"));
                memberVO.setAccount(rs.getString("account"));
                memberVO.setPassword(rs.getString("password"));
                memberVO.setPointsBalance(rs.getInt("pointsBalance"));
                memberVO.setUnclaimedMealCount(rs.getInt("unclaimedMealCount"));
                memberVO.setAccStat(rs.getInt("accStat"));
                memberVO.setReviewed(rs.getInt("reviewed"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return memberVO;
	}

	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setMemberId(rs.getInt("memberId"));
                memberVO.setOrganizationId(rs.getInt("organizationId"));
                memberVO.setName(rs.getString("name"));
                memberVO.setIdNum(rs.getString("idNum"));
                memberVO.setPermAddr(rs.getString("permAddr"));
                memberVO.setAddress(rs.getString("address"));
                memberVO.setRegTime(rs.getTimestamp("regTime"));
                memberVO.setKycImage(rs.getString ("kycImage"));
                memberVO.setEmail(rs.getString("email"));
                memberVO.setPhone(rs.getString("phone"));
                memberVO.setAccount(rs.getString("account"));
                memberVO.setPassword(rs.getString("password"));
                memberVO.setPointsBalance(rs.getInt("pointsBalance"));
                memberVO.setUnclaimedMealCount(rs.getInt("unclaimedMealCount"));
                memberVO.setAccStat(rs.getInt("accStat"));
                memberVO.setReviewed(rs.getInt("reviewed"));
				list.add(memberVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}