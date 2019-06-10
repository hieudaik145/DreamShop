package dreamshop.catalog;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dreamshop.catalog.SanPham.LoaiSanPham;
import dreamshop.catalog.SanPham.SanPhamType;

public interface SanPhamCatalog extends Catalog<SanPham> {

	
	
	
	static final Sort DEFAULT_SORT = Sort.by("productIdentifier").descending();
	static final Sort THAP_CAO = Sort.by("productIdentifier").ascending();
	
	@Query(value="select * from Product where PRODUCT_ID   = :pid",nativeQuery = true)
	Iterable<SanPham> findSanPhamById(@Param("pid")String pid);
	
	Iterable<SanPham> findByTypes(SanPhamType type, Sort sort);
	
	Iterable<SanPham>findByLoaiSanPham(LoaiSanPham loaiSanPham,Sort sort);
	
	@Query(value="select * from Product where lower(NAME) LIKE %:search%" ,nativeQuery = true)
	Iterable<SanPham> findBySearchSanPham(@Param("search")String input);

	//get San pHam bay loai random 12 sp
	@Query(value="select * from Product where Loai_San_Pham = :loaiSP order by Random() ", nativeQuery = true)
	Iterable<SanPham>findSanPhamByLoaiRandom(@Param("loaiSP") int loaiSP);
	
	
	//get san pham by type and loai sp
	@Query(value="select * from Product where TYPES = :types and Loai_San_Pham = :loaiSP  ",nativeQuery = true)
	Iterable<SanPham>findSanPhamByTypeAndLoaiSP(@Param("types") int types, @Param("loaiSP") int loaiSP );
	

	//get San pHam bay loai random 4 sp
	@Query(value="select * from Product where Loai_San_Pham = :loaiSP order by Random() limit 8", nativeQuery = true)
	Iterable<SanPham>findSanPhamByLoaiRandomLimit(@Param("loaiSP") int loaiSP);
	
	//get san pham random 
	@Query("select p from Product p order by Random()")
	Iterable<SanPham> findByRandom(PageRequest pageRequest);
	
	default Iterable<SanPham> findByLoaiSanPham(LoaiSanPham loaiSanPham){
		return findByLoaiSanPham(loaiSanPham,DEFAULT_SORT);
	}
	
	default Iterable<SanPham> findByRandom(){
		return findByRandom(PageRequest.of(0, 6));
	}
	
	default Iterable<SanPham> findByType(SanPhamType type){
	
		return findByTypes(type, DEFAULT_SORT);
	}
	
}
