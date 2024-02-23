package com.estore.drugstore.dto;

import com.estore.drugstore.model.Cart;
import com.estore.drugstore.model.Product;

public class CartItemDto {
	
    private Integer id;
    private  Integer quantity;
    private  Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItemDto(Cart cart) {
        this.setId(cart.getCart_id());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }
}