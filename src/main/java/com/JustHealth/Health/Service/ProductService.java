package com.JustHealth.Health.Service;

import com.JustHealth.Health.Entity.Product;
import com.JustHealth.Health.Exception.ProductNotFoundException;
import org.springframework.data.domain.Page;

public interface ProductService {

    public Product findProductById(Long id) throws ProductNotFoundException;






}
