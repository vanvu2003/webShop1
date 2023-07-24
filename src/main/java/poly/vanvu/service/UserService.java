package poly.vanvu.service;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import poly.vanvu.dto.RegisterDto;
import poly.vanvu.entity.User;

public interface UserService extends UserDetailsService{
	User save(RegisterDto registerDto);
	boolean authenticate(String username,String password,HttpSession session);
	User get(String token);
	void loginOAuth2(OAuth2AuthenticationToken oauth2);
}
