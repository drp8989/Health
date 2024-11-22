package com.JustHealth.Health.DTO;


import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Entity.Product;
import lombok.Data;

@Data
public class ProductRequestDTO {

    // Common fields for all products
    private String productName;
    private String productManufacturer;
//    private Product.productType productType;  // Enum (OTC, MEDICINE)
    private Long productCategoryId;   // Assuming category name or ID can be passed
    private Long productSubCategoryId; // Assuming sub-category name or ID can be passed


    // Fields specific to medicine products
    private Boolean medicineRx;       // Prescription required or not (Only for medicine)
    private MedicineProduct.DosageForm productDosageForm;  // Enum representing dosage form (Only for medicine)
    private String medicinePackSize;  // Pack size (Only for medicine)
    private String medicineReturnPolicy;  // Return policy (Only for medicine)
    private Long medicineCompositionId;   // Composition (Only for medicine)

    // Fields specific to OTC products
    private String usageInstructions;
    private String warnings;
    private String storageInstructions;
    private Double weight;




}
