package poly.vanvu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "content", columnDefinition = "NVARCHAR(300)")
	private String content;
	
	@Column(name = "createDate", columnDefinition = "DATE")
	private Date createDate;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	@JsonIgnore
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "userId", columnDefinition = "VARCHAR(55)")
	@JsonIgnore
	private User user;
}
