package poly.vanvu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import poly.vanvu.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	List<Comment> findByProductId(int prodId);
	
	@Query("SELECT COUNT(c) FROM Comment c WHERE c.product.id = ?1")
	int countCommentByProdId(int prodId);
}
