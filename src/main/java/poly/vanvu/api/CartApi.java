package poly.vanvu.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.entity.Cart;
import poly.vanvu.entity.User;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.service.imp.CartServiceImp;

@RestController
public class CartApi {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	CartServiceImp cartServiceImp;
	@Autowired
	HttpSession session;
	
	@PostMapping("/api/addCart")
	public ResponseEntity<String> add(@RequestParam("productId")Integer productId,@RequestParam("quantity")Integer quantity) {

		try {
			Cart cart = cartServiceImp.setCart(productId, quantity);
			
			cartRepository.save(cart);
		    return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng");
		}  catch (NullPointerException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vui lòng đăng nhập trước khi thêm!!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình xử lý.");
	    }
	}
	
	@PostMapping("/api/update")
	public String update(@RequestParam("id")Integer id,@RequestParam("quantity")Integer quantity) {
		
		Cart cart = cartRepository.findById(id).orElse(null);
		
		if (cart != null) {
			cart.setQuantity(quantity);
			cart.setTotalPrice(cart.getProduct().getPriceSale() * quantity);
			
			cartRepository.save(cart);
			
			return "Đã cập nhật";
		}
		
		return "";
	}
	
	@GetMapping("/api/fillCart")
	public ResponseEntity<List<Cart>> fillCart(){
		User user = (User) session.getAttribute("user");
		List<Cart> listCart = cartRepository.findByUser(user);
		
		return ResponseEntity.ok(listCart);
	}
	
	@DeleteMapping("/api/cart/delete")
	public String delete(@RequestParam("id")Integer id) {
		Cart cart = cartRepository.getById(id);
		
		if (cart != null) {
			cartRepository.delete(cart);
			return "Xóa thành công";
		}
		return "";
	}
	
	@GetMapping("api/getTotalCart")
	public ResponseEntity<Integer> getTotalCart(){
		User user = (User) session.getAttribute("user");
		int totalCart = cartRepository.countCartByUser(user.getUsername());
		
		return ResponseEntity.ok(totalCart);
	}
}
