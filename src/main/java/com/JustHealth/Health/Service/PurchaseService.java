package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.PurchaseDTO;
import com.JustHealth.Health.Entity.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    public Purchase createPurchase(PurchaseDTO purchaseDTO);

    public List<Purchase> findAllPurchase() throws Exception;

    public Optional<Purchase> findPurchaseById(Long id) throws Exception;

    public void deletePurchaseById(Long id) throws Exception;

    public Purchase updatePurchaseById(PurchaseDTO purchaseDTO,Long id) throws Exception;

}
