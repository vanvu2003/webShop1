package poly.vanvu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.vanvu.entity.Oder;

public interface OrderRepossitory extends JpaRepository<Oder, Integer>{
	@Query("SELECT o FROM Oder o WHERE o.status = ?1 and o.user.username like ?2")
	List<Oder> findOrderStatusAndUser(int status , String username);
	
	@Query("SELECT o FROM Oder o WHERE o.status = ?1")
	List<Oder> findOrderByStatus(int status);
	
	@Query("SELECT COUNT(o) FROM Oder o WHERE o.createDate = ?1")
	int countOrderNewDate(Date currentDate);
	
	@Query("SELECT o.user.username FROM Oder o group by o.user.username order by COUNT(o.user.username) desc")
	List<String> topUser();
	
	Oder findById(int id);
}
