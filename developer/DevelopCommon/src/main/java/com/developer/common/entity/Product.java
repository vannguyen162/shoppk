package com.developer.common.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.developer.common.entity.product.ProductDetail;
import com.developer.common.entity.product.ProductImage;

@Entity
@Table(name = "products")
public class Product extends TimeWorkBasedEntity{
	
	@Column(unique = true, length = 256, nullable = false)
	private String name;
	
	@Column(unique = true, length = 256, nullable = false)
	private String alias;
	
	@Column(name = "short_description", length = 512, nullable = false)
	private String shortDescription;
	
	@Lob
	@Column(name = "full_description")
	private String fullDescription;

	private boolean enabled;
	
	@Column(name = "in_stock")
	private boolean inStock;
	
	private boolean relatedPdt;

	private float cost;

	private float price;

	@Column(name = "discount_percent")
	private float discountPercent;

	private float lenght;
	private float width;
	private float height;
	private float weight;

	@Column(name = "qty")
	private Integer qty;
	
	@Column(name = "kind")
	private String kind;
	
	@Column(name = "pdt_code")
	private String pdtCode;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "fabric")
	private String fabric; // loại vải
	
	@Column(name = "main_image")
	private String mainImage;

	@Column(name="number_views")
	private BigDecimal numViews;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductImage> images = new HashSet<>();
	
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductDetail> details = new ArrayList<>();

	public Product() {
	}
	
	public Product(String name) {
		this.name = name;
	}

	public Product(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public boolean isRelatedPdt() {
		return relatedPdt;
	}

	public void setRelatedPdt(boolean relatedPdt) {
		this.relatedPdt = relatedPdt;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}

	public float getLenght() {
		return lenght;
	}

	public void setLenght(float lenght) {
		this.lenght = lenght;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getQty() {
		return qty != null ? qty : 0;
	}

	public void setQty(Integer qty) {
	    if (qty == null || qty <= 0) {
	        this.inStock = false;
	    } else {
	        this.inStock = true;
	    }
	    this.qty = qty;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getPdtCode() {
		return pdtCode;
	}

	public void setPdtCode(String pdtCode) {
		this.pdtCode = pdtCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFabric() {
		return fabric;
	}

	public void setFabric(String fabric) {
		this.fabric = fabric;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public BigDecimal getNumViews() {
		return numViews;
	}

	public void setNumViews(BigDecimal numViews) {
		this.numViews = numViews;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}
	
	public void addExtraImage(String imageName) {
		this.images.add(new ProductImage(imageName, this));
	}
	
	@Transient
	public String getMainImagePath() {
		if(id == null || mainImage == null) return "/images/default-image.jpg";
		
		return "/product-images/" + this.id + "/" + this.mainImage;
	}

	public List<ProductDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ProductDetail> details) {
		this.details = details;
	}
	
	public void addDetail(String name, String value) {
		this.details.add(new ProductDetail(name, value, this));
	}

	public void addDetail(Integer id, String name, String value) {
		this.details.add(new ProductDetail(id, name, value, this));
	}

	public boolean containsImageName(String imageName) {
		Iterator<ProductImage> iterator = images.iterator();
		
		while (iterator.hasNext()) {
			ProductImage image = iterator.next();
			if(image.getName().equals(imageName)) {
				return true;
			}
		}
		return false;
	}
	
	@Transient
	public String getShortName() {
		if(name.length() > 40) {
			return name.substring(0, 40).concat("...");
		}
		return name;
	}
	
	@Transient
	public String getDiscountPercentPrice() {
		if(discountPercent > 0) {
			Float count = (((price - (price - discountPercent)) / price) * 100);
			NumberFormat formatter = new DecimalFormat("0.0");
			String formmatedFloatValue = formatter.format(count);
			return formmatedFloatValue;
		}
		return "0";
	}
	
	@Transient
	public float getDiscountPriceSale() {
		if(discountPercent > 0) {
			return (price - discountPercent);
		}
		return this.price;
	}
	
	@Transient
	public String getCostFormat() {
		DecimalFormat formatter = new DecimalFormat("#,##0");
		return formatter.format(cost);
	}
	
	@Transient
	public String getPriceFormat() {
	    DecimalFormat formatter = new DecimalFormat("#,##0");
	    return formatter.format(price);
	}
	
	@Transient
	public String getBarcode() throws ParseException {
		return id.toString()+ "-" + this.pdtCode;
	}
	
	@Transient
	public int getNumViewValue() {
	    return numViews != null ? numViews.intValue() : 0;
	}
	
	@Transient
	public Integer getQtyValue() {
		return (qty != null) ? qty : 0;
	}
	
	@Transient
	public String getInStockFormat() {
	    return inStock ? "còn hàng" : "hết hàng";
	}
	
	@Transient
	public String getBrandName() {
	    if (brand != null) {
	        return brand.getName();
	    } else {
	        return "";
	    }
	}
	@Transient
	public String getCategoryName() {
		if (category != null) {
			return category.getName();
		} else {
			return "";
		}
	}
}
