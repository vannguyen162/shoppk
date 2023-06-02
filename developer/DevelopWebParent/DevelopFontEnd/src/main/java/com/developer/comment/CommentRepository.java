package com.developer.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.developer.comment.entity.Comments;
import com.developer.common.entity.Customer;

public interface CommentRepository extends JpaRepository<Comments, Integer>{

	@Query("SELECT c FROM Comments c WHERE c.product.id = ?1 ORDER BY c.createdTime DESC")
	List<Comments> findByProductIdOrderByCreatedTimeDesc(Integer productId);

	@Query("DELETE FROM Comments c WHERE c.id = ?1 AND c.customer.id = ?2")
	@Modifying
	void deleteByIdAndCustomer(Integer commentId, Integer customerId);

	@Query("SELECT c FROM Comments c WHERE c.customer = ?1")
	List<Comments> findByCustomer(Customer customer);
}
