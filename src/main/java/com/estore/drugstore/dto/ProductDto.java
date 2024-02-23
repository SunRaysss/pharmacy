package com.estore.drugstore.dto;

import com.estore.drugstore.model.Product;

public class ProductDto {
	
	private Integer id;
    private String productName;
    private String description;
    private double price;
    private Integer categoryId;

    public ProductDto(String name, String description, double price, Integer categoryId) {
        this.productName = name;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    public ProductDto(Product product) {
        this.setId(product.getId());
        this.setProductName(product.getProductName());
        this.setDescription(product.getProductDescription());
        this.setPrice(product.getProductPrice());
        this.setCategoryId(product.getCategory().getId());
    }

    public ProductDto() {
    }

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
}
