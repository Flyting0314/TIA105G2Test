package com.food.model;

import java.util.List;

public interface FoodDAOInterface {
    void insert(FoodVO food);
    void update(FoodVO food);
    void delete(Integer foodId);
    FoodVO findByPrimaryKey(Integer foodId);
    List<FoodVO> getAll();
}
