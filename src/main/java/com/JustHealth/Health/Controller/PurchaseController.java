package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.PurchaseDTO;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/create-purchase")
    private ResponseEntity<Purchase> createPurchase(@RequestBody
    PurchaseDTO req){
        Purchase purchase=purchaseService.createPurchase(req);
        return new  ResponseEntity<>(purchase, HttpStatus.CREATED );

    }

    @GetMapping("/getAll")
    private List<Purchase> findAllPurchase() throws Exception {

        return purchaseService.findAllPurchase();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getById(@PathVariable Long id) throws Exception {
        Optional<Purchase> purchaseOpt = purchaseService.findPurchaseById(id);

        if (purchaseOpt.isPresent()) {
            return ResponseEntity.ok(purchaseOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id) throws Exception {
         purchaseService.deletePurchaseById(id);
    }


}
