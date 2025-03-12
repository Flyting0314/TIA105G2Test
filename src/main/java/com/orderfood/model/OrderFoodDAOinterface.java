package com.orderfood.model;


import java.util.List;

public interface OrderFoodDAOinterface {
    void insert(OrderFoodVO ordVO);
    void update(OrderFoodVO ordVO);
    void delete(Integer orderId);
    OrderFoodVO findByPrimaryKey(Integer orderId);
    List<OrderFoodVO> getAll();
}