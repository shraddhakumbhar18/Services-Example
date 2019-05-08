package com.capgemini.exampleservices.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.exampleservices.dao.ProductDao;
import com.capgemini.exampleservices.entity.Product;

@Service
public class ProductServiceImpl implements ProductService
{
	@Autowired
	ProductDao dao;

	@Override
	public Product addNew(Product product) 
	{
		
		dao.save(product);
		return product;
	}
	
	@Override
	public Optional<Product> getProduct(int id) 
	{
		 Optional<Product> product = dao.findById(id);
		 return product;
	}
	
}
