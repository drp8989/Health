package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.PurchaseDTO;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Entity.PurchaseInventory;
import com.JustHealth.Health.Repository.BatchRepository;
import com.JustHealth.Health.Repository.InventoryRepository;
import com.JustHealth.Health.Repository.PurchaseInventoryRepository;
import com.JustHealth.Health.Repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private PurchaseInventoryRepository purchaseInventoryRepository;


    @Override
    @Transactional
    public Purchase createPurchase(PurchaseDTO purchaseDTO) {

        Purchase savedPurchase = new Purchase();

        savedPurchase.setBillDate(purchaseDTO.getBillDate());
        savedPurchase.setDueDate(purchaseDTO.getDueDate());

        List<Integer> inventoryIds = purchaseDTO.getInventoryPurchaseProducts();
        List<Integer> inventoryQTY = purchaseDTO.getInventoryPurchaseProductsQTY();

        Integer totalItems=0;
        Integer totalAmount=0;
        Integer baseAmount=0;


        if (inventoryIds.size() != inventoryQTY.size()) {
            throw new RuntimeException("Product IDs and Quantities sizes do not match");
        }

        List<PurchaseInventory> purchaseInventories = new ArrayList<>();
        List<Inventory> inventories=new ArrayList<>();

        for (int i = 0; i < inventoryIds.size(); i++) {
            Integer id = inventoryIds.get(i);
            Integer quantity = inventoryQTY.get(i);

            Inventory inventory = inventoryRepository.findById(Long.valueOf(id)).orElse(null);
            inventories.add(inventory);

            if (inventory != null) {

                Integer batchPTR=inventory.getInventoryBatch().getLast().getBatchPTR();
                Integer batchQtyStock=inventory.getInventoryBatch().getLast().getQuantityInStock();
                inventory.getInventoryBatch().getLast().setQuantityInStock(batchQtyStock+quantity);
                Integer GST=inventory.getGST();

                totalItems=totalItems+quantity;
                baseAmount=batchPTR*quantity;
                totalAmount=totalAmount+(baseAmount+(batchPTR*GST/100));

                PurchaseInventory purchaseInventory = new PurchaseInventory();
                purchaseInventory.setInventory(inventory);
                purchaseInventory.setQuantity(quantity);
                purchaseInventory.setPurchase(savedPurchase);
                purchaseInventories.add(purchaseInventory);
                purchaseInventoryRepository.save(purchaseInventory);
            }
        }

        savedPurchase.setPurchaseInventories(purchaseInventories);
        savedPurchase.setTotalAmount(totalAmount);
        savedPurchase.setTotalItems(totalItems);
        savedPurchase.setPurchasePaymentType(Purchase.paymentType.valueOf(purchaseDTO.getPurchasePaymentType()));
        purchaseRepository.save(savedPurchase);


//        purchaseRepository.save(savedPurchase);
//
//        for (int i=0;i<inventories.size();i++){
//            PurchaseInventory purchaseInventory=new PurchaseInventory();
////            purchaseInventory.setPurchase(savedPurchase);
//            purchaseInventory.setInventory(inventories.get(i));
//            purchaseInventory.setQuantity(inventoryQTY.get(i));
//            purchaseInventoryRepository.save(purchaseInventory);
//            purchaseInventories.add(purchaseInventory);
//        }
//        savedPurchase.setPurchaseInventories(purchaseInventories);
//        purchaseRepository.save(savedPurchase);

        return savedPurchase;

//
//        Purchase savedPurchase = new Purchase();
//        savedPurchase.setBillDate(purchaseDTO.getBillDate());
//        savedPurchase.setDueDate(purchaseDTO.getDueDate());
//
//        List<Integer> inventoryIds = purchaseDTO.getInventoryPurchaseProducts();
//        List<Integer> inventoryQTY = purchaseDTO.getInventoryPurchaseProductsQTY();
//
//        if (inventoryIds.size() != inventoryQTY.size()) {
//            throw new RuntimeException("Product IDs and Quantities sizes do not match");
//        }
//
//        int totalItems = 0;
//        int totalAmount = 0;
//
//        List<PurchaseInventory> purchaseInventories = new ArrayList<>();
//
//        for (int i = 0; i < inventoryIds.size(); i++) {
//            Integer id = inventoryIds.get(i);
//            Integer quantity = inventoryQTY.get(i);
//
//            Inventory inventory = inventoryRepository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException("Inventory not found"));
//
//            Integer batchPTR = inventory.getInventoryBatch().getLast().getBatchPTR();
//            Integer batchQtyStock = inventory.getInventoryBatch().getLast().getQuantityInStock();
//            inventory.getInventoryBatch().getLast().setQuantityInStock(batchQtyStock + quantity);
//            Integer GST = inventory.getGST();
//
//            totalItems += quantity;
//            int baseAmount = batchPTR * quantity;
//            totalAmount += baseAmount + (baseAmount * GST / 100);
//
//            PurchaseInventory purchaseInventory = new PurchaseInventory();
//            purchaseInventory.setInventory(inventory);
//            purchaseInventory.setQuantity(quantity);
//            purchaseInventory.setPurchase(savedPurchase);
//            purchaseInventories.add(purchaseInventory);
//        }
//
//        savedPurchase.setTotalAmount(totalAmount);
//        savedPurchase.setTotalItems(totalItems);
//        savedPurchase.setPurchasePaymentType(Purchase.paymentType.valueOf(purchaseDTO.getPurchasePaymentType()));
//        savedPurchase.setPurchaseInventories(purchaseInventories);
//
//        purchaseRepository.save(savedPurchase);
//        purchaseInventoryRepository.saveAll(purchaseInventories); // Save all purchase inventories in one go
//
//        return savedPurchase;



//        purchaseRepository.save(savedPurchase);
//
//        for(PurchaseInventory purchaseInventory: purchaseInventories){
//            PurchaseInventory purchaseInventory1=new PurchaseInventory();
//
//            purchaseInventoryRepository.save(purchaseInventory1);
//        };



//        Integer total = 0;
//        Integer items = 0;
//
//        Map<Integer, Integer> map = new HashMap<>();
//        List<Integer> productIds = purchaseDTO.getInventoryPurchaseProducts();
//        List<Integer> productQTY = purchaseDTO.getInventoryPurchaseProductsQTY();
//
//        if (productIds.size() != productQTY.size()) {
//            throw new RuntimeException("Product IDs and Quantities sizes do not match");
//        }
//
//        for (int i = 0; i < productIds.size(); i++) {
//            map.put(productIds.get(i), productQTY.get(i));
//        }
//
//        List<PurchaseInventory> purchaseInventories = new ArrayList<>();
//
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            Inventory inventory = inventoryRepository.findByProductId(entry.getKey());
//            Batch batch = inventory.getInventoryBatch().getLast();
//            Integer ptr = batch.getBatchPTR();
//            Integer qty = entry.getValue();
//            items += qty;
//            Integer baseValue = ptr * qty;
//            total += baseValue;
//
//            PurchaseInventory purchaseInventory = new PurchaseInventory();
//            purchaseInventory.setInventory(inventory);
//            purchaseInventory.setQuantity(qty);
//            purchaseInventory.setPurchase(purchase);
//            purchaseInventories.add(purchaseInventory);
//
//            // Save purchaseInventory
//            purchaseInventoryRepository.save(purchaseInventory);
//        }
//
//        purchase.setTotalAmount(total);
//        purchase.setTotalItems(items);
//        purchase.setPurchasePaymentType(Purchase.paymentType.valueOf(purchaseDTO.getPurchasePaymentType()));
//
//        purchase = purchaseRepository.save(purchase); // Save purchase and get managed entity
//
//        // Set purchaseInventories in purchase
//        purchase.setPurchaseInventories(purchaseInventories);

//        return purchaseRepository.save(purchase);
    }

    @Override
    @Transactional()
    public List<Purchase> findAllPurchase() throws Exception {
        try {
            System.out.println(purchaseRepository.findAll());
            return purchaseRepository.findAll();
        } catch (Exception e) {

            throw new RuntimeException("Failed to fetch purchases", e);
        }
    }

    @Override
    public Optional<Purchase> findPurchaseById(Long id) throws Exception {
        Optional<Purchase> purchase=purchaseRepository.findById(id);

        if(purchase.isEmpty()){
            throw new Exception("ID doesn't exist");
        }
        return purchase;

    }

    @Override
    public void deletePurchaseById(Long id) throws Exception {

        purchaseRepository.deleteById(id);


    }

    @Override
    public Purchase updatePurchaseById(PurchaseDTO purchaseDTO, Long id) throws Exception {
        return null;
    }
}
