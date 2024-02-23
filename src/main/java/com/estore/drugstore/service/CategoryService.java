package com.estore.drugstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.estore.drugstore.model.Category;
import com.estore.drugstore.repository.CategoryRepository;

@Service
public class CategoryService {
	private CategoryRepository categoryRepository;
	public CategoryService(CategoryRepository categRepo) {
		this.categoryRepository=categRepo;
	}
	
	public Optional<Category> readCategory(Integer id) {
		return categoryRepository.findById(id);
	}
	public List<Category> readAll(){
		return categoryRepository.findAll();
	}
	public Category readCategory(String name) {
		return categoryRepository.findByCategoryName(name);
	}
	public void createCategory(Category c) {
		categoryRepository.save(c);
	}
	public void updateCategory(Integer id, Category updateC) {
		Category c= readCategory(id).get();
		c.setCategoryName(updateC.getCategoryName());
		c.setDescription(updateC.getDescription());
		categoryRepository.save(c);
	}
	public void deleteCategory(Integer id) {
		categoryRepository.deleteById(id);
	}
}
