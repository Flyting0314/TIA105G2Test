package com.member.model;


import java.util.*;

public interface MemberDAOinterface {
    public void insert(MemberVO memberVO);
    public void update(MemberVO memberVO);
    public void delete(Integer memberId);
    public MemberVO findByPrimaryKey(Integer memberId);
    public List<MemberVO> getAll();
    // 萬用複合查詢(傳入參數型態 Map，回傳 List)
    // public List<MemberVO> getAll(Map<String, String[]> map);
}



