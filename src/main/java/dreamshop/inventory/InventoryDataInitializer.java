package dreamshop.inventory;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import dreamshop.catalog.SanPhamCatalog;

@Component
@Order(20)
public class InventoryDataInitializer implements DataInitializer {

	private final Inventory<InventoryItem> inventory;
	private final SanPhamCatalog sanPhamCatalog;
	
	public InventoryDataInitializer(Inventory<InventoryItem> inventory, SanPhamCatalog sanPhamCatalog) {
		super();
		this.inventory = inventory;
		this.sanPhamCatalog = sanPhamCatalog;
	}



	@Override
	public void initialize() {

		sanPhamCatalog.findAll().forEach(sp -> {
			inventory.findByProduct(sp).orElseGet(() -> inventory.save(new InventoryItem(sp, Quantity.of(10))));
		});
	}

}
