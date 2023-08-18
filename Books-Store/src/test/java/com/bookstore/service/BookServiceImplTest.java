package com.bookstore.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookstore.entity.Book;
import com.bookstore.exception.BadRequestException;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.DuplicateResourceException;
import com.bookstore.exception.ResourseNotFoundExceptions;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.dto.Bookdto;
import com.bookstore.service.dto.SellDto;
import com.bookstore.constants.Category;

@SpringBootTest(classes= {BookServiceImplTest.class})
public class BookServiceImplTest {
	
	 private final Long id = 1234L;
	 private final int totalCount = 2;
	 private final int sold = 2;
	 private final Category category = Category.ACTION;
	private final String keyword = "keyword";
	 
	@Rule
	public ExpectedException thrown = ExpectedException.none();              
	
	@Mock
	private BookRepository repo;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private BookServiceImpl bookServiceImpl;
	
	
	@Test
	public void testAddNewBook() {
		Bookdto bookdto =mock(Bookdto.class);
		Book book = mock(Book.class);
		when(bookdto.getId()).thenReturn(id);
		when(repo.findById(id)).thenReturn(Optional.empty());
		when(modelMapper.map(bookdto, Book.class)).thenReturn(book);
		when(modelMapper.map(book, Bookdto.class)).thenReturn(bookdto);
		
		//act
		
		bookServiceImpl.addNewBook(bookdto);
		
		//verify
		
		verify(repo).save(book);
		verify(repo, times(1)).save(book);
		
   }
	
	@Test
	public void testAddNewBook_Given_IdIsPresent_Then_ThrowsDuplicateResourceException() {
        
		Bookdto bookbto = mock(Bookdto.class);
		Book book = mock(Book.class);
		when(bookbto.getId()).thenReturn(id);
		when(repo.findById(id)).thenReturn(Optional.ofNullable(book));
		
	   assertThrows(DuplicateResourceException.class, () -> bookServiceImpl.addNewBook(bookbto));
	
	}
	
	@Test
	public void testAddBook() {
		Book book = mock(Book.class);
		when(repo.findById(id)).thenReturn(Optional.ofNullable(book));
		when(book.getTotalCount()).thenReturn(totalCount);
		
		//act
		bookServiceImpl.addbook(id, 2);
		
		//verify
		verify(repo).save(book);
		verify(repo, times(1)).save(book);
	}
	
	@Test
	public void testAddBook_Given_NoBookIsFoundById_Then_ThrowsBookNotFoundException() {
		
		//Arrange
		when(repo.findById(id)).thenReturn(Optional.empty());
		
		//Act
		
		assertThrows(BookNotFoundException.class, () -> bookServiceImpl.addbook(id,totalCount ));
		
}
	
	@Test
	public void testGetBookById() {
		//Arrange
		Book book =mock(Book.class);
		Bookdto bookdto =mock(Bookdto.class);
		when(repo.findById(id)).thenReturn(Optional.ofNullable(book));
		when(modelMapper.map(book, Bookdto.class)).thenReturn(bookdto);
		
		//Act
		Bookdto actualBookdto =bookServiceImpl.getBookById(id);
		
		//assert
		assertEquals(bookdto, actualBookdto);
		 
	 }
	
	@Test
	public void testGetBookById_Given_NoBookIsFoundForId_Then_ThrowsBookNotFoundException() {
        //Arrange
        when(repo.findById(id)).thenReturn(Optional.empty());

        //Act
        assertThrows(ResourseNotFoundExceptions.class, () -> bookServiceImpl.getBookById(id));
		
	}
	
	@Test
	public void testGetAllBooks() {
		//Arrange 
		Book book =mock(Book.class);
		List<Book> listBook = new ArrayList<>();
		listBook.add(book);
		
		Bookdto bookdto =mock(Bookdto.class);
		List<Bookdto> listBookdto = new ArrayList<>();
		listBookdto.add(bookdto);
		
		when(repo.findAll()).thenReturn(listBook);
		when(modelMapper.map(book, Bookdto.class)).thenReturn(bookdto);
		
		//Act
		List<Bookdto> actuallistBookdto =bookServiceImpl.getAllBooks();
		
		//Assert
		assertEquals(listBookdto, actuallistBookdto);
		
		
		 
	 }
	
