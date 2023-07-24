package poly.vanvu.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.vanvu.entity.Cart;
import poly.vanvu.entity.Oder;
import poly.vanvu.entity.OrderDetail;
import poly.vanvu.repository.OrderDetailRepository;
import poly.vanvu.service.OrderDetailService;

@Service
public class OrderDetailServiceImp implements OrderDetailService{
	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Override
	public OrderDetail save(Cart cart,Oder oder) {
		OrderDetail orderDetail = new OrderDetail();
		
		orderDetail.setProduct(cart.getProduct());
		orderDetail.setOrder(oder);
		orderDetail.setPrice(cart.getProduct().getPriceSale());
		orderDetail.setQuantity(cart.getQuantity());
		
		return orderDetailRepository.save(orderDetail);
	}
	
	

}
