package com.JustHealth.Health.Service;


import com.JustHealth.Health.DTO.PurchaseDTO;
import com.JustHealth.Health.DTO.PurchaseProduct;
import com.JustHealth.Health.DTO.PurchaseResponeDTO;
import com.JustHealth.Health.Entity.*;
import com.JustHealth.Health.Repository.*;
import com.JustHealth.Health.Response.ErrorMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PurchaseServiceImpl implements PurchaseService {



    @Autowired
    PurchaseRepository purchaseRepository;


    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    PurchaseInventoryRepository purchaseInventoryRepository;

    @Autowired
    DistributorRepository distributorRepository;


    @Autowired
    DistributorService distributorService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    InventoryLedgerRepository inventoryLedgerRepository;



//    @Override
//    @Transactional
//    public Purchase createPurchase(PurchaseDTO purchaseDTO) throws Exception {
//
//
////        Steps for creating a purchase
////        1.User search from products table
////        2.Add Batch of product(If a batch exists it gets data from there)
//    //        The data which is retreived from the batch(batch string,batchPTR,batchMRP,Quantiy in stock,ExpiryDate)
//    //        if batch doesn't exist create a batch(with giver data)
////        3.The user can add a list of products
////        4.Then the user is required to set Inventory Details
////        5.After successfully completion purchase adds products to inventory
//
//
//        //Creating a Purchase
////        Purchase savedPurchase = new Purchase();
////
////        savedPurchase.setBillDate(purchaseDTO.getBillDate());
////        savedPurchase.setDueDate(purchaseDTO.getDueDate());
////
////        //Getting List of integer for products in purchase with their respective quantity.
////        List<Integer> inventoryIds = purchaseDTO.getInventoryPurchaseProducts();
////        List<Integer> inventoryQTY = purchaseDTO.getInventoryPurchaseProductsQTY();
////
////        if (inventoryIds.size() != inventoryQTY.size()) {
////            throw new RuntimeException("Product IDs and Quantities sizes do not match");
////        }
////
////        List<PurchaseInventory> purchaseInventories = new ArrayList<>();
////        Integer totalItems = 0;
////        Integer totalAmount = 0;
////        Integer baseAmount = 0;
////
////        //Mapping Inventory items with entered quantity to purchaseinventory table.
////        //Or, Adding Product to Inventory and updating batch data
////        for (int i = 0; i < inventoryIds.size(); i++) {
////            Integer id = inventoryIds.get(i);
////            Integer quantity = inventoryQTY.get(i);
////
////            Inventory inventory = inventoryRepository.findById(Long.valueOf(id)).orElseThrow(() -> new Exception("Inventory not found"));
////
////            Integer batchPTR = inventory.getInventoryBatch().getLast().getBatchPTR();
////            Integer batchQtyStock = inventory.getInventoryBatch().getLast().getQuantityInStock();
////            inventory.getInventoryBatch().getLast().setQuantityInStock(batchQtyStock + quantity);
////            Integer GST = inventory.getGST();
////
////            totalItems += quantity;
////            baseAmount = batchPTR * quantity;
////            totalAmount += (baseAmount + (batchPTR * GST / 100));
////
////            PurchaseInventory purchaseInventory = new PurchaseInventory();
////            purchaseInventory.setInventory(inventory);
////            purchaseInventory.setQuantity(quantity);
//////            purchaseInventory.setPurchase(savedPurchase);  // Set the savedPurchase here
////            purchaseInventories.add(purchaseInventory);
////        }
////
////        Distributor distributor=distributorService.findById(purchaseDTO.getPurchaseDistributor());
////
////        savedPurchase.setPurchaseDistributor(distributor);
////        savedPurchase.setPurchaseInventories(purchaseInventories);
////        savedPurchase.setTotalAmount(totalAmount);
////        savedPurchase.setTotalItems(totalItems);
////        savedPurchase.setPurchasePaymentType(Purchase.paymentType.valueOf(purchaseDTO.getPurchasePaymentType()));
////
////        savedPurchase = purchaseRepository.save(savedPurchase); // Save the purchase first to generate an ID
////
////        for (PurchaseInventory purchaseInventory : purchaseInventories) {
////            purchaseInventory.setPurchase(savedPurchase); // Ensure the purchase is set after saving
////            purchaseInventoryRepository.save(purchaseInventory); // Save each PurchaseInventory
////        }
////
////        distributor.getPurchase().add(savedPurchase);
////
////        if (Objects.equals(purchaseDTO.getPurchasePaymentType(), "CREDIT")) {
////            Integer accountBalance = distributor.getDistributorAccountBalance();
////            distributor.setDistributorAccountBalance(savedPurchase.getTotalAmount() + accountBalance);
////        }
////
////        distributorRepository.save(distributor);
////        return savedPurchase;
//        return null;
//    }


    public Integer calculateCurrentQTYinStock(List<Batch> batches){
        //Used to update qty in inventory
        Integer totalQTY = 0;
        for (Batch batch:batches){
            totalQTY+=batch.getQuantityInStock();
        }
        return totalQTY;
    }


    @Override
    @Transactional
    public PurchaseResponeDTO createPurchase(PurchaseDTO purchaseDTO) throws Exception {


//        Steps for creating a purchase
//        1.User search from products table
//        2.Add Batch of product(If a batch exists it gets data from there)
        //        The data which is retreived from the batch(batch string,batchPTR,batchMRP,Quantiy in stock,ExpiryDate)
        //        if batch doesn't exist create a batch(with giver data)
//        3.The user can add a list of products
//        4.Then the user is required to set Inventory Details
//        5.After successfully completion purchase adds products to inventory



        Distributor distributor = distributorService.findById(purchaseDTO.getPurchaseDistributorId());

        // Create and save the Purchase entity first
        Purchase savePurchase = new Purchase();
        savePurchase.setBillDate(purchaseDTO.getBillDate());
        savePurchase.setDueDate(purchaseDTO.getDueDate());
        savePurchase.setBillNo(purchaseDTO.getBillNo());


        savePurchase.setPurchaseDistributor(distributor);

        // Save the Purchase entity first
//        purchaseRepository.save(savePurchase);

        Float totalAmount = 0f;
        Integer totalItems = 0;

        List<PurchaseProduct> getPurchaseProducts = purchaseDTO.getPurchaseInventories();
        List<PurchaseInventory> purchaseInventories=new ArrayList<>();

        for (PurchaseProduct product : getPurchaseProducts) {

            Long productId = product.getProductId();
            String batch = product.getBatch();
            LocalDate expiry = product.getExpiry();
            Float batchMRP = product.getBatchMRP();
            Float batchPTR = product.getBatchPTR();
            Integer productQTY = product.getProductInventoryQTY();
            Integer gotgst = product.getGst();
            Integer discount = product.getDiscount();

            Inventory inventory = inventoryService.getInventoryFromProduct(productId);

            //If the product is not in the inventory add it and so are the batches so directly add batch and save(map) it to purchaseInventory
            if (inventory == null) {
                inventoryService.createInventoryForPurchase(productId);
                inventory = inventoryService.getInventoryFromProduct(productId);
                    //Creating a new Batch
                    Batch newBatch=new Batch();
                    newBatch.setBatch(batch);
                    newBatch.setBatchMRP(batchMRP);
                    newBatch.setBatchPTR(batchPTR);
                    newBatch.setBatchLP(batchPTR+(batchPTR*gotgst/100));
                    newBatch.setExpiryDate(expiry);
                    newBatch.setQuantityInStock(productQTY);
                    batchRepository.save(newBatch);
                List<Batch> batches=new ArrayList<>();
                batches.add(newBatch);

                inventory.setInventoryBatch(batches);
                inventory.setProduct(inventory.getProduct());
                inventory.setCurrentStock(productQTY);
                inventory.setInventoryBatch(batches);
                inventory.setGST(gotgst);

                inventoryRepository.save(inventory);

                PurchaseInventory purchaseInventory = new PurchaseInventory();
                purchaseInventory.setInventory(inventory);
//                purchaseInventory.setProductInventory(inventory.getProduct());
                purchaseInventory.setQuantity(productQTY);
                purchaseInventories.add(purchaseInventory);

                totalItems += productQTY;
                totalAmount += batchPTR * productQTY;
                InventoryLedger inventoryLedger=new InventoryLedger();
                inventoryLedger.setEntryDate(LocalDateTime.now());
                inventoryLedger.setInventoryIn(productQTY);
                inventoryLedger.setInventoryOut(0);
                inventoryLedger.setClosing(productQTY);
                inventoryLedgerRepository.save(inventoryLedger);
            }else {

                //If inventory exist get inventoryBatches
                List<Batch> inventoryBatches = inventory.getInventoryBatch();

                //If inventoryBatch doesnt exist add a new batch and map it to inventory and purchaseInventory
                if (inventoryBatches == null || inventoryBatches.isEmpty()) {
                    Batch newBatch = new Batch();
                    newBatch.setBatch(batch);
                    newBatch.setBatchMRP(batchMRP);
                    newBatch.setBatchPTR(batchPTR);
                    newBatch.setExpiryDate(expiry);
                    newBatch.setQuantityInStock(productQTY);
                    newBatch.setBatchLP(batchPTR + (batchPTR * inventory.getGST() / 100));
                    batchRepository.save(newBatch);
                    List<Batch> addedBatch = new ArrayList<>();
                    addedBatch.add(newBatch);
                    inventory.setInventoryBatch(addedBatch);
                    inventory.setCurrentStock(productQTY);
                    inventory.setInventoryBatch(addedBatch);
                    inventoryRepository.save(inventory);
                    //Map it into purchase Inventory
                    PurchaseInventory purchaseInventory = new PurchaseInventory();
                    //                purchaseInventory.setPurchase(savePurchase);  // Save reference to already persisted Purchase entity
                    purchaseInventory.setInventory(inventory);
                    //                purchaseInventory.setProductInventory(inventory.getProduct());
                    purchaseInventory.setQuantity(productQTY);
                    //                purchaseInventoryRepository.save(purchaseInventory);
                    purchaseInventories.add(purchaseInventory);

                    InventoryLedger inventoryLedger = new InventoryLedger();
                    inventoryLedger.setEntryDate(LocalDateTime.now());
                    inventoryLedger.setInventoryIn(productQTY);
                    inventoryLedger.setInventoryOut(0);
                    inventoryLedger.setClosing(productQTY);
                    inventoryLedgerRepository.save(inventoryLedger);
                    totalItems += productQTY;
                    totalAmount += batchPTR * productQTY;
                    break;

                }

                //If the batch is already existing in inventory
                Batch matchingBatch = inventory.getInventoryBatch().stream()
                        .filter(b -> b.getBatch().equals(batch))
                        .findFirst()
                        .orElse(null);

                if (matchingBatch != null) {
                    matchingBatch.setBatchMRP(batchMRP);
                    matchingBatch.setBatchPTR(batchPTR);
                    matchingBatch.setExpiryDate(expiry);
                    matchingBatch.setQuantityInStock(matchingBatch.getQuantityInStock() + productQTY);
                    matchingBatch.setBatchLP(batchPTR + (batchPTR * gotgst / 100));
                    batchRepository.save(matchingBatch);
//                    List<Batch> batches = new ArrayList<>();
//                    batches.add(matchingBatch);
//                    inventory.setInventoryBatch(batches);
                    Integer currentStock = calculateCurrentQTYinStock(inventoryBatches);
                    inventory.setCurrentStock(currentStock);
                    inventoryRepository.save(inventory);
                    // Now save PurchaseInventory
                    PurchaseInventory purchaseInventory = new PurchaseInventory();
                    //                purchaseInventory.setPurchase(savePurchase);  // Save reference to already persisted Purchase entity
                    purchaseInventory.setInventory(inventory);
                    //                purchaseInventory.setProductInventory(inventory.getProduct());
                    purchaseInventory.setQuantity(productQTY);
                    //                purchaseInventoryRepository.save(purchaseInventory);
                    purchaseInventories.add(purchaseInventory);
                    totalItems += productQTY;
                    totalAmount += batchPTR * productQTY;

                } else {
                    Batch newBatch = new Batch();
                    newBatch.setBatch(batch);
                    newBatch.setBatchMRP(batchMRP);
                    newBatch.setBatchPTR(batchPTR);
                    newBatch.setExpiryDate(expiry);
                    newBatch.setQuantityInStock(productQTY);
                    batchRepository.save(newBatch);
//                    List<Batch> batches = new ArrayList<>();
//                    batches.add(newBatch);
                    inventoryBatches.add(newBatch);
                    inventory.setInventoryBatch(inventoryBatches);
                    Integer currentStock = calculateCurrentQTYinStock(inventoryBatches);
                    inventory.setCurrentStock(currentStock);


                    inventoryRepository.save(inventory);

                    PurchaseInventory purchaseInventory = new PurchaseInventory();
                    //                purchaseInventory.setPurchase(savePurchase);  // Save reference to already persisted Purchase entity
                    purchaseInventory.setInventory(inventory);
                    //                purchaseInventory.setProductInventory(inventory.getProduct());
                    purchaseInventory.setQuantity(productQTY);
                    //                purchaseInventoryRepository.save(purchaseInventory);
                    purchaseInventories.add(purchaseInventory);
                    totalItems += productQTY;
                    totalAmount += batchPTR * productQTY;
                }
            }
        }

//        Purchase purchase=new Purchase();
        savePurchase.setTotalAmount(totalAmount);
        savePurchase.setTotalItems(totalItems);
        savePurchase.setPurchaseInventories(purchaseInventories);
        purchaseRepository.save(savePurchase);

        for (PurchaseInventory purchaseInventory:purchaseInventories){
            purchaseInventory.setPurchase(savePurchase);
            purchaseInventoryRepository.save(purchaseInventory);
        }

        PurchaseResponeDTO purchaseResponeDTO = new PurchaseResponeDTO();
        purchaseResponeDTO.setBillNo(savePurchase.getBillNo());
        purchaseResponeDTO.setBillDate(savePurchase.getBillDate());
        purchaseResponeDTO.setDueDate(savePurchase.getDueDate());
        purchaseResponeDTO.setPurchaseProducts(getPurchaseProducts);
        purchaseResponeDTO.setPurchaseDistributor(distributor);
        purchaseResponeDTO.setPurchasePaymentType(savePurchase.getPurchasePaymentType());
        purchaseResponeDTO.setTotalAmount(totalAmount);
        purchaseResponeDTO.setTotalItems(totalItems);

        return purchaseResponeDTO;










//        Purchase savePurchase=new Purchase();
//        savePurchase.setBillDate(purchaseDTO.getBillDate());
//        savePurchase.setDueDate(purchaseDTO.getDueDate());
//        savePurchase.setBillNo(purchaseDTO.getBillNo());
//
//        Float totalAmount= (float) 0;
//        Integer totalItems=0;
//        //Map the Data to respective fields
//        List<PurchaseProduct> getPurchaseProducts=purchaseDTO.getPurchaseInventories();

        //Looping the received data for entry in Purchase
//        for(PurchaseProduct product:getPurchaseProducts){
//
//            Long productId=product.getProductId();
//            String batch=product.getBatch();
//            LocalDate expiry=product.getExpiry();
//            Float batchMRP=product.getBatchMRP();
//            Float batchPTR=product.getBatchPTR();
//            Integer productQTY=product.getProductInventoryQTY();
//            Integer gotgst =product.getGst();
//            //Discount is in
//            Integer discount=product.getDiscount();
//
//            Inventory inventory = inventoryService.getInventoryFromProduct(productId);
//            //If productId is linked to some Inventory
//
//
//
//
//            //If product is already present in inventory update it
////            if(inventoryService.getInventoryFromProduct(productId)!=null){
////                Inventory inventory=inventoryService.getInventoryFromProduct(productId);
////                Product getProduct= inventory.getProduct();
////                List<Batch> batches=inventory.getInventoryBatch();
////                // Check if there's an exact match and update the existing batch if found
////                boolean isExactMatch = batches.stream().anyMatch(batch1 -> {
////                    if (batch1.getBatch().equals(batch)) {
////                        //Check if user has updated values in the field and if
////                        batch1.setBatchMRP(batchMRP);
////                        batch1.setBatchPTR(batchPTR);
////                        batch1.setExpiryDate(expiry);
////                        batch1.setQuantityInStock(batch1.getQuantityInStock()+productQTY);
////                        Integer gst = inventory.getGST();
////                        batch1.setBatchLP(batchPTR + (batchPTR * gst / 100));
////                        batchRepository.save(batch1);
////                        //Storing the data in purchaseInventory for reference to Purchase.
////                        PurchaseInventory purchaseInventory=new PurchaseInventory();
////                        purchaseInventory.setPurchase(savePurchase);
////                        purchaseInventory.setInventory(inventory);
////                        purchaseInventory.setProductInventory(getProduct);
////                        purchaseInventory.setQuantity(productQTY);
////                        purchaseInventoryRepository.save(purchaseInventory);
////                        return true;
////
////                    }
////                    return false;
////                });
////                //If there is no exact match create a new Batch and add it to inventory
////                if(!isExactMatch) {
////                    List<Batch> newBatch=new ArrayList<>();
////                    Batch createBatchForInventory = new Batch();
////                    createBatchForInventory.setBatch(batch);
////                    createBatchForInventory.setBatchPTR(batchPTR);
////                    createBatchForInventory.setBatchMRP(batchMRP);
////                    Integer gst = inventory.getGST();
////                    createBatchForInventory.setBatchLP(batchPTR + (batchPTR * gst / 100));
////                    createBatchForInventory.setQuantityInStock(productQTY);
////                    batchRepository.save(createBatchForInventory);
////                    newBatch.add(createBatchForInventory);
////                    inventory.setInventoryBatch(newBatch);
////                    inventoryRepository.save(inventory);
////                    PurchaseInventory purchaseInventory=new PurchaseInventory();
////                    purchaseInventory.setPurchase(savePurchase);
////                    purchaseInventory.setInventory(inventory);
////                    purchaseInventory.setProductInventory(getProduct);
////                    purchaseInventory.setQuantity(productQTY);
////                    purchaseInventoryRepository.save(purchaseInventory);
////                }
////
////            }
//
//            //create a new product in inventory if it doesn't exist
//            inventoryService.createInventoryForPurchase(productId);
//            List<Batch> newBatch=new ArrayList<>();
//            Inventory inventory=inventoryService.getInventoryFromProduct(productId);
//            inventory.setGST(gotgst);
//            //Creating a new Batch for Inventory
//                Batch NewBatchForInventory = new Batch();
//                NewBatchForInventory.setBatch(batch);
//                NewBatchForInventory.setBatchPTR(batchPTR);
//                NewBatchForInventory.setBatchMRP(batchMRP);
//                Integer gst = inventory.getGST();
//                NewBatchForInventory.setBatchLP(batchPTR + (batchPTR * gst / 100));
//                NewBatchForInventory.setQuantityInStock(productQTY);
//                batchRepository.save(NewBatchForInventory);
//            newBatch.add(NewBatchForInventory);
//            inventory.setInventoryBatch(newBatch);
//
//            inventoryRepository.save(inventory);
//
//
//
//
//
//
//        }





//
//
//        for(PurchaseProduct product : getPurchaseProducts) {
//            Long productId = product.getProductId();
//            String batch = product.getBatch();
//            LocalDate expiry = product.getExpiry();
//            Float batchMRP = product.getBatchMRP();
//            Float batchPTR = product.getBatchPTR();
//            Integer productQTY = product.getProductInventoryQTY();
//            Integer gotgst = product.getGst();
//            Integer discount = product.getDiscount();
//
//            Inventory inventory = inventoryService.getInventoryFromProduct(productId);
//            if (inventory == null) {
//                inventoryService.createInventoryForPurchase(productId);
//                inventory = inventoryService.getInventoryFromProduct(productId);
//            }
//
//            Batch matchingBatch = inventory.getInventoryBatch().stream()
//                    .filter(b -> b.getBatch().equals(batch))
//                    .findFirst()
//                    .orElse(null);
//
//            if (matchingBatch != null) {
//                matchingBatch.setBatchMRP(batchMRP);
//                matchingBatch.setBatchPTR(batchPTR);
//                matchingBatch.setExpiryDate(expiry);
//                matchingBatch.setQuantityInStock(matchingBatch.getQuantityInStock() + productQTY);
//                matchingBatch.setBatchLP(batchPTR + (batchPTR * inventory.getGST() / 100));
//                batchRepository.save(matchingBatch);
//            } else {
//                Batch newBatch = new Batch();
//                newBatch.setBatch(batch);
//                newBatch.setBatchMRP(batchMRP);
//                newBatch.setBatchPTR(batchPTR);
//                newBatch.setExpiryDate(expiry);
//                newBatch.setQuantityInStock(productQTY);
//                newBatch.setBatchLP(batchPTR + (batchPTR * inventory.getGST() / 100));
//                batchRepository.save(newBatch);
//                inventory.getInventoryBatch().add(newBatch);
//            }
//
//            inventoryRepository.save(inventory);
//
//            PurchaseInventory purchaseInventory = new PurchaseInventory();
//            purchaseInventory.setPurchase(savePurchase);
//            purchaseInventory.setInventory(inventory);
//            purchaseInventory.setProductInventory(inventory.getProduct());
//            purchaseInventory.setQuantity(productQTY);
//            purchaseInventoryRepository.save(purchaseInventory);
//
//            totalItems += productQTY;
//            totalAmount += batchPTR * productQTY;  // Update this calculation as per your business logic
//        }
//
//
//
//
//        Distributor distributor=distributorService.findById(purchaseDTO.getPurchaseDistributor());
//        savePurchase.setPurchaseDistributor(distributor);
//        purchaseRepository.save(savePurchase);
//        PurchaseResponeDTO purchaseResponeDTO=new PurchaseResponeDTO();
//        purchaseResponeDTO.setBillNo(savePurchase.getBillNo());
//        purchaseResponeDTO.setBillDate(savePurchase.getBillDate());
//        purchaseResponeDTO.setDueDate(savePurchase.getDueDate());
//        purchaseResponeDTO.setPurchaseProducts(getPurchaseProducts);
//        purchaseResponeDTO.setPurchaseDistributor(distributor);
//        purchaseResponeDTO.setPurchasePaymentType(savePurchase.getPurchasePaymentType());
//        return purchaseResponeDTO;


        










        //Creating a Purchase
//        Purchase savedPurchase = new Purchase();
//
//        savedPurchase.setBillDate(purchaseDTO.getBillDate());
//        savedPurchase.setDueDate(purchaseDTO.getDueDate());
//
//        //Getting List of integer for products in purchase with their respective quantity.
//        List<Integer> inventoryIds = purchaseDTO.getInventoryPurchaseProducts();
//        List<Integer> inventoryQTY = purchaseDTO.getInventoryPurchaseProductsQTY();
//
//        if (inventoryIds.size() != inventoryQTY.size()) {
//            throw new RuntimeException("Product IDs and Quantities sizes do not match");
//        }
//
//        List<PurchaseInventory> purchaseInventories = new ArrayList<>();
//        Integer totalItems = 0;
//        Integer totalAmount = 0;
//        Integer baseAmount = 0;
//
//        //Mapping Inventory items with entered quantity to purchaseinventory table.
//        //Or, Adding Product to Inventory and updating batch data
//        for (int i = 0; i < inventoryIds.size(); i++) {
//            Integer id = inventoryIds.get(i);
//            Integer quantity = inventoryQTY.get(i);
//
//            Inventory inventory = inventoryRepository.findById(Long.valueOf(id)).orElseThrow(() -> new Exception("Inventory not found"));
//
//            Integer batchPTR = inventory.getInventoryBatch().getLast().getBatchPTR();
//            Integer batchQtyStock = inventory.getInventoryBatch().getLast().getQuantityInStock();
//            inventory.getInventoryBatch().getLast().setQuantityInStock(batchQtyStock + quantity);
//            Integer GST = inventory.getGST();
//
//            totalItems += quantity;
//            baseAmount = batchPTR * quantity;
//            totalAmount += (baseAmount + (batchPTR * GST / 100));
//
//            PurchaseInventory purchaseInventory = new PurchaseInventory();
//            purchaseInventory.setInventory(inventory);
//            purchaseInventory.setQuantity(quantity);
////            purchaseInventory.setPurchase(savedPurchase);  // Set the savedPurchase here
//            purchaseInventories.add(purchaseInventory);
//        }
//
//        Distributor distributor=distributorService.findById(purchaseDTO.getPurchaseDistributor());
//
//        savedPurchase.setPurchaseDistributor(distributor);
//        savedPurchase.setPurchaseInventories(purchaseInventories);
//        savedPurchase.setTotalAmount(totalAmount);
//        savedPurchase.setTotalItems(totalItems);
//        savedPurchase.setPurchasePaymentType(Purchase.paymentType.valueOf(purchaseDTO.getPurchasePaymentType()));
//
//        savedPurchase = purchaseRepository.save(savedPurchase); // Save the purchase first to generate an ID
//
//        for (PurchaseInventory purchaseInventory : purchaseInventories) {
//            purchaseInventory.setPurchase(savedPurchase); // Ensure the purchase is set after saving
//            purchaseInventoryRepository.save(purchaseInventory); // Save each PurchaseInventory
//        }
//
//        distributor.getPurchase().add(savedPurchase);
//
//        if (Objects.equals(purchaseDTO.getPurchasePaymentType(), "CREDIT")) {
//            Integer accountBalance = distributor.getDistributorAccountBalance();
//            distributor.setDistributorAccountBalance(savedPurchase.getTotalAmount() + accountBalance);
//        }
//
//        distributorRepository.save(distributor);
//        return savedPurchase;
    }

    @Override
    @Transactional
    public Page<Purchase> findAllPurchasePaginated(int page,int size) throws Exception {
        Pageable pageable= PageRequest.of(page, size);
        return purchaseRepository.findAll(pageable);
    }


    @Override
    public List<Purchase> findAllPurchase() throws Exception {
        try {
            return purchaseRepository.findAll();
        } catch (Exception e) {

            throw new RuntimeException("Failed to fetch purchases", e);
        }
    }

    @Override
    public Purchase purchaseById(Long id) throws Exception {
        Optional<Purchase> purchase=purchaseRepository.findById(id);

        if(purchase.isEmpty()){
            throw new Exception("ID doesn't exist");
        }
        return purchase.get();

    }

    @Override
    @Transactional
    public void deletePurchaseById(Long id) throws Exception {

        try {
            // Fetch the purchase by ID
            Purchase purchase = purchaseById(id);

            if (purchase != null) {
                // Check and clear associated PurchaseInventory records
                List<PurchaseInventory> inventories = purchase.getPurchaseInventories();
                if (inventories != null && !inventories.isEmpty()) {
                    inventories.clear();
                    // Ensure that the clearing of associated PurchaseInventory records is persisted
                    purchaseRepository.save(purchase);
                }
                Distributor distributor=purchase.getPurchaseDistributor();
                distributor.getPurchase().remove(purchase);

                // Delete the purchase
                purchaseRepository.delete(purchase);
                purchaseRepository.flush(); // Ensure changes are flushed to the database
            } else {
                throw new Exception("Purchase not found with id: " + id);
            }
        } catch (Exception e) {
            // Log the exception with more details
            System.err.println("Failed to delete purchase with id: " + id);
            // Optionally, rethrow the exception
            throw new Exception("Failed to delete purchase with id: " + id, e);
        }
//        try {
//            // Fetch the purchase by ID
//            Purchase purchase = purchaseById(id);
//            if (purchase != null) {
//                // Remove associated PurchaseInventory records manually if necessary
//                List<PurchaseInventory> inventories = purchase.getPurchaseInventories();
//                if (inventories != null && !inventories.isEmpty()) {
//                    purchase.getPurchaseInventories().clear();
//                }
//
//                // Delete the purchase
//                purchaseRepository.delete(purchase);
//            } else {
//                throw new Exception("Purchase not found with id: " + id);
//            }
//        } catch (Exception e) {
//            // Log the exception with more details
//            e.printStackTrace();
//            // Optionally, rethrow the exception
//            throw new Exception("Failed to delete purchase with id: " + id, e);
//        }
//        try {
//            Purchase purchase = purchaseById(id);
//            if (purchase != null) {
//                purchaseRepository.delete(purchase);
//            } else {
//                throw new Exception("Purchase not found with id: " + id);
//            }
//        } catch (Exception e) {
//            // Log the exception or handle it as per your requirement
//            System.err.println("Error occurred while deleting purchase: " + e.getMessage());
//            // Optionally, rethrow the exception to propagate it further
//            throw new Exception("Failed to delete purchase with id: " + id, e);
//        }




    }

    @Override
    public List<PurchaseInventory> getPurchaseInventoryByPurchaseId(Long id) throws Exception {
        Optional<Purchase> purchase=purchaseRepository.findById(id);
        if(purchase.isEmpty()){
            throw new Exception("Cant find Purchase");
        }
        List<PurchaseInventory> inventories=purchase.get().getPurchaseInventories();
        return inventories;

    }

    @Override
    public Purchase updatePurchaseById(PurchaseDTO purchaseDTO, Long id) throws Exception {

        Purchase updatedPurchase =purchaseById(id);

        if(purchaseDTO.getBillDate() != null){
            updatedPurchase.setBillDate(purchaseDTO.getBillDate());
        }
        if(purchaseDTO.getDueDate() != null){
            updatedPurchase.setBillDate(purchaseDTO.getDueDate());
        }
//        if(purchaseDTO.getInventoryPurchaseProducts()!=null){
////            purchaseDTO.getInventoryPurchaseProducts().st
//        }



        return updatedPurchase;
    }
}
