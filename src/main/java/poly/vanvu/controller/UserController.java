package poly.vanvu.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import poly.vanvu.dto.RegisterDto;
import poly.vanvu.service.UserService;

@Controller
public class UserController {

	@Autowired
	ServletContext app;
	@Autowired
	UserService userService;
	@Autowired
	HttpSession session;
	@Autowired
	JavaMailSender mailSender;
	
	@RequestMapping("/login")
	public String viewlogin() {
		return "login";
	}
	
	
	@ModelAttribute("user")
	public RegisterDto registerDto() {
		return new RegisterDto();
	}
	
	@RequestMapping("/register")
	public String viewRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam("image")MultipartFile image,@ModelAttribute("user") RegisterDto registerDto) throws IllegalStateException, IOException, MessagingException {
//		ResponseEntity<String> response = null;
		if(!image.isEmpty()) {	
			String filename= image.getOriginalFilename();
			File folder = new File(app.getRealPath("/upload"));
			if (!folder.exists()) {
				folder.mkdir();
			}
			File file = new File(app.getRealPath("/upload/"+ filename));
			image.transferTo(file);
		}
		
		session.setAttribute("registerDto", registerDto);
		
		sendOTP(registerDto.getEmail(), registerDto);
		
		session.setAttribute("registerDto", registerDto);
//		User user = userService.save(registerDto);
		return "redirect:/otp-verification";
	}
	
	private void sendOTP(String email,RegisterDto registerDto) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		int randomNum = ThreadLocalRandom.current().nextInt(10000, 99999);
		session.setAttribute("codeVerify", randomNum);
		
		helper.setFrom("vanvu19102003@gmail.com", "V&V SHOP");
		helper.setTo(email);
		
		String subject = "MÃ XÁC NHẬN TÀI KHOẢN";
		String content = "<p>Chào "+registerDto.getFullname()+"!</p>"
				+ "<p>Chúng tôi đã nhận được yêu cầu xác nhận mật khẩu V&V Shop của bạn.</p>"
				+ "<p>Nhập mã dưới đây để xác nhận:</p>"
				+"<p style=\"padding: 10px; background-color: aquamarine; width: 90px; text-align: center; font-weight: bolder; border-radius: 10px;\">"+randomNum +"</p>";
		helper.setSubject(subject);
		helper.setText(content,true);
		
		mailSender.send(message);
	}
}
