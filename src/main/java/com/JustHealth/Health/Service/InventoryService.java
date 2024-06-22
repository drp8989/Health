package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.BatchDTO;
import com.JustHealth.Health.DTO.InventoryDTO;
import com.JustHealth.Health.Entity.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InventoryService {

    public Inventory createInventory(InventoryDTO inventoryDTO) throws Exception;

    public List<Inventory> getAllInventory();

    public void deleteInventory(Long id);


    public Inventory addBatch(BatchDTO batchDTO);

}
