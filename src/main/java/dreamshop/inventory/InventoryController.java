package dreamshop.inventory;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InventoryController {
	
	private final Inventory<InventoryItem> inventory;

	InventoryController(Inventory<InventoryItem> inventory) {
		this.inventory = inventory;
	}
	
	@GetMapping("/stock")
	@PreAuthorize("hasRole('BOSS')")
	String stock(Model model) {

		model.addAttribute("stock", inventory.findAll());

		return "stock";
	}
}
