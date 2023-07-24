package poly.vanvu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.vanvu.entity.Vourcher;

public interface VourcherRepository extends JpaRepository<Vourcher, Integer>{
	@Query("SELECT v FROM Vourcher v WHERE v.minimumApply <= ?1 AND v.quantity > 0 AND v.expirationDate > ?2")
	List<Vourcher> findVourcher(double totalPrice, Date currentDate);
}
