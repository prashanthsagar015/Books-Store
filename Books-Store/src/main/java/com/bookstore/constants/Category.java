package com.bookstore.constants;

public enum Category {
	
	    ACTION(0),
	    THRILLER(1),
	    TECHNOLOGY(2),
	    DRAMA(3),
	    POETRY(4),
	    MEDIA(5),
	    OTHERS(6);
	
	 private int value;
	  
	 private Category(int value) {
	     this.value = value;
	    }

	 public int getValue() {
	        return value;
	    }

}
