package com.payRecord.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayRecordJDBCDAO implements PayRecordDAO_interface {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/group_db?serverTimezone=Asia/Taipei";
    String userid = "root";
    String passwd = "123456";

    private static final String INSERT_STMT = "INSERT INTO payRecord (totalPoint, payoutDate, status) VALUES (?, ?, ?)";
    private static final String GET_ALL_STMT = "SELECT payoutId, totalPoint, payoutDate, status FROM payRecord ORDER BY payoutId";
    private static final String GET_ONE_STMT = "SELECT payoutId, totalPoint, payoutDate, status FROM payRecord WHERE payoutId = ?";
    private static final String DELETE = "DELETE FROM payRecord WHERE payoutId = ?";
    private static final String UPDATE = "UPDATE payRecord SET totalPoint = ?, payoutDate = ?, status = ? WHERE payoutId = ?";
    private static final String FIND_BY_STATUS = "SELECT payoutId, totalPoint, payoutDate, status FROM payRecord WHERE status = ? ORDER BY payoutId";
    private static final String FIND_BY_MONTH = "SELECT payoutId, totalPoint, payoutDate, status FROM payRecord WHERE DATE_FORMAT(payoutDate, '%Y-%m') = ? ORDER BY payoutId";



    @Override
    public void insert(PayRecordVO payRecordVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setInt(1, payRecordVO.getTotalPoint());
            pstmt.setTimestamp(2, payRecordVO.getPayoutDate());
            pstmt.setInt(3, payRecordVO.getStatus());

            pstmt.executeUpdate();
//            System.out.println("新增成功！");
            
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
    public void update(PayRecordVO payRecordVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setInt(1, payRecordVO.getTotalPoint());
            pstmt.setTimestamp(2, payRecordVO.getPayoutDate());
            pstmt.setInt(3, payRecordVO.getStatus());
            pstmt.setInt(4, payRecordVO.getPayoutId());

            pstmt.executeUpdate();
            System.out.println("更新成功！");
        } catch (SQLException se) {
            se.printStackTrace(System.err);
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
    public void delete(Integer payoutId) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(DELETE);
            pstmt.setInt(1, payoutId);
            pstmt.executeUpdate();
//            System.out.println("刪除成功！");
        } catch (SQLException se) {
            se.printStackTrace(System.err);
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
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public PayRecordVO findByPrimaryKey(Integer payoutId) {
        PayRecordVO payRecordVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);
            pstmt.setInt(1, payoutId);
            rs = pstmt.executeQuery();

            ////=====欄位名稱去底線！！！！！=========
            if (rs.next()) {
                payRecordVO = new PayRecordVO();
                payRecordVO.setPayoutId(rs.getInt("payoutId"));
                payRecordVO.setTotalPoint(rs.getInt("totalPoint"));
                payRecordVO.setPayoutDate(rs.getTimestamp("payoutDate"));
                payRecordVO.setStatus(rs.getInt("status"));
            }

        } catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
        } catch (SQLException se) {
            se.printStackTrace(System.err);
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
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
        }
        return payRecordVO;
    }
    
    
    

    @Override
    public List<PayRecordVO> getAll() {
        List<PayRecordVO> list = new ArrayList<>();
        PayRecordVO payRecordVO = null;
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                payRecordVO = new PayRecordVO();
                payRecordVO.setPayoutId(rs.getInt("payoutId"));
                payRecordVO.setTotalPoint(rs.getInt("totalPoint"));
                payRecordVO.setPayoutDate(rs.getTimestamp("payoutDate"));
                payRecordVO.setStatus(rs.getInt("status"));
                list.add(payRecordVO);
            }
        } catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			
		} catch (SQLException se) {
            se.printStackTrace(System.err);
            
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
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
        }
        return list;
    }
    
    @Override
    public List<PayRecordVO> findByStatus(Integer status) {
        List<PayRecordVO> list = new ArrayList<>();
        PayRecordVO payRecordVO = null;
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(FIND_BY_STATUS);
            pstmt.setInt(1, status);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                payRecordVO = new PayRecordVO();
                payRecordVO.setPayoutId(rs.getInt("payoutId"));
                payRecordVO.setTotalPoint(rs.getInt("totalPoint"));
                payRecordVO.setPayoutDate(rs.getTimestamp("payoutDate"));
                payRecordVO.setStatus(rs.getInt("status"));
                list.add(payRecordVO);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            se.printStackTrace(System.err);
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
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
        }
        return list;
    }

    @Override
    public List<PayRecordVO> findByMonth(String month) {
        List<PayRecordVO> list = new ArrayList<>();
        PayRecordVO payRecordVO = null;
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(FIND_BY_MONTH);
            pstmt.setString(1, month);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                payRecordVO = new PayRecordVO();
                payRecordVO.setPayoutId(rs.getInt("payoutId"));
                payRecordVO.setTotalPoint(rs.getInt("totalPoint"));
                payRecordVO.setPayoutDate(rs.getTimestamp("payoutDate"));
                payRecordVO.setStatus(rs.getInt("status"));
                list.add(payRecordVO);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            se.printStackTrace(System.err);
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
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
        }
        return list;
    }
    
    
    



//============main方法==============

    public static void main(String[] args) {
        PayRecordJDBCDAO dao = new PayRecordJDBCDAO();
        PayRecordVO prVO1 = new PayRecordVO();

        // insert
        prVO1.setTotalPoint(300);
        prVO1.setPayoutDate(new Timestamp(System.currentTimeMillis()));
        prVO1.setStatus(1); // 改為使用整數狀態值
        dao.insert(prVO1);

        // update
//        prVO1.setPayoutId(2);
//        prVO1.setTotalPoint(180);
//        prVO1.setPayoutDate(new Timestamp(System.currentTimeMillis()));
//        prVO1.setStatus(0); // 改為使用整數狀態值
//        dao.update(prVO1);

        // delete
//        dao.delete(2);

        // find by key
//        PayRecordVO prVO2 = dao.findByPrimaryKey(3);
//        System.out.println(prVO2 == null ? "查無此紀錄" : prVO2.getPayoutId() + ", " + prVO2.getTotalPoint() + ", " + prVO2.getPayoutDate() + ", " + prVO2.getStatus());

        // getAll
//        List<PayRecordVO> list = dao.getAll();
//        for (PayRecordVO temp : list) {
//            System.out.print(temp.getPayoutId() + ", ");
//            System.out.print(temp.getTotalPoint() + ", ");
//            System.out.print(temp.getPayoutDate() + ", ");
//            System.out.print(temp.getStatus() + ", ");
//            System.out.println();
//        }
    }
}