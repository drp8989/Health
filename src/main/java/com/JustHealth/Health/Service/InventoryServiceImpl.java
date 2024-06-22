package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.BatchDTO;
import com.JustHealth.Health.DTO.InventoryDTO;
import com.JustHealth.Health.Entity.Batch;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Product;
import com.JustHealth.Health.Repository.BatchRepository;
import com.JustHealth.Health.Repository.InventoryRepository;
import com.JustHealth.Health.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BatchRepository batchRepository;

    public List<Batch> getBatchesForInventory(Integer inventoryId) {
        Inventory inventory = inventoryRepository.findById(Long.valueOf(inventoryId)).orElse(null);
        if (inventory != null) {
            return inventory.getInventoryBatch();
        }
        return null;
    }


    @Override
    public Inventory createInventory(InventoryDTO req) throws Exception {

        Inventory inventory = new Inventory();

        inventory.setReorderLevel(req.getReorderLevel());
        inventory.setReorderQuantity(req.getReorderQuantity());
        inventory.setLocation(req.getLocation());
        inventory.setMinQTY(req.getMinQTY());
        inventory.setMaxQTY(req.getMaxQTY());
        inventory.setGST(req.getGST());
        Optional<Product> product =productRepository.findById(Long.valueOf(req.getProductId()));
        if (product.isEmpty()){
            throw new Exception("Product for inventory not found");
        }
        inventory.setProduct(product.get());



        return inventoryRepository.save(inventory);
    }

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }


    @Override
    @Transactional
    public Inventory addBatch(BatchDTO req) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findById(Long.valueOf(req.getInventoryId()));
        if (inventoryOpt.isEmpty()) {
            throw new RuntimeException("Inventory not found for adding batch");
        }
        Inventory inventory = inventoryOpt.get();

        // Check if the batch already exists
        boolean batchExists = inventory.getInventoryBatch().stream()
                .anyMatch(existingBatch -> existingBatch.getBatch().equals(req.getBatch()));
        if (batchExists) {
            throw new RuntimeException("Batch already exists in inventory");
        }

        // Create and set up the new batch
        Batch batch = new Batch();
        batch.setBatch(req.getBatch());
        batch.setBatchPTR(req.getBatchPTR());
        batch.setBatchMRP(req.getBatchMRP());
        batch.setQuantityInStock(req.getQuantityInStock());
        batch.setExpiryDate(req.getExpiryDate());
//        batch.setBatchInventory(inventory); // Set the inventory reference in the batch

        // Add the new batch to the inventory
        inventory.getInventoryBatch().add(batch);

        // Save the inventory (should cascade and save the batch as well if cascade settings are correct)
        inventoryRepository.save(inventory);
        return inventory;
    }
}
