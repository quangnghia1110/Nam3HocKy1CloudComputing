package hcmute.service;

import javax.servlet.http.HttpServletRequest;

import hcmute.model.PasswordResetToken;
import hcmute.model.user.User;

public interface IPasswordResetTokenService {

	PasswordResetToken findByUser(User user);
	String sendResetPasswordToken(HttpServletRequest request, PasswordResetToken newToken, User user);
	PasswordResetToken getPasswordTokenByUser(User user);
}
