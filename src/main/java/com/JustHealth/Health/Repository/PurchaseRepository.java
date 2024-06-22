package com.JustHealth.Health.Repository;


import com.JustHealth.Health.Entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

//    @Query("SELECT p FROM Purchase p INNER JOIN p.purchaseInventories pi WHERE p.id = :purchaseId")
//    Purchase findPurchasesWithInventories(@Param("purchaseId") Long purchaseId);

}
