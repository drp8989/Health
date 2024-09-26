package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.MedicineCompositionDTO;
import com.JustHealth.Health.DTO.MedicineCompositionResponseDTO;
import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Request.MedicineCompositionRequest;

import java.util.List;

public interface MedicineCompositionService {


    public MedicineComposition findMedicineCompositionById(Long id) throws Exception;

    public MedicineCompositionResponseDTO createMedicineComposition(MedicineCompositionDTO medicineCompositionDTO) throws Exception;
    public List<MedicineComposition> getAllCompositions();
    public MedicineComposition updateMedicineComposition() throws Exception;
    public void deleteMedicineComposition(Long id);


}
