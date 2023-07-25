package poly.vanvu.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VourcherDto {
	private int id;
	private String nameVourcher;
	private String vourcherCode;
	private Double minimumApply;
	private Double vourcherValue;
	private Date createDate;
	private Date expirationDate;
	private int quantity;
}
