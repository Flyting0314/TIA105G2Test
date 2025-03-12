package com.member.model;

import java.sql.*;
import java.util.*;

public class MemberJDBCDAO implements MemberDAOinterface {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/allieatfinal_db01?serverTimezone=Asia/Taipei";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    // **SQL 語句修正**
    private static final String INSERT_STMT = 
        "INSERT INTO member (organizationId, name, idnum, permAddr, address, regTime, kycImage, email, phone, account, password, pointsBalance, unclaimedMealCount, accStat, reviewed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String GET_ALL_STMT = 
        "SELECT memberid, organizationId, name, idnum, permAddr, address, regTime, kycImage, email, phone, account, password, pointsBalance, unclaimedMealCount, accStat, reviewed FROM member ORDER BY memberId";
   
    private static final String GET_ONE_STMT = 
        "SELECT memberid, organizationId, name, idnum, permAddr, address, regTime, kycImage, email, phone, account, password, pointsBalance, unclaimedMealCount, accStat, reviewed FROM member WHERE memberId = ?";
    
    private static final String DELETE = 
        "DELETE FROM member WHERE memberId = ?";
    
    private static final String UPDATE = 
        "UPDATE member SET organizationId=?, name=?, idnum=?, permAddr=?, address=?, regTime=?, kycImage=?, email=?, phone=?, account=?, password=?, pointsBalance=?, unclaimedMealCount=?, accStat=?, reviewed=? WHERE memberId = ?";

