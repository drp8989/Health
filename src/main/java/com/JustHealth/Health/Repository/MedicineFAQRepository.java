package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineFAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineFAQRepository extends JpaRepository<MedicineFAQ,Long> {

    @Query("SELECT f.medicineComposition FROM MedicineFAQ f WHERE f.id = :faqId")
    MedicineComposition findCompositionByFaqId(@Param("faqId") Long faqId);


    @Query("SELECT f FROM MedicineFAQ f WHERE f.medicineComposition.id = :compositionId")
    List<MedicineFAQ> findFAQsByCompositionId(@Param("compositionId") Long compositionId);
}
