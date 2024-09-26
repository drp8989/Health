package com.JustHealth.Health.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicine_faq")
public class MedicineFAQ {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "medicine_question")
    private String question;

    @Lob
    @Column(name = "medicine_answer")
    private String answer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_composition_id")
    private MedicineComposition medicineComposition;


    @JsonBackReference
    public MedicineComposition getMedicineComposition() {
        return medicineComposition;
    }

}
