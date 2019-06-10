package dreamshop.catalog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.classic.spi.STEUtil;
import dreamshop.catalog.SanPham.LoaiSanPham;
import dreamshop.catalog.SanPham.SanPhamType;

@Controller
public class TrangChuController {

	@Autowired
	SanPhamCatalog sanPhamCatalog;
	
	@GetMapping("/")
	public String getDefault(Model model) {
		
		model.addAttribute("sanphamrandom",sanPhamCatalog.findByRandom());
		model.addAttribute("aothun",sanPhamCatalog.findSanPhamByLoaiRandomLimit(LoaiSanPham.AOTHUN.ordinal()));
		model.addAttribute("aosomi",sanPhamCatalog.findSanPhamByLoaiRandomLimit(LoaiSanPham.AOSOMI.ordinal()));
		model.addAttribute("aokhoat",sanPhamCatalog.findSanPhamByLoaiRandomLimit(LoaiSanPham.AOKHOAT.ordinal()));
		model.addAttribute("quandai",sanPhamCatalog.findSanPhamByLoaiRandomLimit(LoaiSanPham.QUANDAI.ordinal()));
		model.addAttribute("quanngan",sanPhamCatalog.findSanPhamByLoaiRandomLimit(LoaiSanPham.QUANNGAN.ordinal()));

	
		return "index";
	}
}
