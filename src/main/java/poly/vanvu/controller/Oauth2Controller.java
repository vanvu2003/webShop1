package poly.vanvu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.vanvu.service.imp.UserServiceImp;

@Controller
public class Oauth2Controller {
	@Autowired
	private UserServiceImp userServiceImp;
	
	@RequestMapping("/oauth2/login/success")
	public String success(OAuth2AuthenticationToken oauth2) {
		
		userServiceImp.loginOAuth2(oauth2);
		
		return "redirect:/home";
	}
}
