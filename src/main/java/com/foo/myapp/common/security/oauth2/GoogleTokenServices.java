package com.foo.myapp.common.security.oauth2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleTokenServices  extends RemoteTokenServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleTokenServices.class);

	private RestOperations restTemplate;

    private String checkTokenEndpointUrl;

    private String clientId;

    private String clientSecret;

    private AccessTokenConverter tokenConverter = new GoogleAccessTokenConverter();

    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
        this.checkTokenEndpointUrl = checkTokenEndpointUrl;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setTokenConverter(AccessTokenConverter tokenConverter) {
		this.tokenConverter = tokenConverter;
	}

	public GoogleTokenServices() {
		this.restTemplate = new RestTemplate();
		((RestTemplate) this.restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			// Ignore 400
			public void handleError(ClientHttpResponse response) throws IOException {
				if (response.getRawStatusCode() != 400) {
					super.handleError(response);
				}
			}
		});
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Access Token=" + accessToken);
		}

		//Access Token으로부터 정보 조회
		String tokenInfo = checkToken(accessToken);

		//오류가 있는 경우 다음과 같은 형태로 리턴
		//{
		//   "error": "invalid_grant",
        //   "error_description": "Invalid Value"
        //}
		Pattern p = Pattern.compile("error");
		Matcher m = p.matcher(tokenInfo);
		if (m.find()) {
			LOGGER.debug("Access Token check failed\n" + tokenInfo);
            throw new InvalidTokenException(accessToken);
        }

		//json to map using Jackson
		ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();

        try {

			map = mapper.readValue(tokenInfo, new TypeReference<Map<String, String>>(){});

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //https://www.googleapis.com/oauth2/v3/tokeninfo 에서 전달하는 키값이 Spring Security의 기본 키값과 다르므로 맞춘다.
        //API의 리턴 값에 따라 변경이나 조정 가능
        map.put("client_id", map.get("aud"));
        map.put("user_name", map.get("email"));

		return tokenConverter.extractAuthentication(map);
	}

	private String checkToken(String accessToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", getAuthorizationHeader(clientId, clientSecret));

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("access_token", accessToken);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		String response = this.restTemplate.postForObject(checkTokenEndpointUrl, request, String.class);

		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug(response);
		}

		return response;
	}

	//TODO HTTP header?
	private String getAuthorizationHeader(String clientId, String clientSecret) {
        String creds = String.format("%s:%s", clientId, clientSecret);
        try {
            return "Basic " + new String(Base64.encode(creds.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Could not convert String");
        }
    }


}
