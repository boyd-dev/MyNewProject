package com.foo.myapp.common.security.oauth2;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;
import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;
import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


public class DefaultUserAuthenticationConverter extends org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter {

	private Collection<? extends GrantedAuthority> defaultAuthorities;

    private AuthorityGranter authorityGranter;

    /**
     * Default value for authorities if an Authentication is being created and the input has no data for authorities.
     * Note that unless this property is set, the default Authentication created by {@link #extractAuthentication(java.util.Map)}
     * will be unauthenticated.
     *
     * @param defaultAuthorities the defaultAuthorities to set. Default null.
     */
    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = commaSeparatedStringToAuthorityList(arrayToCommaDelimitedString(defaultAuthorities));
    }

    /**
     * Authority granter which can grant additional authority to the user based on custom rules.
     *
     * @param authorityGranter
     */
    public void setAuthorityGranter(AuthorityGranter authorityGranter) {
        this.authorityGranter = authorityGranter;
    }

    //인증만 되면 로그인이 가능
    //클라이언트 애플리케이션의 권한을 Google이 제공할 리는 없다!
    //Not granted any authorities
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            return new UsernamePasswordAuthenticationToken(map.get(USERNAME), "N/A", getAuthorities(map));
        	//return new UsernamePasswordAuthenticationToken(map.get(USERNAME), "N/A", null);
        }
        return null;
    }

    //TODO 기본 권한 주는 방안
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        List<GrantedAuthority> authorityList = newArrayList();
        if (!map.containsKey(AUTHORITIES)) {
            assignDefaultAuthorities(authorityList);
        } else {
            grantAuthoritiesBasedOnValuesInMap(map, authorityList);
        }
        grantAdditionalAuthorities(map, authorityList);
        return authorityList;
    }

    private void grantAuthoritiesBasedOnValuesInMap(Map<String, ?> map, List<GrantedAuthority> authorityList) {
        List<GrantedAuthority> parsedAuthorities = parseAuthorities(map);
        authorityList.addAll(parsedAuthorities);
    }

    private void grantAdditionalAuthorities(Map<String, ?> map, List<GrantedAuthority> authorityList) {
        if (authorityGranter != null) {
            authorityList.addAll(authorityGranter.getAuthorities(map));
        }
    }

    private void assignDefaultAuthorities(List<GrantedAuthority> authorityList) {
        if (defaultAuthorities != null) {
            authorityList.addAll(defaultAuthorities);
        }
    }

    private List<GrantedAuthority> parseAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        List<GrantedAuthority> parsedAuthorities;
        if (authorities instanceof String) {
            // Bugfix for Spring OAuth codebase
            parsedAuthorities = commaSeparatedStringToAuthorityList((String) authorities);
        } else if (authorities instanceof Collection) {
            parsedAuthorities = commaSeparatedStringToAuthorityList(collectionToCommaDelimitedString((Collection<?>) authorities));
        } else {
            throw new IllegalArgumentException("Authorities must be either a String or a Collection");
        }
        return parsedAuthorities;
    }

}
