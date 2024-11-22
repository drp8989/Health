package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicineCompositionRepository extends JpaRepository<MedicineComposition,Long> {



}
