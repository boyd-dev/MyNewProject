package com.foo.myapp.common.security.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.foo.myapp.common.security.service.ISecuredObjectDao;

/**
 *
 * @author kim
 *
 */
public class SecuredObjectDaoImpl implements ISecuredObjectDao {

	public static final String DEF_HIERARCHICAL_ROLES_QUERY =
		        "SELECT A.CHLDRN_ROLE AS CHILD, A.PARNTS_ROLE AS PARENT "
		            + "FROM T_ROLE_HIERARCHY A LEFT JOIN T_ROLE_HIERARCHY B ON (A.CHLDRN_ROLE = B.PARNTS_ROLE)";


	private String sqlHierarchicalRoles;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public SecuredObjectDaoImpl() {
		this.setSqlHierarchicalRoles(DEF_HIERARCHICAL_ROLES_QUERY);
    }

	@Override
	public String getHierarchicalRoles() throws Exception {

		List<Map<String, Object>> resultList = this.namedParameterJdbcTemplate.queryForList(getSqlHierarchicalRoles(), new HashMap<String, String>());

        Iterator<Map<String, Object>> itr = resultList.iterator();
        StringBuffer concatedRoles = new StringBuffer();

        Map<String, Object> tempMap;
        //ROLE_ADMIN > ROLE_USER 형태의 문자열을 만든다.
        while (itr.hasNext()) {
            tempMap = itr.next();
            concatedRoles.append(tempMap.get("CHILD"));
            concatedRoles.append(" > ");
            concatedRoles.append(tempMap.get("PARENT"));
            concatedRoles.append("\n");
        }

        return concatedRoles.toString();
	}

	public String getSqlHierarchicalRoles() {
		return sqlHierarchicalRoles;
	}

	//설정을 통해 권한의 상하관계를 조회하는 SQL을 대체할 수 있다.
	public void setSqlHierarchicalRoles(String sqlHierarchicalRoles) {
		this.sqlHierarchicalRoles = sqlHierarchicalRoles;
	}


}
