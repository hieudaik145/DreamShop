package dreamshop.catalog;

import java.util.Optional;

import javax.transaction.Transactional;

import org.omg.CORBA.portable.Streamable;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.Totalable;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CommentManager {

	private final OrderManager<Order> orderManager;
	private final UserAccountManager userAccountManager;

	public CommentManager(OrderManager<Order> orderManager,UserAccountManager userAccountManager) {
		super();
		this.orderManager = orderManager;
		this.userAccountManager = userAccountManager;
	}
	
	public boolean checkComment(String name,ProductIdentifier productIdentifier) {
		
		Optional<UserAccount> useracount = userAccountManager.findByUsername(name);
		UserAccount userAcount = useracount.get();
		org.springframework.data.util.Streamable<Order> order = orderManager.findBy(userAcount);
		
		for(Order o : order) {
			for(OrderLine orderline : o.getOrderLines()) {
				if(orderline.getProductIdentifier().equals(productIdentifier)) {
					return true;
				}
			}
		}
		
//		order.forEach(o->{
//		
//			o.getOrderLines().forEach(oline->{
//				if(oline.getProductIdentifier().equals(productIdentifier)) {
//				}
//			});
//			return true;
//		});
		
		return false;
	}
}
