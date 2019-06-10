package dreamshop.customer;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CustomerRepository  extends CrudRepository<Customer, Long>{

	@Query(value = "SELECT USERACCOUNT_ID FROM USER_ACCOUNT WHERE lower(USERACCOUNT_ID) = :name ",nativeQuery = true)
	String getUSERACCOUNT_ID (@Param("name")String name);
	
	@Query(value = "SELECT EMAIL   FROM USER_ACCOUNT WHERE lower(EMAIL) = :name ",nativeQuery = true)
	String getEmail(@Param("name")String name);
	
	Iterable<Customer> findByUserAcount(UserAccount userAccount);
	
	
}
