package com.estore.drugstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estore.drugstore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	public void findByProductName(String productName);
}
