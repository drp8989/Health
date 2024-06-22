package com.JustHealth.Health.Controller;


import com.JustHealth.Health.Entity.InventoryLedger;
import com.JustHealth.Health.Service.InventoryLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/inventoryLedger")
public class InventoryLedgerController {

    @Autowired
    InventoryLedgerService inventoryLedgerService;


    @GetMapping("/getInventoryLedger")
    private Page<InventoryLedger> getLedger(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "0") int size){
        return inventoryLedgerService.getLedger(page, size);
    }


}
