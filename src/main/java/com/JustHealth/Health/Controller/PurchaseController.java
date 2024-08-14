package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.PurchaseDTO;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Entity.PurchaseInventory;
import com.JustHealth.Health.Response.ErrorMessage;
import com.JustHealth.Health.Service.PurchaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @PostMapping("/createPurchase")
    public ResponseEntity<?> createPurchase(@RequestBody
                                             PurchaseDTO req) throws Exception {
        try {
            Purchase purchase=purchaseService.createPurchase(req);
            return new  ResponseEntity<>(purchase, HttpStatus.CREATED );

        }catch (Exception e){
            throw new Exception("Error "+e.getMessage());
        }

    }


    @GetMapping("/getAllPurchases")
    public ResponseEntity<Page<Purchase>> getAllPurchase(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size)throws Exception{
        Page<Purchase> purchases=purchaseService.findAllPurchasePaginated(page, size);
        return new ResponseEntity<>(purchases,HttpStatus.OK);
    }


    @GetMapping("/getAll")
    @Transactional
    public ResponseEntity<List<Purchase>> findAllPurchase() {
        try {
            List<Purchase> purchases = purchaseService.findAllPurchase();
            return ResponseEntity.ok(purchases);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            ErrorMessage errorMessage=new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
            ResponseEntity responseEntity = new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getById(@PathVariable Long id) throws Exception {
        Purchase purchaseOpt = purchaseService.purchaseById(id);

        return ResponseEntity.status(HttpStatus.OK).body(purchaseOpt);

//        if (purchaseOpt.isPresent()) {
//            return ResponseEntity.ok(purchaseOpt.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
    }


    @GetMapping("/purchaseInventory/{id}")
    public ResponseEntity<List<PurchaseInventory>> getPurchaseInventoryByPurchaseId(@PathVariable Long id) throws Exception{
        List<PurchaseInventory> inventories=purchaseService.getPurchaseInventoryByPurchaseId(id);
        return ResponseEntity.status(HttpStatus.OK).body(inventories);
    }


    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id) throws Exception {
         purchaseService.deletePurchaseById(id);
    }


}
