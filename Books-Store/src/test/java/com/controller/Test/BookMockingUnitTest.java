package com.controller.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bookstore.constants.Category;
import com.bookstore.controller.BookController;
import com.bookstore.entity.Book;
import com.bookstore.service.BookServiceImpl;
import com.bookstore.service.dto.Bookdto;
import com.bookstore.service.dto.SellDto;

@SpringBootTest(classes = {BookMockingUnitTest.class})
public class BookMockingUnitTest {
	
	private final Long id = 2134L;
    private final String title = "title";
    private final String author = "author";
    private final float price = 25;
    private final Category category = Category.DRAMA;
    private final int totalCount = 12;
    private final int sold = 2;
    private final int addByNum = 1;
    private final String keyword = "aut";
	
    
	@Mock
    BookServiceImpl bookServiceImpl;
	
	@InjectMocks
	BookController bookController;
	
	List<Bookdto> listBookdto;
	Bookdto bookDto;
	Book book;
	List<Book> books;
	SellDto selldtos;
	List<SellDto> sellDto;
	
	
	@Test
	public void test_addNewBook() {
		//mock data
	     bookDto = new Bookdto();
	   	 bookDto.setId(id);
	   	 bookDto.setTitle(title);
	   	 bookDto.setAuthor(author);
	   	 bookDto.setCategory(category);
	   	 bookDto.setTotalCount(totalCount);
	   	 bookDto.setPrice(price);
         
	   	 when(bookServiceImpl.addNewBook(bookDto)).thenReturn(bookDto);
	   	ResponseEntity<Bookdto> respdto =bookController.addNewBook(bookDto);
	   	
	   	//Assert
	   	assertEquals(HttpStatus.CREATED, respdto.getStatusCode());
	   	assertEquals(bookDto, respdto.getBody());
	}

	
	
	@Test
	public void test_getAllBooks() {
		//mock data
	     bookDto = new Bookdto();
	   	 bookDto.setId(id);
	   	 bookDto.setTitle(title);
	   	 bookDto.setAuthor(author);
	   	 bookDto.setCategory(category);
	   	 bookDto.setTotalCount(totalCount);
	   	 bookDto.setPrice(price);

		
		listBookdto = new ArrayList<>();
		listBookdto.add(bookDto);
		
		when(bookServiceImpl.getAllBooks()).thenReturn(listBookdto);//Mocking
		ResponseEntity<List<Bookdto>> res =bookController.getAllBooks();
		
		//assert
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(1, res.getBody().size());
	}
	
	@Test 
	public void test_getBookById() {
		//mock data
	     bookDto = new Bookdto();
	   	 bookDto.setId(id);
	   	 bookDto.setTitle(title);
	   	 bookDto.setAuthor(author);
	   	 bookDto.setCategory(category);
	   	 bookDto.setTotalCount(totalCount);
	   	 bookDto.setPrice(price);
	   	 
	   	 Long booksId =2134L;
	   	 
	   	 when(bookServiceImpl.getBookById(booksId)).thenReturn(bookDto);
	    ResponseEntity<Bookdto> resp =bookController.getBookById(booksId);
	    
	    //assert
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertEquals(booksId, resp.getBody().getId());
		
	}
	
	@Test
	public void test_updateBook() {
		bookDto = new Bookdto();
	   	 bookDto.setId(id);
	   	 bookDto.setTitle("RRR");
	   	 bookDto.setAuthor("SSR");
	   	 bookDto.setCategory(category);
	   	 bookDto.setTotalCount(3);
	   	 bookDto.setPrice(55);
	   	 
	   	 Long booksId =2134L;
	   	 when(bookServiceImpl.updateBook(booksId, bookDto)).thenReturn(bookDto);
	   	 ResponseEntity<Bookdto> respupdate =bookController.updateBook(booksId, bookDto);
	   	 //assert
	   	 assertEquals(HttpStatus.OK, respupdate.getStatusCode());
	   	 assertEquals(booksId, respupdate.getBody().getId());
	   	 assertEquals("RRR", respupdate.getBody().getTitle());
	   	 
	}
	
