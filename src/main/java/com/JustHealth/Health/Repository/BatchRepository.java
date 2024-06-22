package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.Batch;
import com.JustHealth.Health.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> {



//    @Query("SELECT b FROM Batch b WHERE b.batchInventory.id = :inventoryId")
//    List<Batch> findBatchesByInventoryId(Integer inventoryId);

}
