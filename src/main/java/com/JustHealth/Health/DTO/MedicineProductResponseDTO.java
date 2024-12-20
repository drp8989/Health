package com.JustHealth.Health.DTO;


import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Entity.Product;
import lombok.Data;

@Data
public class MedicineProductResponseDTO {

    private String productName;
    private String productManufacturer;
    private Product.productType productType;  // Enum (OTC, MEDICINE)
    private CategoryResponseDTO productCategory;   // Assuming category name or ID can be passed
    private SubCategoryResponeDTO productSubCategory; // Assuming sub-category name or ID can be passed
    private String slug;

    // Fields specific to medicine products
    private Boolean medicineRx;       // Prescription required or not (Only for medicine)
    private MedicineProduct.DosageForm productDosageForm;  // Enum representing dosage form (Only for medicine)
    private String medicinePackSize;  // Pack size (Only for medicine)
    private String medicineReturnPolicy;  // Return policy (Only for medicine)
    private MedicineComposition medicineComposition;
//    private MedicineCompositionResponseDTO medicineComposition;   // Composition (Only for medicine)


}
