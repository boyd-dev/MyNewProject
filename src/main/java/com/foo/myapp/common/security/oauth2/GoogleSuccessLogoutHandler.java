package com.foo.myapp.common.security.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * logout하면서 Access Token을 폐기하기 위한 LogoutSuccessHandler
 * context-security.xml
 *
 * @author Kang
 *
 */
public class GoogleSuccessLogoutHandler extends SimpleUrlLogoutSuccessHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleSuccessLogoutHandler.class);

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		//토큰 폐기
		String accessToken = ((OAuth2AuthenticationDetails)authentication.getDetails()).getTokenValue();
		String revokeUrl = "https://accounts.google.com/o/oauth2/revoke";

		LOGGER.debug("user_name=" + authentication.getPrincipal().toString());
		LOGGER.debug("Access Token=" + ((OAuth2AuthenticationDetails)authentication.getDetails()).getTokenValue());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		/*
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("token", accessToken);

		HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(params, headers);

		RestOperations restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(revokeUrl, req, String.class);
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug(result);
		}
		*/

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("token", accessToken);
		RestOperations restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(revokeUrl, HttpMethod.POST, new HttpEntity<>(params, headers), String.class);

		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug(result.getBody());
		    //If the revocation is successfully processed, then the status code of the response is 200.
		    //For error conditions, a status code 400 is returned along with an error code.
		    LOGGER.debug(result.getStatusCode().toString());
		}

		setDefaultTargetUrl("/");

		super.onLogoutSuccess(request, response, authentication);
	}



}

