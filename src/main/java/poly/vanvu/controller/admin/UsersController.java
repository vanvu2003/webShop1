package poly.vanvu.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.vanvu.entity.User;
import poly.vanvu.repository.UserRespository;

@Controller
@RequestMapping("/admin")
public class UsersController {
	@Autowired
	UserRespository userRespository;
	
	@RequestMapping("/user-management")
	public String viewUsers(Model model) {
		List<User> listUser = userRespository.findAll();
		model.addAttribute("listUser",listUser);
		return "admin/users";
	}
}
