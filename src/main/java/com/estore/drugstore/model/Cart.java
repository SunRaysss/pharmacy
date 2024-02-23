package com.estore.drugstore.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="cart")
public class Cart {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer cart_id;
	
	@Column(name="createdDate")
	private Date createdDate;
	
	@ManyToOne(targetEntity=User.class, fetch= FetchType.EAGER)
	@JoinColumn(name="user_id", nullable = false)
	private User user; 
	
	@ManyToOne(targetEntity=Product.class, fetch= FetchType.EAGER )
	@JoinColumn(name="product_id", nullable = false)
	private Product product;
	
	private Integer quantity;
	
	public Cart() {}

	public Cart(User user, Product product, Integer quantity) {
		super();
		this.user = user;
		this.product = product;
		this.quantity = quantity;
	}

	public Integer getCart_id() {
		return cart_id;
	}

	public void setCart_id(Integer cart_id) {
		this.cart_id = cart_id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
