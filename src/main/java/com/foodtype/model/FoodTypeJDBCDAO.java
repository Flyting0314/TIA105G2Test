package com.foodtype.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodTypeJDBCDAO implements FoodTypeDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/db4test?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";
	private static final String INSERT_STMT = "INSERT INTO foodType (storeId, type) VALUES (?, ?)";
	private static final String UPDATE = "UPDATE foodType set storeId=?,type=? where foodTypeId = ?";
	private static final String DELETE = "DELETE FROM foodType where foodTypeId= ?";
	private static final String GET_ONE_STMT = "SELECT foodTypeId,storeId,type FROM foodType where foodTypeId = ?";
	private static final String GET_ALL_STMT = "SELECT foodTypeId,storeId,type FROM foodType order by foodTypeId";
	
	
	
	public void insert(FoodTypeVO foodType) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, foodType.getStoreId());
			pstmt.setString(2, foodType.getType());
			pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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

	public void update(FoodTypeVO foodType) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, foodType.getStoreId());
			pstmt.setString(2, foodType.getType());
			pstmt.setInt(3, foodType.getFoodTypeId());
			pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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

	public void delete(Integer foodTypeId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, foodTypeId);

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

	public FoodTypeVO findByPrimaryKey(Integer foodTypeId) {
		FoodTypeVO foodTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, foodTypeId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				foodTypeVO = new FoodTypeVO();
				foodTypeVO.setFoodTypeId(rs.getInt("foodTypeId"));
				foodTypeVO.setStoreId(rs.getInt("storeId"));
				foodTypeVO.setType(rs.getString("type"));
				
			}
			System.out.println();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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

		return foodTypeVO;
		
	}

	public List<FoodTypeVO> getAll() {
		List<FoodTypeVO> list = new ArrayList<FoodTypeVO>();
		FoodTypeVO ftVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ftVO = new FoodTypeVO();
				ftVO.setFoodTypeId(rs.getInt("foodTypeId"));
				ftVO.setStoreId(rs.getInt("storeId"));
				ftVO.setType(rs.getString("type"));
				list.add(ftVO);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	public static void main(String[] args) {

		FoodTypeJDBCDAO dao = new FoodTypeJDBCDAO();
		FoodTypeVO ftVO1 = new FoodTypeVO();

		// insert
//		ftVO1.setStoreId(6);
//		ftVO1.setType("清真");
//		dao.insert(ftVO1);

		// update
//		ftVO1.setStoreId(11);
//		ftVO1.setType("safaf");
//		ftVO1.setFoodTypeId(2);
//		dao.update(ftVO1);

		// Delete
//		dao.delete(4);

		// find by key
//		FoodTypeVO ftVO2 = dao.findByPrimaryKey(11);
//		System.out.print(ftVO2.getFoodTypeId()+ ", ");
//		System.out.print(ftVO2.getStoreId()+ ", ");
//		System.out.print(ftVO2.getType()+ ", ");
//		

		// getAll
		List<FoodTypeVO> list = dao.getAll();
		for (FoodTypeVO temp : list) {
			System.out.print(temp.getFoodTypeId() + ", ");
			System.out.print(temp.getStoreId() + ", ");
			System.out.print(temp.getType() + ", ");
			System.out.println();
		}

	}


}
