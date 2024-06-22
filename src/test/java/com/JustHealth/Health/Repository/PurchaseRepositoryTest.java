package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Entity.PurchaseInventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    public void  savePurchase(){



//        Purchase purchase=Purchase.builder()
//                .billDate(new Date())
//                .dueDate(new Date())
//                .qty(List.of(12,3,4))
//                .purchaseInventories();
//
//        PurchaseInventory purchaseInventory=PurchaseInventory.builder()
//                .purchase(purchase)
//                .inventory()
//                .
//
//


    }



}