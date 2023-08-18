package poly.vanvu.service.imp;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import poly.vanvu.dto.RegisterDto;
import poly.vanvu.entity.User;
import poly.vanvu.repository.UserRespository;
import poly.vanvu.service.UserService;

@Service
public class UserServiceImp implements UserService{
	@Autowired
	UserRespository userRespository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	HttpSession session;
	
	
	@Override
	public User save(RegisterDto registerDto) {
		User user = new User();
		
		user.setUsername(registerDto.getUsername());
		user.setFullname(registerDto.getFullname());
		user.setPhone(registerDto.getPhone());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setEmail(registerDto.getEmail());
		user.setAddress(registerDto.getAddress());
		user.setCreateDate(new Date());
		user.setImage(registerDto.getImage().getOriginalFilename());
		user.setRole("user");
		user.setStatus(true);
		
		return userRespository.save(user);
	}
	
	//bỏ
	@Override
	public boolean authenticate(String username, String password,HttpSession session) {
		User user = userRespository.findById(username).orElse(null);
		
		if (user != null) {
			
			session.setAttribute("user", user);
			return passwordEncoder.matches(password, user.getPassword());
		}
		return false;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> lisUsers = userRespository.findByUsername(username);
		
		if(lisUsers.size()>0) {
			User user = lisUsers.get(0);
			
			session.setAttribute("user", user);
			Set<GrantedAuthority> auth = new HashSet<>();
			auth.add(new SimpleGrantedAuthority(user.getRole()));
			
			return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),auth);
		}else {
			throw new UsernameNotFoundException("Không tìm thấy username này!!");
			
		}
	}

	@Override
	public User get(String token) {
		User user = userRespository.get(token);
		return user;
	}

	@Override
	public void loginOAuth2(OAuth2AuthenticationToken oauth2) {
		String email = oauth2.getPrincipal().getAttribute("email");
		String fullname = oauth2.getPrincipal().getAttribute("name");
		String password = Long.toHexString(System.currentTimeMillis());
		
		String provider = oauth2.getAuthorizedClientRegistrationId();
		
		User user = new User();
		user.setFullname(fullname);
		user.setCreateDate(new Date());
		user.setPassword(password);
		user.setRole("user");
		
		User userFindById = null;
		if("google".equals(provider)) {
			user.setUsername(email);
			user.setEmail(email);
			
			userFindById = userRespository.findById(email).orElse(null);
		}else if("facebook".equals(provider)) {
			String id = oauth2.getPrincipal().getAttribute("id");
			user.setUsername(id);
			
			userFindById = userRespository.findById(user.getUsername()).orElse(null);
		}
		if (userFindById == null) {
			userRespository.save(user);
		}
		
		session.setAttribute("user", user);
		
		UserDetails userDetail = org.springframework.security.core.userdetails.User
				.withUsername(email)
				.password(passwordEncoder
				.encode(password))
				.roles("user")
				.build();
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	
	
}
