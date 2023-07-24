package poly.vanvu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import poly.vanvu.dto.RegisterDto;

@Controller
public class OTPVerificationController {
	@Autowired
	HttpSession session;
	
	@GetMapping("/otp-verification")
	public String viewOtpPage() {
		RegisterDto registerDto = (RegisterDto) session.getAttribute("registerDto");
		
		System.out.println(registerDto.getImage().getOriginalFilename());
		return "OTPVerification";
	}
	
}
