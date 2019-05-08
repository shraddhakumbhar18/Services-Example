package com.capgemini.exampleservices.service;

import java.util.Optional;

import com.capgemini.exampleservices.entity.Product;

public interface ProductService 
{
	public Product addNew(Product product);
	public Optional<Product> getProduct(int id);
}
