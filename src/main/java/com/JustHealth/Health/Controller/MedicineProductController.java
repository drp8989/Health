package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.MedicineProductDTO;
import com.JustHealth.Health.DTO.MedicineProductResponseDTO;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Exception.MedicineNotFoundException;
import com.JustHealth.Health.MedicineProductDao.MedicineProductSearchQuery;
import com.JustHealth.Health.Service.MedicineProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/medicine")
public class MedicineProductController{


    @Autowired
    MedicineProductService medicineProductService;

    @Autowired
    MedicineProductSearchQuery medicineProductSearchQuery;


//    @GetMapping("/{id}")
//    private ResponseEntity<MedicineProduct> getMedicine(@PathVariable Long id) throws Exception{
//
//        try {
//            MedicineProduct medicineProduct = medicineProductService.getMedicineById(id);
//            return new ResponseEntity<>(medicineProduct, HttpStatus.OK);
//        } catch (MedicineNotFoundException e) {
//            String errorMessage=e.getMessage();
//            System.out.println(errorMessage);
//            throw  new MedicineNotFoundException(errorMessage);
//        } catch (Exception e) {
//            throw  new Exception(e.getMessage());
//        }
//    }

    //@PostMapping("/create")
    //private ResponseEntity<MedicineProduct> createMedicine(@RequestBody MedicineProductDTO medicineProductDTO) throws Exception {
    //    MedicineProduct medicineProduct=medicineProductService.createMedicineProduct(medicineProductDTO);
    //    return new ResponseEntity<>(medicineProduct,HttpStatus.CREATED);
    //
    //}

//    @PutMapping("/update/{id}")
//    private MedicineProduct updateMedicine(@RequestBody MedicineProductDTO medicineProductDTO,@PathVariable Long id) throws Exception {
//        return medicineProductService.updateMedicineProduct(medicineProductDTO,id);
//
//    }



    @GetMapping("/getMedicine/{id}")
    private MedicineProductResponseDTO getMedicineById(@PathVariable Long id) throws Exception{
        return medicineProductService.getMedicineById(id);
    }

    @PostMapping("/createMedicine")
    private ResponseEntity<MedicineProductResponseDTO> createMedicineProduct(@RequestBody MedicineProductDTO medicineProductDTO) throws Exception{
        MedicineProductResponseDTO createdMedicine=medicineProductService.createMedicineProductDTO(medicineProductDTO);
        return new ResponseEntity<>(createdMedicine,HttpStatus.CREATED);
    }


    @PutMapping("/updateMedicine/{id}")
    private ResponseEntity<MedicineProductResponseDTO> updateMedicineDetails(@RequestBody MedicineProductDTO medicineProductDTO,@PathVariable Long id) throws Exception{
        MedicineProductResponseDTO updatedMedicine=medicineProductService.updateMedicineProduct(medicineProductDTO,id);
        return new ResponseEntity<>(updatedMedicine,HttpStatus.CREATED);
    }

    @GetMapping("/getAllMedicine")
    private Page<MedicineProduct> getAllMedicineProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "productName") String sortBy){
        return medicineProductService.getAllMedicineProducts(page,size,sortBy);
    }

    @GetMapping("/searchMedicineProducts")
    private List<MedicineProduct> searchMedicineProducts(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "")String manufacturer) {
        String toLowerCaseName=name.toLowerCase();
        return medicineProductSearchQuery.searchByNameAndManufacturer(toLowerCaseName, manufacturer);
    }



    @GetMapping("/composition/{compositionId}")
    private List<MedicineProduct> getProductsByCompositionId(@PathVariable Long compositionId)throws Exception{
        try {
            return medicineProductService.getMedicineProductByCompositionId(compositionId);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    private void deleteMedicine(@PathVariable Long id) throws Exception{
        medicineProductService.deleteMedicineProduct(id);
    }















}
