package com.JustHealth.Health.Service;

import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Entity.PurchaseInventory;
import com.JustHealth.Health.Repository.InventoryRepository;
import com.JustHealth.Health.Repository.PurchaseInventoryRepository;
import com.JustHealth.Health.Repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PurchaseInventoryServiceImpl implements PurchaseInventoryService {

    @Autowired
    PurchaseInventoryRepository purchaseInventoryRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    PurchaseService purchaseService;

    @Override
    public List<PurchaseInventory> getPurchaseInventoryByInventoryId(Long id) throws Exception {
        return purchaseInventoryRepository.findByInventoryId(id);
    }

    public Page<PurchaseInventory> getPurchaseInventoryByPurchaseId(Long id, int page, int size) throws Exception {
        // Fetch the purchase by id
        Purchase purchase = purchaseService.purchaseById(id);

        // Get the list of PurchaseInventory from the purchase
        List<PurchaseInventory> purchaseInventories = purchase.getPurchaseInventories();

        // Convert the list to a Page using Pageable
        Pageable pageable = PageRequest.of(page, size);
        int start = Math.min((int)pageable.getOffset(), purchaseInventories.size());
        int end = Math.min((start + pageable.getPageSize()), purchaseInventories.size());

        // Return the paginated result as a Page
        return new PageImpl<>(purchaseInventories.subList(start, end), pageable, purchaseInventories.size());
    }




//    public PurchaseInventory getPurchaseByInventoryId(Long id) throws Exception{
//
//        return null;
//    }


}
