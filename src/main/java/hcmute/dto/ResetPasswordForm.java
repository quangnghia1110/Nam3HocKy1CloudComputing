package hcmute.dto;

import lombok.Data;

@Data
public class ResetPasswordForm {

	private String password;
	private String confirmPassword;
}
