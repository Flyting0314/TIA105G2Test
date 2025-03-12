package com.organization.model;

import java.util.List;
import java.util.Set;

import com.member.model.MemberVO;

public class OrganizationService {

	private OrganizationDAOInterface dao;

	public OrganizationService() {
		dao = new OrganizationDAO();
	}

	public List<OrganizationVO> getAll() {
		return dao.getAll();
	}

	public OrganizationVO getOneOrganization(Integer organizationId) {
		return dao.findByPrimaryKey(organizationId);
	}

	public Set<MemberVO> getMembersByOrganizationId(Integer organizationId) {
		return dao.getMembersByOrganizationId(organizationId);
	}

	public void deleteOrganization(Integer organizationId) {
		dao.delete(organizationId);
	}
}