    // **載入驅動**
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver.", e);
        }
    }

    // **取得資料庫連線**
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void insert(MemberVO memberVO) {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_STMT, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, memberVO.getOrganizationId());
            pstmt.setString(2, memberVO.getName());
            pstmt.setString(3, memberVO.getIdNum());
            pstmt.setString(4, memberVO.getPermAddr());
            pstmt.setString(5, memberVO.getAddress());
            pstmt.setTimestamp(6, memberVO.getRegTime());
            pstmt.setString (7, memberVO.getKycImage());
            pstmt.setString(8, memberVO.getEmail());
            pstmt.setString(9, memberVO.getPhone());
            pstmt.setString(10, memberVO.getAccount());
            pstmt.setString(11, memberVO.getPassword());
            pstmt.setInt(12, memberVO.getPointsBalance());
            pstmt.setInt(13, memberVO.getUnclaimedMealCount());
            pstmt.setInt(14, memberVO.getAccStat());
            pstmt.setInt(15, memberVO.getReviewed());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("A database error occurred.", e);
        }
    }

    @Override
    public void update(MemberVO memberVO) {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE)) {

            pstmt.setInt(1, memberVO.getOrganizationId());
            pstmt.setString(2, memberVO.getName());
            pstmt.setString(3, memberVO.getIdNum());
            pstmt.setString(4, memberVO.getPermAddr());
            pstmt.setString(5, memberVO.getAddress());
            pstmt.setTimestamp(6, memberVO.getRegTime());
            pstmt.setString (7, memberVO.getKycImage());
            pstmt.setString(8, memberVO.getEmail());
            pstmt.setString(9, memberVO.getPhone());
            pstmt.setString(10, memberVO.getAccount());
            pstmt.setString(11, memberVO.getPassword());
            pstmt.setInt(12, memberVO.getPointsBalance());
            pstmt.setInt(13, memberVO.getUnclaimedMealCount());
            pstmt.setInt(14, memberVO.getAccStat());
            pstmt.setInt(15, memberVO.getReviewed());
            pstmt.setInt(16, memberVO.getMemberId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("A database error occurred.", e);
        }
    }

    @Override
    public void delete(Integer memberId) {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE)) {

            pstmt.setInt(1, memberId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("A database error occurred.", e);
        }
    }

    @Override
    public MemberVO findByPrimaryKey(Integer memberId) {
        MemberVO memberVO = null;
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ONE_STMT)) {

            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    memberVO = new MemberVO();
                    memberVO.setMemberId(rs.getInt("memberId"));
                    memberVO.setOrganizationId(rs.getInt("organizationId"));
                    memberVO.setName(rs.getString("name"));
                    memberVO.setIdNum(rs.getString("idnum"));
                    memberVO.setPermAddr(rs.getString("permAddr"));
                    memberVO.setAddress(rs.getString("address"));
                    memberVO.setRegTime(rs.getTimestamp("regTime"));
                    memberVO.setKycImage(rs.getString ("kycImage"));
                    memberVO.setEmail(rs.getString("email"));
                    memberVO.setPhone(rs.getString("phone"));
                    memberVO.setAccount(rs.getString("account"));
                    memberVO.setPassword(rs.getString("password"));
                    memberVO.setPointsBalance(rs.getInt("pointsBalance"));
                    memberVO.setUnclaimedMealCount(rs.getInt("unclaimedMealCount"));
                    memberVO.setAccStat(rs.getInt("accStat"));
                    memberVO.setReviewed(rs.getInt("reviewed"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("A database error occurred.", e);
        }
        return memberVO;
    }

    @Override
    public List<MemberVO> getAll() {
        List<MemberVO> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ALL_STMT);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(findByPrimaryKey(rs.getInt("memberId")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("A database error occurred.", e);
        }
        return list;
    }


public static void main(String[] args) {
    // **建立 DAO 物件**
    MemberJDBCDAO dao = new MemberJDBCDAO();
    MemberVO memVO2  = new MemberVO();
    
    

    // **建立一個新的會員物件**
    MemberVO newMember = new MemberVO();
    newMember.setOrganizationId(9);
    newMember.setName("李小鳳");
    newMember.setIdNum("B987654321");
    newMember.setPermAddr("台北市西屯區");
    newMember.setAddress("台北市南屯區");
    newMember.setRegTime(new Timestamp(System.currentTimeMillis())); // 當前時間
    newMember.setKycImage("一張測試圖片"); // 模擬圖片
    newMember.setEmail("bru@example.com");
    newMember.setPhone("0999654321");
    newMember.setAccount("brulee");
    newMember.setPassword("dra123");
    newMember.setPointsBalance(200);
    newMember.setUnclaimedMealCount(3);
    newMember.setAccStat(1);
    newMember.setReviewed(1);
    dao.insert(newMember);
    System.out.println("上傳完成！");
 

//修改

	memVO2.setMemberId(13);
	memVO2.setOrganizationId(1);
	memVO2.setName("王n0");
	memVO2.setIdNum("A123456789");
	memVO2.setPermAddr("台北市");
	memVO2.setAddress("台北市");
	memVO2.setRegTime(new Timestamp(System.currentTimeMillis()));
	memVO2.setKycImage("123@.com");
	memVO2.setEmail("123@.com");
	memVO2.setPhone("0912345678");
	memVO2.setAccount("a123");
	memVO2.setPassword("b123");
	memVO2.setPointsBalance(100);
	memVO2.setUnclaimedMealCount(0);
	memVO2.setAccStat(1);
	memVO2.setReviewed(1);
	dao.update(memVO2);
	System.out.println("更新完成！");
	
	
//查詢單筆
	MemberVO memVO3 = dao.findByPrimaryKey(10);
	
	System.out.print(memVO3.getOrganizationId() + ",");
	System.out.print(memVO3.getName() + ",");
	System.out.print(memVO3.getIdNum() + ",");
	System.out.print(memVO3.getPermAddr() + ",");
	System.out.print(memVO3.getAddress() + ",");
	System.out.print(memVO3.getRegTime() + ",");
	System.out.print(memVO3.getKycImage() + ",");
	System.out.print(memVO3.getEmail() + ",");
	System.out.print(memVO3.getPhone() + ",");
	System.out.print(memVO3.getAccount() + ",");
	System.out.print(memVO3.getPassword() + ",");
	System.out.print(memVO3.getPointsBalance() + ",");
	System.out.print(memVO3.getUnclaimedMealCount() + ",");
	System.out.print(memVO3.getAccStat() + ",");
	System.out.print(memVO3.getReviewed() + ",");
	System.out.println("---------------------");

	//查詢
	List<MemberVO> list = dao.getAll();
	for (MemberVO mem : list) {
	    System.out.print(mem.getMemberId() + ",");
	    System.out.print(mem.getOrganizationId() + ",");
	    System.out.print(mem.getName() + ",");
	    System.out.print(mem.getIdNum() + ",");
	    System.out.print(mem.getPermAddr() + ",");
	    System.out.print(mem.getAddress() + ",");
	    System.out.print(mem.getRegTime() + ",");
	    System.out.print(mem.getKycImage() + ",");
	    System.out.print(mem.getEmail() + ",");
	    System.out.print(mem.getPhone() + ",");
	    System.out.print(mem.getAccount() + ",");
	    System.out.print(mem.getPassword() + ",");
	    System.out.print(mem.getPointsBalance() + ",");
	    System.out.print(mem.getUnclaimedMealCount() + ",");
	    System.out.print(mem.getAccStat() + ",");
	    System.out.print(mem.getReviewed() + ",");
	    
	    
	 //刪除 
	    dao.delete(13);  
	    System.out.println("刪除成功");
	}

}
}


