package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.MedicineProductDTO;
import com.JustHealth.Health.Entity.MedicineProduct;
import com.JustHealth.Health.Exception.medicineNotFoundException;
import com.JustHealth.Health.Service.MedicineProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/medicine")
public class MedicineProductController {


    @Autowired
    MedicineProductService medicineProductService;




    @PostMapping("/create")
    private MedicineProduct createMedicine(@RequestBody MedicineProductDTO medicineProductDTO) throws Exception {
        return medicineProductService.createMedicineProduct(medicineProductDTO);

    }

    @PutMapping("/update/{id}")
    private MedicineProduct updateMedicine(@RequestBody MedicineProductDTO medicineProductDTO,@PathVariable Long id) throws Exception {
        return medicineProductService.updateMedicineProduct(medicineProductDTO,id);

    }
    @GetMapping("/{id}")
    private ResponseEntity<MedicineProduct> getMedicine(@PathVariable Long id) throws Exception{

        try {
            MedicineProduct medicineProduct = medicineProductService.getMedicineById(id);
            return new ResponseEntity<>(medicineProduct, HttpStatus.OK);
        } catch (medicineNotFoundException e) {
            String errorMessage=e.getMessage();
            System.out.println(errorMessage);
            throw  new medicineNotFoundException(errorMessage);
        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }
    }


    @GetMapping("/composition/{compositionId}")
    private List<MedicineProduct> getProductsByCompositionId(@PathVariable Long compositionId)throws Exception{
        try {
            System.out.println(compositionId);
            return medicineProductService.getMedicineProductByCompositionId(compositionId);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

//
//    @DeleteMapping("/{id}")
//    private void deleteMedicine(@PathVariable Long id) throws Exception{
//        medicineProductService.deleteMedicineProduct(id);
//    }
//

//

    @GetMapping("/get")
    private List<MedicineProduct> getAll(){
        return medicineProductService.getAll();
    }


    @GetMapping("/medicine_product")
    private Page<MedicineProduct> getAllMedicineProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "productName") String sortBy){
        return medicineProductService.getAllMedicineProducts(page,size,sortBy);
    }

}
