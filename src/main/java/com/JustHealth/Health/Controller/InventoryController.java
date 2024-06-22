package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.BatchDTO;
import com.JustHealth.Health.DTO.InventoryDTO;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;


    @PostMapping("/create")
    private Inventory createInventory(@RequestBody InventoryDTO inventoryDTO) throws Exception {
        return inventoryService.createInventory(inventoryDTO);


    }

    @GetMapping("/")
    private List<Inventory> getAll(){
        return inventoryService.getAllInventory();
    }

    @PostMapping("/add-batch")
    private Inventory addBatch(@RequestBody BatchDTO batchDTO){
        return inventoryService.addBatch(batchDTO);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        inventoryService.deleteInventory(id);
    }
}
