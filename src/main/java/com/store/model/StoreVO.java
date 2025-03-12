package com.store.model;

import java.sql.Date;
import java.io.*;

public class StoreVO implements Serializable{
	private Integer storeId;
	private String name;
	private String managerName;
	private String email;
	private String password;
	private String phoneNum;
	private String guiNum;
	private String businessRegNum;
//	private Integer foodType;
	private Date regTime;
	private Integer points;
	private Integer accStat;
	private Integer opStat;
	private Date opTime;
	private Date  pickTime;
	private Date  lastOrder;
	private Date  closeTime;
	private String address;
	private String county;
	private String district;
	private Integer postalCode;
	private Integer starNum;
	private Integer visitorsNum;
	private Integer reviewed; 
	
	public Integer getReviewed() {
		return reviewed;
	}
	public void setReviewed(Integer reviewed) {
		this.reviewed = reviewed;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getGuiNum() {
		return guiNum;
	}
	public void setGuiNum(String guiNum) {
		this.guiNum = guiNum;
	}
	public String getBusinessRegNum() {
		return businessRegNum;
	}
	public void setBusinessRegNum(String businessRegNum) {
		this.businessRegNum = businessRegNum;
	}
//	public Integer getFoodType() {
//		return foodType;
//	}
//	public void setFoodType(Integer foodType) {
//		this.foodType = foodType;
//	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Integer getAccStat() {
		return accStat;
	}
	public void setAccStat(Integer accStat) {
		this.accStat = accStat;
	}
	public Integer getOpStat() {
		return opStat;
	}
	public void setOpStat(Integer opStat) {
		this.opStat = opStat;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public Date getPickTime() {
		return pickTime;
	}
	public void setPickTime(Date pickTime) {
		this.pickTime = pickTime;
	}
	public Date getLastOrder() {
		return lastOrder;
	}
	public void setLastOrder(Date lastOrder) {
		this.lastOrder = lastOrder;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Integer getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}
	public Integer getStarNum() {
		return starNum;
	}
	public void setStarNum(Integer starNum) {
		this.starNum = starNum;
	}
	public Integer getVisitorsNum() {
		return visitorsNum;
	}
	public void setVisitorsNum(Integer visitorsNum) {
		this.visitorsNum = visitorsNum;
	}
	
	
	

}
