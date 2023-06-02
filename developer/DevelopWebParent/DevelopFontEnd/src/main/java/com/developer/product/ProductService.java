package com.developer.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.developer.common.entity.Board;
import com.developer.common.entity.Product;
import com.developer.common.exception.ProductNotFoundException;

@Service
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 10;
	public static final int SEARCH_RESULTS_PER_PAGE = 10;
	
	@Autowired private ProductRepository repo;
	@Autowired private ProductViewRepository saveViewRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Page<Product> listByCategory(int pageNum, Integer categoryId){
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		
		return repo.listByCategory(categoryId, categoryIdMatch, pageable);
	}
	
	public Product getProduct(String alias) throws ProductNotFoundException {
		Product product = repo.findByAlias(alias);
		if(product == null) {
			throw new ProductNotFoundException("Không thể tìm thấy sản phẩm" + alias);
		}
		return product;
	}
	
	public Page<Product> search(String keyword, int pageNum){
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
		return repo.search(keyword, pageable);
	}
	
//	Tính View sản phẩm
	@Transactional
	@Modifying
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void increaseProductViews(int id) {
		Optional<Product> productOpt = repo.findById(id);
	    if(productOpt.isPresent()){
	    	Product product = productOpt.get();
	        BigDecimal numViews = product.getNumViews();
	        if(numViews == null){
	            numViews = new BigDecimal(0);
	        }
	        numViews = numViews.add(new BigDecimal(1));
	        product.setNumViews(numViews);
	        repo.save(product);
	    }
	}
	
	public Page<Product> getMostViewsProduct(int pageNum) {
	    Pageable pageable = PageRequest.of(pageNum - 1, 10);
	    return repo.findAllByOrderByNumViewsDesc(pageable);
	}
	
	public List<Product> getRelatedProduct() {
	    return repo.findByRelatedPdtTrue();
	}
	
	// lấy sản phẩm theo id
	public Product get(Integer id) {
		return repo.findById(id).get();
	}

	// 26/03/2023
	public Page<Product> listByBrands(int pageNum, Integer barndId){
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		return repo.listByBanrds(barndId, pageable);
	}
	
//	03/04/2023 Sort Product
	public Page<Product> listByBrandsAndPriceRange(int pageNum, Integer brandId, float minPrice, float maxPrice) {
	    Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
	    return repo.listByBrandsAndPriceRange(brandId, minPrice, maxPrice, pageable);
	}
	public Page<Product> listByCategoryAndPriceRange(int pageNum, String alias, float minPrice, float maxPrice) {
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		return repo.listByCategoryAndPriceRange(alias, minPrice, maxPrice, pageable);
	}
	
	public Page<Product> listAllPDT(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		return repo.listAllPDT(pageable);
	}
	public Page<Product> listProductsByPriceRange(int pageNum, float minPrice, float maxPrice) {
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		return repo.listProductsByPriceRange( minPrice, maxPrice, pageable);
	}
	
	public Page<Product> listProductsByCategories(int pageNum, List<String> aliases) {
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		return repo.listProductsByCategories(aliases, pageable);
	}
	
	public Page<Product> listMoreCategoriesAndPrice(int pageNum, List<String> aliases, float minPrice, float maxPrice) {
	    Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
	    return repo.listMoreCategoriesAndPrice(aliases, minPrice, maxPrice, pageable);
	}
	
	@Transactional
	public Product getProductById(Integer productId) throws ProductNotFoundException {
	    return repo.findById(productId).orElseThrow(() -> new ProductNotFoundException("Cannot find product with ID: " + productId));
	}

}
