package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.DistributorDTO;
import com.JustHealth.Health.Entity.Distributor;

public interface DistributorService {

    public Distributor createDistributor(DistributorDTO distributorDTO);
    public Distributor getAllDistributor() throws Exception;

}
