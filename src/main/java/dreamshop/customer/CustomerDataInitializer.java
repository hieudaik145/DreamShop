package dreamshop.customer;

import java.util.ArrayList;
import java.util.Arrays;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Order(10)
public class CustomerDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerDataInitializer.class);
	private  final UserAccountManager userAccountManager;
	private final CustomerRepository customerRepository;
	public CustomerDataInitializer(UserAccountManager userAccountManager, CustomerRepository customerRepository) {
		super();
		Assert.notNull(userAccountManager, "CustomerRepository must not be null!");
		Assert.notNull(customerRepository, "UserAccountManager must not be null!");
		
		this.userAccountManager = userAccountManager;
		this.customerRepository = customerRepository;
	}
	@Override
	public void initialize() {
		if (userAccountManager.findByUsername("boss").isPresent()) {
			return;
		}
		
		LOG.info("Creating default users and customers.");
		
		UserAccount bossAcount = userAccountManager.create("boss", "123", Role.of("ROLE_BOSS"));
		userAccountManager.save(bossAcount);
		Role customerRole = Role.of("ROLE_CUSTOMER");
		
		UserAccount ua1 = userAccountManager.create("hieupro", "1234", "vanhieupro", customerRole);
		UserAccount ua2 = userAccountManager.create("lovett", "1234", "hieu@gmail.com", customerRole);
		Customer c1 = new Customer(ua1, "Tam Ky", "0326797145");
		Customer c2 = new Customer(ua2,"Binh Dinh","03267971433");
		
		customerRepository.saveAll(Arrays.asList(c1,c2));
		
	}
	
	
	
}
