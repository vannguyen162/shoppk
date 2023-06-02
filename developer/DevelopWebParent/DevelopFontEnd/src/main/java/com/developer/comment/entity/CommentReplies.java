package com.developer.comment.entity;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.developer.comment.CommentRepliessSerializer;
import com.developer.common.entity.Customer;
import com.developer.common.entity.Product;
import com.developer.common.entity.TimeWorkBasedEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = CommentRepliessSerializer.class)
@Entity
@Table(name = "comment_replies")
public class CommentReplies extends TimeWorkBasedEntity {

	@Column(name = "content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "comment_id")
	private Comments comment;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Comments getComment() {
		return comment;
	}

	public void setComment(Comments comment) {
		this.comment = comment;
	}

	@Transient
	public String getCreatedTimeFormat() {
		if(createdTime != null) {
			Instant now = Instant.now();
		    Instant activityInstant = createdTime.toInstant();
		    Duration duration = Duration.between(activityInstant, now);
		    long seconds = duration.getSeconds();
		    if (seconds < 60) {
		        return "cách đây: " + seconds + " giây trước";
		    } else if (seconds < 3600) {
		        long minutes = seconds / 60;
		        return "cách đây: " + minutes + " phút trước";
		    } else if (seconds < 86400) {
		        long hours = seconds / 3600;
		        return "cách đây: " + hours + " giờ trước";
		    } else {
		    	SimpleDateFormat formatter = new SimpleDateFormat("HH:ss | dd/MM/yyyy");  
				String time = formatter.format(createdTime);
				return time;
		    }
		}
		return "";
	}

}
