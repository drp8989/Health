package com.JustHealth.Health.Controller;


import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Request.MedicineCompositionRequest;
import com.JustHealth.Health.Service.MedicineCompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/composition")
public class MedicineCompositionController {


    @Autowired
    MedicineCompositionService medicineCompositionService;

    @PostMapping("/create")
    private ResponseEntity<MedicineComposition> createComposition(@RequestBody MedicineCompositionRequest req) throws Exception {
        MedicineComposition medicineComposition=medicineCompositionService.createMedicineComposition(req);
        return new  ResponseEntity<>(medicineComposition,HttpStatus.CREATED);
    }

    @GetMapping("/")
    private List<MedicineComposition> getAllComposition(){
        return medicineCompositionService.getAllCompositions();
    }

    @DeleteMapping("/{id}")
    private void deleteComposition(@PathVariable Long id){
        medicineCompositionService.deleteMedicineComposition(id);
    }
}