package poly.vanvu.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.vanvu.entity.Category;
import poly.vanvu.entity.Product;
import poly.vanvu.entity.User;
import poly.vanvu.repository.BrandRepository;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.repository.ProductRepository;

@Controller
public class SearchController {
	@Autowired
	BrandRepository brandRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	HttpSession session;
	@Autowired
	CartRepository cartRepository;
	
	@GetMapping("/search/{keyword}")
	public String viewSearch(@RequestParam("index")Optional<Integer> index,Model model,@PathVariable("keyword")String keyword) {
		Pageable pageable = paging(index, model,keyword);
		
		Page<Product> listProdBySearch = productRepository.listProdBySearch(keyword,pageable);
		int numberProdSearch = productRepository.countProdBySearch(keyword);
		
		model.addAttribute("numberProd", numberProdSearch);
		model.addAttribute("listProd",listProdBySearch);
		model.addAttribute("key", keyword);
		fillBrand(model);
		countCart(model);
		return "search.html";
	}
	
	public void fillBrand(Model model) {
		List<Category> listBrand = brandRepository.findAll();
		model.addAttribute("listBrand",listBrand);
	}
	public int countCart(Model model) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			int cartNumber = cartRepository.countCartByUser(user.getUsername());
			model.addAttribute("cartNumber",cartNumber);
			return cartNumber;
		}
		return 0;
	}
	public Pageable paging(@RequestParam("index")Optional<Integer> index, Model model, String keyword) {
		int count = (int) productRepository.countProdBySearch(keyword);

		int countPage = (int) Math.ceil((double) count / 9);

		int active;
		if (!index.isEmpty()) {
			active = index.get();
			index = Optional.of(index.get() - 1);
		} else {
			active = 1;
		}

		Pageable pageable = PageRequest.of(index.orElse(0), 9);
		
		model.addAttribute("act",active);
		model.addAttribute("countPage", countPage);
		return pageable;
	}
}
