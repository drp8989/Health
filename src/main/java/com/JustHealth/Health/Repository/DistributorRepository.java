package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor,Long> {

    @Query("SELECT D FROM Distributor D WHERE D.distributorName LIKE %:query%")
    List<Distributor> searchByDistributorName(String query);


    @Query("SELECT D FROM Distributor D WHERE D.distributorGSTIN LIKE %:query%")
    List<Distributor> findByDistributorGSTIN(String query);

}
