package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.MedicineCompositionRequestDTO;
import com.JustHealth.Health.DTO.MedicineCompositionResponseDTO;
import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineFAQ;
import com.JustHealth.Health.Request.MedicineCompositionRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MedicineCompositionService {


    public MedicineCompositionResponseDTO createMedicineComposition(MedicineCompositionRequestDTO medicineCompositionRequestDTO) throws Exception;

    Page<MedicineCompositionResponseDTO> getAllCompositions(int page, int size, String sortBy);

    public MedicineComposition findMedicineCompositionById(Long id) throws Exception;

    public MedicineCompositionResponseDTO getMedicineComposition(Long id) throws Exception;




    public MedicineCompositionResponseDTO updateMedicineComposition(Long id, MedicineCompositionRequestDTO medicineCompositionRequestDTO) throws Exception;

    public void deleteMedicineComposition(Long id) throws Exception;


    public List<MedicineFAQ> getMedicineFAQsByCompositionId(Long id);

    public Long totalCount()throws Exception;
}
