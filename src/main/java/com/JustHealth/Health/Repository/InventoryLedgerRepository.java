package com.JustHealth.Health.Repository;


import com.JustHealth.Health.Entity.InventoryLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryLedgerRepository extends JpaRepository<InventoryLedger,Long> {
}
