package com.JustHealth.Health.DTO;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class MedicineCompositionRequestDTO {

    @NotBlank(message = "MEDICINE Composition Name is required.")
    private String medicineCompositionName;

    @NotBlank(message = "MEDICINE Therapeutic class is required.")
    private String compositionTherapeuticClass;

    @NotBlank(message = "MEDICINE Therapeutic use is required.")
    private String compositionUse;

    @NotBlank(message = "MEDICINE Composition Side Effecits is required.")
    private String compositionSideEffects;

    @NotBlank(message = "MEDICINE Composition Working is required.")
    private String compositionWorking;

    @NotBlank(message = "MEDICINE Composition Expert Advice is required.")
    private String compositionExpertAdvice;


    private List<MedicineFAQRequestDTO> medicineFAQ;


    public boolean isEmpty() {
        return (medicineCompositionName == null || medicineCompositionName.isEmpty())
                && (compositionTherapeuticClass == null || compositionTherapeuticClass.isEmpty())
                && (compositionUse == null || compositionUse.isEmpty())
                && (compositionSideEffects == null || compositionSideEffects.isEmpty())
                && (compositionWorking == null || compositionWorking.isEmpty())
                && (compositionExpertAdvice == null || compositionExpertAdvice.isEmpty())
                && (medicineFAQ == null || medicineFAQ.isEmpty());
    }



}
