package com.payRecord.model;

import java.util.List;
import java.sql.Timestamp;

public class PayRecordService {

    private PayRecordDAO_interface dao;

    public PayRecordService() {
        dao = new PayRecordDAO();
    }

    public PayRecordVO addPayRecord(Integer totalPoint, Timestamp payoutDate, Integer status) {
        PayRecordVO payRecordVO = new PayRecordVO();

        payRecordVO.setTotalPoint(totalPoint);
        payRecordVO.setPayoutDate(payoutDate);
        payRecordVO.setStatus(status);
        dao.insert(payRecordVO);

        return payRecordVO;
    }

    public PayRecordVO updatePayRecord(Integer payoutId, Integer totalPoint, Timestamp payoutDate, Integer status) {
        PayRecordVO payRecordVO = new PayRecordVO();

        payRecordVO.setPayoutId(payoutId);
        payRecordVO.setTotalPoint(totalPoint);
        payRecordVO.setPayoutDate(payoutDate);
        payRecordVO.setStatus(status);
        dao.update(payRecordVO);

        return payRecordVO;
    }

    public void deletePayRecord(Integer payoutId) {
        dao.delete(payoutId);
    }

    public PayRecordVO getOnePayRecord(Integer payoutId) {
        return dao.findByPrimaryKey(payoutId);
    }

    public List<PayRecordVO> getAll() {
        return dao.getAll();
    }
    
    
    
    //新功能
    
    public List<PayRecordVO> getPayRecordsByStatus(Integer status) {
        return dao.findByStatus(status);
    }

    public List<PayRecordVO> getPayRecordsByMonth(String queryMonth) {
        return dao.findByMonth(queryMonth);
    }
}