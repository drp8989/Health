package com.JustHealth.Health.Service;


import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Repository.MedicineCompositionRepository;
import com.JustHealth.Health.Request.MedicineCompositionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineCompositionServiceImpl implements MedicineCompositionService {


    @Autowired
    MedicineCompositionRepository medicineCompositionRepository;

    public List<MedicineComposition> getAllCompositions(){
        return medicineCompositionRepository.findAll();
    }


    @Override
    public MedicineComposition createMedicineComposition(MedicineCompositionRequest req) throws Exception {
        MedicineComposition medicineComposition=new MedicineComposition();

        medicineComposition.setMedicineCompositionName(req.getMedicineCompositionName());
        medicineComposition.setCompositionTherapeuticClass(req.getCompositionTherapeuticClass());
        medicineComposition.setCompositionUse(req.getCompositionUse());
        medicineComposition.setCompositionSideEffects(req.getCompositionSideEffects());
        medicineComposition.setCompositionWorking(req.getCompositionWorking());
        medicineComposition.setCompositionExpertAdvice(req.getCompositionExpertAdvice());

        return medicineCompositionRepository.save(medicineComposition);

    }

    @Override
    public MedicineComposition updateMedicineComposition() throws Exception {
        return null;
    }

    @Override
    public void deleteMedicineComposition(Long id) {
        MedicineComposition medicineComposition=medicineCompositionRepository.getReferenceById(id);
        medicineCompositionRepository.delete(medicineComposition);
    }




}
