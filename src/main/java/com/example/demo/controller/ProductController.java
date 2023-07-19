package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;

@RestController
public class ProductController {
	private List<Product> data = new ArrayList<Product>();

	@GetMapping("/product")
	public List<Product> getProduct() {
		return data;
	}
	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable Integer id) {
		System.out.print("Employee Id="+id);
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductId() == id) {
				return data.get(i);
			}
		}
		return null;
	}
	@GetMapping("/search_text")
	public ArrayList<Product> getProduct(@RequestParam String text) {
		ArrayList<Product> products = new ArrayList<Product>(); 
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductName().contains(text)||data.get(i).getProductDetail().contains(text)) {
				products.add(data.get(i));
			}
		}
		return products;
	}

	@PostMapping("/product")
	public Product addProduct(@RequestBody Product body) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductId() == body.getProductId()) {
				return null;
			}
		}

		data.add(body);
		return body;
	}
	
	@PutMapping("/product/{id}")
	public Product updatProduct(@PathVariable Integer id,@RequestBody Product body) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductId() == id) {
				data.get(i).setProductName(body.getProductName());
				data.get(i).setProductPrice(body.getProductPrice());
				data.get(i).setProductAmount(body.getProductAmount());
				data.get(i).setProductDetail(body.getProductDetail());
				return data.get(i);
			}
		}
		return null;
	}
	
	@DeleteMapping("/product/{id}")
	public String deleteProduct(@PathVariable Integer id) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getProductId() == id) {
				data.remove(i);
				return "Deleted success!";
			}
		}
		return "Employee not found!";
	}

}
