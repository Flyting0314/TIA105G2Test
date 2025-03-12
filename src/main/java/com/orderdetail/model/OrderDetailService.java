package com.orderdetail.model;


import java.sql.Timestamp;
import java.util.List;

import com.food.model.FoodDAOinterface;
import com.food.model.FoodJDBCDAO;
import com.food.model.FoodVO;


public class OrderDetailService {

	private OrderDetailDAOInterface dao;
	public OrderDetailService() {
		dao = new OrderDetailJDBCDAO();
	}

	public OrderDetailVO addOD(Integer orderId, Integer foodId, Timestamp createdTime, Integer amount, Integer pointsCost,
			String note) {

		OrderDetailVO oDVO = new OrderDetailVO();
		oDVO.setOrderId(orderId);
		oDVO.setFoodId(foodId);
		oDVO.setCreatedTime(createdTime);
		oDVO.setAmount(amount);
		oDVO.setPointsCost(pointsCost);
		oDVO.setNote(note);
		dao.insert(oDVO);

		return oDVO;
	}

	public OrderDetailVO updateOD( Timestamp createdTime, Integer amount, Integer pointsCost,
			String note,Integer orderId, Integer foodId) {
		OrderDetailVO oDVO = new OrderDetailVO();
		oDVO.setOrderId(orderId);
		oDVO.setFoodId(foodId);
		oDVO.setCreatedTime(createdTime);
		oDVO.setAmount(amount);
		oDVO.setPointsCost(pointsCost);
		oDVO.setNote(note);
		dao.update(oDVO);

		return dao.findByPrimaryKey(orderId, foodId);
	}

	public void deleteOD(Integer orderId, Integer foodId) {
		dao.delete(orderId, foodId);
	}

	public OrderDetailVO getOneOD(Integer orderId, Integer foodId) {
		return dao.findByPrimaryKey(orderId, foodId);
	}

	public List<OrderDetailVO> getAll() {
		return dao.getAll();
	}
	

}
