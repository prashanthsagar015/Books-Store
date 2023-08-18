package com.bookstore.service;

import java.util.List;

import com.bookstore.constants.Category;
import com.bookstore.entity.Book;
import com.bookstore.service.dto.Bookdto;
import com.bookstore.service.dto.SellDto;

public interface BookService {
	
	Bookdto addNewBook(Bookdto bookdto);
	
	void addbook(Long id, int quantityToAdd);
	
	Bookdto getBookById(Long id);
	
	List<Bookdto> getAllBooks();
	
	int getNumberOfBooksById(Long id);
	
	Bookdto updateBook(Long id, Bookdto bookDto);
	
	Book sellBook(Long id);
	
	 List<Book> sellBooks(List<SellDto> sellDtos);
	 
	List<Bookdto> getBookByCategoryKeyWord(String keyword, Category category);
	
	int getNumberOfBooksSoldByCategoryAndKeyword(String keyword, Category category);
	
    
}
