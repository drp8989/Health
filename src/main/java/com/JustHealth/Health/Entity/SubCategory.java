package com.JustHealth.Health.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_category")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sub_category_id")
    private Long id;

    @Column(name = "sub_category_name")
    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "productSubCategory", cascade = CascadeType.ALL)
    private List<Product> products;



    @JsonBackReference
    public Category getCategory(){return this.category;}





    // Helper method to add a product to the subcategory
    public void addProduct(Product product) {
        this.products.add(product);
        product.setProductSubCategory(this);
    }
}
