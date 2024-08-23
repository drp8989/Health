package com.JustHealth.Health.Controller;


import com.JustHealth.Health.Entity.PurchaseInventory;
import com.JustHealth.Health.Service.PurchaseInventoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchaseInventory")
public class PurchaseInventoryController {


    @Autowired
    PurchaseInventoryService purchaseInventoryService;

    @GetMapping("/{id}/getAll")
    @Transactional
    public List<PurchaseInventory> getAll(@PathVariable Long id) throws Exception {
        List<PurchaseInventory> purchaseInventories=purchaseInventoryService.getPurchaseInventoryByInventoryId(id);
        return purchaseInventories;
    }


//    @GetMapping("/{purchaseId}/getAllPurchaseInventory")
//    public ResponseEntity<Page<PurchaseInventory>> getPurchaseInventoryByPurchaseId(@PathVariable Long purchaseId,@RequestParam int page,
//                                                                                    @RequestParam int size) throws Exception{
//        Pageable pageable = PageRequest.of(page, size);
//        Page<PurchaseInventory> purchaseInventoryPage = purchaseInventoryService.getPurchaseInventoryByPurchaseId(purchaseId,page,size);
//        return ResponseEntity.ok(purchaseInventoryPage);
//    }

}
