/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dreamshop.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;


@Entity
public class SanPham extends Product {

	public static enum SanPhamType{
		WOMEN,MAN,KID;
	}
	
	public static enum LoaiSanPham{
		AOTHUN, AOKHOAT, AOSOMI,QUANDAI,QUANNGAN;
	}
	
	private LoaiSanPham loaiSanPham;
	private String image;
	private String mota;
	private SanPhamType types ;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();
	
	
	
	
	public LoaiSanPham getLoaiSanPham() {
		return loaiSanPham;
	}

	public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}

	
	
	public List<Comment> getComments() {
		return comments;
	}

	public void addComments(Comment comment) {
		this.comments.add(comment)
;	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getMota() {
		return mota;
	}
	public void setMota(String mota) {
		this.mota = mota;
	}

	public SanPhamType getTypes() {
		return types;
	}

	public void setTypes(SanPhamType types) {
		this.types = types;
	}

	public SanPham(String name, Money price, String image, String mota, SanPhamType type, LoaiSanPham loaSanPham) {
		super(name,price);
		this.image = image;
		this.mota = mota;
		this.types = type;
		this.loaiSanPham = loaSanPham;
	}
	
	public SanPham(String name, Money price, String image, String mota, SanPhamType types) {
		super(name,price);
		this.image = image;
		this.mota = mota;
		this.types = types;
		
	}
	
	@SuppressWarnings("unused")
	public SanPham() {
		super();
	}
	
}
