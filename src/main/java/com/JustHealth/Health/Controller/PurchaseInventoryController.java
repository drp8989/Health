package com.JustHealth.Health.Controller;


import com.JustHealth.Health.Entity.PurchaseInventory;
import com.JustHealth.Health.Service.PurchaseInventoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
