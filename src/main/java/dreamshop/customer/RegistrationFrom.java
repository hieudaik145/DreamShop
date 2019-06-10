package dreamshop.customer;

import javax.validation.constraints.NotEmpty;

public interface RegistrationFrom {

	@NotEmpty(message = "{Name khong the bo trong}")
	String getName();
	
	@NotEmpty(message = "{Password khong the bo trong}")
	String getPassword();
	
	@NotEmpty(message = "{Address khong the bo trong}")
	String getAddress();
	
	@NotEmpty(message = "{Email khong the bo trong}")
	String getEmail();
	
	@NotEmpty(message = "{So dien thoai khong the bo trong}")
	String getSdt();
	
	@NotEmpty(message = "{confirm thoai khong the bo trong}")
	String getConfirmPassword();
}
