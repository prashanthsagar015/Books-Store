package com.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.constants.Category;
import com.bookstore.entity.Book;
import com.bookstore.exception.BadRequestException;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.DuplicateResourceException;
import com.bookstore.exception.ResourseNotFoundExceptions;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.dto.Bookdto;
import com.bookstore.service.dto.SellDto;


@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Override
	public Bookdto addNewBook(Bookdto bookdto) {
		
		Optional<Book> bookbyid=bookRepository.findById(bookdto.getId());

		if(bookbyid.isPresent()) {
			throw new DuplicateResourceException("Book with same id is already present:"+bookdto.getId());
		}
		
		    Book book = modelmapper.map(bookdto, Book.class);
			Book savedBook = bookRepository.save(book);
			Bookdto bookDto = modelmapper.map(savedBook, Bookdto.class);
			return bookDto;
	
	}


	@Override
	public void addbook(Long id, int quantityToAdd) {
		Book book=bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book with id:" +id+ " is not registered. Use addNewBook to register."+id)); 

				
		 int totalCountAfterAdd = book.getTotalCount() + quantityToAdd;
		 
		 book.setTotalCount(totalCountAfterAdd);
		 bookRepository.save(book);
		 
	}
	

	@Override
	public Bookdto getBookById(Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourseNotFoundExceptions("Book", "Id" ,id));
		
		return modelmapper.map(book, Bookdto.class);
	}

	@Override
	public List<Bookdto> getAllBooks() {
		List<Book> books =bookRepository.findAll();
		return mapBookListToBookDto(books);
	}
    
	  //Convert List of books to List of bookDto
	private List<Bookdto> mapBookListToBookDto(List<Book> books) {
		return books.stream()
				.map(book -> modelmapper.map(book, Bookdto.class))
				.collect(Collectors.toList());
	}
      
	 //Convert bookDto to book
	public Book  dtoToBook(Bookdto bookdto) {
	    Book book = this.modelmapper.map(bookdto, Book.class);
	    return book;
	
	}
	
	//Convert book to bookdto
	public Bookdto BookToDto(Book book) {
		Bookdto bookdto = this.modelmapper.map(book, Bookdto.class);
		return bookdto;
	}


	@Override
	public int getNumberOfBooksById(Long id) {
	     Optional<Book> book = bookRepository.findById(id);
	     if(book.isPresent()) {
	    	  return book.get().getTotalCount();
	     }
	     else
	     {
	      return 0;
	     }
}

	@Override
	public Bookdto updateBook(Long id, Bookdto bookDto) {

	 Book book = modelmapper.map(bookDto, Book.class);
     if (bookDto.getId() != null) {
         if (!bookDto.getId().equals(id)) {
        	
             throw  new BadRequestException("Book with id:" +id+ " is not registered");
         }
     }
     //Set sold
     book.setSold(bookRepository.getReferenceById(id).getSold());
     
     book.setId(id);
    Book bk = bookRepository.save(book);
    Bookdto bdto= modelmapper.map(bk ,Bookdto.class);
    return bdto;
		}



	@Override
	public Book sellBook(Long id) {
	 Book book = bookRepository.findById(id)
             .orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " is not found."));
     //Selling one book decreases the amount of book in the store and increases the amount of book sold.
     int totalCount = book.getTotalCount() - 1;
     if (totalCount < 0) {
         throw new BadRequestException("TotalCount cannot be negative. Not enough book in store to sell.");
     }
     int sold = book.getSold() + 1;
     book.setTotalCount(totalCount);
     book.setSold(sold);
    Book books = bookRepository.save(book);
    return books;
}


	@Override
	public List<Book> sellBooks(List<SellDto> sellDtos) {
	List<Book> soldBooks = new ArrayList<>(); 
	
	 for (SellDto sellDto : sellDtos) {
		  Book book = bookRepository.findById(sellDto.getBookId())
               .orElseThrow(() -> new BookNotFoundException("Book with id: " + sellDto.getBookId() + " is not found."));
              
		  int totalCount = book.getTotalCount() - sellDto.getQuantity();
	        if (totalCount < 0) {
	            throw new BadRequestException("TotalCount cannot be negative. Not enough book in store to sell.");
	        } 
	        
	        int sold = book.getSold() + sellDto.getQuantity();
         book.setTotalCount(totalCount);
         book.setSold(sold);
        bookRepository.save(book);  
        soldBooks.add(book);
	  } 
	 
	 return soldBooks;
	
}


	@Override
	public List<Bookdto> getBookByCategoryKeyWord(String keyword, Category category) {

    //if the status is Available, gives list of books which are available
    List<Book> book = bookRepository.findAllBookByCategoryAndKeyword(keyword.toLowerCase(), category.getValue());
    return mapBookListToBookDto(book);
}


	@Override
	public int getNumberOfBooksSoldByCategoryAndKeyword(String keyword, Category category) {
	
	return (int) bookRepository.countNumberOfBooksSold(keyword.toLowerCase(), category.getValue());
}


}
