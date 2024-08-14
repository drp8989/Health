package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.Batch;
import com.JustHealth.Health.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Inventory findByProductId(Integer productId);



}
