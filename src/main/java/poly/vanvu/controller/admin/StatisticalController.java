package poly.vanvu.controller.admin;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.vanvu.entity.Oder;
import poly.vanvu.entity.Product;
import poly.vanvu.entity.User;
import poly.vanvu.repository.OrderDetailRepository;
import poly.vanvu.repository.OrderRepossitory;
import poly.vanvu.repository.ProductRepository;
import poly.vanvu.repository.UserRespository;

@Controller
@RequestMapping("/admin")
public class StatisticalController {
	@Autowired
	OrderRepossitory orderRepossitory;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	UserRespository userRespository;
	@Autowired
	ProductRepository productRepository;
	
	@RequestMapping("/statistical")
	public String viewStatistical(Model model) {
		countOrder(model);
		totalProfit(model);
		getOrderNewDate(model);
		topUser(model);
		topProduct(model);
		return "admin/statistical";
	}
	
	public void countOrder(Model model) {
		long totalOrder = orderRepossitory.count();
		
		model.addAttribute("totalOrder", totalOrder);
	}
	
	public void totalProfit(Model model) {
		List<Long> totalPriceSale = orderDetailRepository.listTotalPriceSale();
		List<Long> totalPriceImport = orderDetailRepository.listTotalPriceImport();
		long priceSale1 = 0;
		long priceImport1 = 0;
		for (Long priceBuy : totalPriceSale) {
			priceSale1 += priceBuy;
		}
		
		for (Long priceImport : totalPriceImport) {
			priceImport1 += priceImport;
		}
//		System.out.println(priceImport1);
		long profit = priceSale1 - priceImport1;
		

		model.addAttribute("profit", profit);
	}
	
	public void getOrderNewDate(Model model) {
		Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		
	    int totalOrderNewDate = orderRepossitory.countOrderNewDate(currentDate);
	    model.addAttribute("orderNewDate", totalOrderNewDate);
	}
	
	public void topUser(Model model) {
		List<String> listUsername = orderRepossitory.topUser();
		
		List<User> listUserTop = new ArrayList<>(); 
		for (String username : listUsername) {
			User user = userRespository.findById(username).orElse(null);
			listUserTop.add(user);
		}
		
		model.addAttribute("listTopUser", listUserTop);
	}
	
	public void topProduct(Model model) {
		List<Integer> listProdId = orderDetailRepository.topProduct();
		
		List<Product> listProdTop = new ArrayList<>();
		for (Integer proId : listProdId) {
			Product prod = productRepository.findById(proId).orElse(null);
			listProdTop.add(prod);
		}
		
		model.addAttribute("listProdTop", listProdTop);
	}
}
