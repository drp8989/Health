package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Entity.PurchaseInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseInventoryRepository extends JpaRepository<PurchaseInventory,Long> {

//
//    @Query("SELECT P FROM Purchase P JOIN P.purchase pi where pi.purchase=:purchaseId ")
//    Purchase findByPurchaseId(@Param("purchaseId") Long id);
}
