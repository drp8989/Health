package com.JustHealth.Health.Service;


import com.JustHealth.Health.DTO.MedicineProductDTO;
import com.JustHealth.Health.DTO.MedicineProductResponseDTO;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Request.MedicineProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicineProductService {


    public MedicineProductResponseDTO getMedicineById(Long medicineId) throws Exception;

    public MedicineProductResponseDTO createMedicineProductDTO(MedicineProductDTO req) throws Exception;

    public MedicineProductResponseDTO updateMedicineProduct(MedicineProductDTO req,Long medicineId) throws Exception;

    public Page<MedicineProduct> getAllMedicineProducts(int page, int size, String sortBy);

    public void deleteMedicineProduct(Long medicineId) throws Exception;




//    public MedicineProduct getMedicineById(Long medicineId) throws Exception;
//    public MedicineProduct createMedicineProduct(MedicineProductDTO req ) throws Exception;
//    public MedicineProduct updateMedicineProduct(MedicineProductDTO req,Long medicineId) throws Exception;



    public  List<MedicineProduct> getAll();

    public List<MedicineProduct> getMedicineProductByCompositionId(Long compositionId);

}
