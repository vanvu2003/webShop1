package poly.vanvu.api;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.dto.PaginationDto;
import poly.vanvu.entity.Product;
import poly.vanvu.entity.User;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class PaginationApiController {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	HttpSession session;
	@Autowired
	CartRepository cartRepository;
	
	@GetMapping("/pagination")
	public PaginationDto pagination(@RequestParam("page")Optional<Integer> page,
											@RequestParam("minPrice")double minPrice,
											@RequestParam("maxPrice")double maxPrice){
		PaginationDto paginationDto = new PaginationDto();
		if (!page.isEmpty()) {
			page = Optional.of(page.get() - 1);
		} else {
			page = Optional.of(0);
		}
//		int countProd = productRepository.countProdFilter(minPrice, maxPrice);
		Pageable pageable = PageRequest.of(page.orElse(0), 9);
		// có thẻ chia ra thành 2 hàm
		
		Page<Product> listProd = productRepository.filterProdByPrice(minPrice, maxPrice, pageable);
		List<Product> listProduct = listProd.toList();
		
		paginationDto.setPage(page.orElse(null));
		paginationDto.setTotalPage(listProd.getTotalPages());
		paginationDto.setListResult(listProduct);
		
		return paginationDto;
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
}
