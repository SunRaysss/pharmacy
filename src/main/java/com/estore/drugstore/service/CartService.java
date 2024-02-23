package com.estore.drugstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.drugstore.dto.AddToCartDto;
import com.estore.drugstore.dto.CartDto;
import com.estore.drugstore.dto.CartItemDto;
import com.estore.drugstore.exceptions.CartItemNotExistException;
import com.estore.drugstore.model.Cart;
import com.estore.drugstore.model.Product;
import com.estore.drugstore.model.User;
import com.estore.drugstore.repository.CartRepository;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository; 
	public CartService (CartRepository cartRepo){
		this.cartRepository=cartRepo;
	}
	
	public void addToCart(AddToCartDto addToCartDto, Product product, User user) {
		Cart cart = new Cart(user, product, addToCartDto.getQuantity());
        cartRepository.save(cart);
	}
	
    public CartDto listCartItems(User user) {
        // first get all the cart items for user
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        // convert cart to cartItemDto
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = new CartItemDto(cart);
            cartItems.add(cartItemDto);
        }

        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += cartItemDto.getProduct().getProductPrice() * cartItemDto.getQuantity();
        }
        return new CartDto(cartItems,totalCost);
    }
    public void deleteCartItem(Integer cartItemId, User user) throws CartItemNotExistException{

        Optional<Cart> OptionalCart = cartRepository.findById(cartItemId);
        if (!OptionalCart.isPresent()) {
            throw new CartItemNotExistException("cartItemId not valid");
        }

        //check if the cartItem belongs to the user else throw CartItemNotExistException exception
        Cart cart = OptionalCart.get();
        if (cart.getUser() != user) {
            throw new CartItemNotExistException("cart item does not belong to user");
        }
        cartRepository.deleteById(cartItemId);
    }
}
