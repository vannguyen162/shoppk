package com.developer.comment;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.comment.entity.CommentReplies;
import com.developer.comment.entity.Comments;
import com.developer.common.entity.Customer;

@Service
@Transactional
public class CommentService {
	@Autowired private CommentRepository commentRepo;
	@Autowired private CommentRepliesRepository repliesRepo;
	public Comments saveComment(Comments comment) {
		comment.setCreatedTime(new Date());
		return commentRepo.save(comment);
	}
	public CommentReplies saveCommentReplies(CommentReplies replies) {
		replies.setCreatedTime(new Date());
		return repliesRepo.save(replies);
	}
	public List<Comments> getCommentsByProductId(Integer productId) {
	    return commentRepo.findByProductIdOrderByCreatedTimeDesc(productId);
	}
	public List<Comments> getCustomer(Customer customer) {
		return commentRepo.findByCustomer(customer);
	}
	public Comments getCommentById(Integer comentId) {
		return commentRepo.findById(comentId).get();
	}
	public List<CommentReplies> getReliesByProductId(Integer productId) {
		return repliesRepo.findByProductIdOrderByCreatedTimeDesc(productId);
	}
	public void delete(Integer commentId, Integer customerId) {
		List<CommentReplies> replies = repliesRepo.findByCommentId(commentId);
		if(!replies.isEmpty()) {
			repliesRepo.deleteByCommentId(commentId);
		}
		commentRepo.deleteByIdAndCustomer(commentId, customerId);
		
	}
	public void deleteReplies(Integer repliesId, Integer customerId) {
		repliesRepo.deleteByIdAndCustomer(repliesId, customerId);
		
	}
}
