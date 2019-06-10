package dreamshop.customer;

import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomerController {

	private final CustomerManagement customerManagement;

	CustomerController(CustomerManagement customerManagement) {

		Assert.notNull(customerManagement, "CustomerManagement must not be null!");

		this.customerManagement = customerManagement;
	}
	 
	@PostMapping("/register")
	public String registerCustomer(@Valid RegistrationFrom form, Errors result,Model model) {
		System.out.println(form.getPassword());
		System.out.println(form.getConfirmPassword());
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			model.addAttribute("error","Lỗi Confirm Password không trùng khớp !");
		}
		else {
		customerManagement.createCustomer(form);
		model.addAttribute("error","Bạn đã đăng ký thành công , bây h có thể đăng nhập bằng tài khoản vừa tạo");
		}
		return "login";
	}
	
	@GetMapping("/checkusernameandemail")
	@ResponseBody
	public String checkTrungUserName(@RequestParam("name") String name, @RequestParam("type") String type) {
		if(type.equals("1")) {
			try {
				String u = customerManagement.checkUsername(name.toLowerCase());
				if(u!=null) {
					return "true";
				}else {
					return "false";
				}
			}catch(Exception e)
			{
				return "false";
			}
		}else {
			try {
				String u = customerManagement.checkEmail(name.toLowerCase());
				if(u!=null) {
					return "true";
				}else {
					return "false";
				}
			}catch(Exception e)
			{
				return "false";
			}
		}
	}
	
	
	@GetMapping("/customer")
	@PreAuthorize("hasRole('BOSS')")
	public String customer(Model model) {
		model.addAttribute("customerList", customerManagement.findAll());
		
		return "/customer";
	}
	
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('BOSS')")
	@ResponseBody
	public String delete(@PathVariable("id") long  id,Model model) {
		
		Optional<Customer> customer = customerManagement.findById(id);
		try {
			customerManagement.delete(customer.get());
			return "true";
		}catch(Exception e) {
			return "false";
		}
		

	}
}
