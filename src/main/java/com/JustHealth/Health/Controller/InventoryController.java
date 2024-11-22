package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.BatchDTO;
import com.JustHealth.Health.DTO.InventoryDTO;
import com.JustHealth.Health.DTO.InventoryResponseDTO;
import com.JustHealth.Health.Entity.Batch;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Exception.InternalServerErrorException;
import com.JustHealth.Health.Response.ErrorMessage;
import com.JustHealth.Health.Service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


//    @Operation(summary = "Get Inventory By Id", description = "Display's information of inventory of which id is provided.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successful Operation"),
//            @ApiResponse(responseCode = "400", description = "Bad Request"),
//    })
//    @GetMapping("/{id}")
//    private InventoryResponseDTO getDataById(@PathVariable Long id) throws Exception{
//        return inventoryService.getInventoryById(id);
//    }


    @Operation(summary = "Get Inventory By Id", description = "Display's information of inventory of which id is provided.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @GetMapping("/{id}")
    private ResponseEntity<?> getDataById(@PathVariable Long id) throws Exception{
        try{
             InventoryResponseDTO inventoryResponseDTO=inventoryService.getInventoryById(id);
             return new ResponseEntity<>(inventoryResponseDTO,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Bad Request",HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(summary = "Create Inventory By Id", description = "Create's an inventory in the system. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesful Operation"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @PostMapping("/createInventory")
    private ResponseEntity<?> createInventory(@RequestBody InventoryDTO inventoryDTO) throws Exception {

        try {
            InventoryResponseDTO inventoryResponseDTO= inventoryService.createInventory(inventoryDTO);
            return new ResponseEntity<>(inventoryResponseDTO,HttpStatus.OK);
        }catch (InternalServerErrorException e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }



    @Operation(summary = "Add Batch to inventory", description = "A batch is used to distinguish the product in the inventory such as expiry,new packaging,changes ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @PostMapping("/add-batch")
    private ResponseEntity<?> addBatch(@RequestBody BatchDTO batchDTO){
        try{
             Inventory inventory=inventoryService.addBatch(batchDTO);
             return new ResponseEntity<>(inventory,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }

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


//    @GetMapping("/getAllExpiryProducts")
//    private List<Inventory> getAllExpiry(){
//        return inventoryService.getExpiredProducts();
//    }
//
//
//
//    @GetMapping("/getAllExpiringProducts")
//    private List<Inventory> getAllExpiringProducts(){
//        return inventoryService.getExpiringProducts();
//    }

//    @GetMapping("/")
//    private List<Inventory> getAll(){
//        return inventoryService.getAllInventory();
//    }



}
