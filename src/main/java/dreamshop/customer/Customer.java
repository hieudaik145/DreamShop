package dreamshop.customer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.salespointframework.catalog.Product;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class Customer {

	@Id
	@GeneratedValue
	private long id;
	
	private String address;
	private String sdt;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	private UserAccount userAcount;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Product> listWishlist= new ArrayList<Product>();
	
	
	
	public void addToWishlist(Product product) {
		this.listWishlist.add(product);
	}
	
	public List<Product> getListWishlist() {
		return listWishlist;
	}

	public void setListWishlist(List<Product> listWishlist) {
		this.listWishlist = listWishlist;
	}

	public Customer() {
		super();
	}
	
	public Customer(UserAccount userAccount, String address,String sdt ) {
		this.userAcount = userAccount;
		this.address = address;
		this.sdt = sdt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public UserAccount getUserAcount() {
		return userAcount;
	}

	public void setUserAcount(UserAccount userAcount) {
		this.userAcount = userAcount;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	
	
	
}
