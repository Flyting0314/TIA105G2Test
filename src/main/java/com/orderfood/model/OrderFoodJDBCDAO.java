package com.orderfood.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderFoodJDBCDAO implements OrderFoodDAOinterface{
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/ allieatfinal_db01?serverTimezone=Asia/Taipei";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    
    private static final String INSERT_STMT =
        "INSERT INTO  orderList (storeId, memberId, rate, comment, servestat, pickStat, pickTime, createdTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_ALL_STMT =
        "SELECT * FROM  orderList ORDER BY orderId";

    private static final String GET_ONE_STMT =
        "SELECT * FROM  orderList WHERE orderId = ?";

    private static final String DELETE =
        "DELETE FROM  orderList WHERE orderId = ?";

    private static final String UPDATE =
        "UPDATE  orderList SET storeId=?, memberId=?, rate=?, comment=?, servestat=?, pickStat=?, pickTime=?, createdTime=? WHERE orderid = ?";

    // **載入驅動**
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver.", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void insert(OrderFoodVO orderVO) {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_STMT, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, orderVO.getStoreId());
            pstmt.setInt(2, orderVO.getMemberId());
            pstmt.setInt(3, orderVO.getRate());
            pstmt.setString(4, orderVO.getComment());
            pstmt.setInt(5, orderVO.getServeStat());
            pstmt.setInt(6, orderVO.getPickStat());
            pstmt.setTimestamp(7, orderVO.getPickTime());
            pstmt.setTimestamp(8, orderVO.getCreatedTime());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ 訂單新增成功！");
            } else {
                System.out.println("❌ 訂單新增失敗！");
            }
        } catch (SQLException e) {
            throw new RuntimeException("A database error occurred.", e);
        }
        
    }

    @Override
    public void update(OrderFoodVO orderVO) {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE)) {

            pstmt.setInt(1, orderVO.getStoreId());
            pstmt.setInt(2, orderVO.getMemberId());
            pstmt.setInt(3, orderVO.getRate());
            pstmt.setString(4, orderVO.getComment());
            pstmt.setInt(5, orderVO.getServeStat());
            pstmt.setInt(6, orderVO.getPickStat());
            pstmt.setTimestamp(7, orderVO.getPickTime());
            pstmt.setTimestamp(8, orderVO.getCreatedTime());
            pstmt.setInt(9, orderVO.getOrderId());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ 訂單更新成功！");
            } else {
                System.out.println("❌ 訂單更新失敗！");
            }
        } catch (SQLException e) {
            throw new RuntimeException("A database error occurred.", e);
        }
    }

    @Override
    public void delete(Integer orderId) {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE)) {

            pstmt.setInt(1, orderId);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ 訂單刪除成功！");
            } else {
                System.out.println("❌ 找不到該訂單，刪除失敗！");
            }
        } catch (SQLException e) {
            throw new RuntimeException("A database error occurred.", e);
        }
    }

    @Override
    public OrderFoodVO findByPrimaryKey(Integer orderId) {
    	OrderFoodVO orderVO = null;
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ONE_STMT)) {

            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    orderVO = mapResultSetToOrderVO(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("A database error occurred.", e);
        }
        return orderVO;
    }

    @Override
    public List<OrderFoodVO> getAll() {
        List<OrderFoodVO> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ALL_STMT);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToOrderVO(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("A database error occurred.", e);
        }
        return list;
    }

    private OrderFoodVO mapResultSetToOrderVO(ResultSet rs) throws SQLException {
    	OrderFoodVO orderVO = new OrderFoodVO();
        orderVO.setOrderId(rs.getInt("orderId"));
        orderVO.setStoreId(rs.getInt("storeId"));
        orderVO.setMemberId(rs.getInt("memberId"));
        orderVO.setRate(rs.getInt("rate"));
        orderVO.setComment(rs.getString("comment"));
        orderVO.setServeStat(rs.getInt("serveStat"));
        orderVO.setPickStat(rs.getInt("pickStat"));
        orderVO.setPickTime(rs.getTimestamp("pickTime"));
        orderVO.setCreatedTime(rs.getTimestamp("createdTime"));
        return orderVO;
    }




    public static void main(String[] args) {
        // **建立 DAO 物件**
        OrderFoodJDBCDAO dao = new OrderFoodJDBCDAO();

////         **建立一個新的訂單物件**
//        OrderFoodVO newOrder = new OrderFoodVO();
//        newOrder.setStoreId(1); // 
//        newOrder.setMemberId(2); //
//        newOrder.setRate(4); // 
//        newOrder.setComment("服務不錯，餐點好吃！");
//        newOrder.setServeStat(1); // 
//        newOrder.setPickStat(0); // 
//        newOrder.setPickTime(new Timestamp(System.currentTimeMillis())); 
//        newOrder.setCreatedTime(new Timestamp(System.currentTimeMillis()));    
//        dao.insert(newOrder);
//        System.out.println("🎉 測試完成！");
//        
     // 修改
//      OrderFoodVO orderFoodVO2 = new OrderFoodVO();
//
//      orderFoodVO2.setOrderId(6);
//      orderFoodVO2.setStoreId(1);
//      orderFoodVO2.setMemberId(2);
//      orderFoodVO2.setRate(4);
//      orderFoodVO2.setComment("改完");
//      orderFoodVO2.setServeStat(1);
//      orderFoodVO2.setPickStat(1);
//      orderFoodVO2.setPickTime(new Timestamp(System.currentTimeMillis()));
//      orderFoodVO2.setCreatedTime(new Timestamp(System.currentTimeMillis()));
//   
//      dao.update(orderFoodVO2);
//      System.out.println("🎉 修改完成！");



      dao.delete(11);
      System.out.println("🎉 刪除完成！");

//查詢單筆
//      OrderFoodVO orderFoodVO3 = dao.findByPrimaryKey(1);
//      System.out.print(orderFoodVO3.getOrderId() + ",");
//      System.out.print(orderFoodVO3.getStoreId() + ",");
//      System.out.print(orderFoodVO3.getMemberId() + ",");
//      System.out.print(orderFoodVO3.getRate() + ",");
//      System.out.print(orderFoodVO3.getComment() + ",");
//      System.out.print(orderFoodVO3.getServeStat() + ",");
//      System.out.print(orderFoodVO3.getPickStat() + ",");
//      System.out.print(orderFoodVO3.getPickTime() + ",");
//      System.out.print(orderFoodVO3.getCreatedTime() + ",");
//      System.out.println("---------------------");




//查詢全部
//      List<OrderFoodVO> list = dao.getAll();
//      for (OrderFoodVO orderList : list) {
//
//          System.out.print(orderList.getOrderId() + ",");
//          System.out.print(orderList.getStoreId() + ",");
//          System.out.print(orderList.getMemberId() + ",");
//          System.out.print(orderList.getRate() + ",");
//          System.out.print(orderList.getComment() + ",");
//          System.out.print(orderList.getServeStat() + ",");
//          System.out.print(orderList.getPickStat() + ",");
//          System.out.print(orderList.getPickTime() + ",");
//          System.out.print(orderList.getCreatedTime() + ",");
//          System.out.println();
//      }
  

}

}