package poly.vanvu.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class VourcherController {
	
	@RequestMapping("/vourcher")
	public String viewAddVourcher() {
		
		return "/admin/vourcher";
	}
}
