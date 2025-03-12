package com.organization.model;

import java.util.List;
import java.util.Set;

import com.member.model.MemberVO;

public interface OrganizationDAOInterface {

	public void insert(OrganizationVO organizationVO);


	public void update(OrganizationVO organizationVO);


	public void delete(Integer organizationId);


	public OrganizationVO findByPrimaryKey(Integer organizationId);


	public List<OrganizationVO> getAll();


	public Set<MemberVO> getMembersByOrganizationId(Integer organizationId);
}
