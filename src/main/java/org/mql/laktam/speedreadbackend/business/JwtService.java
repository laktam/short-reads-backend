package org.mql.laktam.speedreadbackend.business;

import org.mql.laktam.speedreadbackend.models.User;

public interface JwtService {
	public String login(String username, String password);
	public String createToken(String username);
	public User signup(String username, String email, String password) throws Exception ;

}
