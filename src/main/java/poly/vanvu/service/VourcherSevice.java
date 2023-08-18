package poly.vanvu.service;

import poly.vanvu.dto.VourcherDto;
import poly.vanvu.entity.Vourcher;

public interface VourcherSevice {
	Vourcher save(VourcherDto vourcherDto);
	void delete(int id);
}
