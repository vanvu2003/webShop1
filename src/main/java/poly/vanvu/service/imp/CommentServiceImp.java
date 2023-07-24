package poly.vanvu.service.imp;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.vanvu.dto.CommentDto;
import poly.vanvu.entity.Comment;
import poly.vanvu.repository.CommentRepository;
import poly.vanvu.service.CommentService;

@Service
public class CommentServiceImp implements CommentService{
	@Autowired
	CommentRepository commentRepository;
	
	@Override
	public void save(CommentDto commentDto) {
		Comment comment = new Comment();
		
		try {
			BeanUtils.copyProperties(commentDto, comment);
			
			commentRepository.save(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
