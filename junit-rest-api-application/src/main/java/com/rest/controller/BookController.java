package com.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.dao.BookRepository;
import com.rest.entity.Book;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@GetMapping
	public List<Book> getallBooks(){
		return bookRepository.findAll();	
	}
	
	@GetMapping(value = "{bookId}")
	public Book getBookById(@PathVariable("bookId") Long bookid){
		return bookRepository.findById(bookid).get();	
	}
	
	@PostMapping
	public Book createBooksRecord(@RequestBody Book bookRecord) {
		return bookRepository.save(bookRecord);
	}
	
	@PutMapping("/updateData")
	public Book updateBookRecord(@RequestBody Book bookRecord) throws NotFoundException {
		if (bookRecord == null || bookRecord.getBookId() == null) {
			throw new NotFoundException();
		}
		Optional<Book> optionalBook = bookRepository.findById(bookRecord.getBookId());
		if (!optionalBook.isPresent()) {
			throw new NotFoundException();
		}
		
		Book existingBookRecordBook = optionalBook.get();
		existingBookRecordBook.setName(bookRecord.getName());
		existingBookRecordBook.setSummry(bookRecord.getSummry());
		existingBookRecordBook.setRating(bookRecord.getRating());
		
		return bookRepository.save(existingBookRecordBook);
	}
	
	@DeleteMapping("/bookId")
	public void deleteBookById(@PathVariable("bookid") Long bookId) throws Exception {
		if (bookRepository.findById(bookId).isPresent()) {
			throw new Exception("bookid "+ bookId + "notPresent");
			
		}
		bookRepository.deleteById(bookId);
	}
}
