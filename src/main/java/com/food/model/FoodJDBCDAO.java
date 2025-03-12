package com.food.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodJDBCDAO implements FoodDAOInterface {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/allieatfinal_db01?serverTimezone=Asia/Taipei";
    String userid = "root";
    String passwd = "123456";

    private static final String INSERT_STMT = 
        "INSERT INTO food (storeId, name, createdTime, status, amount, photo, cost) VALUES (?, ?, NOW(), ?, ?, ?, ?)";
    private static final String UPDATE_STMT = 
        "UPDATE food SET storeId=?, name=?, status=?, amount=?, photo=?, cost=? WHERE foodId=?";
    private static final String DELETE_STMT = 
        "DELETE FROM food WHERE foodId=?";
    private static final String GET_ONE_STMT = 
        "SELECT * FROM food WHERE foodId=?";
    private static final String GET_ALL_STMT = 
        "SELECT * FROM food";

    @Override
    public void insert(FoodVO food) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setInt(1, food.getStoreId());
            pstmt.setString(2, food.getName());
            // createdTime 採用 NOW() 數值不需設定
            pstmt.setInt(3, food.getStatus());
            pstmt.setInt(4, food.getAmount());
            pstmt.setString(5, food.getPhoto());
            pstmt.setInt(6, food.getCost());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
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
    public void update(FoodVO food) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE_STMT);

            pstmt.setInt(1, food.getStoreId());
            pstmt.setString(2, food.getName());
            pstmt.setInt(3, food.getStatus());
            pstmt.setInt(4, food.getAmount());
            pstmt.setString(5, food.getPhoto());
            pstmt.setInt(6, food.getCost());
            pstmt.setInt(7, food.getFoodId());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
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

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(DELETE_STMT);

            pstmt.setInt(1, foodId);

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
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
        FoodVO food = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, foodId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                food = new FoodVO();
                food.setFoodId(rs.getInt("foodId"));
                food.setStoreId(rs.getInt("storeId"));
                food.setName(rs.getString("name"));
                food.setCreatedTime(rs.getTimestamp("createdTime"));
                food.setStatus(rs.getInt("status"));
                food.setAmount(rs.getInt("amount"));
                food.setPhoto(rs.getString("photo"));
                food.setCost(rs.getInt("cost"));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
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
        return food;
    }

    @Override
    public List<FoodVO> getAll() {
        List<FoodVO> list = new ArrayList<FoodVO>();
        FoodVO food = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                food = new FoodVO();
                food.setFoodId(rs.getInt("foodId"));
                food.setStoreId(rs.getInt("storeId"));
                food.setName(rs.getString("name"));
                food.setCreatedTime(rs.getTimestamp("createdTime"));
                food.setStatus(rs.getInt("status"));
                food.setAmount(rs.getInt("amount"));
                food.setPhoto(rs.getString("photo"));
                food.setCost(rs.getInt("cost"));
                list.add(food);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
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

    public static void main(String[] args) {

        FoodJDBCDAO dao = new FoodJDBCDAO();

        // 新增
//        FoodVO food = new FoodVO();
//        food.setStoreId(1);
//        food.setName("炸雞");
//        food.setStatus(1);
//        food.setAmount(50);
//        food.setPhoto("images/food/fried_chicken.jpg");
//        food.setCost(150);
//        dao.insert(food);

        // 修改
//        FoodVO food2 = new FoodVO();
//        food2.setFoodId(2);
//        food2.setStoreId(2);
//        food2.setName("烤鴨");
//        food2.setStatus(1);
//        food2.setAmount(30);
//        food2.setPhoto("images/food/roast_duck.jpg");
//        food2.setCost(200);
//        dao.update(food2);

        // 刪除
//        dao.delete(1);

        // 查詢
//        FoodVO result = dao.findByPrimaryKey(2);
//        System.out.println("查詢結果: " + result.getName());

        // 查詢
        List<FoodVO> list = dao.getAll();
        for (FoodVO foodItem : list) {
            System.out.print(foodItem.getFoodId() + ",");
            System.out.print(foodItem.getStoreId() + ",");
            System.out.print(foodItem.getName() + ",");
            System.out.print(foodItem.getCreatedTime() + ",");
            System.out.print(foodItem.getStatus() + ",");
            System.out.print(foodItem.getAmount() + ",");
            System.out.print(foodItem.getPhoto() + ",");
            System.out.print(foodItem.getCost());
            System.out.println();
        }
    }
}
