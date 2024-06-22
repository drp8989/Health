package com.JustHealth.Health.Request;

import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineProduct;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Value;

@Data
public class MedicineProductRequest {

    private String medicineName;

    private String medicineManufacturer;

    private Boolean medicineRx;


    private String productDosageForm;


    private String medicinePackSize;

    private String medicineReturnPolicy;

    private Long medicineCompositionId;

//    private MedicineComposition medicineComposition;




}
