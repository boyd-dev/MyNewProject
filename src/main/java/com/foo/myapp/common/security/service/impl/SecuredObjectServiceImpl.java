package com.foo.myapp.common.security.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.foo.myapp.common.security.service.ISecuredObejctService;
import com.foo.myapp.common.security.service.ISecuredObjectDao;

/**
 *
 * TODO 나중에 필요한 기능을 추가
 * @author kim
 *
 */
public class SecuredObjectServiceImpl implements ISecuredObejctService {

	private ISecuredObjectDao securedObjectDAO;

	@Override
	public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getRolesAndUrl() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getHierarchicalRoles() throws Exception {
		return this.securedObjectDAO.getHierarchicalRoles();
	}


	public void setSecuredObjectDAO(ISecuredObjectDao securedObjectDAO) {
		this.securedObjectDAO = securedObjectDAO;
	}

}
