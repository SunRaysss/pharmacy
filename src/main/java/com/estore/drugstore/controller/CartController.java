package com.estore.drugstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.drugstore.ApiResponse;
import com.estore.drugstore.dto.AddToCartDto;
import com.estore.drugstore.dto.CartDto;
import com.estore.drugstore.exceptions.AuthenticationFailException;
import com.estore.drugstore.exceptions.CartItemNotExistException;
import com.estore.drugstore.exceptions.ProductNotExistException;
import com.estore.drugstore.model.Cart;
import com.estore.drugstore.model.Product;
import com.estore.drugstore.model.User;
import com.estore.drugstore.service.AuthenticationService;
import com.estore.drugstore.service.CartService;
import com.estore.drugstore.service.CategoryService;
import com.estore.drugstore.service.ProductService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
    CategoryService categoryService;
    
    @Autowired
    ProductService productService;
    
    @Autowired
    AuthenticationService authenticationService;
	public CartController(CartService cartServ) {
		this.cartService=cartServ;
	}
	
	@PostMapping("/addToCart")
	public ResponseEntity<ApiResponse> addProdcutToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token)
			throws  AuthenticationFailException, ProductNotExistException {
		
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Product product = productService.getProductById(addToCartDto.getProductId());
        cartService.addToCart(addToCartDto, product, user);
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }
	@GetMapping("/")
	public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException{
		authenticationService.authenticate(token);
		User user = authenticationService.getUser(token);
		CartDto cartDto = cartService.listCartItems(user);
		return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
	}
	
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int cartItemId,
                                                      @RequestParam("token") String token)
            throws AuthenticationFailException, CartItemNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        cartService.deleteCartItem(cartItemId, user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }
}

