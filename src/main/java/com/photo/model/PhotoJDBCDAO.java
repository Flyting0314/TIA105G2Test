package com.photo.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhotoJDBCDAO implements PhotoDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/db4test?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO photo (storeId,photoSrc,updateTime) VALUES (?, ?,?)";
	private static final String UPDATE = "UPDATE photo set storeId=?,photoSrc=?,updateTime=? where photoId = ?";
	private static final String DELETE = "DELETE FROM photo where photoId= ?";
	private static final String GET_ONE_STMT = "SELECT photoId,storeId,photoSrc,updateTime FROM photo where photoId = ?";
	private static final String GET_ALL_STMT = "SELECT photoId, storeId, photoSrc, updateTime FROM photo ORDER BY photoId";
	@Override
	public void insert(PhotoVO photoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, photoVO.getStoreId());
			pstmt.setString(2, photoVO.getPhotoSrc());
			pstmt.setDate(3, photoVO.getUpdateTime());
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

	@Override
	public void update(PhotoVO photoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, photoVO.getStoreId());
			pstmt.setString(2, photoVO.getPhotoSrc());
			pstmt.setDate(3, photoVO.getUpdateTime());
			pstmt.setInt(4, photoVO.getPhotoId());
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

	@Override
	public void delete(Integer photoId) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, photoId);

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
	public PhotoVO  findByPrimaryKey(Integer photoId) {
		PhotoVO photoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, photoId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				photoVO = new PhotoVO();
				photoVO.setPhotoId(rs.getInt("photoId"));
				photoVO.setStoreId(rs.getInt("storeId"));
				photoVO.setPhotoSrc(rs.getString("photoSrc"));
				photoVO.setUpdateTime(rs.getDate("updateTime"));
				
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

		
		
		return photoVO;
	}

	@Override
	public List<PhotoVO> getAll() {
		List<PhotoVO> list = new ArrayList<PhotoVO>();
		PhotoVO ptVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ptVO = new PhotoVO();
				ptVO.setPhotoId(rs.getInt("photoId"));
				ptVO.setStoreId(rs.getInt("storeId"));
				ptVO.setPhotoSrc(rs.getString("photoSrc"));
				ptVO.setUpdateTime(rs.getDate("updateTime"));
				list.add(ptVO);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return list;
	}

	
	
	
	public static void main(String[] args) {

		PhotoJDBCDAO dao = new PhotoJDBCDAO();
		PhotoVO ptVO1 = new PhotoVO();
		Date fakeDate = new Date(System.currentTimeMillis());

		// insert
//		ptVO1.setStoreId(6);
//		ptVO1.setPhotoSrc("./IBM_11/YOYO");
//		ptVO1.setUpdateTime(fakeDate);
//		dao.insert(ptVO1);
		

		// update
//		ptVO1.setStoreId(4);
//		ptVO1.setPhotoSrc("./IBM_11/YOYOzvdeaeageagg");
//		ptVO1.setUpdateTime(fakeDate);
//		ptVO1.setPhotoId(5);
//		dao.update(ptVO1);

		// Delete
//		dao.delete(4);


		// find by key
//		PhotoVO  ptVO2 = dao.findByPrimaryKey(6);
//		System.out.print(ptVO2.getPhotoId()+ ", ");
//		System.out.print(ptVO2.getStoreId()+ ", ");
//		System.out.print(ptVO2.getPhotoSrc()+ ", ");
//		System.out.print(ptVO2.getUpdateTime()+ ", ");

//		// getAll
		List<PhotoVO> list = dao.getAll();
		for (PhotoVO temp : list) {
			System.out.print(temp.getPhotoId()+ ", ");
			System.out.print(temp.getStoreId()+ ", ");
			System.out.print(temp.getPhotoSrc()+ ", ");
			System.out.print(temp.getUpdateTime()+ ", ");
			System.out.println();
		}

	}


}
