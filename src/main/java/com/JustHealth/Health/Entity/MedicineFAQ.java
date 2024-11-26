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
    @Basic(fetch = FetchType.EAGER)
    private String question;

    @Lob
    @Column(name = "medicine_answer")
    @Basic(fetch = FetchType.EAGER)
    private String answer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_composition_id")
    private MedicineComposition medicineComposition;


    //Constructor to initialize question and answer
    public MedicineFAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }


    @JsonBackReference
    public MedicineComposition getMedicineComposition() {
        return medicineComposition;
    }


}
