package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.type.descriptor.java.ObjectArrayJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@RestController
public class ProductController {
	@Autowired
	ProductRepository productRepository;

	@GetMapping("/product")
	public ResponseEntity<Object> getProduct() {
		List<Product> product = productRepository.findAll();
		try {
			return new ResponseEntity<>(product,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal sever error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@GetMapping("/product/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable Integer id) {
		Optional<Product> product =productRepository.findById(id);
		try {
			if (product.isPresent()) {
				return new ResponseEntity<>(product.get(),HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Product not found",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/search_text")
	public ResponseEntity<Object> getProduct(@RequestParam String text) {
		List<Product> products = new ArrayList<Product>();
		products.add((Product) productRepository.findByProductNameContaining(text));
		products.add((Product) productRepository.findByProductDetailContaining(text));
		try {
			if (!(products.isEmpty())) {
				return new ResponseEntity<>(products.toArray(),HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Product not found",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/product")
	public ResponseEntity<Object> addProduct(@RequestBody Product body) {
		Product product = productRepository.save(body);
		try {
			return new ResponseEntity<>(product,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal sever error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<Object> updatProduct(@PathVariable Integer id,@RequestBody Product body) {
		Optional<Product> product = productRepository.findById(id);
		try {
			if(product.isPresent()) {
				product.get().setProductAmount(body.getProductAmount());
				product.get().setProductDetail(body.getProductDetail());
				product.get().setProductName(body.getProductName());
				product.get().setProductPrice(body.getProductPrice());
				productRepository.save(product.get());
				return new ResponseEntity<>(product.get(),HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Product not found",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal sever error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Integer id) {
		Optional<Product> product = productRepository.findById(id);
		try {
			if (product.isPresent()) {
				productRepository.deleteById(id);
				return new ResponseEntity<>("",HttpStatus.NO_CONTENT);
			}else {
				return new ResponseEntity<>("Product not found",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal sever error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}

}
