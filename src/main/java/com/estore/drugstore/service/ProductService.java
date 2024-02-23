package com.estore.drugstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.estore.drugstore.dto.ProductDto;
import com.estore.drugstore.exceptions.ProductNotExistException;
import com.estore.drugstore.model.Category;
import com.estore.drugstore.model.Product;
import com.estore.drugstore.repository.ProductRepository;

@Service
public class ProductService {
	private ProductRepository productRepository;
	public ProductService(ProductRepository productRepo) {
		this.productRepository=productRepo;
	}
	
	public void createProduct(ProductDto productDto, Category category) {
		Product product = productFromDto(productDto, category);
		productRepository.save(product);
	}
	public Product productFromDto(ProductDto productDto, Category category) {
		Product p = new Product();
		p.setCategory(category);
		p.setProductName(productDto.getProductName());
		p.setProductDescription(productDto.getDescription());
		p.setProductPrice(productDto.getPrice());
		return p;
	}
	public List<ProductDto> readAllProducts(){
		List<Product> products= productRepository.findAll();
		List<ProductDto> productsDto = new ArrayList<ProductDto>();
		for(Product p : products) 
			productsDto.add(DtoFromProduct(p));
		return productsDto;
	}
	public ProductDto DtoFromProduct(Product p) {
		return new ProductDto(p);
	}
	public ProductDto readProduct(Integer productId){
		Product p =productRepository.findById(productId).get();
		return DtoFromProduct(p);
	}
	public void updateProduct(ProductDto productDto, Integer productID, Category category) {
		Product p = productFromDto(productDto, category);
		p.setId(productID);
		productRepository.save(p);
	}
	public Product getProductById(Integer id)throws ProductNotExistException{
		Optional<Product> product = productRepository.findById(id);
		if(!product.isPresent()) 
			throw new ProductNotExistException("Product id is invalid " + id);
		return product.get();
	}
}
