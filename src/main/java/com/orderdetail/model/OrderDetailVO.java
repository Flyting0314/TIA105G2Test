package com.orderdetail.model;


import java.sql.Timestamp;

public class OrderDetailVO {
	
	private Integer orderId;
	private Integer foodId ;
	private Timestamp createdTime ;
	private Integer amount ;
	private Integer pointsCost ;
	private String note ;
	
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getFoodId() {
		return foodId;
	}
	public void setFoodId(Integer foodId) {
		this.foodId = foodId;
	}

	
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getPointsCost() {
		return pointsCost;
	}
	public void setPointsCost(Integer pointsCost) {
		this.pointsCost = pointsCost;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
