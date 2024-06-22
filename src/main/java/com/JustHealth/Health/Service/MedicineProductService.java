package com.JustHealth.Health.Service;


import com.JustHealth.Health.DTO.MedicineProductDTO;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Request.MedicineProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicineProductService {

    public MedicineProduct createMedicineProduct(MedicineProductDTO req ) throws Exception;

    public MedicineProduct updateMedicineProduct(MedicineProductDTO req,Long medicineId) throws Exception;

    public void deleteMedicineProduct(Long medicineId) throws Exception;

    public MedicineProduct getMedicineById(Long medicineId) throws Exception;

    public Page<MedicineProduct> getAllMedicineProducts(int page, int size, String sortBy);

    public  List<MedicineProduct> getAll();

    public List<MedicineProduct> getMedicineProductByCompositionId(Long compositionId);

}
