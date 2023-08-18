package poly.vanvu.service.imp;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.vanvu.dto.VourcherDto;
import poly.vanvu.entity.Vourcher;
import poly.vanvu.repository.VourcherRepository;
import poly.vanvu.service.VourcherSevice;

@Service
public class VourcherServiceImp implements VourcherSevice{
	@Autowired
	VourcherRepository vourcherRepository;
	
	@Override
	public Vourcher save(VourcherDto vourcherDto) {
		Vourcher vourcher = new Vourcher();
		
		BeanUtils.copyProperties(vourcherDto, vourcher);

		
		vourcherRepository.save(vourcher);
		return vourcher;
	}

	@Override
	public void delete(int id) {
		Vourcher vourcher = vourcherRepository.findById(id).orElse(null);
		vourcherRepository.delete(vourcher);
	}

}
