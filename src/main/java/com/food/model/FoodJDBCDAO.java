package com.food.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodJDBCDAO implements FoodDAOinterface {
    private static final String URL = "jdbc:mysql://localhost:3306/allieatfinal_db01?serverTimezone=Asia/Taipei";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static final String INSERT_SQL = "INSERT INTO food (storeId, name, createdTime, status, amount, photo, cost) VALUES (?, ?, NOW(), ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE food SET storeId=?, name=?, status=?, amount=?, photo=?, cost=? WHERE foodId=?";
    private static final String DELETE_SQL = "DELETE FROM food WHERE foodId=?";
    private static final String FIND_BY_PK_SQL = "SELECT * FROM food WHERE foodId=?";
    private static final String GET_ALL_SQL = "SELECT * FROM food";

    @Override
    public void insert(FoodVO food) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(INSERT_SQL)) {

        	pstmt.setInt(1, food.getStoreId());
        	pstmt.setString(2, food.getName());
        	pstmt.setInt(3, food.getStatus());
        	pstmt.setInt(4, food.getAmount());
        	pstmt.setString(5, food.getPhoto());
        	pstmt.setInt(6, food.getCost());

        	pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(FoodVO food) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(UPDATE_SQL)) {

        	pstmt.setInt(1, food.getStoreId());
        	pstmt.setString(2, food.getName());
        	pstmt.setInt(3, food.getStatus());
        	pstmt.setInt(4, food.getAmount());
        	pstmt.setString(5, food.getPhoto());
        	pstmt.setInt(6, food.getCost());
        	pstmt.setInt(7, food.getFoodId());

        	pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int foodId) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(DELETE_SQL)) {

        	pstmt.setInt(1, foodId);
        	pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FoodVO findByPrimaryKey(int foodId) {
        FoodVO food = null;
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(FIND_BY_PK_SQL)) {

        	pstmt.setInt(1, foodId);
            ResultSet rs = pstmt.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return food;
    }

    @Override
    public List<FoodVO> getAll() {
        List<FoodVO> list = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(GET_ALL_SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                FoodVO food = new FoodVO();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        FoodJDBCDAO dao = new FoodJDBCDAO();

//        // === 1️⃣ 新增測試 ===
        FoodVO food1 = new FoodVO();
        food1.setStoreId(1);
        food1.setName("炸雞");
        food1.setStatus(1);
        food1.setAmount(50);
        food1.setPhoto("images/food/fried_chicken.jpg");
        food1.setCost(150);
        dao.insert(food1);
        System.out.println("✅ 新增成功！");

        // === 2️⃣ 單筆查詢測試 ===
//        FoodVO food2 = dao.findByPrimaryKey(1);
//        if (food2 != null) {
//            System.out.println("🔍 查詢結果:");
//            System.out.println("名稱: " + food2.getName());
//            System.out.println("啟用狀態: " + food2.getStatus());
//            System.out.println("餐點數量: " + food2.getAmount());
//            System.out.println("餐點照片: " + food2.getPhoto());
//            System.out.println("餐點點數: " + food2.getCost());
//        } else {
//            System.out.println("⚠️ 查詢失敗：找不到餐點 ID = 1");
//        }

//        // === 3️⃣ 更新測試 ===
        FoodVO food3 = dao.findByPrimaryKey(1); 
        if (food3 != null) {
            food3.setName("香辣炸雞");
            food3.setStatus(0);  
            food3.setAmount(30);  
            food3.setPhoto("images/food/spicy_fried_chicken.jpg");
            food3.setCost(180);  
            dao.update(food3);
            System.out.println("✅ 更新成功！");
        } else {
            System.out.println("⚠️ 無法更新：找不到餐點 ID = 1");
        }
//
//        // === 4️⃣ 查詢全部測試 ===
//        List<FoodVO> foodList = dao.getAll();
//        System.out.println("📜 所有餐點資料：");
//        for (FoodVO foodItem : foodList) {
//            System.out.println("-------------------------");
//            System.out.println("餐點 ID: " + foodItem.getFoodId());
//            System.out.println("店家 ID: " + foodItem.getStoreId());
//            System.out.println("名稱: " + foodItem.getName());
//            System.out.println("上架時間: " + foodItem.getCreatedTime());
//            System.out.println("啟用狀態: " + foodItem.getStatus());
//            System.out.println("餐點數量: " + foodItem.getAmount());
//            System.out.println("餐點照片: " + foodItem.getPhoto());
//            System.out.println("餐點點數: " + foodItem.getCost());
//        }

//        // === 5️ 刪除測試 ===
//        int deleteFoodId = 3; 
//        FoodVO food4 = dao.findByPrimaryKey(deleteFoodId);
//        if (food4 != null) {
//            dao.delete(deleteFoodId);
//            System.out.println("✅ 刪除成功！ID：" + deleteFoodId);
//        } else {
//            System.out.println("⚠️ 無法刪除：找不到餐點 ID = " + deleteFoodId);
//        }
    }
}

