package com.developer.admin.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.admin.security.DevelopUserDetails;
import com.developer.common.entity.Product;
import com.developer.common.exception.ProductNotFoundException;



@Service
@Transactional
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 10;
	
	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll(){
		updateInStockForAllProducts();
		return (List<Product>) repo.findAll();
	}
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer categoryId){
		
		Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
		String keyword = helper.getKeyword();
		Page<Product> page = null;
		
		if (keyword != null && !keyword.isEmpty()) {
			if(categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = repo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
			} else {
				page = repo.findAll(keyword, pageable);
			}
		}else {
			if(categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
			}else {
				page = repo.findAll(pageable);
			}
		}
		helper.updateModelAttributes(pageNum, page);
	}
	
	public Product save(Product product) {
		if (product.getId() == null) {
	        // New product
	        product.setCreatedTime(new Date());
	        product.setUpdatedTime(new Date());
	        
	        // create pdt code.
	        String typeCode = product.getKind();
	        List<Product> products = repo.findByKindOrderByPdtCodeDesc(typeCode);
	        int nextRandomNumber;
	        if (products.isEmpty()) {
	            nextRandomNumber = 1;
	        } else {
	            String latestProductKey = products.get(0).getPdtCode();
	            int latestRandomNumber = Integer.parseInt(latestProductKey.substring(typeCode.length()));
	            nextRandomNumber = latestRandomNumber + 1;
	        }
	        String productKey = generateProductKey(typeCode, nextRandomNumber);
	        product.setPdtCode(productKey);
	        // end create pdt code
	    } else {
	        // Existing product
	        // Retrieve product information from the database
	        Product existingProduct = repo.findById(product.getId()).get();
	        // Set the creation time to the existing value
	        product.setCreatedTime(existingProduct.getCreatedTime());
	        product.setUpdatedTime(new Date());
	        product.setNumViews(existingProduct.getNumViews());
	    }
		
		if(product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replace(" ", "-");
			product.setAlias(defaultAlias);
		}else {
			product.setAlias(product.getAlias().replace(" ", "-"));
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DevelopUserDetails loggedUser = (DevelopUserDetails)auth.getPrincipal();
		product.setWorkUser(loggedUser.getFullname());
		
        
		
		return repo.save(product);
	}

	public String generateProductKey(String typeCode, int nextRandomNumber) {
	    switch(typeCode) {
	        case "A01":
	            return typeCode + String.format("%05d", nextRandomNumber);
	        case "B01":
	            return typeCode + String.format("%05d", nextRandomNumber);
	        case "D01":
	            return typeCode + String.format("%05d", nextRandomNumber);
	        default:
	            return null;
	    }
	}
	
	public void saveProductPrice(Product productInForm) {
		Product productInDB = repo.findById(productInForm.getId()).get();
		productInDB.setCost(productInForm.getCost());
		productInDB.setPrice(productInForm.getPrice());
		productInDB.setDiscountPercent(productInForm.getDiscountPercent());
		
		productInDB.setUpdatedTime(new Date());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DevelopUserDetails loggedUser = (DevelopUserDetails)auth.getPrincipal();
		productInDB.setWorkUser(loggedUser.getFullname());
		
		repo.save(productInDB);
	}
	
	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = repo.findByName(name);

		if (isCreatingNew) {
			if (productByName != null)
				return "Duplicate";
		} else {
			if (productByName != null && productByName.getId() != id) {
				return "Duplicate";
			}
		}

		return "OK";
	}
	
	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
	
	public void delete(Integer id) throws ProductNotFoundException {
		Long countById = repo.countById(id);
		
		if (countById == null || countById == 0) {
			throw new ProductNotFoundException("Không tìm thấy ID sản phẩm: " + id);
		}
		repo.deleteById(id);
	}
	
	public Product get(Integer id) throws ProductNotFoundException {
		try {
			return repo.findById(id).get();
		}catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("Không thể tìm thấy sản phẩm với ID: " + id);
		}
	}
	
	@Transactional
    public void updateInStockForAllProducts() {
        Iterable<Product> productsIterable = repo.findAll();
        List<Product> products = new ArrayList<>();
        productsIterable.forEach(products::add);
        for (Product product : products) {
            if (product.getQty() == null || product.getQty() <= 0) {
                product.setInStock(false);
            } else {
                product.setInStock(true);
            }
        }
        repo.saveAll(products);
    }

}
