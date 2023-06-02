package com.developer.comment;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.Utility;
import com.developer.comment.entity.CommentReplies;
import com.developer.comment.entity.Comments;
import com.developer.common.entity.Customer;
import com.developer.common.entity.Product;
import com.developer.common.exception.ProductNotFoundException;
import com.developer.customer.CustomerService;
import com.developer.product.ProductService;

@RestController
public class CommentRestController {

	@Autowired private CommentService service;
	@Autowired private CustomerService customerService;
	@Autowired private ProductService productService;
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
 		String email = Utility.getEmailOfAuthenticatedCustomer(request);
 		return customerService.getCustomerByEmail(email);
 	}
	
	@PostMapping("/comments")
	public Comments addComment(@RequestBody Comments comment, HttpServletRequest request) throws ProductNotFoundException {
		Customer customer = getAuthenticatedCustomer(request);
		comment.setCustomer(customer);
		
		// retrieve the productId from the request
	    Integer productId = Integer.parseInt(request.getParameter("productId"));
	 // set the product_id on the comment
	    Product product = productService.getProductById(productId);
	    comment.setProduct(product);
	    
	    return service.saveComment(comment);
	}
	
	@PostMapping("/replies")
	public CommentReplies addReplies(@RequestBody CommentReplies replies, HttpServletRequest request) throws ProductNotFoundException {
		Customer customer = getAuthenticatedCustomer(request);
		replies.setCustomer(customer);
		Integer productId = Integer.parseInt(request.getParameter("productId"));
		Product product = productService.getProductById(productId);
		replies.setProduct(product);
		Integer commentId = Integer.parseInt(request.getParameter("commentId"));
		Comments comment = service.getCommentById(commentId);
		replies.setComment(comment);
		return service.saveCommentReplies(replies);
	}
	
	@GetMapping("/comments/delete/{id}")
	public ModelAndView deleteComment(@PathVariable("id") Integer commentId, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		service.delete(commentId, customer.getId());
	    String referer = request.getHeader("Referer");
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:" + referer);
	    return modelAndView;
	}
	
	@GetMapping("/replies/delete/{id}")
	public ModelAndView deleteReplies(@PathVariable("id") Integer commentId, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		service.deleteReplies(commentId, customer.getId());
		String referer = request.getHeader("Referer");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:" + referer);
		return modelAndView;
	}
}
