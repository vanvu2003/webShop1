package poly.vanvu.service;

import poly.vanvu.dto.OrderDto;
import poly.vanvu.entity.Oder;

public interface OrderService {
	Oder save(OrderDto orderDto);
}
