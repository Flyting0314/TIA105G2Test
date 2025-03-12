package com.food.model;

import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FoodDAO implements FoodDAOInterface {

	// 一個應用程式中，針對一個資料庫，共用一個 DataSource 即可
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
		"INSERT INTO food (storeId, name, createdTime, status, amount, photo, cost) VALUES (?, ?, NOW(), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT foodId, storeId, name, createdTime, status, amount, photo, cost FROM food order by foodId";
	private static final String GET_ONE_STMT = 
		"SELECT foodId, storeId, name, createdTime, status, amount, photo, cost FROM food where foodId = ?";
	private static final String DELETE = 
		"DELETE FROM food where foodId = ?";
	private static final String UPDATE = 
		"UPDATE food set storeId=?, name=?, status=?, amount=?, photo=?, cost=? where foodId=?";

	@Override
	public void insert(FoodVO foodVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, foodVO.getStoreId());
			pstmt.setString(2, foodVO.getName());
			// createdTime 採用 NOW() 數值，不需設定
			pstmt.setInt(3, foodVO.getStatus());
			pstmt.setInt(4, foodVO.getAmount());
			pstmt.setString(5, foodVO.getPhoto());
			pstmt.setInt(6, foodVO.getCost());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(FoodVO foodVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, foodVO.getStoreId());
			pstmt.setString(2, foodVO.getName());
			pstmt.setInt(3, foodVO.getStatus());
			pstmt.setInt(4, foodVO.getAmount());
			pstmt.setString(5, foodVO.getPhoto());
			pstmt.setInt(6, foodVO.getCost());
			pstmt.setInt(7, foodVO.getFoodId());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(Integer foodId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, foodId);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public FoodVO findByPrimaryKey(Integer foodId) {

		FoodVO foodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, foodId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				foodVO = new FoodVO();
				foodVO.setFoodId(rs.getInt("foodId"));
				foodVO.setStoreId(rs.getInt("storeId"));
				foodVO.setName(rs.getString("name"));
				foodVO.setCreatedTime(rs.getTimestamp("createdTime"));
				foodVO.setStatus(rs.getInt("status"));
				foodVO.setAmount(rs.getInt("amount"));
				foodVO.setPhoto(rs.getString("photo"));
				foodVO.setCost(rs.getInt("cost"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return foodVO;
	}

	@Override
	public List<FoodVO> getAll() {
		List<FoodVO> list = new ArrayList<FoodVO>();
		FoodVO foodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				foodVO = new FoodVO();
				foodVO.setFoodId(rs.getInt("foodId"));
				foodVO.setStoreId(rs.getInt("storeId"));
				foodVO.setName(rs.getString("name"));
				foodVO.setCreatedTime(rs.getTimestamp("createdTime"));
				foodVO.setStatus(rs.getInt("status"));
				foodVO.setAmount(rs.getInt("amount"));
				foodVO.setPhoto(rs.getString("photo"));
				foodVO.setCost(rs.getInt("cost"));
				list.add(foodVO); // 將每一筆資料存入 List
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
