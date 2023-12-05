package hcmute.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import hcmute.dto.CheckoutForm;
import hcmute.dto.ProfileForm;
import hcmute.dto.RegisterForm;
import hcmute.dto.UserEditForm;
import hcmute.model.PasswordResetToken;
import hcmute.model.VerificationToken;
import hcmute.model.order.Order;
import hcmute.model.user.User;

public interface IUserService {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	User createNewUser(RegisterForm registerRequest);

	void createVerificationTokenForUser(User user, String token);

	User updateUser(User user);

	VerificationToken generateNewVerificationToken(String existingToken);

	User getUserByVerificationToken(String token);
	User getUserByPasswordToken(String token);
	VerificationToken generateVerifyTokenById(Long userId);

	User findByEmail(String email);

	User createNewUserOAuth2(User user);

	PasswordResetToken generatePasswordTokenByUser(User user);

	PasswordResetToken generateNewPasswordToken(String existingtoken);

	void updatePassword(String passwordToken, String password);

	User getUserById(Long id);

	void updateProfile(ProfileForm profileForm);

	void updateImage(MultipartFile file) throws IOException;

	String getUsernameById(Long createBy);

	void updateCheckout(CheckoutForm checkoutForm);

	List<User> getAllUser();

	User createNewUserForAdmin(RegisterForm registerRequest) throws IOException;

	void updateImageForAdmin(MultipartFile file, Long id) throws IOException;

	void uploadImageForAdmin(MultipartFile file, String userId) throws NumberFormatException, IOException;

	void updateProfileForAdmin(UserEditForm userEditForm);

	void updatePasswordForAdmin(String userId, String password);

	User findByOrder(Order order);

}
