package poly.vanvu.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.dto.VourcherDto;
import poly.vanvu.service.VourcherSevice;

@RestController
@RequestMapping("/api")
public class VourcherApi {
	@Autowired
	VourcherSevice vourcherSevice;
	
	@PostMapping("/vourcher")
	private void saveVourcher(@RequestBody VourcherDto vourcherDto ){
		
		vourcherSevice.save(vourcherDto);
	}
}
