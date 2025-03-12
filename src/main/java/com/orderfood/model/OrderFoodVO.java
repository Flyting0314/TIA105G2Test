package com.orderfood.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrderFoodVO implements Serializable {
    private Integer orderId;
    private Integer storeId;
    private Integer memberId;
    private Integer  rate;
    private String comment;
    private Integer serveStat;
    private Integer pickStat;
    private Timestamp pickTime;
    private Timestamp createdTime;

    // Constructor
    public OrderFoodVO() {
    }

    // Getter & Setter 方法
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getServeStat() {
        return serveStat;
    }

    public void setServeStat(Integer serveStat) {
        this.serveStat = serveStat;
    }

    public Integer getPickStat() {
        return pickStat;
    }

    public void setPickStat(Integer pickStat) {
        this.pickStat = pickStat;
    }

    public Timestamp getPickTime() {
        return pickTime;
    }

    public void setPickTime(Timestamp pickTime) {
        this.pickTime = pickTime;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    // toString 方法 (方便除錯)
    @Override
    public String toString() {
        return "OrderVO{" +
                "orderId=" + orderId +
                ", storeId=" + storeId +
                ", memberId=" + memberId +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", serveStat=" + serveStat +
                ", pickStat=" + pickStat +
                ", pickTime=" + pickTime +
                ", createdTime=" + createdTime +
                '}';
    }
}