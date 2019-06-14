package dreamshop.order;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.common.collect.Iterables;

import dreamshop.catalog.SanPham;
import dreamshop.catalog.SanPhamCatalog;
import dreamshop.customer.Customer;
import dreamshop.customer.CustomerRepository;

@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
public class OrderController {
	private static final Quantity NONE = Quantity.of(0);

	private final OrderManager<Order> orderManager;
	private final SanPhamCatalog sanPhamCatalog;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private Inventory<InventoryItem> inventory;

	public OrderController(OrderManager<Order> orderManager, SanPhamCatalog sanPhamCatalog) {

		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.sanPhamCatalog = sanPhamCatalog;
		this.orderManager = orderManager;
	}

	@ModelAttribute("cart")
	Cart initializeCart() {
		
		return new Cart();
	}

	@PostMapping("/cart")
	public String addToCart(@RequestParam("pid") SanPham sanPham,@RequestParam("soluong")int soLuong, @ModelAttribute Cart cart) {
		
		
		
		if(soLuong<=0) {
			soLuong=1;
		}
	
		  Optional<InventoryItem> item = inventory.findByProductIdentifier(sanPham.getId()); 
		  Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);
		  int soLuongKho = quantity.getAmount().intValue();
		  if(soLuong>soLuongKho) {
			  return "redirect:/";
		  }
		  Iterator<CartItem> listItem = cart.iterator();
		  
		  if(listItem.hasNext())
		  {
			  	CartItem cartItem = listItem.next();
		
				 if(cartItem.getProduct().getId().equals(sanPham.getId())) {
					  int soLuongMoi = cartItem.getQuantity().getAmount().intValue()+soLuong;
						 if(soLuongMoi>=soLuongKho) {
							 if(soLuongKho>=20) {
								 cart.addOrUpdateItem(sanPham, 20);
							 }else {
								 cart.removeItem(cartItem.getId());
								 cart.addOrUpdateItem(sanPham, soLuongKho);
							 }
							
						 }else {
							 if(soLuongMoi>=20) {
								 cart.addOrUpdateItem(sanPham,20);
							 }else {
								 cart.addOrUpdateItem(sanPham, soLuong);
							 }
							 
						 }
				  }else {
					  if(soLuong>=soLuongKho) {
						  if(soLuongKho>=20) {
							  cart.addOrUpdateItem(sanPham, 20);
						  }else {
							  cart.addOrUpdateItem(sanPham, soLuongKho);
						  }
					  }else {
						  cart.addOrUpdateItem(sanPham, soLuong);
					  }
				  }
		  }else {
			  if(soLuong>=soLuongKho) {
				  if(soLuongKho>=20) {
					  cart.addOrUpdateItem(sanPham, 20);
				  }else {
					  cart.addOrUpdateItem(sanPham, soLuongKho);
				  }
			  }else {
				  cart.addOrUpdateItem(sanPham, soLuong);
			  }
		  }
		  return "redirect:/";
		
		
	}

	@GetMapping("/cart/{pid}")
	public String removeItem(@PathVariable("pid") String id, @ModelAttribute Cart cart) {

		cart.removeItem(id);
		return "redirect:/cart";
	}

	@PostMapping("/cart/update")
	public String updateItem(@RequestParam("data") String data, @ModelAttribute Cart cart) {
		

		String[] data1 = data.split(",");
		for (int i = 0; i < data1.length; i++) {
			String id = data1[i].split("/")[0];
			int soluong = Integer.parseInt(data1[i].split("/")[1]);
			String iditem = data1[i].split("/")[2];
			if(soluong<=0) {
				soluong=1;
			}
			Iterable<SanPham> listSP = sanPhamCatalog.findSanPhamById(id);
			SanPham sp = Iterables.get(listSP, 0);
			
			Optional<InventoryItem> item = inventory.findByProductIdentifier(sp.getId()); 
			Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);
			int soLuongKho = quantity.getAmount().intValue();
			
			
			cart.removeItem(iditem);
			
			if(soluong>=soLuongKho) {
				if(soLuongKho>20) {
					cart.addOrUpdateItem(sp, 20);
				}else {
					cart.addOrUpdateItem(sp, soLuongKho);
				}
			}else if(soluong<soLuongKho) {
				if(soluong>20) {
					cart.addOrUpdateItem(sp, 20);
				}else {
					cart.addOrUpdateItem(sp, soluong);
				}
			}
		}

		return "redirect:/";
	}

	@GetMapping("/cart")
	String getCart(@ModelAttribute Cart cart,Model model) {
		boolean ktrasl = false;
		if(cart.toList().size()>0) {
			ktrasl=true;
		}else {
			ktrasl=false;
		}
		
		model.addAttribute("ktrasl",ktrasl);
		return "cart";
	}

	@GetMapping("/checkout")
	String buy(@ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount) {

		return userAccount.map(account -> {

			Order order = new Order(account, Cash.CASH);

			cart.addItemsTo(order);
			orderManager.save(order);
			orderManager.payOrder(order);
			/* orderManager.completeOrder(order); */

			cart.clear();

			return "redirect:/";
		}).orElse("redirect:/cart");
	}

	@GetMapping("/orders")
	@PreAuthorize("hasRole('BOSS')")
	String orders(Model model) {

		model.addAttribute("ordersCompleted", orderManager.findAll(new PageRequest(0, 10)));

		return "orders";
	}

	@GetMapping("/detail-order/{id}")
	@PreAuthorize("hasRole('BOSS')")
	String orderDetail(@PathVariable("id") Order order, Model model) {

		model.addAttribute("order", order);
		Customer cus = Iterables.get(customerRepository.findByUserAcount(order.getUserAccount()), 0);
		model.addAttribute("customer", cus);
		return "order-detail";
	}

	@PostMapping("/xacnhan/{id}")
	@PreAuthorize("hasRole('BOSS')")
	@ResponseBody
	public String orderXacNhan(@PathVariable("id") Order order) {

		try {
			orderManager.completeOrder(order);
			return "true";
		} catch (Exception e) {
			return "false";
		}

	}

	@PostMapping("/huybo/{id}")
	@PreAuthorize("hasRole('BOSS')")
	@ResponseBody
	public String orderHuyBo(@PathVariable("id") Order order) {
		try {
			orderManager.cancelOrder(order);
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}
}
