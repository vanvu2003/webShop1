package poly.vanvu.service;

import poly.vanvu.entity.Cart;
import poly.vanvu.entity.Oder;
import poly.vanvu.entity.OrderDetail;

public interface OrderDetailService {
	OrderDetail save(Cart cart,Oder oder);
}
