package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.MedicineProductDTO;
import com.JustHealth.Health.DTO.MedicineProductResponseDTO;
import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Exception.MedicineCompositionNotFoundException;
import com.JustHealth.Health.Exception.MedicineNotFoundException;
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
            throw new MedicineNotFoundException("Medicine Not found");
        }
        return medicineProduct.get();
    }

    public MedicineComposition findMedicineCompositionById(Long id)throws Exception{
        Optional<MedicineComposition> medicineComposition=medicineCompositionRepository.findById(id);

        if (medicineComposition.isEmpty()){
            throw new MedicineCompositionNotFoundException("Medicine Composition Not found");
        }
        return medicineComposition.get();
    }



//    @Override
//    public MedicineProduct createMedicineProduct(MedicineProductDTO req) throws Exception {
//
//        MedicineProduct medicineProduct=new MedicineProduct();
//
//        medicineProduct.setProductName(req.getProductName());
//        medicineProduct.setProductManufacturer(req.getProductManufacturer());
//        medicineProduct.setProductType("Medicine");
//        medicineProduct.setMedicineRx(req.getMedicineRx());
//        medicineProduct.setProductDosageForm(MedicineProduct.DosageForm.valueOf(req.getProductDosageForm()));
//        medicineProduct.setMedicinePackSize(req.getMedicinePackSize());
//        medicineProduct.setMedicineReturnPolicy(req.getMedicineReturnPolicy());
//
//        MedicineComposition medicineComposition=findMedicineCompositionById(req.getMedicineCompositionId());
//
//        medicineProduct.setMedicineComposition(medicineComposition);
//
//        return medicineProductRepository.save(medicineProduct);
//
//    }