	@Test
	public void testGetNumberOfBooksById() {
		//Arrange
		Book book =mock(Book.class);
		when(repo.findById(id)).thenReturn(Optional.ofNullable(book));
		when(book.getTotalCount()).thenReturn(totalCount);
		
		//act
		int actualNumberOfBooks = bookServiceImpl.getNumberOfBooksById(id);
		
		//Asssert
		assertEquals(totalCount, actualNumberOfBooks);
		
	}
	 
	@Test
	public void testGetNumberOfBooksById_Given_NoBookIsPresent() {
		
		//Arrange
		Book book =mock(Book.class);
		when(repo.findById(id)).thenReturn(Optional.empty());
		
		//act
		int actualNumberOfBooks = bookServiceImpl.getNumberOfBooksById(id);
				
		//Asssert
	    assertEquals(0, actualNumberOfBooks);
		 
	 }
	
	 @Test
	 public void testUpdateBook() {
	   //Arrange
		 Book book =mock(Book.class);
		 Bookdto bookdto =mock(Bookdto.class);
		 when(modelMapper.map(bookdto, Book.class)).thenReturn(book);
		 when(bookdto.getId()).thenReturn(id);
		 Book bookFromrepo = mock(Book.class);
		 when(repo.getReferenceById(id)).thenReturn(bookFromrepo);
		 when(bookFromrepo.getSold()).thenReturn(sold);
		 when(modelMapper.map(book, Bookdto.class)).thenReturn(bookdto);
		 
		 //act
		 bookServiceImpl.updateBook(id, bookdto);
		 
		 //verify
		 verify(repo).save(book);
		 
	 }
	 
	 @Test
	 public void testUpdateBook_Given_IdIsChange_Then_ThrowsBadRequestException() {
	   //Arrange
        Book book =mock(Book.class);
	    Bookdto bookdto =mock(Bookdto.class);
	    when(modelMapper.map(bookdto, Book.class)).thenReturn(book);
	    when(bookdto.getId()).thenReturn(33L);
	    
	    //assert
	    assertThrows(BadRequestException.class, () ->  bookServiceImpl.updateBook(id, bookdto));
	    
	 }
	 
	 @Test
	 public void testSellBook() {
		 //Arrange 
		  Book book =mock(Book.class);
		  when(repo.findById(id)).thenReturn(Optional.ofNullable(book));
		  when(book.getTotalCount()).thenReturn(totalCount);
		  when(book.getSold()).thenReturn(sold);
		  
		  //Act
		  bookServiceImpl.sellBook(id);
		  
		  //verify
		  verify(repo).save(book);
		  
	 }
	 
	 @Test
	 public void testSellBook_Given_NoBookIsPresent_Then_ThrowsBookNotFoundException() {
		 //Arrange
		  when(repo.findById(id)).thenReturn(Optional.empty());
		  
		  //assert
		  assertThrows(BookNotFoundException.class, () ->  bookServiceImpl.sellBook(id));
		 
	 }
	 
	 
	 @Test
	 public void testSellBook_Given_NotSufficientBook_Then_ThrowsBadRequestException() {
		 //Arrange
		 Book book =mock(Book.class);
		 when(repo.findById(id)).thenReturn(Optional.ofNullable(book));
		 when(book.getTotalCount()).thenReturn(0);
		 
		 //Assert
		 assertThrows(BadRequestException.class, () -> bookServiceImpl.sellBook(id) );
		 
	 }
	 
