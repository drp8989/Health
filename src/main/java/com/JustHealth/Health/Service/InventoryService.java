package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.BatchDTO;
import com.JustHealth.Health.DTO.InventoryDTO;
import com.JustHealth.Health.DTO.InventoryResponseDTO;
import com.JustHealth.Health.Entity.Batch;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Purchase;
import org.springframework.data.domain.Page;

import java.util.List;


public interface InventoryService {

//      Features to implement
//      ->create inventory which requires user to follow steps
    //      1.Search product from inventory table
    //      2.Add batch which includes batch name,expiry,mrp,ptr,margin which is calculated,and qty
    //      3.and then product is inserted in inventory
    //      4.ledger is updated
//      ->A page which shows information about inventory.
    //      ->The page shows all batch details
    //      ->All the purchase related to inventtory
    //      ->Purchase Return related to inventory
    //      ->Sales related to inventory
    //      ->Sales return related to inventory
    //      ->Ledger details
//        ->Search by name

//    public Inventory getInventoryById(Long id) throws Exception;

    public InventoryResponseDTO createInventory(InventoryDTO req) throws Exception;

    public InventoryResponseDTO getInventoryById(Long id) throws Exception;

    public InventoryResponseDTO updateInventory(InventoryDTO inventoryDTO, Long id)throws Exception;

    public void deleteInventory(Long id);

    public Page<Inventory> getAllInventoryPaginated(int page,int size);

    public Page<Batch> getBatchesForInventory(Long id,int page, int size) throws Exception;

    public Inventory addBatch(BatchDTO batchDTO);


    
    public List<Purchase> getPurchaseForInventory(Long id) throws Exception;

    public List<Inventory> getExpiredProducts();



    //    public Inventory createInventory(InventoryDTO inventoryDTO) throws Exception;
//    public List<Inventory> getAllInventory();


}
