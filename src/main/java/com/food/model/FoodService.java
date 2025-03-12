package com.food.model;

import java.util.List;

public class FoodService {
	
	private FoodDAOInterface dao;
	
	public FoodService() {
		dao = new FoodJDBCDAO();
	}
	
	public FoodVO addFood(Integer storeId, String name, 
			Integer status, Integer amount, String photo, Integer cost) {
		
		FoodVO foodVO = new FoodVO();
		
		foodVO.setStoreId(storeId);
		foodVO.setName(name);
		foodVO.setStatus(status);
		foodVO.setAmount(amount);
		foodVO.setPhoto(photo);
		foodVO.setCost(cost);
		dao.insert(foodVO);
		
		return foodVO;
	}
	
	public FoodVO updateFood(Integer foodId, Integer storeId, String name, java.sql.Timestamp createdTime,
			Integer status, Integer amount, String photo, Integer cost) {
		
		FoodVO foodVO = new FoodVO();
		
		foodVO.setFoodId(foodId);
		foodVO.setStoreId(storeId);
		foodVO.setName(name);
		foodVO.setCreatedTime(createdTime);
		foodVO.setStatus(status);
		foodVO.setAmount(amount);
		foodVO.setPhoto(photo);
		foodVO.setCost(cost);
		dao.update(foodVO);
		
		return foodVO;
	}
	
	public void deleteFood(Integer foodId) {
		dao.delete(foodId);
	}
	
	public FoodVO getOneFood(Integer foodId) {
		return dao.findByPrimaryKey(foodId);
	}
	
	public List<FoodVO> getAll(){
		return dao.getAll();
	}
	
}
