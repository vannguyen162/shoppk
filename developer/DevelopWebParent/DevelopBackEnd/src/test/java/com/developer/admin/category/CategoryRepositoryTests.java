package com.developer.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.developer.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
	
	@Autowired
	private CategoryRepository repo;
	
	@Test
	public void testCategoryRootCategory() {
		Category category = new Category("Samsung");
		Category saveCategory = repo.save(category);
		
		assertThat(saveCategory.getId()).isGreaterThan(0);
	}
	@Test
	public void testCategorySubCategory() {
		Category parent = new Category(7);
		Category ddr3 = new Category("Logitech Mouse", parent);
		
		
		Category saveCategory = repo.save(ddr3);
		assertThat(saveCategory.getId()).isGreaterThan(0);
		
//		Category saveCategory = repo.save(subCategory);
		
//		assertThat(saveCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testGetCategory() {
		Category category = repo.findById(2).get();
		System.out.println(category.getName());
		
		Set<Category> children = category.getChildren();
		for(Category subCategory : children) {
			System.out.println(subCategory.getName());
		}
		assertThat(children.size()).isGreaterThan(0);
	}
	@Test
	public void testPrintHierarchicalCategory() {
		Iterable<Category> categories = repo.findAll();
		
		for(Category category : categories) {
			if(category.getParent() == null) {
				System.out.println(category.getName());
				
				Set<Category> children = category.getChildren();
				
				for(Category subCategory : children) {
					System.out.println("--" + subCategory.getName());
					printChildren(subCategory, 1);
				}
			}
		}
	}
	
	private void printChildren(Category parent, int subLevel) {
		int newSubLevel = subLevel + 1; 
		Set<Category> children = parent.getChildren();
		
		for(Category subCategory : children) {
			for(int i = 0; i < newSubLevel; i++) {
				System.out.print("--"); 
			}
			System.out.println(subCategory.getName());
			printChildren(subCategory, newSubLevel);
		}
	}
	
	@Test
	public void testListRootCategories() {
		List<Category> rootCategories = repo.findRootCategories(Sort.by("name").ascending());
		rootCategories.forEach(cat -> System.out.println(cat.getName()));
	}
	
	@Test
	public void testFindByName() {
		String name = "Computers";
		Category category = repo.findByName(name);
		
		assertThat(category).isNotNull();
		assertThat(category.getName()).isEqualTo(name);
	}
	@Test
	public void testFindByAlias() {
		String alias = "DeskTop1";
		Category category = repo.findByAlias(alias);
		
		assertThat(category).isNotNull();
		assertThat(category.getAlias()).isEqualTo(alias);
	}

}
