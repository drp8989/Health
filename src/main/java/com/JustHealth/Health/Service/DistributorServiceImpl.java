package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.DistributorDTO;
import com.JustHealth.Health.Entity.Distributor;
import com.JustHealth.Health.Repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DistributorServiceImpl implements DistributorService{

    @Autowired
    DistributorRepository distributorRepository;




    @Override
    public Distributor createDistributor(DistributorDTO req) {
        Distributor distributor=new Distributor();
        distributor.setDistributorStoreName(req.getDistributorStoreName());
        distributor.setDistributorName(req.getDistributorName());
        distributor.setDistributorGSTIN(req.getDistributorGSTIN());
        distributor.setDistributorEmail(req.getDistributorEmail());
        distributor.setDistributorMobileNo(req.getDistributorMobileNo());
        distributor.setDistributorAddress(req.getDistributorAddress());



        return distributorRepository.save(distributor);
    }

    @Override
    public Distributor getAllDistributor() throws Exception {
        return null;
    }
}
