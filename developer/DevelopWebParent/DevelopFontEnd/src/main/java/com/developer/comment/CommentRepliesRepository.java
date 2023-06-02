package com.developer.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.developer.comment.entity.CommentReplies;
import com.developer.comment.entity.Comments;

public interface CommentRepliesRepository extends JpaRepository<CommentReplies, Integer>{

	List<CommentReplies> findByComment(Comments comment);

	@Query("SELECT c FROM CommentReplies c WHERE c.product.id = ?1 ORDER BY c.createdTime DESC")
	List<CommentReplies> findByProductIdOrderByCreatedTimeDesc(Integer productId);
	
	@Query("SELECT c FROM CommentReplies c WHERE c.comment.id = ?1")
	List<CommentReplies> findByCommentId(Integer commentId);

	@Query("DELETE FROM CommentReplies c WHERE c.comment.id = ?1")
	@Modifying
	void deleteByCommentId(Integer commentId);

	@Query("DELETE FROM CommentReplies c WHERE c.id = ?1 AND c.customer.id = ?2")
	@Modifying
	void deleteByIdAndCustomer(Integer repliesId, Integer customerId);

}
