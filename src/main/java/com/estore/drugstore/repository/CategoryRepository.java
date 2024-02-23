package com.estore.drugstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estore.drugstore.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Category findByCategoryName(String categoryName);
}
