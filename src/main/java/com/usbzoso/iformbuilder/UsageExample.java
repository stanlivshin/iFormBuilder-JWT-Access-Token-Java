package com.usbzoso.iformbuilder;

import com.usbzoso.iformbuilder.auth.AccessToken;

public class UsageExample {
	public static void main(String[] args) {
		String clientKey = "yourIFromBuilderClientKey";
		String clientSecret = "yourIFromBuilderClientSecret";
		String URL = "https://yourIFormBuilderCompany.iformbuilder.com/exzact/api/oauth/token";
		
		String accessToken = AccessToken.getToken(clientKey, clientSecret, URL);
		
		System.out.println("accessToken = " + accessToken);
	}
}
