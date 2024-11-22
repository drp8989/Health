package com.JustHealth.Health.DTO;


import com.JustHealth.Health.Entity.MedicineFAQ;
import lombok.Data;

import java.util.List;

@Data
public class MedicineCompositionResponseDTO {

    private Long medicineCompositionId;

    private String medicineCompositionName;

    private String compositionTherapeuticClass;

    private String compositionUse;

    private String compositionSideEffects;

    private String compositionWorking;

    private String compositionExpertAdvice;

    private List<MedicineFAQResponseDTO> medicineFAQ;

    private String slug;

}
