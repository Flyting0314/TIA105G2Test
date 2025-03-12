package com.orderfood.model;

import java.sql.Timestamp;
import java.util.List;

import com.member.model.MemberDAOinterface;
import com.member.model.MemberJDBCDAO;
import com.member.model.MemberVO;
import com.orderdetail.model.OrderDetailDAOInterface;
import com.orderdetail.model.OrderDetailJDBCDAO;
import com.orderdetail.model.OrderDetailVO;
import com.store.model.StoreDAOInterface;
import com.store.model.StoreJDBCDAO;
import com.store.model.StoreVO;


public class OrderFoodService {

	private OrderFoodDAOinterface dao;
	private StoreDAOInterface dao1;
	private MemberDAOinterface dao2;
	private OrderDetailDAOInterface dao3;

	public OrderFoodService() {
		dao = new OrderFoodJDBCDAO();
		dao1= new StoreJDBCDAO();
		dao2= new MemberJDBCDAO();
		dao3= new OrderDetailJDBCDAO();
	}

	public OrderFoodVO addOF(Integer storeId, Integer memberId, Integer rate, String comment, Integer serveStat,
			Integer pickStat, Timestamp pickTime, Timestamp createdTime) {

		OrderFoodVO ofVO = new OrderFoodVO();
		ofVO.setStoreId(storeId);
		ofVO.setMemberId(memberId);
		ofVO.setRate(rate);
		ofVO.setComment(comment);
		ofVO.setServeStat(serveStat);
		ofVO.setPickStat(pickStat);
		ofVO.setPickTime(pickTime);
		ofVO.setCreatedTime(createdTime);
		dao.insert(ofVO);

		return ofVO;
	}

	public OrderFoodVO updateOF(Integer orderId, Integer storeId, Integer memberId, Integer rate, String comment,
			Integer serveStat, Integer pickStat, Timestamp pickTime, Timestamp createdTime) {
		OrderFoodVO ofVO = new OrderFoodVO();
		ofVO.setOrderId(orderId);
		ofVO.setStoreId(storeId);
		ofVO.setMemberId(memberId);
		ofVO.setRate(rate);
		ofVO.setComment(comment);
		ofVO.setServeStat(serveStat);
		ofVO.setPickStat(pickStat);
		ofVO.setPickTime(pickTime);
		ofVO.setCreatedTime(createdTime);
		dao.update(ofVO);

		return dao.findByPrimaryKey(orderId);
	}

	public void deleteOF(Integer orderId) {
		dao.delete(orderId);
	}

	public OrderFoodVO getOneOF(Integer orderId) {
		return dao.findByPrimaryKey(orderId);
	}

	public List<OrderFoodVO> getAll() {
		return dao.getAll();
	}
	

	
	public List<StoreVO> getAllFromStore() {
		return dao1.getAll();
	}
	
	
	public List<MemberVO> getAllFromMember() {
		return dao2.getAll();
	}
	
	public List<OrderDetailVO> getAllOD() {
		return dao3.getAll();
	}
	
}
