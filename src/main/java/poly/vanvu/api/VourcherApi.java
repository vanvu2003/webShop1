package poly.vanvu.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.dto.VourcherDto;
import poly.vanvu.entity.Vourcher;
import poly.vanvu.repository.VourcherRepository;
import poly.vanvu.service.VourcherSevice;

@RestController
@RequestMapping("/api")
public class VourcherApi {
	@Autowired
	VourcherSevice vourcherSevice;
	@Autowired
	VourcherRepository vourcherRepository;
	
	
	@GetMapping("/listVourcher")
	private ResponseEntity<List<Vourcher>> fillAll(){
		List<Vourcher> listVourcher = vourcherRepository.findAll();
		
		return ResponseEntity.ok(listVourcher);
	}
	
	@PostMapping("/vourcher")
	private void saveVourcher(@RequestBody()VourcherDto vourcher ){
		System.out.println(vourcher);
		vourcherSevice.save(vourcher);
	}
	
	@GetMapping("/findVourcher")
	private ResponseEntity<Vourcher> findVourcher(@RequestParam("id")int id) {
		
		Vourcher vourcher = vourcherRepository.findById(id).orElse(null);
		
		return ResponseEntity.ok(vourcher);
	}
	
	@DeleteMapping("/vourcher")
	private void deleteVourcher(@RequestParam("id")int id) {
		vourcherSevice.delete(id);
	}
}
