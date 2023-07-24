package poly.vanvu.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.dto.RegisterDto;
import poly.vanvu.entity.User;
import poly.vanvu.repository.UserRespository;
import poly.vanvu.service.UserService;

@RestController
@RequestMapping("/account/api")
public class AccountApi {
	@Autowired
	UserRespository userRespository;
	@Autowired
	UserService userService;
	@Autowired
	HttpSession session;

	@GetMapping("/login")
	public ResponseEntity<List<User>> login() {
		List<User> user = userRespository.findAll();

		return ResponseEntity.ok(user);
	}

	@PostMapping("/delete")
	public ResponseEntity<String> delete(@RequestParam("username") String username) {
		User user = userRespository.findByUsername(username).get(0);
		System.out.println(user);

		userRespository.delete(user);
		System.out.println("nckaj");
		return ResponseEntity.ok("Đã xóa thành công");

	}

	@PostMapping("/otp")
	public ResponseEntity<String> register(@RequestParam("otp") Integer otp) {
		int otpRandum = (int) session.getAttribute("codeVerify");
		System.out.println("ablaksc");
		RegisterDto registerDto = (RegisterDto) session.getAttribute("registerDto");
		System.out.println("a");
		if (otp != otpRandum) {
			return ResponseEntity.ok("Mã xác nhận không đúng");
		} else {
			userService.save(registerDto);
			return ResponseEntity.ok("Thành công");
		}
	}
}
