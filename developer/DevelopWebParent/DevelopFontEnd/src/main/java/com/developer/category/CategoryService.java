package com.developer.category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.developer.common.entity.Category;
import com.developer.common.exception.CategoryNotFoundException;
import com.developer.common.exception.ProductNotFoundException;

@Service
public class CategoryService {
	
	@Autowired private CategoryRepository repo;
	
	public List<Category> rootCategories(){
		
		List<Category> rootCategories = repo.findRootCategories();
		
		return rootCategories;
	}
	
	public List<Category> listNoChildrenCategories(){
		List<Category> listNoChildrenCategories = new ArrayList<>();
		
		List<Category> listEnabledCategories = repo.findAllEnabled();
		
		listEnabledCategories.forEach(category -> {
			Set<Category> children = category.getChildren();
			if(children == null || children.size() == 0) {
				listNoChildrenCategories.add(category);
			}
		});
		
		return listNoChildrenCategories;
	}
	
	public Category getCategory (String alias) throws CategoryNotFoundException {
		Category category = repo.findByAliasEnabled(alias);
		if(category == null) {
			throw new CategoryNotFoundException("Không thể tìm thấy danh mục" + alias);
		}
		return category;
	}
	
	public List<Category> getCategoryParents(Category child){
		List<Category> listParents = new ArrayList<>();
		
		Category parent = child.getParent();
		
		while (parent != null) {
			listParents.add(0, parent);
			parent = parent.getParent();
			
		}
		
		listParents.add(child);
		return listParents;
	}

//	03/04/2023 Sort Product
	public List<Category> listAllCat() {
		List<Category> listAll = repo.findAllEnabled();
		return listAll;
	}
	
}
