package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.BatchDTO;
import com.JustHealth.Health.DTO.InventoryDTO;
import com.JustHealth.Health.DTO.InventoryResponseDTO;
import com.JustHealth.Health.Entity.*;
import com.JustHealth.Health.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    InventoryLedgerRepository inventoryLedgerRepository;

    @Autowired
    PurchaseInventoryRepository purchaseInventoryRepository;



    //@Override
//    public Inventory createInventory(InventoryDTO req) throws Exception {
//
//        Inventory inventory = new Inventory();
//
//        inventory.setReorderLevel(req.getReorderLevel());
//        inventory.setReorderQuantity(req.getReorderQuantity());
//        inventory.setLocation(req.getLocation());
//        inventory.setMinQTY(req.getMinQTY());
//        inventory.setMaxQTY(req.getMaxQTY());
//        inventory.setGST(req.getGST());
////        Optional<Product> product =productRepository.findById(Long.valueOf(req.get()));
////        if (product.isEmpty()){
////            throw new Exception("Product for inventory not found");
////        }
////        inventory.setProduct(product.get());
//
//
//
//        return inventoryRepository.save(inventory);
//    }


//    @Override
//    public Inventory getInventoryById(Long id)throws Exception{
//        Optional<Inventory> inventoryOpt=inventoryRepository.findById(id);
//        if(inventoryOpt.isEmpty()){
//            throw new Exception("Inventory not found");
//        }
//        return inventoryOpt.get();
//    }

//    @Override
//    public List<Inventory> getAllInventory() {
//        return inventoryRepository.findAll();
//    }

    @Override
    public InventoryResponseDTO createInventory(InventoryDTO req) throws Exception {

        if(req.getReorderLevel()==null){
            throw new IllegalArgumentException("Reorder Level is required");
        }
        if(req.getReorderQuantity()==null){
            throw new IllegalArgumentException("Reorder Quantity is required");
        }
        if(req.getLocation()==null || req.getLocation().isEmpty()){
            throw new IllegalArgumentException("Location is required");
        }
        if(req.getMinQTY()==null){
            throw new IllegalArgumentException("MIN QTY is required");
        }
        if(req.getMaxQTY()==null){
            throw new IllegalArgumentException("MAX QTY is required");
        }
        if(req.getGST()==null){
            throw new IllegalArgumentException("GST is required");
        }
        if(req.getProductId()==null){
            throw new IllegalArgumentException("Inventory should be linked to a product");
        }

        Inventory createdInventory=new Inventory();
        createdInventory.setReorderLevel(req.getReorderLevel());
        createdInventory.setReorderQuantity(req.getReorderQuantity());
        createdInventory.setLocation(req.getLocation());
        createdInventory.setMinQTY(req.getMinQTY());
        createdInventory.setMaxQTY(req.getMaxQTY());
        createdInventory.setGST(req.getGST());
        //todo adding product reference and batch details and current stock which needs to be calculated
        createdInventory.setProduct(productService.findProductById(req.getProductId()));
        inventoryRepository.save(createdInventory);

        //Updating in ledger
        InventoryLedger inventoryLedger=new InventoryLedger();
        inventoryLedger.setEntryDate(LocalDateTime.now());
        inventoryLedger.setLedgerTransaction("Inventory Created");
        inventoryLedger.setInventoryIn((float) 0);
        inventoryLedger.setInventoryOut((float) 0);
        inventoryLedger.setClosing((float) 0);
        inventoryLedgerRepository.save(inventoryLedger);

        //setting the response
        InventoryResponseDTO inventoryResponseDTO=new InventoryResponseDTO();
        inventoryResponseDTO.setReorderLevel(createdInventory.getReorderLevel());
        inventoryResponseDTO.setReorderQuantity(createdInventory.getReorderQuantity());
        inventoryResponseDTO.setLocation(createdInventory.getLocation());
        inventoryResponseDTO.setMinQTY(createdInventory.getMinQTY());
        inventoryResponseDTO.setMaxQTY(createdInventory.getMaxQTY());
        inventoryResponseDTO.setGST(createdInventory.getGST());
        inventoryResponseDTO.setProduct(createdInventory.getProduct());


        return inventoryResponseDTO;
    }

    @Override
    public InventoryResponseDTO getInventoryById(Long id)throws Exception{

        Optional<Inventory> inventoryOpt=inventoryRepository.findById(id);
        if(inventoryOpt.isEmpty()){
            throw new Exception("Inventory not found");
        }
        Inventory inventory=inventoryOpt.get();

        InventoryResponseDTO inventoryResponseDTO=new InventoryResponseDTO();
        inventoryResponseDTO.setReorderLevel(inventory.getReorderLevel());
        inventoryResponseDTO.setReorderQuantity(inventory.getReorderQuantity());
        inventoryResponseDTO.setLocation(inventory.getLocation());
        inventoryResponseDTO.setMinQTY(inventory.getMinQTY());
        inventoryResponseDTO.setMinQTY(inventory.getMaxQTY());
        inventoryResponseDTO.setGST(inventory.getGST());
        inventoryResponseDTO.setCurrentStock(inventory.getCurrentStock());
        inventoryResponseDTO.setProduct(inventory.getProduct());
        inventoryResponseDTO.setInventoryBatches(inventory.getInventoryBatch());

        return inventoryResponseDTO;
    }

    @Override
    @Transactional
    public InventoryResponseDTO updateInventory(InventoryDTO inventoryDTO, Long id) throws Exception {

        Optional<Inventory> optInventory=inventoryRepository.findById(id);

        if(optInventory.isEmpty()){
            throw new Exception("Inventory Not Found");
        }
        Inventory inventory=optInventory.get();

        if (Objects.nonNull(inventoryDTO.getReorderLevel())){
            inventory.setReorderLevel(inventoryDTO.getReorderLevel());
        }
        if(Objects.nonNull(inventoryDTO.getReorderQuantity())){
            inventory.setReorderQuantity(inventoryDTO.getReorderQuantity());
        }
        if (Objects.nonNull(inventoryDTO.getLocation())){
            inventory.setLocation(inventoryDTO.getLocation());
        }
        if(Objects.nonNull(inventoryDTO.getMinQTY())){
            inventory.setMinQTY(inventoryDTO.getMinQTY());
        }
        if (Objects.nonNull(inventoryDTO.getMaxQTY())){
            inventory.setMaxQTY(inventoryDTO.getMaxQTY());
        }
        if(Objects.nonNull(inventoryDTO.getGST())){
            inventory.setGST(inventoryDTO.getGST());
        }
        if(Objects.nonNull(inventoryDTO.getProductId())){
            Optional<Product> product=productRepository.findById(inventoryDTO.getProductId());
            inventory.setProduct(product.get());
        }
        //Saving the data
        Inventory updatedInventory = inventoryRepository.save(inventory);

        InventoryResponseDTO updatedInventoryDTO = new InventoryResponseDTO();
        updatedInventoryDTO.setReorderLevel(updatedInventory.getReorderQuantity());
        updatedInventoryDTO.setReorderQuantity(updatedInventory.getReorderLevel());
        updatedInventoryDTO.setLocation(updatedInventory.getLocation());
        updatedInventoryDTO.setMinQTY(updatedInventory.getMinQTY());
        updatedInventoryDTO.setMaxQTY(updatedInventory.getMaxQTY());
        updatedInventoryDTO.setGST(updatedInventory.getGST());

        return updatedInventoryDTO;

    }

    @Override
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public Page<Inventory> getAllInventoryPaginated(int page, int size) {
        Pageable pageable=PageRequest.of(page, size);
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public Page<Batch> getBatchesForInventory(Long id, int page, int size) throws Exception {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);


        if (inventory != null) {
            Pageable pageable= PageRequest.of(page,size);
            List<Batch> inventoryBatches = inventory.getInventoryBatch();

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), inventoryBatches.size());

            if (start > end) {
                throw new Exception("Invalid page number");
            }

            List<Batch> paginatedBatches = inventoryBatches.subList(start, end);
            return new PageImpl<>(paginatedBatches, pageable, inventoryBatches.size());
