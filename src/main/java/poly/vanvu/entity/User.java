package poly.vanvu.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements UserDetails {

	@Id
	private String username;

	private String address;

	private Date createDate;

	private String email;

	private String fullname;

	private String image;

	private String password;

	private String phone;

	private String role;
	
	@Column(name = "token", columnDefinition = "VARCHAR(50)")
	private String token;

	@Column(name="Status")
	private boolean status;

	//bi-directional many-to-one association to Oder
	@OneToMany(mappedBy="user")
	private List<Oder> oder;
	
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Cart> cart;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Comment> comment;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}