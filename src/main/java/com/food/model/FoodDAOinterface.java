package com.food.model;


import java.util.List;

public interface FoodDAOinterface {
    void insert(FoodVO food);
    void update(FoodVO food);
    void delete(int foodId);
    FoodVO findByPrimaryKey(int foodId);
    List<FoodVO> getAll();
}
