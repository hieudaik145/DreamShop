package dreamshop.customer;

import java.util.Optional;

import javax.transaction.Transactional;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import dreamshop.catalog.SanPham;

@Service
@Transactional
public class CustomerManagement {

	private final CustomerRepository customers;
	private final UserAccountManager userAccounts;
	
	
	public CustomerManagement(CustomerRepository customers, UserAccountManager userAccounts) {
		Assert.notNull(customers, "CustomerRepository must not be null!");
		Assert.notNull(userAccounts, "UserAccountManager must not be null!");
		
		this.customers = customers;
		this.userAccounts = userAccounts;
	}
	
	public Customer createCustomer(RegistrationFrom form) {
		
		Assert.notNull(form, "Registration form must not be null!");
		
		UserAccount userAccount = userAccounts.create(form.getName(), form.getPassword(),org.salespointframework.useraccount.Role.of("ROLE_CUSTOMER"));
		userAccount.setEmail(form.getEmail());
		return customers.save(new Customer(userAccount, form.getAddress(),form.getSdt()));
	}
	
	public Streamable<Customer> findAll(){
		return Streamable.of(customers.findAll());
	}
	
	public String checkUsername(String id) {
		return customers.getUSERACCOUNT_ID(id);
	}
	
	public String checkEmail(String email) {
		return customers.getEmail(email);
	}
	
	public Iterable<Customer> findByUserAcount(UserAccount userAccount){
		return customers.findByUserAcount(userAccount);
	}
	
	public void delete(Customer cus) {
		customers.delete(cus);
	}
	
	public Optional<Customer> findById(long id){
		return customers.findById(id);
	}

	
	
	
}
