package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.constants.Category;
import com.bookstore.entity.Book;
import com.bookstore.service.BookServiceImpl;
import com.bookstore.service.dto.Bookdto;
import com.bookstore.service.dto.SellDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class BookController {
	
	@Autowired
	private BookServiceImpl bookServiceImpl;
	
	
	//Add a new-book
	@PostMapping("/add-new-book")
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Bookdto> addNewBook(@Valid @RequestBody Bookdto bookdto) {
		Bookdto createBookDto=bookServiceImpl.addNewBook(bookdto);
		return new ResponseEntity<>(createBookDto,HttpStatus.CREATED);
		
	}
	
	//Add a book
	@PutMapping("/add-Book/{id}/{quantityToAdd}")
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> addBook(@PathVariable Long id,@PathVariable int quantityToAdd) {
		bookServiceImpl.addbook(id, quantityToAdd);
		return ResponseEntity.ok("Total no. of books added for this id-"+id+ " is : " +quantityToAdd);
		
	}
	
	//get books by id
	@GetMapping("/book/{id}")
	public ResponseEntity<Bookdto> getBookById(@PathVariable Long id) {
		Bookdto bookdto= bookServiceImpl.getBookById(id);
		 return new ResponseEntity<Bookdto>(bookdto, HttpStatus.OK);
		
	}
	
	//Get All Books
	@GetMapping("/book-list")
	public ResponseEntity<List<Bookdto>> getAllBooks(){
		List<Bookdto> listBookdto = bookServiceImpl.getAllBooks();
		return new ResponseEntity< List<Bookdto>>(listBookdto, HttpStatus.OK);
		}
	
	//Get number of books available by id.
	@GetMapping("/number-of-books/{id}")
	public ResponseEntity<String> getNumberOfBooksById(@PathVariable Long id) {
		int totalCount= bookServiceImpl.getNumberOfBooksById(id);
		return ResponseEntity.ok("Total Number of books = "+totalCount);
	}
	
	//Update a book.
	@PutMapping("/books/{id}")
	public ResponseEntity<Bookdto> updateBook(@PathVariable Long id,
	                           @Valid @RequestBody Bookdto bookDto) {
	    Bookdto bdto =bookServiceImpl.updateBook(id, bookDto);
	    return new ResponseEntity<Bookdto>(bdto, HttpStatus.OK);
	    }
	
	//Sell a single book
	@PutMapping("/sell-book/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String sellBook(@PathVariable Long id) {
	    bookServiceImpl.sellBook(id);
	    return "Single Book is sold successfully..!!!";
	    }
	

	 
	 //Sell list of books
	 @PutMapping("/sell-books")
	// @ResponseStatus(HttpStatus.OK)
	 public ResponseEntity<List<Book>> sellBooks(@Valid @RequestBody List<SellDto> sellDto) {
		 List<Book> books = bookServiceImpl.sellBooks(sellDto);
	     return new ResponseEntity<List<Book>>(books,HttpStatus.OK);
	    }
	 
	  //Get Books by category and keyword
	  @GetMapping("/books")
	  public ResponseEntity<List<Bookdto>> getBookByCategoryKeyWord(@RequestParam String keyword,
	                                                  @RequestParam Category category) {
	     List<Bookdto> list = bookServiceImpl.getBookByCategoryKeyWord(keyword, category);
	      return new ResponseEntity<List<Bookdto>>(list,HttpStatus.OK);
	    }
	   
	  
	  //Get Number of books sold per category and keyword
	   @GetMapping("/number-of-books")
	   public ResponseEntity<String> getNumberOfBooksSoldByCategoryAndKeyword(@RequestParam String keyword,
	                                                        @RequestParam Category category) {
	        int i= bookServiceImpl.getNumberOfBooksSoldByCategoryAndKeyword(keyword, category);
	        return ResponseEntity.ok("Total Number of Books Sold :"+i);
	        
	    }

}
