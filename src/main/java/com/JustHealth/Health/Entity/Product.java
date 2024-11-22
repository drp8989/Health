package com.JustHealth.Health.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type")
@Table(name = "product")
public abstract class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_manufacturer")
    private String productManufacturer;


    //OTC/MEDICINE
    @Column(name = "product_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private productType productType;

    // Category and SubCategory relations
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category productCategory;

    @ManyToOne
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory productSubCategory;

    @Column(name = "product_slug")
    private String slug;

    @JsonBackReference
    public Category getProductCategory(){
        return productCategory;
    }

    @JsonBackReference
    public SubCategory getProductSubCategory(){
        return productSubCategory;
    }


    public enum productType {
        OTC,
        MEDICINE,
    }






}
