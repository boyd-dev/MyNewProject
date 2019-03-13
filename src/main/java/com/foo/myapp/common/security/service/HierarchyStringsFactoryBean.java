package com.foo.myapp.common.security.service;

import org.springframework.beans.factory.FactoryBean;

/**
 *
 * 권한의 상하관계를 DB에서 조회
 * 빈 초기화되면서 즉시 해당 정보를 context-security.xml에 설정된 jdbcUserService에 전달한다.
 * @author foo
 *
 */
public class HierarchyStringsFactoryBean implements FactoryBean<String> {

	private String hierarchyStrings;
    private ISecuredObejctService securedObjectService;

	public void setSecuredObjectService(ISecuredObejctService securedObjectService) {
		this.securedObjectService = securedObjectService;
	}

	public void init() throws Exception {
		this.hierarchyStrings = (String) securedObjectService.getHierarchicalRoles();
    }


	@Override
	public String getObject() throws Exception {
		if (hierarchyStrings == null) {
	        init();
	    }
	    return hierarchyStrings;
	}

	@Override
	public Class<String> getObjectType() {
		 return String.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}


}


