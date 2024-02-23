package com.estore.drugstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
	public Category() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "description")
	private String description;
	
	public Category(String categorName, String description) {
	   this.categoryName = categorName;
	   this.description = description;
	}
	public Integer getId() {
	    return id;
	  }
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", description=" + description + "]";
	}
	

}

