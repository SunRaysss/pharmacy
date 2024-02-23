package com.estore.drugstore.controller;

import java.util.List;
import java.util.Objects;

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
import com.estore.drugstore.model.Category;
import com.estore.drugstore.service.CategoryService;
import com.estore.drugstore.service.ProductService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private final CategoryService categoryService;
	
	@Autowired
	private final ProductService productService;
	
	public CategoryController(CategoryService categService, ProductService productServ) {
		this.categoryService=categService;
		this.productService=productServ;
	}
	//just for testing
	@GetMapping("/hello")
	public String hello(@RequestParam(value= "name", defaultValue="world") String name) {
		return String.format("Hello %s!", name);
	}
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createCategory(@RequestBody Category c){
		if(Objects.nonNull(categoryService.readCategory(c.getCategoryName()))) 
			return new ResponseEntity<>(new ApiResponse(false, "category already exists"), HttpStatus.CONFLICT);
		categoryService.createCategory(c);
		return new ResponseEntity<>(new ApiResponse(true, "category created"), HttpStatus.OK);
	}
	
	@GetMapping("/read/{categoryID}")
	public ResponseEntity<Category> readCategory(@PathVariable("categoryID") Integer id){
		Category c = categoryService.readCategory(id).get();
		if(Objects.nonNull(c)) {
			return new ResponseEntity<>(c, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<List<Category>> readAll(){
		List all = categoryService.readAll();
		return new ResponseEntity<>(all, HttpStatus.OK);
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable("id") Integer id, @RequestBody Category updateC){
		if(Objects.nonNull(categoryService.readCategory(id).get())) {
			categoryService.updateCategory(id, updateC);
			return new ResponseEntity<>(new ApiResponse(true, "category updated successfully"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse(false, "category does not exist"), HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") Integer id){
		if(Objects.nonNull(categoryService.readCategory(id).get())) {
			categoryService.deleteCategory(id);
			return new ResponseEntity<>(new ApiResponse(true, "category deleted successfully"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse(false, "category does not exist"), HttpStatus.NO_CONTENT);
	}
}
