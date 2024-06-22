package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.MedicineProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineProductRepository extends JpaRepository<MedicineProduct,Long>  {

    @Query("SELECT mp FROM MedicineProduct mp WHERE mp.medicineComposition.id = :compositionId")
    List<MedicineProduct> findMedicineProductsByCompositionId(@Param("compositionId") Long compositionId);



}
