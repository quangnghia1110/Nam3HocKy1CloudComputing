package hcmute.service;

import javax.servlet.http.HttpServletRequest;

import hcmute.model.VerificationToken;
import hcmute.model.user.User;

public interface IVerificationTokenService {

	VerificationToken getVerificationToken(String token);

	String sendVerificationToken(HttpServletRequest request, VerificationToken newToken, User user);
	
	VerificationToken getTokenByUser(User savedUser);


}
