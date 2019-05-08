package com.capgemini.exampleservices.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exampleservices.entity.Product;
import com.capgemini.exampleservices.service.ProductService;

@RestController
public class ProductController
{
	
	@Autowired
	private ProductService service;
	
	
	@PostMapping("/product")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) 
	{
		product= service.addNew(product);
			try 
			{
				return ResponseEntity.created(new URI("/product" + product.getProductId())).body(product);
			}
			catch (Exception e) 
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
}
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProduct(@PathVariable Integer id)
	{
		return service.getProduct(id)
                .map(product -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .location(new URI("/product/" + product.getProductId()))
                                .body(product);
                    } catch (URISyntaxException e ) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }) .orElse(ResponseEntity.notFound().build());
	}
	
}
