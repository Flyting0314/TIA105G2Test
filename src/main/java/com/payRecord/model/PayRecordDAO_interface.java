package com.payRecord.model;
import java.util.*;

//import com.pay.model.PayRecordVO;

public interface PayRecordDAO_interface {

    public void insert(PayRecordVO payRecordVO);
    public void update(PayRecordVO payRecordVO);
    public void delete(Integer payoutId);
    public PayRecordVO findByPrimaryKey(Integer payoutId);
    public List<PayRecordVO> getAll();
    //新增加兩個功能
    public List<PayRecordVO> findByStatus(Integer status);
    public List<PayRecordVO> findByMonth(String month); 
        
	}
	

