package com.JustHealth.Health.DTO;


import com.JustHealth.Health.Entity.MedicineComposition;
import lombok.Data;

@Data
public class MedicineProductResponseDTO {

    private String productName;
    private String productManufacturer;
    private Boolean medicineRx;
    private String productDosageForm;
    private String medicinePackSize;
    private String medicineReturnPolicy;
    private MedicineComposition medicineComposition;



    public enum DosageForm {
        TOPICAL_GEL,
        SYRUP,
        TABLET,
        CAPSULE,
        INJECTION,
        SUSPENSION,
    }
}
