package poly.vanvu.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Products")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "createDate", columnDefinition = "DATE")
	private Date createDate;

	@Column(name = "description", columnDefinition = "NVARCHAR(500)")
	private String description;
	
	private int guarantee;
	
	@Column(name = "rom", columnDefinition = "INTEGER")
	private int rom;

	@Column(name = "code", columnDefinition = "NVARCHAR(50)")
	private String code;
	
	private String image;

	@Column(name = "name", columnDefinition = "NVARCHAR(100)")
	private String name;
	
	@Column(name = "quantity", columnDefinition = "INTEGER")
	private int quantity;

	private double price;

	private double priceSale;
	
	private double priceImport;

	@Column(name = "source", columnDefinition = "NVARCHAR(50)")
	private String source;
	
	@Column(name = "status", columnDefinition = "NVARCHAR(50)")
	private String status;

	@OneToMany(mappedBy="product")
	@JsonIgnore
	private List<OrderDetail> orderDetail;
	
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<Cart> cart;
	
	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="categoryId")
	@JsonIgnore
	private Category category;

	@ManyToOne
	@JoinColumn(name="colorId")
	@JsonIgnore
	private Color color;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<Comment> comment;
}