package poly.vanvu.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Vourcher")
public class Vourcher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "vourcherCode", columnDefinition = "VARCHAR(50)")
	private String vourcherCode;
	
	@Column(name = "nameVourcher", columnDefinition = "NVARCHAR(80)")
	private String nameVourcher;
	
	@Column(name = "vourcherValue", columnDefinition = "FLOAT")
	private double vourcherValue;
	
	@Column(name = "quantity", columnDefinition = "INTEGER")
	private int quantity;
	
	@Column(name = "minimumApply", columnDefinition = "FLOAT")
	private double minimumApply;
	
	@Column(name = "createDate", columnDefinition = "DATE")
	private Date createDate;
	
	@Column(name = "expirationDate", columnDefinition = "DATE")
	private Date expirationDate;
	
	@OneToMany(mappedBy = "vourcher")
	private List<Oder> oders;
}
