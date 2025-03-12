package com.store.model;

import java.util.List;

public class StoreService {

	private StoreDAOInterface dao;
	
	public StoreService() {
		dao = new StoreJDBCDAO();
	}
	
	public StoreVO getOneStore(Integer storeId) {
		return dao.findByPrimaryKey(storeId);
	}
	
	 public List<StoreVO> getAll() {
	        return dao.getAll();
	}
}
