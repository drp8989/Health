package com.JustHealth.Health.Service;


import com.JustHealth.Health.Entity.Product;
import com.JustHealth.Health.Exception.ProductNotFoundException;
import com.JustHealth.Health.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product findProductById(Long id) throws ProductNotFoundException {
        Optional<Product> product=productRepository.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Product not Found");
        }
        return product.get();
    }


}
