package com.JustHealth.Health.Entity;


import com.JustHealth.Health.DTO.MedicineProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;




@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@DiscriminatorValue("Medicine")
public class MedicineProduct extends Product {

    @Column(name = "medicine_rx")
    private Boolean medicineRx;

    @Enumerated(EnumType.STRING)
    @Column(name = "medicine_dosage_form")
    private DosageForm productDosageForm;


    @Column(name = "medicine_pack_size")
    private String medicinePackSize;

    @Column(name = "medicine_return_policy")
    private String medicineReturnPolicy;

    @ManyToOne()
    @JoinColumn(name = "medicine_product_composition_id",nullable = true)
    private MedicineComposition medicineComposition;



//    @OneToOne(mappedBy = "medicineProduct")
//    @JoinColumn(name = "medicine_inventory_id")
//    private Inventory inventory;

    public enum DosageForm {
        TOPICAL_GEL,
        SYRUP,
        TABLET,
        CAPSULE,
        INJECTION,
        SUSPENSION,
    }

}
