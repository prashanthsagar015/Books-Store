package com.bookstore.service.dto;

import org.antlr.v4.runtime.misc.NotNull;

import com.bookstore.constants.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Bookdto {
	
	
	private Long id;
	@NotEmpty(message="title should be not null")
	private String title;
    @NotEmpty(message="author should be not null")
	private String author;
	private Category category;
    @Min(value = 0, message = "Total Count should be positive value.")
	private int totalCount;
    @Min(value = 0, message = "price should be positive value.")
	private float price;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public static Bookdto builder() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
