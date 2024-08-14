package com.JustHealth.Health.Service;

import com.JustHealth.Health.Entity.PurchaseInventory;

import java.util.List;

public interface PurchaseInventoryService {

//    public PurchaseInventory getPurchaseByInventoryId(Long id) throws Exception;

    public List<PurchaseInventory> getPurchaseInventoryByInventoryId(Long id) throws Exception;
}
