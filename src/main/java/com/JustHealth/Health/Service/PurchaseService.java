package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.PurchaseDTO;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Entity.PurchaseInventory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {

//    Features
//    An authorized personel can make a purchase entry
//    Purchase Entry is responsible for setting up inventory of a pharmacy
    

    public Purchase createPurchase(@RequestBody PurchaseDTO purchaseDTO) throws Exception;

    public Page<Purchase> findAllPurchasePaginated(int page,int size) throws Exception;

    public List<Purchase> findAllPurchase() throws Exception;

    public Purchase purchaseById(Long id) throws Exception;

    public void deletePurchaseById(Long id) throws Exception;

    public List<PurchaseInventory> getPurchaseInventoryByPurchaseId(Long id) throws Exception;

    public Purchase updatePurchaseById(@RequestBody PurchaseDTO purchaseDTO, Long id) throws Exception;

}
