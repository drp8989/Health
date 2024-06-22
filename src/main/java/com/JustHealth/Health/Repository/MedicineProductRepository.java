package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.MedicineProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicineProductRepository extends PagingAndSortingRepository<MedicineProduct,Long> ,JpaRepository<MedicineProduct,Long>  {

    @Query("SELECT mp FROM MedicineProduct mp WHERE mp.medicineComposition.id = :compositionId")
    List<MedicineProduct> findMedicineProductsByCompositionId(@Param("compositionId") Long compositionId);

}
