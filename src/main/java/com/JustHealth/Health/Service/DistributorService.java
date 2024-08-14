package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.DistributorDTO;
import com.JustHealth.Health.Entity.Distributor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DistributorService {



//    Features to Implement
//    Show a list of all distributors with data
//    Enter a new distributor
//    Show purchases of distributor
//    Show purchaseReturn of distributor
//    Show Ledger of distributor
//    History of purchased items from distribnutor
//    Vouchers of Distributor //Credit/debit
//    Show toPay distributor



    public Distributor createDistributor(DistributorDTO distributorDTO) throws Exception;
    public Distributor findById(Long id) throws Exception;


    public List<Distributor> getAllDistributor() throws Exception;

    public List<Distributor> findDistributorsByName(String query);
    public List<Distributor> findByDistributorGSTIN(String query);



    public Page<Distributor> findAllDistributor(int page, int size, String sortBy)throws Exception;

    public List<Distributor> getAllDistributorPurchase();


}
