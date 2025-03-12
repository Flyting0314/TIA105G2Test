package com.orderdetail.model;


import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailJDBCDAO implements OrderDetailDAOInterface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/allieatfinal_db01?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO ORDERDETAIL (orderId, foodId,createdTime,amount,pointsCost,note) VALUES (?,?,?, ?, ?,?)";
	private static final String GET_ALL_STMT = "SELECT orderId, foodId,createdTime,amount,pointsCost,note FROM ORDERDETAIL order by orderId AND foodId";
	private static final String GET_ONE_STMT = "SELECT orderId, foodId,createdTime,amount,pointsCost,note FROM ORDERDETAIL where orderId=? AND foodId=?";
	private static final String DELETE = "DELETE FROM ORDERDETAIL where orderId=? AND foodId=?";
	private static final String UPDATE = "UPDATE ORDERDETAIL set createdTime=?, amount=?, pointsCost=?,note=? where orderId=? AND foodId=?";

	@Override
	public void insert(OrderDetailVO orderDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, orderDetailVO.getOrderId());
			pstmt.setInt(2, orderDetailVO.getFoodId());
			pstmt.setTimestamp(3, orderDetailVO.getCreatedTime());
			pstmt.setInt(4 ,orderDetailVO.getAmount());
			pstmt.setInt(5, orderDetailVO.getPointsCost());
			pstmt.setString(6, orderDetailVO.getNote());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(OrderDetailVO orderDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, orderDetailVO.getCreatedTime());
			pstmt.setInt(2, orderDetailVO.getAmount());
			pstmt.setInt(3, orderDetailVO.getPointsCost());
			pstmt.setString(4, orderDetailVO.getNote());
			pstmt.setInt(5, orderDetailVO.getOrderId());
			pstmt.setInt(6, orderDetailVO.getFoodId());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(Integer orderId, Integer foodId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, orderId);
			pstmt.setInt(2, foodId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public OrderDetailVO findByPrimaryKey(Integer orderId, Integer foodId) {
		OrderDetailVO orderDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, orderId);
			pstmt.setInt(2, foodId);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				orderDetailVO = new OrderDetailVO();

				orderDetailVO.setOrderId(rs.getInt("orderId"));
				orderDetailVO.setFoodId(rs.getInt("foodId"));
				orderDetailVO.setCreatedTime(rs.getTimestamp("createdTime"));
				orderDetailVO.setAmount(rs.getInt("amount"));
				orderDetailVO.setPointsCost(rs.getInt("pointsCost"));
				orderDetailVO.setNote(rs.getString("note"));
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return orderDetailVO;
	}

	@Override
	public List<OrderDetailVO> getAll() {

		List<OrderDetailVO> list = new ArrayList<OrderDetailVO>();
		OrderDetailVO orderDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				orderDetailVO = new OrderDetailVO();

				orderDetailVO.setOrderId(rs.getInt("orderId"));
				orderDetailVO.setFoodId(rs.getInt("foodId"));
				orderDetailVO.setCreatedTime(rs.getTimestamp("createdTime"));
				orderDetailVO.setAmount(rs.getInt("amount"));
				orderDetailVO.setPointsCost(rs.getInt("pointsCost"));
				orderDetailVO.setNote(rs.getString("note"));

				list.add(orderDetailVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	
//		public static void main(String[] args) {
//		Timestamp date = new Timestamp(System.currentTimeMillis());
//		OrderDetailJDBCDAO dao = new OrderDetailJDBCDAO();
//
//		OrderDetailVO orderDetailVO1 = new OrderDetailVO();
		
//		orderDetailVO1.setOrderId(1);
//		orderDetailVO1.setFoodId(1);
//		orderDetailVO1.setCreatedTime(date);
//		orderDetailVO1.setAmount(2);
//		orderDetailVO1.setPointsCost(4);
//		orderDetailVO1.setNote("---有新增---");
//		System.out.println("-----有新增------");
//		
//		dao.insert(orderDetailVO1);

//		// 修改
//		OrderDetailVO orderDetailVO2 = new OrderDetailVO();
//
//		orderDetailVO2.setOrderId(4);
//		orderDetailVO2.setFoodId(4);
//		orderDetailVO2.setCreatedTime(date);
//		orderDetailVO2.setAmount(1);
//		orderDetailVO2.setPointsCost(1);
//		orderDetailVO2.setNote("---有修改sjslsnsnnn---");
//		System.out.println("-----美夢------");
//		
//		dao.update(orderDetailVO2);
//
//		// 刪除
//		dao.delete(1,1);
//		System.out.println("-----刪除o3f3------");
		


		// 查詢
//			OrderDetailVO orderDetailVO3 = dao.findByPrimaryKey(4,4);
//		
//			System.out.print(orderDetailVO3.getOrderId() + ",");
//			System.out.print(orderDetailVO3.getFoodId() + ",");
//			System.out.print(orderDetailVO3.getCreatedTime() + ",");
//			System.out.print(orderDetailVO3.getAmount() + ",");
//			System.out.print(orderDetailVO3.getPointsCost() + ",");
//			System.out.print(orderDetailVO3.getNote());
//			System.out.println("-----查o4f4------");

		// 查詢
//		List<OrderDetailVO> list = dao.getAll();
//		System.out.println("======全查始=====");
//		
//		for (OrderDetailVO orderDetail : list) {			
//			System.out.print(orderDetail.getOrderId() + ",");
//			System.out.print(orderDetail.getFoodId() + ",");
//			System.out.print(orderDetail.getCreatedTime() + ",");
//			System.out.print(orderDetail.getAmount() + ",");
//			System.out.print(orderDetail.getPointsCost() + ",");
//			System.out.print(orderDetail.getNote());
//			System.out.println();
//		}
		
//		System.out.println("======全查畢=====");
//	}
	 
}
