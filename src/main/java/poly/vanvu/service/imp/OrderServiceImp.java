package poly.vanvu.service.imp;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.vanvu.dto.OrderDto;
import poly.vanvu.entity.Oder;
import poly.vanvu.entity.User;
import poly.vanvu.repository.OrderRepossitory;
import poly.vanvu.service.OrderService;

@Service
public class OrderServiceImp implements OrderService {
	@Autowired
	OrderRepossitory orderRepossitory;
	@Autowired
	HttpSession session;
	
	@Override
	public Oder save(OrderDto orderDto) {
		Oder oder = new Oder();
		
		oder.setFullname(orderDto.getFullname());
		oder.setPhone(orderDto.getPhone());
		oder.setCreateDate(new Date());
		oder.setAddress(orderDto.getSpecificAddress()+", "+ orderDto.getWard() +", "+ orderDto.getDistrict()+", "+orderDto.getProvince());
		oder.setNote(orderDto.getNote());
		
		User user = (User) session.getAttribute("user");
		
		oder.setUser(user);
		oder.setStatus(0);
		
		
		
		return orderRepossitory.save(oder);
	}

}
