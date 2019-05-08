package com.capgemini.exampleservices.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.capgemini.exampleservices.entity.Product;
import com.capgemini.exampleservices.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

public class ProductControllerTest {

	@MockBean
	private ProductService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("POST /product- Success")
	public void testCreateProduct() throws Exception 
	{
		Product postProduct = new Product(1, "ABC");
		Product mockProduct = new Product(1, "ABC");

	//        Mockito.when(service.addProduct(postProduct)).thenReturn(mockProduct);
		doReturn(mockProduct).when(service).addNew(any());
		mockMvc.perform(post("/product")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(postProduct)))
					.andExpect(header().string(HttpHeaders.LOCATION, "/product1"))
					.andExpect(jsonPath("$.productId", is(1)))
					.andExpect(jsonPath("$.productName", is("ABC")));
		}

	private String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("GET/product/1 - Found")
	public void testGetProductByIdFound() throws Exception
	{
		Product mockProduct = new Product(1, "Mango");
		doReturn(Optional.of(mockProduct)).when(service).getProduct(1);
		
		mockMvc.perform(get("/product/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			
			.andExpect(jsonPath("$.productId", is(1)))
            .andExpect(jsonPath("$.productName", is("Mango")));

		
	}
	
	@Test
    @DisplayName("GET /product/1 - Not Found")
    void testGetProductByIdNotFound() throws Exception {
        doReturn(Optional.empty()).when(service).getProduct(1);

        mockMvc.perform(get("/product/{id}", 1))

                .andExpect(status().isNotFound());
    }

}
