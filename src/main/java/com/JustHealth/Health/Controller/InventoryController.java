package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.BatchDTO;
import com.JustHealth.Health.DTO.InventoryDTO;
import com.JustHealth.Health.DTO.InventoryResponseDTO;
import com.JustHealth.Health.Entity.Batch;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Response.ErrorMessage;
import com.JustHealth.Health.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;


    @GetMapping("/{id}")
    private InventoryResponseDTO getDataById(@PathVariable Long id) throws Exception{
        return inventoryService.getInventoryById(id);
    }


    @PostMapping("/createInventory")
    private InventoryResponseDTO createInventory(@RequestBody InventoryDTO inventoryDTO) throws Exception {
        return inventoryService.createInventory(inventoryDTO);


    }


    @PostMapping("/add-batch")
    private Inventory addBatch(@RequestBody BatchDTO batchDTO){
        return inventoryService.addBatch(batchDTO);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id){
        try {
            inventoryService.deleteInventory(id);

            return new ResponseEntity<>("Deleted Succesfully", HttpStatus.OK);
        }catch (Exception e){
            ErrorMessage errorMessage=new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
            ResponseEntity responseEntity = new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        }

    }


    @GetMapping("/batch/{id}")
    private Page<Batch> getBatches(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size, @PathVariable Long id) throws Exception {
//        List<Batch> inventoryBatches=inventoryService.getBatchesForInventory(id,page,size);
//        return inventoryBatches;

        return inventoryService.getBatchesForInventory(id,page,size);

    }

    @GetMapping("/purchase/{id}")
    private List<Purchase> getPurchasesForInventory(@PathVariable Long id) throws Exception{
        List<Purchase> purchases=inventoryService.getPurchaseForInventory(id);
        return purchases;
    }


    @GetMapping("/getAllInventory")
    private Page<InventoryResponseDTO> getAllInventory(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size) throws Exception{
        return inventoryService.getAllInventoryPaginated(page,size);
    }

    @PutMapping("/update/{id}")
    private InventoryResponseDTO updateInventory(@RequestBody InventoryDTO inventoryDTO, @PathVariable Long id) throws Exception {
        return inventoryService.updateInventory(inventoryDTO,id);
    }


    @GetMapping("/getAllExpiryProducts")
    private List<Inventory> getAllExpiry(){
        return inventoryService.getExpiredProducts();
    }

//    @GetMapping("/")
//    private List<Inventory> getAll(){
//        return inventoryService.getAllInventory();
//    }



}
