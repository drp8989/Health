package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByProductType(Product.productType productType, Pageable pageable);
}
