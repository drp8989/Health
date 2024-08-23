package com.JustHealth.Health.Controller;


import com.JustHealth.Health.Entity.InventoryLedger;
import com.JustHealth.Health.Response.ErrorMessage;
import com.JustHealth.Health.Service.InventoryLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/inventoryLedger")
public class InventoryLedgerController {

    @Autowired
    InventoryLedgerService inventoryLedgerService;

//
//    @GetMapping("/getInventoryLedger")
//    private Page<InventoryLedger> getLedger(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "0") int size){
//        return inventoryLedgerService.getLedger(page, size);
//    }


    @GetMapping("/getInventoryLedger/{id}")
    private ResponseEntity<?> getInventoryLedgerById(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @PathVariable Long id) throws Exception{
        try {
            Page<InventoryLedger> inventoryLedger=inventoryLedgerService.getLedgerByInventoryId(page, size, id);
            return new ResponseEntity<>(inventoryLedger, HttpStatus.OK);
        }catch (Exception e){
            ErrorMessage errorMessage=new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
