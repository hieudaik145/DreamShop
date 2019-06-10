package dreamshop.catalog;

import static org.salespointframework.core.Currencies.EURO;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import dreamshop.catalog.SanPham.LoaiSanPham;
import dreamshop.catalog.SanPham.SanPhamType;


@Component
@Order(20)
public class CatalogDataInitializer implements DataInitializer {

	private static final Logger LOG =  LoggerFactory.getLogger(CatalogDataInitializer.class);

	private  final SanPhamCatalog sanPhamCatalog;
	

	
	public CatalogDataInitializer(SanPhamCatalog sanPhamCatalog) {
		super();
		Assert.notNull(sanPhamCatalog, "SanPhamCatalog must not be null!");
		this.sanPhamCatalog = sanPhamCatalog;
	}





	@Override
	public void initialize() {

		if(sanPhamCatalog.findAll().iterator().hasNext()) {
			return;
		}
		
		LOG.info("Creating default catalog entries.");
		sanPhamCatalog.save(new SanPham("Áo Thun Nữ 1",  Money.of(100, EURO), "product1.jpg", "Hihi", SanPhamType.WOMEN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nữ 2",  Money.of(100, EURO), "product1.jpg", "Hihi", SanPhamType.WOMEN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nữ 3",  Money.of(100, EURO), "product1.jpg", "Hihi", SanPhamType.WOMEN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nữ 4", Money.of(100, EURO), "product1.jpg", "Hihi", SanPhamType.WOMEN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nữ 5",  Money.of(100, EURO), "product1.jpg", "Hihi", SanPhamType.WOMEN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nữ 6",  Money.of(100, EURO), "product1.jpg", "Hihi", SanPhamType.WOMEN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nữ 7",  Money.of(100, EURO), "product1.jpg", "Hihi", SanPhamType.WOMEN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nữ 8",  Money.of(100, EURO), "product1.jpg", "Hihi", SanPhamType.WOMEN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nữ 9", Money.of(100, EURO), "product1.jpg", "Hihi", SanPhamType.WOMEN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nữ 10",  Money.of(100, EURO), "product1.jpg", "Hihi", SanPhamType.WOMEN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nam 1", Money.of(170, EURO),"product2.jpg","HiHi",SanPhamType.MAN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nam 2", Money.of(125, EURO),"product2.jpg","HiHi",SanPhamType.MAN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nam 3", Money.of(130, EURO),"product2.jpg","HiHi",SanPhamType.MAN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nam 4", Money.of(145, EURO),"product2.jpg","HiHi",SanPhamType.MAN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nam 5", Money.of(155, EURO),"product2.jpg","HiHi",SanPhamType.MAN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nam 6", Money.of(200, EURO),"product2.jpg","HiHi",SanPhamType.MAN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nam 7", Money.of(222, EURO),"product2.jpg","HiHi",SanPhamType.MAN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nam 8", Money.of(221, EURO),"product2.jpg","HiHi",SanPhamType.MAN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nam 9", Money.of(176, EURO),"product2.jpg","HiHi",SanPhamType.MAN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Thun Nam 10", Money.of(333, EURO),"product2.jpg","HiHi",SanPhamType.MAN,LoaiSanPham.AOTHUN));
		sanPhamCatalog.save(new SanPham("Áo Sơ Mi 1", Money.of(100, EURO),"aosomi.jpg","HiHI",SanPhamType.MAN,LoaiSanPham.AOSOMI));
		sanPhamCatalog.save(new SanPham("Áo Sơ Mi 2", Money.of(111, EURO),"aosomi.jpg","HiHI",SanPhamType.MAN,LoaiSanPham.AOSOMI));
		sanPhamCatalog.save(new SanPham("Áo Sơ Mi 3", Money.of(800, EURO),"aosomi.jpg","HiHI",SanPhamType.MAN,LoaiSanPham.AOSOMI));
		sanPhamCatalog.save(new SanPham("Áo Sơ Mi 4", Money.of(200, EURO),"aosomi.jpg","HiHI",SanPhamType.MAN,LoaiSanPham.AOSOMI));
		sanPhamCatalog.save(new SanPham("Áo Sơ Mi 5", Money.of(250, EURO),"aosomi.jpg","HiHI",SanPhamType.MAN,LoaiSanPham.AOSOMI));
		sanPhamCatalog.save(new SanPham("Áo Sơ Mi 6", Money.of(131, EURO),"aosomi.jpg","HiHI",SanPhamType.MAN,LoaiSanPham.AOSOMI));
		sanPhamCatalog.save(new SanPham("Áo Sơ Mi 7", Money.of(423, EURO),"aosomi.jpg","HiHI",SanPhamType.MAN,LoaiSanPham.AOSOMI));
		sanPhamCatalog.save(new SanPham("Áo Sơ Mi 8", Money.of(222, EURO),"aosomi.jpg","HiHI",SanPhamType.MAN,LoaiSanPham.AOSOMI));
		sanPhamCatalog.save(new SanPham("Áo Sơ Mi 9", Money.of(332, EURO),"aosomi.jpg","HiHI",SanPhamType.MAN,LoaiSanPham.AOSOMI));
		sanPhamCatalog.save(new SanPham("Áo Sơ Mi 10", Money.of(112, EURO),"aosomi.jpg","HiHI",SanPhamType.MAN,LoaiSanPham.AOSOMI));
		sanPhamCatalog.save(new SanPham("Áo Khoát 1", Money.of(112, EURO), "aokhoat.jpg", "Hi Dream Team", SanPhamType.MAN, LoaiSanPham.AOKHOAT));
		sanPhamCatalog.save(new SanPham("Áo Khoát 2", Money.of(113, EURO), "aokhoat.jpg", "Hi Dream Team", SanPhamType.MAN, LoaiSanPham.AOKHOAT));
		sanPhamCatalog.save(new SanPham("Áo Khoát 3", Money.of(243, EURO), "aokhoat.jpg", "Hi Dream Team", SanPhamType.MAN, LoaiSanPham.AOKHOAT));
		sanPhamCatalog.save(new SanPham("Áo Khoát 4", Money.of(165, EURO), "aokhoat.jpg", "Hi Dream Team", SanPhamType.MAN, LoaiSanPham.AOKHOAT));
		sanPhamCatalog.save(new SanPham("Áo Khoát 5", Money.of(112, EURO), "aokhoat.jpg", "Hi Dream Team", SanPhamType.MAN, LoaiSanPham.AOKHOAT));
		sanPhamCatalog.save(new SanPham("Áo Khoát 6", Money.of(200, EURO), "aokhoat.jpg", "Hi Dream Team", SanPhamType.MAN, LoaiSanPham.AOKHOAT));
		sanPhamCatalog.save(new SanPham("Áo Khoát 7", Money.of(200, EURO), "aokhoat.jpg", "Hi Dream Team", SanPhamType.MAN, LoaiSanPham.AOKHOAT));
		sanPhamCatalog.save(new SanPham("Áo Khoát 8", Money.of(222, EURO), "aokhoat.jpg", "Hi Dream Team", SanPhamType.MAN, LoaiSanPham.AOKHOAT));
		sanPhamCatalog.save(new SanPham("Áo Khoát 9", Money.of(333, EURO), "aokhoat.jpg", "Hi Dream Team", SanPhamType.MAN, LoaiSanPham.AOKHOAT));
		sanPhamCatalog.save(new SanPham("Áo Khoát 10", Money.of(165, EURO), "aokhoat.jpg", "Hi Dream Team", SanPhamType.MAN, LoaiSanPham.AOKHOAT));
	}
}
