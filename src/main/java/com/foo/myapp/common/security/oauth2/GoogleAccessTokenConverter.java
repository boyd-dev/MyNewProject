package com.foo.myapp.common.security.oauth2;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

public class GoogleAccessTokenConverter extends DefaultAccessTokenConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleAccessTokenConverter.class);

	private UserAuthenticationConverter userTokenConverter;

	public GoogleAccessTokenConverter() {
        setUserTokenConverter(new DefaultUserAuthenticationConverter());
    }

	public void setUserTokenConverter(UserAuthenticationConverter userTokenConverter) {
		this.userTokenConverter = userTokenConverter;
	}

	@Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {

    	if (LOGGER.isDebugEnabled()) {
    		map.forEach((k,v)->LOGGER.debug(k + "=" + v));
    	}

        Map<String, String> parameters = new HashMap<>();
        Set<String> scope = parseScopes(map);
        Authentication user = this.userTokenConverter.extractAuthentication(map);
        String clientId = (String) map.get(CLIENT_ID);
        parameters.put(CLIENT_ID, clientId);
        Set<String> resourceIds = new LinkedHashSet<>();
        resourceIds.add(map.get(AUD).toString());
        OAuth2Request request = new OAuth2Request(parameters, clientId, null, true, scope, resourceIds, null, null, null);
        return new OAuth2Authentication(request, user);
    }

    @SuppressWarnings("unchecked")
	private Set<String> parseScopes(Map<String, ?> map) {
        // Parsing of scopes coming back from Google are slightly different from the default implementation
        // Instead of it being a collection it is a String where multiple scopes are separated by a space.
        Object scopeAsObject = map.containsKey(SCOPE) ? map.get(SCOPE) : EMPTY;
        Set<String> scope = new LinkedHashSet<>();
        if (String.class.isAssignableFrom(scopeAsObject.getClass())) {
            String scopeAsString = (String) scopeAsObject;
            Collections.addAll(scope, scopeAsString.split(" "));
        } else if (Collection.class.isAssignableFrom(scopeAsObject.getClass())) {
            Collection<String> scopes = (Collection<String>) scopeAsObject;
            scope.addAll(scopes);
        }
        return scope;
    }

}
