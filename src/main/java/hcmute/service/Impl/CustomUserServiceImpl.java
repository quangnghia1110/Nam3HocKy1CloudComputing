package hcmute.service.Impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hcmute.model.user.User;
import hcmute.repository.UserRepository;
import hcmute.security.UserPrincipal;
import hcmute.utils.AppConstant;

@Service
public class CustomUserServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email){
		User user = userRepository.findByEmail(email);
		if(Objects.isNull(user)) {
			throw new UsernameNotFoundException(AppConstant.USER_NOT_FOUND+email);
		}
		return UserPrincipal.create(user);

		/*
		 * // khai -> database -> tìm được -> tạo userDetail -> trả về UserDetailService
		 * -> // 123 -> userDetailSerice -> kiểm tra -> nếu 123 = pass trong database
		 * (giải mã) -> đăng nhập -> lỗi // đăng nhập thành công -> tạo một
		 * http request -> stateless -> duy trì đăng nhập
		 * UserPrincipal ( đã authenticated ) trong spring container
		 * 
		 */	
		
	}

}
