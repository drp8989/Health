package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.MedicineProductDTO;
import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Exception.medicineNotFoundException;
import com.JustHealth.Health.Repository.MedicineCompositionRepository;
import com.JustHealth.Health.Repository.MedicineProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineProductServiceImpl implements MedicineProductService {


    @Autowired
    MedicineProductRepository medicineProductRepository;

    @Autowired
    MedicineCompositionRepository medicineCompositionRepository;


    public MedicineProduct findMedicineById(Long id)throws Exception{
        Optional<MedicineProduct> medicineProduct=medicineProductRepository.findById(id);

        if(medicineProduct.isEmpty()){
            throw new medicineNotFoundException("Medicine Not found");
        }
        return medicineProduct.get();
    }
//
    public MedicineComposition findMedicineCompositionById(Long id)throws Exception{
        Optional<MedicineComposition> medicineComposition=medicineCompositionRepository.findById(id);

        if (medicineComposition.isEmpty()){
            throw new Exception("Not found");
        }
        return medicineComposition.get();
    }



    @Override
    public MedicineProduct createMedicineProduct(MedicineProductDTO req) throws Exception {

        MedicineProduct medicineProduct=new MedicineProduct();

        medicineProduct.setProductName(req.getProductName());
        medicineProduct.setProductManufacturer(req.getProductManufacturer());
        medicineProduct.setProductType("Medicine");
        medicineProduct.setMedicineRx(req.getMedicineRx());
        medicineProduct.setProductDosageForm(MedicineProduct.DosageForm.valueOf(req.getProductDosageForm()));
        medicineProduct.setMedicinePackSize(req.getMedicinePackSize());
        medicineProduct.setMedicineReturnPolicy(req.getMedicineReturnPolicy());

        MedicineComposition medicineComposition=findMedicineCompositionById(req.getMedicineCompositionId());

        medicineProduct.setMedicineComposition(medicineComposition);

        return medicineProductRepository.save(medicineProduct);

    }

    @Override
    public MedicineProduct updateMedicineProduct(MedicineProductDTO req,Long medicineId) throws Exception {

        MedicineProduct updateMedicineProduct=findMedicineById(medicineId);

        if(req.getProductName()!=null){
            updateMedicineProduct.setProductName(req.getProductName());
        }
        if(req.getProductManufacturer()!=null){
            updateMedicineProduct.setProductManufacturer(req.getProductManufacturer());
        }
        if(req.getMedicineRx()!=null){
            updateMedicineProduct.setMedicineRx(!req.getMedicineRx());
        };
        if(req.getProductDosageForm()!=null){
            updateMedicineProduct.setProductDosageForm(MedicineProduct.DosageForm.valueOf(req.getProductDosageForm()));
        }
        if (req.getMedicinePackSize()!=null){
            updateMedicineProduct.setMedicinePackSize(req.getMedicinePackSize());
        }
        if(req.getMedicineReturnPolicy()!=null){
            updateMedicineProduct.setMedicineReturnPolicy(req.getMedicineReturnPolicy());
        }

        if (req.getMedicineCompositionId() != null) {
            MedicineComposition medicineComposition=findMedicineCompositionById(req.getMedicineCompositionId());
            updateMedicineProduct.setMedicineComposition(medicineComposition);
        }


        return medicineProductRepository.save(updateMedicineProduct);


    }
//

    @Override
    public void deleteMedicineProduct(Long medicineId) throws Exception {
        MedicineProduct medicineProduct=findMedicineById(medicineId);
        medicineProductRepository.delete(medicineProduct);
    }

    @Override
    public MedicineProduct getMedicineById(Long medicineId) throws Exception {
        return findMedicineById(medicineId);
    }

    @Override
    public Page<MedicineProduct> getAllMedicineProducts(int page, int size, String sortBy) {
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));
        return medicineProductRepository.findAll(pageable);
    }

    @Override
    public List<MedicineProduct> getAll(){
        return medicineProductRepository.findAll();
    }

    @Override
    @Transactional
    public List<MedicineProduct> getMedicineProductByCompositionId(Long compositionId) {

        return medicineProductRepository.findMedicineProductsByCompositionId(compositionId);
    }

}
