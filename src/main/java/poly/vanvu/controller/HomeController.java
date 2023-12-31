package poly.vanvu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.vanvu.entity.Cart;
import poly.vanvu.entity.Category;
import poly.vanvu.entity.Product;
import poly.vanvu.entity.User;
import poly.vanvu.repository.BrandRepository;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.repository.ProductRepository;
import poly.vanvu.service.imp.BrandServiceImp;

@Controller
public class HomeController {
	@Autowired
	BrandRepository brandRepository;
	@Autowired
	BrandServiceImp brandServiceImp;
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	HttpSession session;
	
	
	@RequestMapping("/home")
	public String index(Model model) {
		fillBrand(model);
		fillProduct(model);
		countCart(model);
		top5ProdByCreateDate(model);
		return "index";
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
	
	public void fillBrand(Model model) {
		List<Category> listBrand = brandRepository.findAll();
		model.addAttribute("listBrand",listBrand);
	}
	
	public void fillProduct(Model model) {
		List<Product> listProd = productRepository.findAll();
		model.addAttribute("listProd",listProd);
	}
	
	
	@ResponseBody
	public void top5ProdByCreateDate(Model model) {
		List<Product> top5ProdByCraeateDate = productRepository.top5ProdByCreateDate();
		model.addAttribute("listTop5ByCreateDate",top5ProdByCraeateDate);
	}
}
