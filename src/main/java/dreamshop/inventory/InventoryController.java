package dreamshop.inventory;

import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dreamshop.catalog.SanPham;
import dreamshop.catalog.SanPhamCatalog;

@Controller
public class InventoryController {
	
	private final Inventory<InventoryItem> inventory;
	
	@Autowired
	SanPhamCatalog sanPhamCatalog;
	
	@Autowired
	Accountancy accountancy;

	InventoryController(Inventory<InventoryItem> inventory) {
		this.inventory = inventory;
	}
	
	@GetMapping("/stock")
	@PreAuthorize("hasRole('BOSS')")
	String stock(Model model) {

		model.addAttribute("stock", inventory.findAll());

		return "stock";
	}
	
	@PostMapping("/update-stock/{id}")
	@PreAuthorize("hasRole('BOSS')")
	String updateStock(@PathVariable("id") InventoryItem inventoryItem, @RequestParam("quantity") int quantity) {
		
		
		  inventory.delete(inventoryItem);
		  
		  inventory.save(new InventoryItem(inventoryItem.getProduct(),
		  Quantity.of(quantity)));
		 
		
		
		return "redirect:/stock";
		
	}
	@GetMapping("/delete-stock/{id}")
	@PreAuthorize("hasRole('BOSS')")
	String deleteStock(@PathVariable("id") InventoryItem inventoryItem) {
		inventory.delete(inventoryItem);
		sanPhamCatalog.delete((SanPham) inventoryItem.getProduct());
		return "redirect:/stock";
	}
	
	@GetMapping("/accountancy")
	@PreAuthorize("hasRole('BOSS')")
	String accountacy(Model model) {
		model.addAttribute("accountacy",accountancy.findAll());
		
		return "accountancy_entry";
	}
}
