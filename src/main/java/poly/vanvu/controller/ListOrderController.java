package poly.vanvu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.vanvu.entity.Category;
import poly.vanvu.entity.Oder;
import poly.vanvu.entity.User;
import poly.vanvu.repository.BrandRepository;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.repository.OrderRepossitory;

@Controller
public class ListOrderController {
	@Autowired
	OrderRepossitory orderRepossitory;
	@Autowired
	BrandRepository brandRepository;
	@Autowired
	HttpSession session;
	@Autowired
	CartRepository cartRepository;
	
	@RequestMapping("/listOrder")
	public String showListOrder(Model model) {
		fillOrderByStatus(0, "listOrderWaitting", model);
		fillOrderByStatus(1, "listOrderSuccess", model);
		fillOrderByStatus(2, "listOrderCancelled", model);
		fillBrand(model);
		countCart(model);
		return "listOrder";
	}
	
	
	public void fillOrderByStatus(int status,String nameModel, Model model) {
		User user = (User) session.getAttribute("user");
		
		List<Oder> listOrder = orderRepossitory.findOrderStatusAndUser(status, user.getUsername());
		
		model.addAttribute(nameModel,listOrder);
	}
	
	public void fillBrand(Model model) {
		List<Category> listBrand = brandRepository.findAll();
		model.addAttribute("listBrand",listBrand);
	}
	
	public int countCart(Model model) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			int cartNumber =  cartRepository.countCartByUser(user.getUsername());
			model.addAttribute("cartNumber",cartNumber);
			return cartNumber;
		}
		return 0;
	}
	
	
}