	@Test
	public void test_sellBook() {
		selldtos = new SellDto();
		selldtos.setBookId(id);
		selldtos.setQuantity(6);
		String s1= "Single Book is sold successfully..!!!";
		when(bookServiceImpl.sellBook(id)).thenReturn(book);
		String str1 =bookController.sellBook(id);
		
		//assert
		assertEquals(s1, str1);
		
	}
	
	@Test
	public void test_sellBooks() {
		selldtos = new SellDto();
		selldtos.setBookId(id);
		selldtos.setQuantity(6);
		
		sellDto = new ArrayList<>();
		sellDto.add(selldtos);
		
		when(bookServiceImpl.sellBooks(sellDto)).thenReturn(books);
		ResponseEntity<List<Book>> listbooks=bookController.sellBooks(sellDto);
		
		//assert
		assertEquals(HttpStatus.OK, listbooks.getStatusCode());
		verify(bookServiceImpl, times(1)).sellBooks(sellDto);
		
	}
	
	@Test
	public void test_getBookByCategoryKeyWord() {
		//mock data
	     bookDto = new Bookdto();
	   	 bookDto.setId(id);
	   	 bookDto.setTitle(title);
	   	 bookDto.setAuthor(author);
	   	 bookDto.setCategory(category);
	   	 bookDto.setTotalCount(totalCount);
	   	 bookDto.setPrice(price);
	   	 
        listBookdto = new ArrayList<>();
		listBookdto.add(bookDto);
		
		when(bookServiceImpl.getBookByCategoryKeyWord(keyword, category)).thenReturn(listBookdto);
		ResponseEntity<List<Bookdto>> actlistbookdto =bookController.getBookByCategoryKeyWord(keyword, category);
		
		//assert
		assertEquals(HttpStatus.OK, actlistbookdto.getStatusCode());
	}
	
	@Test
	public void getNumberOfBooksSoldByCategoryAndKeyword() {
		//mock data
	     bookDto = new Bookdto();
	   	 bookDto.setId(id);
	   	 bookDto.setTitle(title);
	   	 bookDto.setAuthor(author);
	   	 bookDto.setCategory(category);
	   	 bookDto.setTotalCount(totalCount);
	   	 bookDto.setPrice(price);
	   	 
	   	 int solds =5;
	   	 when(bookServiceImpl.getNumberOfBooksSoldByCategoryAndKeyword(keyword, category)).thenReturn(solds);
	   	 ResponseEntity<String> resstr= bookController.getNumberOfBooksSoldByCategoryAndKeyword(keyword, category);
	   	 
	   	 //assert
	   	 assertEquals(HttpStatus.OK, resstr.getStatusCode());
		
	}
	
	@Test
	public void test_addBook()  {
		//mock data
	     bookDto = new Bookdto();
	   	 bookDto.setId(id);
	   	 bookDto.setTitle(title);
	   	 bookDto.setAuthor(author);
	   	 bookDto.setCategory(category);
	   	 bookDto.setTotalCount(totalCount);
	   	 bookDto.setPrice(price);
	   	 
	   	 
	     //Arrange

	        doNothing().when(bookServiceImpl).addbook(id, addByNum);
	       

	        ResponseEntity<String> respstr= bookController.addBook(id, addByNum);
	        
	        //assert
	        assertEquals(HttpStatus.OK, respstr.getStatusCode());
	   	  

	}
	

	@Test
	public void test_getNumberOfBooksById() {
		//mock data
	     bookDto = new Bookdto();
	   	 bookDto.setId(id);
	   	 bookDto.setTitle(title);
	   	 bookDto.setAuthor(author);
	   	 bookDto.setCategory(category);
	   	 bookDto.setTotalCount(totalCount);
	   	 bookDto.setPrice(price);
	   	 
	   	 //Arrange 
	   	 when(bookServiceImpl.getNumberOfBooksById(id)).thenReturn(totalCount);
	   	ResponseEntity<String> strbookid = bookController.getNumberOfBooksById(id);
	   	
	   	//assert
	   	assertEquals(HttpStatus.OK, strbookid.getStatusCode());
	}
	
	
}
