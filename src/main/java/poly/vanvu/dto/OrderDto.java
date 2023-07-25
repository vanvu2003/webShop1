package poly.vanvu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	private String fullname;
	private String phone;
	private String note;
	private String specificAddress;
	private String province;
	private String district;
	private String ward;
	private int vourcherId;
	private Double totalPrice;
}
