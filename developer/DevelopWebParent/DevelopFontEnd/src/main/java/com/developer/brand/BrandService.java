package com.developer.brand;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.common.entity.Brand;

@Service
public class BrandService {
	@Autowired
	private BrandRepository repo;
	
	@Transactional
	public List<Brand> listAllBrands() {
		return repo.findAll();
	}
	
	@Transactional
	public Brand get(Integer id) {
		return repo.findById(id).get();
	}
}
