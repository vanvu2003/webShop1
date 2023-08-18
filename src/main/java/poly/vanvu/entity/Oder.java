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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Oders")
@NamedQuery(name="Oder.findAll", query="SELECT o FROM Oder o")
public class Oder implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "fullname", columnDefinition = "NVARCHAR(50)")
	private String fullname;
	
	@Column(name = "phone", columnDefinition = "VARCHAR(50)")
	private String phone;
	
	@Column(name = "address",columnDefinition = "NVARCHAR(300)")
	private String address;
	
	@Column(name = "note", columnDefinition = "NVARCHAR(400)")
	private String note;
	
	@Column(name="createDate", columnDefinition = "DATE")
	private Date createDate;
	
	@Column(name = "status", columnDefinition = "INTEGER")
	private int status;
	
	@Column(name = "totalPrice", columnDefinition = "FLOAT")
	private Double totalPrice;
	
	@OneToMany(mappedBy = "order")
	@JsonIgnore
	private List<OrderDetail> orderDetail;

	//bi-directional many-to-one association to User
	@ManyToOne()
	@JoinColumn(name = "userId")
	@JsonIgnore
	private User user;
	
	@ManyToOne()
	@JoinColumn(name = "vourcherId")
	@JsonIgnore
	private Vourcher vourcher;
	

}