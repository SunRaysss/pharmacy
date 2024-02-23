package com.estore.drugstore.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.estore.drugstore.ApiResponse;
import com.estore.drugstore.model.*;
import com.estore.drugstore.dto.ProductDto;
import com.estore.drugstore.service.CategoryService;
import com.estore.drugstore.service.ProductService;

@RestController
@RequestMapping("api/product")
public class ProductController {
	@Autowired
	private final ProductService productService;
	@Autowired
	private final CategoryService categoryService;
	public ProductController(ProductService productServ, CategoryService categoryServ) {
		this.productService= productServ;
		this.categoryService= categoryServ;
	}
	
	@GetMapping("/hello")
	public String hello(@RequestParam(value= "name", defaultValue="world") String name) {
		return String.format("Hello %s!", name);
	}
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
		Optional<Category> category = categoryService.readCategory(productDto.getCategoryId());
		if(category.isPresent()){
			productService.createProduct(productDto, category.get());
			return new ResponseEntity<>(new ApiResponse(true, "product added successfully"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse(false, "category does not exist"), HttpStatus.CONFLICT);
	}
	
	@GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.readAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/update/{productID}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Integer productID,
                                                     @RequestBody ProductDto productDtoUpdate) {
    	
    	Optional<Category> category = categoryService.readCategory(productDtoUpdate.getCategoryId());
		if(category.isPresent()){
	    	ProductDto productDto = productService.readProduct(productID);
	    	if(Objects.nonNull(productDto)) {
	    		productService.updateProduct(productDtoUpdate, productID, category.get());
	    		return new ResponseEntity<>(new ApiResponse(true, "product updated successfully"), HttpStatus.OK);
	    	}
	    	else
	    		return new ResponseEntity<>(new ApiResponse(false, "product does not exist"), HttpStatus.NOT_FOUND);
		}
		else 
			return new ResponseEntity<>(new ApiResponse(false, "category does not exist"), HttpStatus.NOT_FOUND);
    }
}
