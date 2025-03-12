package com.store.model;

import java.util.List;

public interface StoreDAOInterface {
	 public void insert(StoreVO storeVO);
     public void update(StoreVO storeVO);
     public void delete(Integer storeId); //正常不刪資料
     public StoreVO findByPrimaryKey(Integer storeId);
     public List<StoreVO> getAll();

}
