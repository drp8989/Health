package com.JustHealth.Health.Service;


import com.JustHealth.Health.DTO.PurchaseDTO;
import com.JustHealth.Health.Entity.Distributor;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Entity.PurchaseInventory;
import com.JustHealth.Health.Repository.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


//    @Override
//    @Transactional
//    public Purchase createPurchase(PurchaseDTO purchaseDTO) throws Exception {
//
//        Purchase savedPurchase = new Purchase();
//
//        savedPurchase.setBillDate(purchaseDTO.getBillDate());
//        savedPurchase.setDueDate(purchaseDTO.getDueDate());
//
//        List<Integer> inventoryIds = purchaseDTO.getInventoryPurchaseProducts();
//        List<Integer> inventoryQTY = purchaseDTO.getInventoryPurchaseProductsQTY();
//
//        List<Purchase> purchases=new ArrayList<>();
//        Integer totalItems=0;
//        Integer totalAmount=0;
//        Integer baseAmount=0;
//
//
//        if (inventoryIds.size() != inventoryQTY.size()) {
//            throw new RuntimeException("Product IDs and Quantities sizes do not match");
//        }
//
//        List<PurchaseInventory> purchaseInventories = new ArrayList<>();
//        List<Inventory> inventories=new ArrayList<>();
//
//        for (int i = 0; i < inventoryIds.size(); i++) {
//            Integer id = inventoryIds.get(i);
//            Integer quantity = inventoryQTY.get(i);
//
//            Inventory inventory = inventoryRepository.findById(Long.valueOf(id)).orElse(null);
//            inventories.add(inventory);
//
//            if (inventory != null) {
//
//                Integer batchPTR=inventory.getInventoryBatch().getLast().getBatchPTR();
//                Integer batchQtyStock=inventory.getInventoryBatch().getLast().getQuantityInStock();
//                inventory.getInventoryBatch().getLast().setQuantityInStock(batchQtyStock+quantity);
//                Integer GST=inventory.getGST();
//
//                totalItems=totalItems+quantity;
//                baseAmount=batchPTR*quantity;
//                totalAmount=totalAmount+(baseAmount+(batchPTR*GST/100));
//
//                PurchaseInventory purchaseInventory = new PurchaseInventory();
//                purchaseInventory.setInventory(inventory);
//                purchaseInventory.setQuantity(quantity);
//                purchaseInventory.setPurchase(savedPurchase);
//                purchaseInventories.add(purchaseInventory);
//                purchaseInventoryRepository.save(purchaseInventory);
//            }
//        }
//        Optional<Distributor> distributor=distributorRepository.findById(Long.valueOf(purchaseDTO.getPurchaseDistributor()));
//        if(distributor.isEmpty()){
//            throw new Exception("Distributor not found");
//        }
//
//        savedPurchase.setPurchaseDistributor(distributor.get());
//        savedPurchase.setPurchaseInventories(purchaseInventories);
//        savedPurchase.setTotalAmount(totalAmount);
//        savedPurchase.setTotalItems(totalItems);
//        savedPurchase.setPurchasePaymentType(Purchase.paymentType.valueOf(purchaseDTO.getPurchasePaymentType()));
//
//        purchaseRepository.save(savedPurchase);
//
//        purchases.add(savedPurchase);
//
//        distributor.get().getPurchase().add(savedPurchase);
//
//
//
//        if(Objects.equals(purchaseDTO.getPurchasePaymentType(), "CREDIT")){
//            Integer accountBalance=distributor.get().getDistributorAccountBalance();
//            distributor.get().setDistributorAccountBalance(savedPurchase.getTotalAmount()+accountBalance);
//
//        }
//        distributorRepository.save(distributor.get());
//        return savedPurchase;
//
//
//    }




    @Override
    @Transactional
    public Purchase createPurchase(PurchaseDTO purchaseDTO) throws Exception {



        Purchase savedPurchase = new Purchase();

        savedPurchase.setBillDate(purchaseDTO.getBillDate());
        savedPurchase.setDueDate(purchaseDTO.getDueDate());

        //Getting List of integer for products in purchase with their respective quantity.
        List<Integer> inventoryIds = purchaseDTO.getInventoryPurchaseProducts();
        List<Integer> inventoryQTY = purchaseDTO.getInventoryPurchaseProductsQTY();

        if (inventoryIds.size() != inventoryQTY.size()) {
            throw new RuntimeException("Product IDs and Quantities sizes do not match");
        }

        List<PurchaseInventory> purchaseInventories = new ArrayList<>();
        Integer totalItems = 0;
        Integer totalAmount = 0;
        Integer baseAmount = 0;

        //Mapping Inventory items with entered quantity to purchaseinventory table.
        //Or, Adding Product to Inventory and updating batch data
        for (int i = 0; i < inventoryIds.size(); i++) {
            Integer id = inventoryIds.get(i);
            Integer quantity = inventoryQTY.get(i);

            Inventory inventory = inventoryRepository.findById(Long.valueOf(id)).orElseThrow(() -> new Exception("Inventory not found"));

            Integer batchPTR = inventory.getInventoryBatch().getLast().getBatchPTR();
            Integer batchQtyStock = inventory.getInventoryBatch().getLast().getQuantityInStock();
            inventory.getInventoryBatch().getLast().setQuantityInStock(batchQtyStock + quantity);
            Integer GST = inventory.getGST();

            totalItems += quantity;
            baseAmount = batchPTR * quantity;
            totalAmount += (baseAmount + (batchPTR * GST / 100));

            PurchaseInventory purchaseInventory = new PurchaseInventory();
            purchaseInventory.setInventory(inventory);
            purchaseInventory.setQuantity(quantity);
//            purchaseInventory.setPurchase(savedPurchase);  // Set the savedPurchase here
            purchaseInventories.add(purchaseInventory);
        }

        Distributor distributor=distributorService.findById(purchaseDTO.getPurchaseDistributor());

        savedPurchase.setPurchaseDistributor(distributor);
        savedPurchase.setPurchaseInventories(purchaseInventories);
        savedPurchase.setTotalAmount(totalAmount);
        savedPurchase.setTotalItems(totalItems);
        savedPurchase.setPurchasePaymentType(Purchase.paymentType.valueOf(purchaseDTO.getPurchasePaymentType()));

        savedPurchase = purchaseRepository.save(savedPurchase); // Save the purchase first to generate an ID

        for (PurchaseInventory purchaseInventory : purchaseInventories) {
            purchaseInventory.setPurchase(savedPurchase); // Ensure the purchase is set after saving
            purchaseInventoryRepository.save(purchaseInventory); // Save each PurchaseInventory
        }

        distributor.getPurchase().add(savedPurchase);

        if (Objects.equals(purchaseDTO.getPurchasePaymentType(), "CREDIT")) {
            Integer accountBalance = distributor.getDistributorAccountBalance();
            distributor.setDistributorAccountBalance(savedPurchase.getTotalAmount() + accountBalance);
        }

        distributorRepository.save(distributor);
        return savedPurchase;
    }

    @Override
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
    public void deletePurchaseById(Long id) throws Exception {

        purchaseRepository.deleteById(id);


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
        if(purchaseDTO.getInventoryPurchaseProducts()!=null){
//            purchaseDTO.getInventoryPurchaseProducts().st
        }



        return updatedPurchase;
    }
}