//    @Override
//    public MedicineProduct updateMedicineProduct(MedicineProductDTO req,Long medicineId) throws Exception {
//
//        MedicineProduct updateMedicineProduct=findMedicineById(medicineId);
//
//        if(req.getProductName()!=null){
//            updateMedicineProduct.setProductName(req.getProductName());
//        }
//        if(req.getProductManufacturer()!=null){
//            updateMedicineProduct.setProductManufacturer(req.getProductManufacturer());
//        }
//        if(req.getMedicineRx()!=null){
//            updateMedicineProduct.setMedicineRx(!req.getMedicineRx());
//        };
//        if(req.getProductDosageForm()!=null){
//            updateMedicineProduct.setProductDosageForm(MedicineProduct.DosageForm.valueOf(req.getProductDosageForm()));
//        }
//        if (req.getMedicinePackSize()!=null){
//            updateMedicineProduct.setMedicinePackSize(req.getMedicinePackSize());
//        }
//        if(req.getMedicineReturnPolicy()!=null){
//            updateMedicineProduct.setMedicineReturnPolicy(req.getMedicineReturnPolicy());
//        }
//
//        if (req.getMedicineCompositionId() != null) {
//            MedicineComposition medicineComposition=findMedicineCompositionById(req.getMedicineCompositionId());
//            updateMedicineProduct.setMedicineComposition(medicineComposition);
//        }
//
//
//        return medicineProductRepository.save(updateMedicineProduct);
//
//
//    }
    @Override
    public MedicineProductResponseDTO getMedicineById(Long medicineId) throws Exception {

        Optional<MedicineProduct> medicineProduct=medicineProductRepository.findById(medicineId);
        if(medicineProduct.isEmpty()){
            throw new MedicineNotFoundException("Medicine Not Found Error");
        }
        MedicineProductResponseDTO medicineProductResponse=new MedicineProductResponseDTO();

        medicineProductResponse.setProductName(medicineProduct.get().getProductName());
        medicineProductResponse.setProductManufacturer(medicineProduct.get().getProductManufacturer());
        medicineProductResponse.setMedicineRx(medicineProduct.get().getMedicineRx());
        medicineProductResponse.setProductDosageForm(String.valueOf(medicineProduct.get().getProductDosageForm()));
        medicineProductResponse.setMedicinePackSize(medicineProduct.get().getMedicinePackSize());
        medicineProductResponse.setMedicineReturnPolicy(medicineProduct.get().getMedicineReturnPolicy());
        medicineProductResponse.setMedicineComposition(medicineProduct.get().getMedicineComposition());


        return medicineProductResponse;
    }

    @Override
    public MedicineProductResponseDTO createMedicineProductDTO(MedicineProductDTO req) throws Exception {

        if (req.getProductName() == null || req.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Medicine Product name is required.");
        }
        if (req.getProductManufacturer() == null || req.getProductManufacturer().isEmpty()) {
            throw new IllegalArgumentException("Medicine Product manufacturer is required.");
        }
        if (req.getMedicineRx() == null) {
                throw new IllegalArgumentException("Medicine Product Field is required.");
        }
        if (req.getProductDosageForm() == null || req.getProductDosageForm().isEmpty()) {
            throw new IllegalArgumentException("Medicine Product Dosage Form is required.");
        }
        if (req.getMedicinePackSize() == null || req.getMedicinePackSize().isEmpty()) {
            throw new IllegalArgumentException("Medicine Product PackSize is required.");
        }
        if (req.getMedicineReturnPolicy() == null || req.getMedicineReturnPolicy().isEmpty()) {
            throw new IllegalArgumentException("Medicine Product Return Policy is required.");
        }
        if (req.getMedicineCompositionId() == null ) {
            throw new IllegalArgumentException("Medicine Product Composition is required.");
        }
        MedicineProduct medicineProduct=new MedicineProduct();
        medicineProduct.setProductName(req.getProductName());
        medicineProduct.setProductManufacturer(req.getProductManufacturer());
        medicineProduct.setMedicineRx(req.getMedicineRx());
        medicineProduct.setProductDosageForm(MedicineProduct.DosageForm.valueOf(req.getProductDosageForm()));
        medicineProduct.setMedicinePackSize(req.getMedicinePackSize());
        medicineProduct.setMedicineReturnPolicy(req.getMedicineReturnPolicy());
        medicineProduct.setMedicineComposition(findMedicineCompositionById(req.getMedicineCompositionId()));
        medicineProductRepository.save(medicineProduct);

        MedicineProductResponseDTO createdMedicineProduct=new MedicineProductResponseDTO();

        createdMedicineProduct.setProductName(medicineProduct.getProductName());
        createdMedicineProduct.setProductManufacturer(medicineProduct.getProductManufacturer());
        createdMedicineProduct.setMedicineRx(medicineProduct.getMedicineRx());
        createdMedicineProduct.setProductDosageForm(String.valueOf(medicineProduct.getProductDosageForm()));
        createdMedicineProduct.setMedicinePackSize(medicineProduct.getMedicinePackSize());
        createdMedicineProduct.setMedicineReturnPolicy(medicineProduct.getMedicineReturnPolicy());
        createdMedicineProduct.setMedicineComposition(medicineProduct.getMedicineComposition());


        return createdMedicineProduct;
    }

    @Override
    public MedicineProductResponseDTO updateMedicineProduct(MedicineProductDTO req, Long medicineId) throws Exception {

//        if (req.getProductName() == null || req.getProductName().isEmpty()) {
//            throw new IllegalArgumentException("Medicine Product name is required.");
//        }
//        if (req.getProductManufacturer() == null || req.getProductManufacturer().isEmpty()) {
//            throw new IllegalArgumentException("Medicine Product manufacturer is required.");
//        }
//        if (req.getMedicineRx() == null) {
//            throw new IllegalArgumentException("Medicine Product Field is required.");
//        }
//        if (req.getProductDosageForm() == null || req.getProductDosageForm().isEmpty()) {
//            throw new IllegalArgumentException("Medicine Product Dosage Form is required.");
//        }
//        if (req.getMedicinePackSize() == null || req.getMedicinePackSize().isEmpty()) {
//            throw new IllegalArgumentException("Medicine Product PackSize is required.");
//        }
//        if (req.getMedicineReturnPolicy() == null || req.getMedicineReturnPolicy().isEmpty()) {
//            throw new IllegalArgumentException("Medicine Product Return Policy is required.");
//        }
//        if (req.getMedicineCompositionId() == null ) {
//            throw new IllegalArgumentException("Medicine Product Composition is required.");
//        }


        MedicineProduct toUpdateMedicine=findMedicineById(medicineId);
        if(req.getProductName()!=null){
            toUpdateMedicine.setProductName(req.getProductName());
        }
        if(req.getProductManufacturer()!=null){
            toUpdateMedicine.setProductManufacturer(req.getProductManufacturer());
        }
        if(req.getMedicineRx()!=null){
            toUpdateMedicine.setMedicineRx(!req.getMedicineRx());
        };
        if(req.getProductDosageForm()!=null){
            toUpdateMedicine.setProductDosageForm(MedicineProduct.DosageForm.valueOf(req.getProductDosageForm()));
        }
        if (req.getMedicinePackSize()!=null){
            toUpdateMedicine.setMedicinePackSize(req.getMedicinePackSize());
        }
        if(req.getMedicineReturnPolicy()!=null){
            toUpdateMedicine.setMedicineReturnPolicy(req.getMedicineReturnPolicy());
        }

        if (req.getMedicineCompositionId() != null) {
            MedicineComposition medicineComposition=findMedicineCompositionById(req.getMedicineCompositionId());
            toUpdateMedicine.setMedicineComposition(medicineComposition);
        }
        medicineProductRepository.save(toUpdateMedicine);

        //Setting the updated medicine details to response
        MedicineProductResponseDTO medicineProductResponseDTO=new MedicineProductResponseDTO();
        medicineProductResponseDTO.setProductName(toUpdateMedicine.getProductName());
        medicineProductResponseDTO.setProductManufacturer(toUpdateMedicine.getProductManufacturer());
        medicineProductResponseDTO.setMedicineRx(toUpdateMedicine.getMedicineRx());
        medicineProductResponseDTO.setProductDosageForm(String.valueOf(toUpdateMedicine.getProductDosageForm()));
        medicineProductResponseDTO.setMedicinePackSize(toUpdateMedicine.getMedicinePackSize());
        medicineProductResponseDTO.setMedicineReturnPolicy(toUpdateMedicine.getMedicineReturnPolicy());
        medicineProductResponseDTO.setMedicineComposition(toUpdateMedicine.getMedicineComposition());



        return medicineProductResponseDTO;
    }




    @Override
    public void deleteMedicineProduct(Long medicineId) throws Exception {
        MedicineProduct medicineProduct=findMedicineById(medicineId);
        medicineProductRepository.delete(medicineProduct);
    }



//    @Override
//    public MedicineProduct getMedicineById(Long medicineId) throws Exception {
//        return findMedicineById(medicineId);
//    }

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
