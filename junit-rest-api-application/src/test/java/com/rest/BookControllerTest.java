package com.rest; 
import static org.hamcrest.CoreMatchers.is; 
import static org.hamcrest.CoreMatchers.notNullValue; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; 
import java.util.ArrayList; 
import java.util.Arrays; 
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.junit.runner.RunWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.http.MediaType; 
import org.springframework.test.context.junit4.SpringRunner; 
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders; 
import org.springframework.test.web.servlet.result.MockMvcResultMatchers; 
import org.springframework.test.web.servlet.setup.MockMvcBuilders; 
import static org.hamcrest.Matchers.hasSize; 
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter; 
import com.rest.controller.BookController; 
import com.rest.dao.BookRepository; 
import com.rest.entity.Book; 


@SpringBootTest 
@RunWith(SpringRunner.class) 
//@ExtendWith(MockitoExtension.class) 
public class BookControllerTest { 

	//It is a class provided by spring test modules for testing controllers.
	private MockMvc mockMvc; 

// ObjectMapper,ObjectWriter They are used to convert Java objects to JSON.
ObjectMapper objectMapper = new ObjectMapper();
ObjectWriter objectWriter = objectMapper.writer();


@Mock
private BookRepository bookRepository; 

@InjectMocks
private BookController bookController;
Book r1 = new Book(1L,"Atomic Habits","How to build better habits",5);
Book r2 = new Book(2L,"Thinking fast and slow","How to create good mental model about thinking",5);
Book r3 = new Book(3L,"Grokking Algorithms","Learn algorithms the fun way",5);

@BeforeEach
public void setUp() {
	MockitoAnnotations.initMocks(this);  //Initialize the Mocks
	this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();  //sets up the MockMvc instance for the test.
}

@Test 
public void getAllRecords_sucess() throws Exception { 
List<Book> records=new ArrayList<>(Arrays.asList(r1,r2,r3));
Mockito.when(bookRepository.findAll()).thenReturn(records); 

mockMvc.perform(MockMvcRequestBuilders 
.get("/book") 
.contentType(MediaType.APPLICATION_JSON))
.andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
.andExpect(jsonPath("$[2].name", is("Grokking Algorithms")))
.andExpect(jsonPath("&[1].name", is("Thinking fast and slow")));
}


@Test
public void getBookById_success() throws Exception{
	Mockito.when(bookRepository.findById(r1.getBookId())).thenReturn(Optional.of(r1));
	mockMvc.perform(MockMvcRequestBuilders
			.get("/book/1")
			.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk())
	.andExpect(jsonPath("$", notNullValue()))
	.andExpect(jsonPath("$.name", is("Atomic Habits")));
}


@Test
public void createRecord_success() throws Exception{
	Book record = Book.builder()
			.bookId(4l)
			.name("Let's C")
			.summry("Understaing of C Language")
			.rating(5)
			.build();
	
	Mockito.when(bookRepository.save(record)).thenReturn(record);
	String content = objectWriter.writeValueAsString(record);
	
	MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(content);
	
	mockMvc.perform(mockRequest)
	.andExpect(status().isOk())
	.andExpect(jsonPath("$", notNullValue()))
	.andExpect(jsonPath("$.name", is("Let's C")));
}

@Test
public void updateRecord_success() throws Exception {
	Book updatedRecord = Book.builder()
			.bookId(1l)
			.name("Let's C Programming")
			.summry("This is Best for C lang basic concept")
			.rating(4)
			.build();
	
	Mockito.when(bookRepository.findById(r1.getBookId())).thenReturn(Optional.ofNullable(r1));
	if (r1.getBookId() == null) {
		throw new Exception("Book Not found at this id");
	}
		Mockito.when(bookRepository.save(updatedRecord)).thenReturn(updatedRecord);
	
		String updatedContent = objectWriter.writeValueAsString(updatedRecord);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/book")
		  	.contentType(MediaType.APPLICATION_JSON)
		  	.accept(MediaType.APPLICATION_JSON)
		  	.content(updatedContent);
	mockMvc.perform(mockRequest)
	.andExpect(status().isOk())
	.andExpect(jsonPath("$", notNullValue()))
	.andExpect(jsonPath("$.name", is("Let's C Programming")));
}


@Test
public void deleteBookbyId_success() throws Exception{
	Mockito.when(bookRepository.findById(r2.getBookId())).thenReturn(Optional.of(r2));
	
	mockMvc.perform(MockMvcRequestBuilders
			.delete("/book/1")
			.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk());
}



}