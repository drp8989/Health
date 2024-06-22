package com.JustHealth.Health.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MedicineProductDTO {


    private String productName;
    private String productManufacturer;
    private Boolean medicineRx;
    private String productDosageForm;
    private String medicinePackSize;
    private String medicineReturnPolicy;
    private Long medicineCompositionId;



    public enum DosageForm {
        TOPICAL_GEL,
        SYRUP,
        TABLET,
        CAPSULE,
        INJECTION,
        SUSPENSION,
    }
}
