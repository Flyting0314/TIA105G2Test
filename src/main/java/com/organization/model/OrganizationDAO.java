package com.organization.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member.model.MemberVO;

public class OrganizationDAO implements OrganizationDAOInterface {

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
	        "INSERT INTO organization (name, type,createdTime, status) VALUES (?, ?, ?,?)";
	    
	    private static final String GET_ALL_STMT = 
	        "SELECT organizationId, name, type, createdTime, status FROM organization ORDER BY organizationId";
	   
	    private static final String GET_ONE_STMT = 
	        "SELECT organizationId, name, type, createdTime, status FROM organization WHERE organizationId=?";
	    
	    private static final String DELETE = 
	        "DELETE FROM organization WHERE organizationId=?";
	    
	    private static final String UPDATE = 
	        "UPDATE organization SET name=?, type=?, status=?, createdTime=CURRENT_TIMESTAMP WHERE organizationId=?";

	    private static final String GET_Members_ByOrganizationId_STMT = "SELECT memberId,organizationId, name, idNum, permAddr, address, regTime, kycImage, email, phone, account, password, pointsBalance, unclaimedMealCount, accStat, reviewed FROM member where organizationId = ? order by memberId";


		

	

	@Override
	public void insert(OrganizationVO organizationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, organizationVO.getName());
            pstmt.setString(2, organizationVO.getType());
            pstmt.setTimestamp(3, organizationVO.getCreatedTime());
            pstmt.setInt(4, organizationVO.getStatus());

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
	public void update(OrganizationVO organizationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, organizationVO.getName());
            pstmt.setString(2, organizationVO.getType());
            pstmt.setTimestamp(3, organizationVO.getCreatedTime());
            pstmt.setInt(4, organizationVO.getStatus());
            pstmt.setInt(5, organizationVO.getOrganizationId());

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
	public void delete(Integer organizationId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, organizationId);

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
	public OrganizationVO findByPrimaryKey(Integer organizationId) {

		OrganizationVO organizationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, organizationId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				organizationVO = new OrganizationVO();
                organizationVO.setOrganizationId(rs.getInt("organizationId"));
                organizationVO.setName(rs.getString("name"));
                organizationVO.setType(rs.getString("type"));
                organizationVO.setCreatedTime(rs.getTimestamp("createdTime"));
                organizationVO.setStatus(rs.getInt("status"));
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
		return organizationVO;
	}

	@Override
	public List<OrganizationVO> getAll() {
		List<OrganizationVO> list = new ArrayList<OrganizationVO>();
		OrganizationVO organizationVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				organizationVO = new OrganizationVO();
                organizationVO.setOrganizationId(rs.getInt("organizationId"));
                organizationVO.setName(rs.getString("name"));
                organizationVO.setType(rs.getString("type"));
                organizationVO.setCreatedTime(rs.getTimestamp("createdTime"));
                organizationVO.setStatus(rs.getInt("status"));
                list.add(organizationVO); // Store the row in the list
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

	@Override
	public Set<MemberVO> getMembersByOrganizationId(Integer organizationId) {
		Set<MemberVO> set = new LinkedHashSet<MemberVO>();
		MemberVO memberVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Members_ByOrganizationId_STMT);
			pstmt.setInt(1, organizationId);
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
                memberVO.setReviewed(rs.getInt("reviewed")); // Store the row in the vector
			}
	
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return set;
	}
}