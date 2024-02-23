package com.estore.drugstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estore.drugstore.model.Cart;
import com.estore.drugstore.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	List<Cart> findAllCartByUser(User user);
	List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
