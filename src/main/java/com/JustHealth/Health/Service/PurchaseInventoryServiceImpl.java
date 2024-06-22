package com.JustHealth.Health.Service;

import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.PurchaseInventory;
import com.JustHealth.Health.Repository.InventoryRepository;
import com.JustHealth.Health.Repository.PurchaseInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PurchaseInventoryServiceImpl implements PurchaseInventoryService {

    @Autowired
    PurchaseInventoryRepository purchaseInventoryRepository;

    @Override
    public List<PurchaseInventory> getPurchaseInventoryByInventoryId(Long id) throws Exception {
        return purchaseInventoryRepository.findByInventoryId(id);
    }


//    public PurchaseInventory getPurchaseByInventoryId(Long id) throws Exception{
//
//        return null;
//    }


}
