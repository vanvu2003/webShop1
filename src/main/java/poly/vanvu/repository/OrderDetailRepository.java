package poly.vanvu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.vanvu.entity.Oder;
import poly.vanvu.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
	List<OrderDetail> findByOrder(Oder order);
	
	@Query("SELECT SUM(d.quantity * d.price) FROM OrderDetail d GROUP BY d.product")
	List<Long> listTotalPriceSale();

	@Query("SELECT SUM(d.quantity * d.product.priceImport) FROM OrderDetail d GROUP BY d.product")
	List<Long> listTotalPriceImport();
	
	@Query("select d.product.id from OrderDetail d group by d.product.id order by SUM(d.quantity) desc")
	List<Integer> topProduct();
	
	@Query("SELECT d FROM OrderDetail d WHERE d.order.id = ?1")
	OrderDetail findByIdOrder(int orderId);
}
