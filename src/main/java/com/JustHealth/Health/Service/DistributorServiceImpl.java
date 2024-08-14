package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.DistributorDTO;
import com.JustHealth.Health.Entity.Distributor;
import com.JustHealth.Health.Repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class DistributorServiceImpl implements DistributorService{


    @Autowired
    DistributorRepository distributorRepository;

    @Override
    public Distributor createDistributor(DistributorDTO req)throws Exception {

        Distributor distributor=new Distributor();
        distributor.setDistributorStoreName(req.getDistributorStoreName());
        distributor.setDistributorName(req.getDistributorName());
        distributor.setDistributorGSTIN(req.getDistributorGSTIN());
        distributor.setDistributorEmail(req.getDistributorEmail());
        distributor.setDistributorMobileNo(req.getDistributorMobileNo());
        distributor.setDistributorAddress(req.getDistributorAddress());
        distributor.setDistributorAccountBalance(Integer.valueOf(req.getDistributorAccountBalance()));

        return distributorRepository.save(distributor);
    }

    @Override
    public Distributor findById(Long id) throws Exception{
        Optional<Distributor> distributor=distributorRepository.findById(id);
        if (distributor.isEmpty()){
            throw new Exception("Distributor not Found");
        }
        return distributor.get();

    }


    @Override
    public List<Distributor> getAllDistributor() throws Exception {

        List<Distributor> distributors;
        distributors=distributorRepository.findAll();
        return distributors;

    }

    @Override
    public List<Distributor> findDistributorsByName(String query) {
        return distributorRepository.searchByDistributorName(query);
    }

    @Override
    public List<Distributor> findByDistributorGSTIN(String query) {
        return distributorRepository.findByDistributorGSTIN(query);
    }

    @Override
    public Page<Distributor> findAllDistributor(int page, int size, String sortBy) throws Exception {
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));
        return distributorRepository.findAll(pageable);
    }

    @Override
    public List<Distributor> getAllDistributorPurchase() {
        List<Distributor> distributors=distributorRepository.findAll();
        return distributors;
    }
}
