package com.JustHealth.Health.Service;

import com.JustHealth.Health.Entity.PurchaseInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchaseInventoryService {

//    public PurchaseInventory getPurchaseByInventoryId(Long id) throws Exception;

    public List<PurchaseInventory> getPurchaseInventoryByInventoryId(Long id) throws Exception;


    public Page<PurchaseInventory> getPurchaseInventoryByPurchaseId(Long id, int page, int size) throws Exception;
}
