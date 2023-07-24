package poly.vanvu.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.vanvu.dto.OrderDto;
import poly.vanvu.entity.Cart;
import poly.vanvu.entity.User;
import poly.vanvu.entity.Vourcher;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.repository.VourcherRepository;
import poly.vanvu.service.OrderDetailService;
import poly.vanvu.service.OrderService;

@Controller
public class CheckoutController {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	HttpSession session;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	VourcherRepository vourcherRepository;

	@ModelAttribute("orderDto")
	public OrderDto orderDto() {
		return new OrderDto();
	}
	
	@GetMapping("/checkout")
	public String checkout(@RequestParam(value = "idCart") int[] idCart,Model model) {
		
		List<Cart> listCart = new ArrayList<>();
		double totalPrice = 0;
		for (int i = 0; i < idCart.length; i++) {
			int j = idCart[i];
			Cart cart = cartRepository.getById(j);
			totalPrice += cart.getTotalPrice();
			listCart.add(cart);
		}
		
		fillVourcher(model, totalPrice);
		countCart(model);
		session.setAttribute("listCartCheckout", listCart);
		model.addAttribute("totalPrice",totalPrice);
		return "checkout";
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
	
	public void fillVourcher(Model model, double totalPrice) {
		Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		List<Vourcher> listVourcher = vourcherRepository.findVourcher(totalPrice, currentDate);
		
		model.addAttribute("listVoucher", listVourcher);
	}
	
//	@PostMapping("/checkout")
//	public String checkout(@RequestParam("fullname")String fullname,@RequestParam("specificAddress")String specificAddress
//			,@RequestParam("province")String province,@RequestParam("phone")String phone,
//			@RequestParam("district")String district,@RequestParam("ward")String ward,
//			@RequestParam("note")String note) {
//		
//		Oder oder = orderService.save(fullname,phone,specificAddress,note, province,district,ward);
//		
//		List<Cart> listCarrtCheckout = (List<Cart>) session.getAttribute("listCartCheckout");
//		
//		for (Cart cart : listCarrtCheckout) {
//			orderDetailService.save(cart, oder);
//		}
//		return "redirect:/home";
//	}
}
