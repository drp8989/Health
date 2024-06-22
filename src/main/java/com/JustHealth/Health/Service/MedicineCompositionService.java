package com.JustHealth.Health.Service;

import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Request.MedicineCompositionRequest;

import java.util.List;

public interface MedicineCompositionService {

    public MedicineComposition createMedicineComposition(MedicineCompositionRequest medicineCompositionRequest) throws Exception;
    public List<MedicineComposition> getAllCompositions();
    public MedicineComposition updateMedicineComposition() throws Exception;
    public void deleteMedicineComposition(Long id);


}
