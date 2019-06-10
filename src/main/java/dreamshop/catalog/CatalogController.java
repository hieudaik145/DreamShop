package dreamshop.catalog;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.common.collect.Iterables;

import dreamshop.catalog.CatalogController.CommentAndRating;
import dreamshop.catalog.Comment.CommentTypes;
import dreamshop.catalog.SanPham.LoaiSanPham;
import dreamshop.catalog.SanPham.SanPhamType;
import dreamshop.customer.Customer;
import dreamshop.customer.CustomerManagement;
import dreamshop.customer.CustomerRepository;

@Controller
public class CatalogController {

	@Autowired
	CommentManager commentManager;
	@Autowired
	UserAccountManager userAcountManager;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerManagement customerManager;
	
	private static final Quantity NONE = Quantity.of(0);
//	private final CommentManagement commentManagement;
	private final SanPhamCatalog sanPhamCatalog;
	private final Inventory<InventoryItem> inventory;
	private final BusinessTime businessTime;

	public CatalogController(SanPhamCatalog sanPhamCatalog, Inventory<InventoryItem> inventory,
			BusinessTime businessTime) {
		super();
		this.sanPhamCatalog = sanPhamCatalog;
		this.inventory = inventory;
		this.businessTime = businessTime;
//		this.commentManagement = commentManagement;
	}

	@GetMapping("/men")
	public String getWomenCatalog(Model model) {
		model.addAttribute("title", "MENS");
		model.addAttribute("sanpham", sanPhamCatalog.findByType(SanPhamType.MAN));
		return "shop";
	}

	@GetMapping("/men/{loaisp}")
	public String getSanPhamTypeManTheoDanhMuc(@PathVariable(name = "loaisp") String loaiSP, Model model) {
		
		int loai = 0;
		switch(loaiSP) 
		{
		case "aothun":
			loai = LoaiSanPham.AOTHUN.ordinal();
			break;
		case "aokhoat":
			loai = LoaiSanPham.AOKHOAT.ordinal();
			break;
		case "aosomi":
			loai = LoaiSanPham.AOSOMI.ordinal();
			break;
		case "quandai":
			loai = LoaiSanPham.QUANDAI.ordinal();
			break;
		default:
			loai = LoaiSanPham.QUANNGAN.ordinal();
				
		}
		System.out.println(loaiSP.toUpperCase());
		model.addAttribute("title","MENS "+loaiSP.toUpperCase()+" ITEMS ");
		model.addAttribute("sanpham",sanPhamCatalog.findSanPhamByTypeAndLoaiSP(SanPhamType.MAN.ordinal(), loai));
		
		return "shop";
	}

	@GetMapping("/women/{loaisp}")
	public String getSanPhamTypeWomenTheoLoaiSP(@PathVariable("loaisp")String loaiSP,Model model) {
		
		int loai = 0;
		switch(loaiSP) 
		{
		case "aothun":
			loai = LoaiSanPham.AOTHUN.ordinal();
			break;
		case "aokhoat":
			loai = LoaiSanPham.AOKHOAT.ordinal();
			break;
		case "aosomi":
			loai = LoaiSanPham.AOSOMI.ordinal();
			break;
		case "quandai":
			loai = LoaiSanPham.QUANDAI.ordinal();
			break;
		default:
			loai = LoaiSanPham.QUANNGAN.ordinal();
				
		}
		System.out.println(loaiSP.toUpperCase());
		model.addAttribute("title","WOMENS "+loaiSP.toUpperCase()+" ITEMS ");
		model.addAttribute("sanpham",sanPhamCatalog.findSanPhamByTypeAndLoaiSP(SanPhamType.WOMEN.ordinal(), loai));
		
		return "shop";
	}
	@GetMapping("/kids")
	public String getSanPhamKids(Model model) {
		
		model.addAttribute("title","KIDS ITEMS ");
		model.addAttribute("sanpham",sanPhamCatalog.findByType(SanPhamType.KID));
		return "shop";
	}
	