//            List<Batch> inventoryBatches= inventory.getInventoryBatch();
//            List<Batch> inventoryBatches= inventory.getInventoryBatch();
//            return inventoryBatches;
        }else {
            throw new Exception("Not Found");
        }

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


        //Setting the currentStock in Inventory
        inventory.setCurrentStock(req.getQuantityInStock());


        // Add the new batch to the inventory
        inventory.getInventoryBatch().add(batch);


        // Save the inventory (should cascade and save the batch as well if cascade settings are correct)
        inventoryRepository.save(inventory);
        return inventory;
    }

    @Override
    @Transactional
    public List<Purchase> getPurchaseForInventory(Long id) throws Exception {

        List<PurchaseInventory> purchases=purchaseInventoryRepository.findByInventoryId(id);

        if (purchases == null || purchases.isEmpty()) {
            return Collections.emptyList();
        }
        return purchases.stream().map(PurchaseInventory::getPurchase).collect(Collectors.toList());



    }

    @Override
    @Transactional
    public List<Inventory> getExpiredProducts() {

        List<Inventory> expiredProducts=new ArrayList<>();

        List<Inventory> inventories=inventoryRepository.findAll();

//        //Streaming the inventory
//        inventories.stream().filter(inventory -> {
//            //Getting All the batches of inventory
//            List<Batch> inventoryBatch=inventory.getInventoryBatch();
//            //Streaming the batches for expiry
//            inventoryBatch.stream().filter(batch -> {
//                Date expiryDate=batch.getExpiryDate();
//                Date currentDate=new Date();
//                if(currentDate.compareTo(expiryDate)>0){
//
//                }
//            });
//        })

        // Stream through the inventories
        inventories.stream().forEach(inventory -> {
            // Get all batches of the inventory
            List<Batch> inventoryBatch = inventory.getInventoryBatch();

            // Check if any batch is expired
            boolean hasExpiredBatch = inventoryBatch.stream().anyMatch(batch -> {
                LocalDate currentDate = LocalDate.now();
                LocalDate expiryDate = batch.getExpiryDate();
                return expiryDate.isBefore(currentDate) || expiryDate.isEqual(currentDate);
            });

            // If any batch is expired, add the inventory to the expired products list
            if (hasExpiredBatch) {
                expiredProducts.add(inventory);
            }
        });

        return expiredProducts;
    }


    public List<Batch> getBatchesForInventory(Integer inventoryId) {
        Inventory inventory = inventoryRepository.findById(Long.valueOf(inventoryId)).orElse(null);
        if (inventory != null) {
            return inventory.getInventoryBatch();
        }
        return null;
    }












//









}
