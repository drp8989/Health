package com.JustHealth.Health.Service;


import com.JustHealth.Health.DTO.MedicineCompositionDTO;
import com.JustHealth.Health.DTO.MedicineCompositionResponseDTO;
import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineFAQ;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Repository.MedicineCompositionRepository;
import com.JustHealth.Health.Repository.MedicineFAQRepository;
import com.JustHealth.Health.Request.MedicineCompositionRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineCompositionServiceImpl implements MedicineCompositionService {


    @Autowired
    MedicineCompositionRepository medicineCompositionRepository;

    @Autowired
    MedicineFAQRepository medicineFAQRepository;

    public List<MedicineComposition> getAllCompositions(){
        return medicineCompositionRepository.findAll();
    }


    @Override
    public MedicineComposition findMedicineCompositionById(Long id) throws Exception {
        Optional<MedicineComposition> medicineComposition=medicineCompositionRepository.findById(id);
        if(medicineComposition.isEmpty()){
            throw new Exception("Medicine Compostion Not Found");
        }
        return medicineComposition.get();

    }

    @Override
    @Transactional
    public MedicineCompositionResponseDTO createMedicineComposition(MedicineCompositionDTO req) throws Exception {

        List<MedicineFAQ> savedFAQs = new ArrayList<>();

        MedicineComposition medicineComposition=new MedicineComposition();
        medicineComposition.setMedicineCompositionName(req.getMedicineCompositionName());
        medicineComposition.setCompositionTherapeuticClass(req.getCompositionTherapeuticClass());
        medicineComposition.setCompositionUse(req.getCompositionUse());
        medicineComposition.setCompositionSideEffects(req.getCompositionSideEffects());
        medicineComposition.setCompositionWorking(req.getCompositionWorking());
        medicineComposition.setCompositionExpertAdvice(req.getCompositionExpertAdvice());
        //Saving Medicine FAQ
        for(MedicineFAQ medicineFAQ: req.getMedicineFAQ()){
            MedicineFAQ FAQ=new MedicineFAQ();
            FAQ.setQuestion(medicineFAQ.getQuestion());
            FAQ.setAnswer(medicineFAQ.getAnswer());
            FAQ.setMedicineComposition(medicineComposition);
            medicineFAQRepository.save(FAQ);
            savedFAQs.add(FAQ);
        }
        medicineComposition.setCompositionFAQ(savedFAQs);
        medicineCompositionRepository.save(medicineComposition);


        MedicineCompositionResponseDTO medicineCompositionResponseDTO=new MedicineCompositionResponseDTO();

        medicineCompositionResponseDTO.setMedicineCompositionName(medicineComposition.getMedicineCompositionName());
        medicineCompositionResponseDTO.setCompositionTherapeuticClass(medicineComposition.getCompositionTherapeuticClass());
        medicineCompositionResponseDTO.setCompositionUse(medicineComposition.getCompositionUse());
        medicineCompositionResponseDTO.setCompositionSideEffects(medicineComposition.getCompositionSideEffects());
        medicineCompositionResponseDTO.setCompositionWorking(medicineComposition.getCompositionWorking());
        medicineCompositionResponseDTO.setCompositionExpertAdvice(medicineComposition.getCompositionExpertAdvice());
        medicineCompositionResponseDTO.setMedicineFAQ(medicineComposition.getCompositionFAQ());

        return medicineCompositionResponseDTO;





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
