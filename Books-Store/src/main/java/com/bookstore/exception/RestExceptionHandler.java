package com.bookstore.exception;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestExceptionHandler {
	
	//For rest api also -we a created one restexceptionhandler and which returning the response entity
	//with user defined data and this data is retuening in the json format data
	
	@ExceptionHandler(ResourseNotFoundExceptions.class)
	public ResponseEntity<ApiError> handleResourceNotFoundException(ResourseNotFoundExceptions ex) {
		
		ApiError error = new ApiError(400,ex.getMessage(), new Date());
		return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);

}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiError> handleDuplicateResourceException(DuplicateResourceException ex) {
		
		ApiError error = new ApiError(400,ex.getMessage(), new Date());
		return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);

}
	

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ApiError> handleBookNotFoundException(BookNotFoundException ex) {
		
		ApiError error = new ApiError(400,ex.getMessage(), new Date());
		return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);

}
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException exception){
        Map<String,String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error  ->{
//            String fieldName= error.getField();
//            String message=error.getDefaultMessage();
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.BAD_REQUEST);
    }
 
    @ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex) {
		
		ApiError error = new ApiError(400,ex.getMessage(), new Date());
		return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);

}


}
