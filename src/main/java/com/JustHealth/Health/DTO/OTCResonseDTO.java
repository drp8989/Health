package com.JustHealth.Health.DTO;


import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Entity.Product;
import lombok.Data;

@Data
public class OTCResonseDTO {

    private String productName;
    private String productManufacturer;
    private Product.productType productType;  // Enum (OTC, MEDICINE)
    private CategoryResponseDTO productCategory;   // Assuming category name or ID can be passed
    private SubCategoryResponeDTO productSubCategory; // Assuming sub-category name or ID can be passed
    private String slug;

    // Fields specific otc products
    private String usageInstructions;
    private String warnings;
    private String storageInstructions;
    private Double weight;
}
