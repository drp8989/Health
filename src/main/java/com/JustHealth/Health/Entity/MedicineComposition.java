package com.JustHealth.Health.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "medicine_composition")
public class MedicineComposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="medicine_composition_id")
    private Long id;

    @Lob
    @Column(name = "medicine_composition_name")
    private String medicineCompositionName;


    @Column(name = "medicine_composition_therapy")
    private String compositionTherapeuticClass;

    @Lob
    @Column(name = "medicine_composition_use")
    private String compositionUse;

    @Lob
    @Column(name = "medicine_composition_side_effects")
    private String compositionSideEffects;

    @Lob
    @Column(name = "medicine_composition_working")
    private String compositionWorking;

    @Lob
    @Column(name = "medicine_composition_expert_advice")
    private String compositionExpertAdvice;

    @Column(name = "medicineFAQ")
    private String compositionFAQ;


//    @OneToOne()
//    private MedicineProduct medicineProduct;

    @OneToMany(mappedBy = "medicineComposition")
    @JsonIgnore()
    private List<MedicineProduct> medicineProduct =new ArrayList<>();



}
