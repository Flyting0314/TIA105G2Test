package com.foodtype.model;

import java.util.List;

public interface FoodTypeDAO_interface {
	 public void insert(FoodTypeVO foodType);
     public void update(FoodTypeVO foodType);
     public void delete(Integer foodTypeId); 
     public FoodTypeVO findByPrimaryKey(Integer foodTypeId);
     public List<FoodTypeVO> getAll();


}
