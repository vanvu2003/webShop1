package poly.vanvu.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.vanvu.entity.Oder;
import poly.vanvu.repository.OrderRepossitory;

@Controller
@RequestMapping("/admin")
public class ConfirmOrderController {
	@Autowired
	OrderRepossitory orderRepossitory;
	
	@RequestMapping("/confirmOrder")
	public String viewConfirmOrder(Model model) {
		fillOrderByStatus(0, "listOrderWaitting", model);
		return "/admin/confirmOrder";
	}
	
	public void fillOrderByStatus(int status,String nameModel, Model model) {
		List<Oder> listOrder = orderRepossitory.findOrderByStatus(status);
		
		model.addAttribute(nameModel,listOrder);
	}
}
