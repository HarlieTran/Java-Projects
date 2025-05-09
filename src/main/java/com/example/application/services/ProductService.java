package com.example.application.services;

import com.example.application.data.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;

    public ProductService(ProductRepository productRepository,
                          CompanyRepository companyRepository,
                          StatusRepository statusRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
    }

    public List<Product> findAllProducts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return productRepository.findAll();
        } else {
            return productRepository.search(stringFilter);
        }
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public long countProducts() {
        return productRepository.count();
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public void saveProduct(Product product) {
        if (product == null) {
            System.err.println("Product is null");
            return;
        }
        productRepository.save(product);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Status> findAllStatuses() {
        return statusRepository.findAll();
    }
}
