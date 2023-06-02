package com.developer.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.developer.Utility;
import com.developer.category.CategoryService;
import com.developer.comment.CommentService;
import com.developer.comment.entity.CommentReplies;
import com.developer.comment.entity.Comments;
import com.developer.common.entity.Category;
import com.developer.common.entity.Customer;
import com.developer.common.entity.Product;
import com.developer.common.exception.CategoryNotFoundException;
import com.developer.customer.CustomerService;

@Controller
public class ProductController {
	
	@Autowired private CategoryService categoryService;
	@Autowired private ProductService productService;
	
	@Autowired private CommentService commentService;
	@Autowired private CustomerService customerService;
	
	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias,
			Model model) {
		List<Category> listCategories = categoryService.listNoChildrenCategories();
		List<Category> rootCategories = categoryService.rootCategories();
		
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("rootCategories", rootCategories);
		
//		03/04/2023 Sort Product
		float minPrice = 0;
		float maxPrice = 999999999;
		return viewCategoryByPage(alias, 1, minPrice, maxPrice, model);
	}
	
	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias,
			@PathVariable("pageNum") int pageNum,
			@RequestParam(name = "minPrice", required = false) Float minPrice,
            @RequestParam(name = "maxPrice", required = false) Float maxPrice,
			Model model) {
		try {
			List<Category> listCategories = categoryService.listNoChildrenCategories();
			model.addAttribute("listCategories", listCategories);
			
			Category category = categoryService.getCategory(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);

//			Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
			
//			03/04/2023 Sort Product
			Page<Product> pageProducts;
		    if (minPrice != null && maxPrice != null) {
		        pageProducts = productService.listByCategoryAndPriceRange(pageNum, alias, minPrice, maxPrice);
		    } else {
		        pageProducts = productService.listByCategory(pageNum, category.getId());
		    }
			
			
			List<Product> listProducts = pageProducts.getContent();

			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}

			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);

			return "product/product_by_category";
		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}
	
	@GetMapping("/p/{product_alias}")
	public String viewProductDetail(@PathVariable("product_alias") String alias, Model model, HttpServletRequest request) {
		try {
			
			Product product = productService.getProduct(alias);
			productService.increaseProductViews(product.getId());
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
			
			Page<Product> listProductsByViews = productService.getMostViewsProduct(1);
			model.addAttribute("listProductsByViews", listProductsByViews);
			
			List<Product> listProductsRelateds = productService.getRelatedProduct();
			model.addAttribute("listProductsRelateds", listProductsRelateds);
			
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("product", product);
			model.addAttribute("pageTitle", product.getShortName());
			
			List<Category> listCategories = categoryService.listNoChildrenCategories();
			List<Category> rootCategories = categoryService.rootCategories();
			
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("rootCategories", rootCategories);
			
			List<Comments> comments = commentService.getCommentsByProductId(product.getId());
			model.addAttribute("comments", comments);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated()) {
				Customer customer = getAuthenticatedCustomer(request);
				List<Comments> isCommentByCustomer = new ArrayList<>();
				for (Comments comment : comments) {
					if (comment.getCustomer().equals(customer)) {
						isCommentByCustomer.add(comment);
					}
				}
				List<CommentReplies> isRepliesByCustomer = new ArrayList<>();
				List<CommentReplies> commentReplies = commentService.getReliesByProductId(product.getId());
				for (CommentReplies replies : commentReplies) {
					if (replies.getCustomer().equals(customer)) {
						isRepliesByCustomer.add(replies);
					}
				}
				if (!isCommentByCustomer.isEmpty() || !isRepliesByCustomer.isEmpty()) {
					model.addAttribute("isCommentByCustomer", isCommentByCustomer != null ? isCommentByCustomer : Collections.emptyList());
					model.addAttribute("isRepliesByCustomer", isRepliesByCustomer != null ? isRepliesByCustomer : Collections.emptyList());
				}
			}
			
			return "product/product_detail";
		} catch (Exception e) {
			return "error/404";
		}
	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
 		String email = Utility.getEmailOfAuthenticatedCustomer(request);
 		return customerService.getCustomerByEmail(email);
 	}
	
	@GetMapping("/search")
	public String searchFirstPage(@Param("keyword") String keyword, Model model) {
		return searchByPage(keyword, 1, model);
	}
	
	@GetMapping("/search/page/{pageNum}")
	public String searchByPage(@Param("keyword") String keyword, 
			@PathVariable("pageNum") int pageNum,
			Model model) {
		Page<Product> pageProducts = productService.search(keyword, pageNum);
		List<Product> listResult = pageProducts.getContent();
		
		
		long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;
		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", "Tìm kiếm - " + keyword);
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("listResult", listResult);
		
		return "product/search_result";
	}
	
//	03/04/2023 Sort Product
	@GetMapping("/allpdt/page/{pageNum}")
	public String listAllPDT(@PathVariable("pageNum") int pageNum,
			@RequestParam(name = "minPrice", required = false) Float minPrice,
			@RequestParam(name = "maxPrice", required = false) Float maxPrice, 
			@RequestParam(name = "category_alias", required = false) String aliasesStr, 
			Model model) {
		List<Category> listCategories = categoryService.listAllCat();
		model.addAttribute("listCategories", listCategories);

	    Page<Product> pageProducts;
	    List<String> aliases = null;
	    if (aliasesStr != null && !aliasesStr.isEmpty()) {
	        aliases = Arrays.asList(aliasesStr.split(","));
	    }
	    if (minPrice != null && maxPrice != null) {
	        if (aliases != null && !aliases.isEmpty()) {
	            pageProducts = productService.listMoreCategoriesAndPrice(pageNum, aliases, minPrice, maxPrice);
	        } else {
	            pageProducts = productService.listProductsByPriceRange(pageNum, minPrice, maxPrice);
	        }
	    } else if (aliases != null && !aliases.isEmpty()) {
	        pageProducts = productService.listProductsByCategories(pageNum, aliases);
	    } else {
	        pageProducts = productService.listAllPDT(pageNum);
	    }

		List<Product> listProducts = pageProducts.getContent();

		long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", "Tất cả sản phẩm");
		model.addAttribute("listProducts", listProducts);

		return "product/product_all";
	}
	

}
