package com.orderdetail.model;

import java.util.List;

public interface OrderDetailDAOInterface {

	
	public void insert(OrderDetailVO orderDetailVO);
    public void update(OrderDetailVO orderDetailVO);
    public void delete(Integer orderId, Integer foodId);
    public OrderDetailVO findByPrimaryKey(Integer orderId, Integer foodId);
    public List<OrderDetailVO> getAll();

	

}
