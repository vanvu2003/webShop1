package poly.vanvu.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.dto.OrderDto;
import poly.vanvu.entity.Cart;
import poly.vanvu.entity.Oder;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.service.OrderDetailService;
import poly.vanvu.service.OrderService;

@RestController
public class CheckoutApi {
	@Autowired
	HttpSession session;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	CartRepository cartRepository;
	
	@PostMapping("/api/checkout")
	public String checkout(@RequestBody OrderDto orderDto) {
		
		Oder oder = orderService.save(orderDto);
		
		List<Cart> listCarrtCheckout = (List<Cart>) session.getAttribute("listCartCheckout");
		
		for (Cart cart : listCarrtCheckout) {
			orderDetailService.save(cart, oder);
			cartRepository.delete(cart);
		}
		return "Đặt hàng thành công";
	}
}
