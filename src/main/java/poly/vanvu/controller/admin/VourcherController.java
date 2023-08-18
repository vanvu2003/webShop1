package poly.vanvu.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.vanvu.dto.VourcherDto;
import poly.vanvu.entity.Vourcher;
import poly.vanvu.repository.VourcherRepository;

@Controller
@RequestMapping("/admin")
public class VourcherController {
	@Autowired
	VourcherRepository vourcherRepository;
	
	@ModelAttribute("vourcher")
	public VourcherDto vourcherDto() {
		return new VourcherDto();
	}
	@RequestMapping("/vourcher")
	public String viewAddVourcher() {
		
		return "/admin/vourcher";
	}
	
	@RequestMapping("/listVourcher")
	public String viewListVourcher() {
		
		return"/admin/listVourcher";
	}
	
	@GetMapping("/vourcher/edit")
	public String edit(@RequestParam("id")int id, Model model) {
		Vourcher vourcher = vourcherRepository.findById(id).orElse(null);
		
		model.addAttribute("vourcher",vourcher);
		return "redirect:/admin/vourcher";
	}
	
}
