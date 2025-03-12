package com.payRecord.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PayRecordDAO implements PayRecordDAO_interface {
    
    // 一個應用程式中,針對一個資料庫,共用一個DataSource即可
    private static DataSource ds = null;
    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/group_db");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

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
            con = ds.getConnection();
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setInt(1, payRecordVO.getTotalPoint());
            pstmt.setTimestamp(2, payRecordVO.getPayoutDate());
            pstmt.setInt(3, payRecordVO.getStatus());

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
    public void update(PayRecordVO payRecordVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setInt(1, payRecordVO.getTotalPoint());
            pstmt.setTimestamp(2, payRecordVO.getPayoutDate());
            pstmt.setInt(3, payRecordVO.getStatus());
            pstmt.setInt(4, payRecordVO.getPayoutId());

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
    public void delete(Integer payoutId) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(DELETE);
            pstmt.setInt(1, payoutId);
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
            con = ds.getConnection();
            pstmt = con.prepareStatement(GET_ONE_STMT);
            pstmt.setInt(1, payoutId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                payRecordVO = new PayRecordVO();
                payRecordVO.setPayoutId(rs.getInt("payoutId"));
                payRecordVO.setTotalPoint(rs.getInt("totalPoint"));
                payRecordVO.setPayoutDate(rs.getTimestamp("payoutDate"));
                payRecordVO.setStatus(rs.getInt("status"));
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
            con = ds.getConnection();
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
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
        }
        return list;
    }
    
    
    
    //新增：以狀態查詢
    
    @Override
    public List<PayRecordVO> findByStatus(Integer status) {
        List<PayRecordVO> list = new ArrayList<>();
        PayRecordVO payRecordVO = null;
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ds.getConnection();
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
    
    
    //新增：以月份查詢
    @Override
    public List<PayRecordVO> findByMonth(String month) {
        List<PayRecordVO> list = new ArrayList<>();
        PayRecordVO payRecordVO = null;
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ds.getConnection();
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




//======

//注意：您需要在應用服務器的配置文件中配置"java:comp/env/jdbc/group_db"這個JNDI資源。如果您使用的是Tomcat，需要在context.xml中添加類似以下的配置：
//
//xml
//
//<Resource name="jdbc/group_db" auth="Container"
//type="javax.sql.DataSource" driverClassName="com.mysql.cj.jdbc.Driver"
//url="jdbc:mysql://localhost:3306/group_db?serverTimezone=Asia/Taipei"
//username="root" password="123456" maxTotal="20" maxIdle="10"
//maxWaitMillis="-1"/>




