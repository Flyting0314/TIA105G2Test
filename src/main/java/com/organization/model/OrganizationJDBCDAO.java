package com.organization.model;

import java.sql.*;
import java.util.*;

import com.member.model.MemberVO;


public class OrganizationJDBCDAO implements OrganizationDAOInterface {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/allieatfinal_db01?serverTimezone=Asia/Taipei";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static final String GET_Members_ByOrganizationId_STMT = "SELECT memberId,organizationId, name, idNum, permAddr, address, regTime, kycImage, email, phone, account, password, pointsBalance, unclaimedMealCount, accStat, reviewed FROM member where organizationId = ? order by memberId";
    private static final String INSERT_STMT = "INSERT INTO organization (name, type, createdTime,status) VALUES (?,?, ?, ?)";
    private static final String UPDATE_STMT = "UPDATE organization SET name=?, type=?, status=?, createdTime=CURRENT_TIMESTAMP WHERE organizationId=?";
    private static final String DELETE_STMT = "DELETE FROM organization WHERE organizationId=?";
    private static final String GET_ONE_STMT = "SELECT organizationId, name, type, createdTime, status FROM organization WHERE organizationId=?";
    private static final String GET_ALL_STMT = "SELECT organizationId, name, type, createdTime, status FROM organization ORDER BY organizationId";

 

    public void insert(OrganizationVO organizationVO) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(INSERT_STMT, Statement.RETURN_GENERATED_KEYS)) {

        	pstmt.setString(1, organizationVO.getName());
            pstmt.setString(2, organizationVO.getType());
            pstmt.setTimestamp(3, organizationVO.getCreatedTime());
            pstmt.setInt(4, organizationVO.getStatus());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                organizationVO.setOrganizationId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("資料庫錯誤: " + e.getMessage());
        }
    }

    @Override
    public void update(OrganizationVO organizationVO) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(UPDATE_STMT)) {

        	pstmt.setString(1, organizationVO.getName());
            pstmt.setString(2, organizationVO.getType());
            pstmt.setTimestamp(3, organizationVO.getCreatedTime());
            pstmt.setInt(4, organizationVO.getStatus());
            pstmt.setInt(5, organizationVO.getOrganizationId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("資料庫錯誤: " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer organizationId) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(DELETE_STMT)) {

            pstmt.setInt(1, organizationId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("資料庫錯誤: " + e.getMessage());
        }
    }

    @Override
    public OrganizationVO findByPrimaryKey(Integer organizationId) {
        OrganizationVO organizationVO = null;
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(GET_ONE_STMT)) {

            pstmt.setInt(1, organizationId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                organizationVO = new OrganizationVO();
                organizationVO.setOrganizationId(rs.getInt("organizationId"));
                organizationVO.setName(rs.getString("name"));
                organizationVO.setType(rs.getString("type"));
                organizationVO.setCreatedTime(rs.getTimestamp("createdTime"));
                organizationVO.setStatus(rs.getInt("status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("資料庫錯誤: " + e.getMessage());
        }
        return organizationVO;
    }

    @Override
    public List<OrganizationVO> getAll() {
        List<OrganizationVO> list = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(GET_ALL_STMT);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                OrganizationVO organizationVO = new OrganizationVO();
                organizationVO.setOrganizationId(rs.getInt("organizationId"));
                organizationVO.setName(rs.getString("name"));
                organizationVO.setType(rs.getString("type"));
                organizationVO.setCreatedTime(rs.getTimestamp("createdTime"));
                organizationVO.setStatus(rs.getInt("status"));
                list.add(organizationVO);
            }

        } catch (SQLException e) {
            throw new RuntimeException("資料庫錯誤: " + e.getMessage());
        }
        return list;
    }
    
    @Override
	public Set<MemberVO> getMembersByOrganizationId(Integer organizationId) {
    	Set<MemberVO> set = new LinkedHashSet<MemberVO>();
    	MemberVO memberVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_Members_ByOrganizationId_STMT);
			pstmt.setInt(1, organizationId);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				memberVO = new MemberVO();
                memberVO.setMemberId(rs.getInt("memberId"));
                memberVO.setOrganizationId(rs.getInt("organizationId"));
                memberVO.setName(rs.getString("name"));
                memberVO.setIdNum(rs.getString("idNum"));
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
                memberVO.setReviewed(rs.getInt("reviewed")); // Store the row in the vector
			}
	
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

    public static void main(String[] args) {
        OrganizationJDBCDAO dao = new OrganizationJDBCDAO();

//        新增
        //OrganizationVO OrganizationVO1 = new OrganizationVO();
        
        OrganizationVO OrganizationVO1 = new OrganizationVO(null, "公益機構 B", "營利組織", null, 1);
        dao.insert(OrganizationVO1);
        System.out.println("新增成功！ID：" + OrganizationVO1.getOrganizationId());

//        // 修改
        OrganizationVO OrganizationVO2 = new OrganizationVO();
//       
        OrganizationVO2.setOrganizationId(7);  
        OrganizationVO2.setName("公益機構 B");  
        OrganizationVO2.setType("營利組織");
        OrganizationVO2.setStatus(0);
        dao.update(OrganizationVO2);
        System.out.println("修改成功！ID：" + OrganizationVO2.getOrganizationId());
   
       
        // 查詢單筆
        
        OrganizationVO OrganizationVO3 = new OrganizationVO();
        OrganizationVO3.setOrganizationId(1);
        OrganizationVO record = dao.findByPrimaryKey(OrganizationVO3.getOrganizationId());
        System.out.println("查詢成功：" + record);

        // 查詢所有
        List<OrganizationVO> list = dao.getAll();
        list.forEach(System.out::println);
//
//        // 刪除
        OrganizationVO OrganizationVO4 = new OrganizationVO();
        OrganizationVO4.setOrganizationId(11); 
        dao.delete(OrganizationVO4.getOrganizationId());
        System.out.println("刪除成功！ID：" + OrganizationVO4.getOrganizationId());
    }
	

	
}
