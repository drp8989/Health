package com.JustHealth.Health.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "medicine_composition_name",unique = true)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicineComposition",orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MedicineFAQ> compositionFAQ= new ArrayList<>();


    //It is used to genrate url
    private String slug;


//    @OneToOne()
//    private MedicineProduct medicineProduct;

    @OneToMany(mappedBy = "medicineComposition")
    @JsonIgnore()
    private List<MedicineProduct> medicineProduct =new ArrayList<>();


    // Convenience methods for adding and removing FAQs
    public void addFAQ(MedicineFAQ faq) {
        compositionFAQ.add(faq);
        faq.setMedicineComposition(this); // Set the back-reference
    }

    public void removeFAQ(MedicineFAQ faq) {
        compositionFAQ.remove(faq);
        faq.setMedicineComposition(null); // Clear the back-reference
    }

    public void removeAllFAQs() {
        for (MedicineFAQ faq : new ArrayList<>(compositionFAQ)) {
            removeFAQ(faq); // This will also clear the back-reference
        }
    }

    @JsonManagedReference
    public List<MedicineFAQ> getCompositionFAQ() {
        return compositionFAQ;
    }



}
