package org.mql.laktam.speedreadbackend.models;

public class ProfileUpdateResponse {
	private String token;
	private String username;
	public ProfileUpdateResponse() {
	}
	
	public ProfileUpdateResponse(String token, String username) {
		super();
		this.token = token;
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "ProfileUpdateResponse [token=" + token + ", username=" + username + "]";
	}
	
	
	
	
}
