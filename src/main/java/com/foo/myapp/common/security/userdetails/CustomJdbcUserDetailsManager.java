package com.foo.myapp.common.security.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 *
 * @author foo
 *
 */
public class CustomJdbcUserDetailsManager extends JdbcUserDetailsManager {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());


	private CustomUserDetails userDetails = null;
	private RoleHierarchy roleHierarchy = null;

	public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
		this.roleHierarchy = roleHierarchy;
	}


	@Override
	public UserDetails loadUserByUsername(String mberId) throws UsernameNotFoundException {

		//MappingSqlQuery를 사용하여 사용자정보를 조회
		CustomUserDetailsMappingSqlQuery customDetailQuery = new CustomUserDetailsMappingSqlQuery(getDataSource(), getUsersByUsernameQuery());

		List<CustomUserDetails> users = customDetailQuery.execute(mberId);

		if (users.size() == 0) {
	        LOGGER.debug("Query returned no results for ID '{}'", mberId);
			throw new UsernameNotFoundException("UserDetails query returned no results for " + mberId);
	    }

		//There should normally only be one matching user.
		this.userDetails = users.get(0);

		Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
        dbAuthsSet.addAll(loadUserAuthorities(this.userDetails.getUsername())); //Spring Security의 username은 MBER_ID에 해당한다.

        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);

        addCustomAuthorities(this.userDetails.getUsername(), dbAuths);

        //권한이 설정되어 있지 않으면 아무것도 할 수 없도록 한다. 최소 권한은 ROLE_ANONYMOUS
        if (dbAuths.size() == 0) {
        	LOGGER.debug("No GrantedAuthority for " + mberId);
			throw new UsernameNotFoundException("No GrantedAuthority for " + mberId);
        }

        Collection<? extends GrantedAuthority> authorities = roleHierarchy.getReachableGrantedAuthorities(dbAuths);

        //authorities가 추가되고 최종적인 UserDetails를 생성하여 리턴한다.
        //UsernamePasswordAuthenticationFilter에서 정보를 확인한다.
        return new CustomUserDetails(this.userDetails.getUsername(),
                                     this.userDetails.getPassword(),
                                     this.userDetails.isEnabled(),
                                     this.userDetails.isAccountNonExpired(),
                                     this.userDetails.isCredentialsNonExpired(),
                                     this.userDetails.isAccountNonLocked(),
                                     authorities,
                                     this.userDetails.getLoginVO());
	}

}
