package com.payRecord.model;
import java.sql.Timestamp;
import java.sql.Date;
//import java.time.LocalDateTime;

//pay_record 點數發放表格
public class PayRecordVO implements java.io.Serializable {
	private Integer payoutId;
	private Integer totalPoint;
	private Timestamp payoutDate; 
	private Integer status; //用 Integer 來對應 MySQL 的 TINYINT 型別 
	
	//payoutId 的 getter/setter
	public Integer getPayoutId() {
		return payoutId;
	}
	
	public void setPayoutId(Integer payoutId) {
		this.payoutId = payoutId;
	}
	
	//totalPoint 的 getter/setter
	public Integer getTotalPoint() {
		return totalPoint;
	}
	
	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}
	
	//payoutDate 的 getter/setter
	public Timestamp getPayoutDate() {
		return payoutDate;
	}
	
	public void setPayoutDate(Timestamp payoutDate) {
		this.payoutDate = payoutDate;
	}
	
	//status 的 getter/setter
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}