package poly.vanvu.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.dto.CommentDto;
import poly.vanvu.entity.Comment;
import poly.vanvu.entity.Product;
import poly.vanvu.entity.User;
import poly.vanvu.repository.CommentRepository;

@RestController
public class CommentApi {
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	HttpSession session;
	
	@GetMapping("/api/getListComment/{idProd}")
	public ResponseEntity<List<Comment>> fillAllComment(@PathVariable("idProd")int idProd) {
		List<Comment> listComment = commentRepository.findByProductId(idProd);
		
		return ResponseEntity.ok(listComment);
	}
	
	@PostMapping("/api/saveComment")
	public ResponseEntity<?> saveComment(@RequestBody CommentDto commentDto){
		Comment comment = new Comment();
		
		User user = (User) session.getAttribute("user");
		try {
			BeanUtils.copyProperties(commentDto, comment);
			
			Product product = new Product();
			product.setId(commentDto.getProdId());
			
			comment.setProduct(product);
			comment.setUser(user);
			comment.setCreateDate(new Date());
			commentRepository.save(comment);
		} catch (Exception e) {
			return ResponseEntity.ok("Lưu thất bại");
		}
		
		return ResponseEntity.ok("Lưu thành công");
	}
}