	@GetMapping("/product/{loaisp}")
	public String getSanPhamByLoaiSP(@PathVariable("loaisp")String loaiSP, Model model) {
		int loai = 0;
		switch(loaiSP) 
		{
		case "aothun":
			loai = LoaiSanPham.AOTHUN.ordinal();
			break;
		case "aokhoat":
			loai = LoaiSanPham.AOKHOAT.ordinal();
			break;
		case "aosomi":
			loai = LoaiSanPham.AOSOMI.ordinal();
			break;
		case "quandai":
			loai = LoaiSanPham.QUANDAI.ordinal();
			break;
		default:
			loai = LoaiSanPham.QUANNGAN.ordinal();
		}
		model.addAttribute("title",loaiSP.toUpperCase()+" ITEMS");
		model.addAttribute("sanpham",sanPhamCatalog.findSanPhamByLoaiRandom(loai));
		
		return "shop";
	}
	@GetMapping("/search/{input}")
	public String getListSanPhamSearch(@PathVariable("input")String input, Model model) {
		
		model.addAttribute("title","SEARCH ITEMS");
		model.addAttribute("sanpham",sanPhamCatalog.findBySearchSanPham(input.toLowerCase()));
		return "shop";
	}
	
	
	@GetMapping("/detail/{sanpham}")
	public  String getDetailSanPham(@PathVariable SanPham sanpham,Model model) {
		Optional<InventoryItem> item = inventory.findByProductIdentifier(sanpham.getId());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);
		
		int soLuong = quantity.getAmount().intValue();
		model.addAttribute("sanpham",sanpham);
		model.addAttribute("soluong",soLuong);
		model.addAttribute("orderable",quantity.isGreaterThan(NONE));
		
		
		return "product-details";
	}
	
	//comment function
	@PostMapping("/detail/{pid}/comments")
	public String comment(@PathVariable("pid") SanPham sanPham, @Valid CommentAndRating payload) {
		
		
		
		if(payload.getName()!=null && payload.getEmail()!=null) {
			sanPham.addComments(payload.toComment(CommentTypes.CHUAXACNHAN,businessTime.getTime()));
			
		}else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName();
			boolean kq = commentManager.checkComment(name,sanPham.getId());
			if(kq) {
				sanPham.addComments(payload.toCommentXacNhan(name, "", CommentTypes.XACNHAN, businessTime.getTime()));
			}else {
				sanPham.addComments(payload.toCommentXacNhan(name, "", CommentTypes.CHUAXACNHAN, businessTime.getTime()));
			}
		}
		sanPhamCatalog.save(sanPham);
		return "redirect:/detail/" + sanPham.getId();
	}
	
	@GetMapping("/detail/{pid}/{idcm}/deletecomment")
	@PreAuthorize("hasRole('BOSS')")
	public String deleteComment(@PathVariable("pid") SanPham sanPham, @PathVariable("idcm") long id) {
		
		for(int i=0; i <sanPham.getComments().size();i++) {
			Comment cm = sanPham.getComments().get(i);
			if(cm.getId()==id) {
				sanPham.getComments().remove(i);
			}
		}
		sanPhamCatalog.save(sanPham);
		return "redirect:/detail/"+ sanPham.getId();
	}
	//end comment function
	
	//wish list function
	@GetMapping("wishlist/{pid}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public String WishList(@PathVariable("pid")SanPham sanPham) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		
		Iterable<Customer> customer =  customerManager.findByUserAcount(userAcountManager.findByUsername(name).get());
		
		Customer cus = Iterables.get(customer, 0);
		 
			if(!cus.getListWishlist().contains(sanPham)) {
				cus.addToWishlist(sanPham);
			}
	
		customerRepository.save(cus);
		return "redirect:/";
	}
	
	@GetMapping("wishlist")
	@PreAuthorize("hasRole('CUSTOMER')")
	public String ListWishlish(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		Iterable<Customer> customer =  customerManager.findByUserAcount(userAcountManager.findByUsername(name).get());
		Iterables.get(customer, 0);
		model.addAttribute("customer",Iterables.get(customer, 0));
		return "wishlist";
	}
	
	@GetMapping("wishlist/{pid}/remove")
	@PreAuthorize("hasRole('CUSTOMER')")
	public String RemoveWishList(@PathVariable("pid")SanPham sanPham){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		
		Iterable<Customer> customer =  customerManager.findByUserAcount(userAcountManager.findByUsername(name).get());
		
		customer.forEach(cus ->{
			cus.getListWishlist().remove(sanPham);
			customerRepository.save(cus);
		});
		return "redirect:/wishlist";
	}
	//end wishlist function
	
	interface CommentAndRating {

		@NotEmpty
		String getComment();
		
	
		String getName();
		
		
		
		String getEmail();

		@Range(min = 1, max = 5)
		int getRating();

		default Comment toComment(CommentTypes commentTypes,LocalDateTime time) {
			return new Comment(getName(), getEmail(), getComment(),getRating(), commentTypes, time);
		}
		
		default Comment toCommentXacNhan(String name,String email, CommentTypes commentTypes, LocalDateTime time) {
			return new Comment(name, email, getComment(), getRating(), commentTypes, time);
		}
	}
}
