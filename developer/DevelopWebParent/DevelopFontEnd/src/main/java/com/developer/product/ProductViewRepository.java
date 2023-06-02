package com.developer.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.common.entity.Product;

public interface ProductViewRepository extends JpaRepository<Product,Long>{

}
