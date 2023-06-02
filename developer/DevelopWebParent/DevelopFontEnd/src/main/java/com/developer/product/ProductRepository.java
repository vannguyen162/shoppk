package com.developer.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.developer.common.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

	@Query("SELECT p FROM Product p WHERE p.enabled = true "
			+ "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%)"
			+ " ORDER BY p.name ASC"
			)
	public Page<Product> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);
	
	public Product findByAlias(String alias);
	
	@Query(value = "SELECT * FROM products WHERE enabled = true AND "
			+ "MATCH(name, short_description) AGAINST (?1)",
//			+ "MATCH(name, short_description) AGAINST (:keyword IN NATURAL LANGUAGE MODE)",
//			+ "name LIKE '%' || :keyword || '%' ",
			nativeQuery = true)
	public Page<Product> search(String keyword, Pageable pageable);
	
	Page<Product> findAllByOrderByNumViewsDesc(Pageable pageable);
	@Query("SELECT p FROM Product p WHERE p.enabled = true "
			+ " AND p.relatedPdt = true"
			)
	List<Product> findByRelatedPdtTrue();
	
//	26/03/2023
	@Query("SELECT p FROM Product p WHERE p.enabled = true "
			+ " AND p.brand.id = ?1"
			+ " ORDER BY p.name ASC"
			)
	public Page<Product> listByBanrds(Integer brandId, Pageable pageable);
	
//	03/04/2023 Sort Product
	@Query("SELECT p FROM Product p WHERE p.enabled = true AND p.brand.id = ?1 AND p.price BETWEEN ?2 AND ?3 ORDER BY p.name ASC")
	public Page<Product> listByBrandsAndPriceRange(Integer brandId, float minPrice, float maxPrice, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.enabled = true AND p.category.alias = ?1 AND p.price BETWEEN ?2 AND ?3 ORDER BY p.name ASC")
	public Page<Product> listByCategoryAndPriceRange(String alias, float minPrice, float maxPrice, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.enabled = true ORDER BY p.name ASC")
	public Page<Product> listAllPDT(Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.enabled = true AND p.category.alias IN :aliases AND p.price BETWEEN :minPrice AND :maxPrice ORDER BY p.name ASC")
	public Page<Product> listMoreCategoriesAndPrice(@Param("aliases") List<String> aliases, @Param("minPrice") float minPrice, @Param("maxPrice") float maxPrice, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.enabled = true AND p.category.alias IN :aliases ORDER BY p.name ASC")
	public Page<Product> listProductsByCategories(@Param("aliases") List<String> aliases, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.enabled = true AND p.price BETWEEN :minPrice AND :maxPrice ORDER BY p.name ASC")
	public Page<Product> listProductsByPriceRange(@Param("minPrice") float minPrice, @Param("maxPrice") float maxPrice, Pageable pageable);

}
