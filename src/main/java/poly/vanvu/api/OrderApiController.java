package poly.vanvu.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.entity.Oder;
import poly.vanvu.entity.OrderDetail;
import poly.vanvu.repository.OrderDetailRepository;
import poly.vanvu.repository.OrderRepossitory;

@RestController
public class OrderApiController {
	@Autowired
	OrderRepossitory orderRepossitory;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@PostMapping("/api/updateOrder")
	public String update(@RequestParam("id")Integer id,@RequestParam("status")int status) {
		Oder oder = orderRepossitory.getById(id);
		
		oder.setStatus(status);
		orderRepossitory.save(oder);
		return "Thành công";
	}
	
	@PostMapping("api/order/deletes")
	public ResponseEntity<?> deleteOrder(@RequestParam("orderId")int orderId) {
		Oder order = orderRepossitory.findById(orderId);
		OrderDetail orderDetail = orderDetailRepository.findByIdOrder(orderId);
		orderDetailRepository.delete(orderDetail);
		orderRepossitory.delete(order);
		
		return ResponseEntity.ok("Đã hủy đơn hàng!");
	}
	
	@GetMapping("/api/fillOrder")
	public ResponseEntity<List<Oder>> fillOrder(){
		
		List<Oder> listOrderStatusFale = orderRepossitory.findOrderByStatus(0);
		
		
		return ResponseEntity.ok(listOrderStatusFale);
	}
}
