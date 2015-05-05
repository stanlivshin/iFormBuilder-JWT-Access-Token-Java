package com.usbzoso.iformbuilder.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class AccessToken {

	public static String getToken(String clientKey, String clientSecret, String URL) {

		String token = "";

		Map<String, Object> customHeader = new HashMap<>();
		customHeader.put("typ", "JWT");
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS256, null, null, null,
				null, null, null, null, null, null, null, customHeader, null);

		// Create HMAC signer
		JWSSigner signer = new MACSigner(clientSecret);

		// Prepare JWT with claims set
		JWTClaimsSet claimsSet = new JWTClaimsSet();
		claimsSet.setClaim("iss", clientKey);
		claimsSet.setAudience(URL);
		claimsSet.setExpirationTime(DateUtils.addMinutes(new Date(), 5));
		claimsSet.setIssueTime(new Date());

		SignedJWT signedJWT = new SignedJWT(header, claimsSet);

		// Apply the HMAC
		try {
			signedJWT.sign(signer);
		} catch (JOSEException e) {
			e.printStackTrace();
		}

		// serialize to compact form
		String jwt = signedJWT.serialize();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(URL);
			httpPost.setHeader("Content-Type",
					"application/x-www-form-urlencoded");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("grant_type",
					"urn:ietf:params:oauth:grant-type:jwt-bearer"));
			params.add(new BasicNameValuePair("assertion", jwt));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String responseBody = EntityUtils.toString(entity);
			token = responseBody.substring(17, 57); // token is always in the
													// same place of the same
													// length
			EntityUtils.consume(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) // close response
					response.close();
				httpclient.close(); // close client
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return token;
	}
}