	 @Test
	 public void testSellBooks() {
		 long bookId1= 10L;
		 long bookId2= 20L;
		 int totalCount1=15;
		 int totalCount2=40;
		 int quantity1=10;
		 int quantity2=20;
		 int sold1=5;
		 int sold2=7;
		 
		 Book book1=mock(Book.class);
		 Book book2=mock(Book.class);
		 SellDto sellDto1=mock(SellDto.class);
		 SellDto sellDto2=mock(SellDto.class);
		 List<SellDto> sellDtos = new ArrayList<>();
		 sellDtos.add(sellDto1);
		 sellDtos.add(sellDto2);
		 
		 when(sellDto1.getBookId()).thenReturn(bookId1);
		 when(sellDto2.getBookId()).thenReturn(bookId2);
		 when(repo.findById(bookId1)).thenReturn(Optional.ofNullable(book1));
		 when(repo.findById(bookId2)).thenReturn(Optional.ofNullable(book2));
		 when(book1.getTotalCount()).thenReturn(totalCount1);
		 when(book2.getTotalCount()).thenReturn(totalCount2);
		 when(sellDto1.getQuantity()).thenReturn(quantity1);
		 when(sellDto2.getQuantity()).thenReturn(quantity2);
		 when(book1.getSold()).thenReturn(sold1);
		 when(book1.getSold()).thenReturn(sold2);
		 
		 //act
		 bookServiceImpl.sellBooks(sellDtos);
		 
		//Verify
	     verify(repo, times(2)).save(ArgumentMatchers.any(Book.class));
		 
     
	 }
	 
	 @Test
	 public void testSellBooks_Given_BookIsNotPresent_Then_ThrowsBookNotFoundException() {
		//Arrange
		SellDto sellDto = mock(SellDto.class);
		List<SellDto> sellDtos = new ArrayList<>();
		sellDtos.add(sellDto);
		when(sellDto.getBookId()).thenReturn(id);
		when(repo.findById(id)).thenReturn(Optional.empty());
		
		//Assert
		assertThrows(BookNotFoundException.class, () -> bookServiceImpl.sellBooks(sellDtos));
		 
		 
	 }
	 
	 @Test
	 public void testSellBooks_Given_NotSufficientBook_Then_ThrowsBadRequestException() {
			//Arrange
		    Book book = mock(Book.class);
			SellDto sellDto = mock(SellDto.class);
			List<SellDto> sellDtos = new ArrayList<>();
			sellDtos.add(sellDto);
			when(sellDto.getBookId()).thenReturn(id);
			when(repo.findById(id)).thenReturn(Optional.ofNullable(book));
			
			when(book.getTotalCount()).thenReturn(totalCount);
			when(sellDto.getQuantity()).thenReturn(3);
			
			//Assert
			assertThrows(BadRequestException.class, () -> bookServiceImpl.sellBooks(sellDtos));
			
  }
	 
	 @Test
	 public void testGetBookByCategoryKeyword() {
		//Arrange 
		Book book= mock(Book.class);
		List<Book> books = new ArrayList<>();
		books.add(book);
		
		Bookdto bookdto= mock(Bookdto.class);
		List<Bookdto> bookdtos = new ArrayList<>();
		bookdtos.add(bookdto);
		
		when(repo.findAllBookByCategoryAndKeyword(keyword.toLowerCase(), category.getValue())).thenReturn(books);
		when(modelMapper.map(book, Bookdto.class)).thenReturn(bookdto);
		
		//Act
		List<Bookdto> actualBookdtos =bookServiceImpl.getBookByCategoryKeyWord(keyword, category);
		
		//assert
		assertEquals(bookdtos, actualBookdtos);
		                                         
   }
	 
	 @Test
	 public void testGetNumberOfBooksSoldByCategoryAndKeyword() {
		 //Arrange
	     Long count = Long.valueOf(totalCount);
	     when(repo.countNumberOfBooksSold(keyword.toLowerCase(), category.getValue())).thenReturn(count);
		 
		 //act
		 int actualCount=bookServiceImpl.getNumberOfBooksSoldByCategoryAndKeyword(keyword, category);
		 
		 //Assert
		 assertEquals(totalCount, actualCount);
	 }
	
	
	
	
	
	
	
	

}
