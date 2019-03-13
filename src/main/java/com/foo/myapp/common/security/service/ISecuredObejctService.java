package com.foo.myapp.common.security.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 *
 * @author foo
 *
 */
public interface ISecuredObejctService {

    /**
     * 롤에 대한 URL의 매핑 정보를 얻어온다.
     * @return
     * @throws Exception
     */
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getRolesAndUrl() throws Exception;


    /**
     * 롤의 계층적 구조를 얻어온다.
     * @return
     * @throws Exception
     */
    public String getHierarchicalRoles() throws Exception;
}
