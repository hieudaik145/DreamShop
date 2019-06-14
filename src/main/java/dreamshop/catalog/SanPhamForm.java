package dreamshop.catalog;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

public interface SanPhamForm {

	@NotEmpty
	String getName();
	
	@NotEmpty
	String getPrice();
	
	@NotEmpty
	String getLoaiSanPham();
	
	@NotEmpty
	String getTypes();
	
	
	@NotEmpty
	String getImage();
	
	@NotEmpty
	String getMoTa();
	
	int getSoLuong();
	
	
}
