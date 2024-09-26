package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.DistributorDTO;
import com.JustHealth.Health.DTO.DistributorPurchasesDTO;
import com.JustHealth.Health.DTO.DistributorResponseDTO;
import com.JustHealth.Health.Entity.Distributor;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Repository.DistributorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DistributorServiceImpl implements DistributorService{


    @Autowired
    DistributorRepository distributorRepository;

    @Override
    public DistributorResponseDTO createDistributor(DistributorDTO req)throws Exception {

        Distributor distributor=new Distributor();
        distributor.setDistributorStoreName(req.getDistributorStoreName());
        distributor.setDistributorName(req.getDistributorName());
        distributor.setDistributorGSTIN(req.getDistributorGSTIN());
        distributor.setDistributorEmail(req.getDistributorEmail());
        distributor.setDistributorMobileNo(req.getDistributorMobileNo());
        distributor.setDistributorAddress(req.getDistributorAddress());
        distributor.setDistributorAccountBalance(Float.valueOf(req.getDistributorAccountBalance()));
        distributorRepository.save(distributor);

        DistributorResponseDTO distributorResponseDTO=new DistributorResponseDTO();
        distributorResponseDTO.setDistributorStoreName(distributor.getDistributorStoreName());
        distributorResponseDTO.setDistributorName(distributor.getDistributorName());
        distributorResponseDTO.setDistributorGSTIN(distributor.getDistributorGSTIN());
        distributorResponseDTO.setDistributorEmail(distributor.getDistributorEmail());
        distributorResponseDTO.setDistributorMobileNo(distributor.getDistributorMobileNo());
        distributorResponseDTO.setDistributorAddress(distributor.getDistributorAddress());
        distributorResponseDTO.setDistributorAccountBalance(distributor.getDistributorAccountBalance());

        return distributorResponseDTO;
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
    public void deleteByDistributorId(Long id) throws Exception {
        Distributor distributor=findById(id);
        distributorRepository.delete(distributor);


    }


    @Override
    public List<Distributor> getAllDistributor() throws Exception {

        List<Distributor> distributors;
        distributors=distributorRepository.findAll();
        return distributors;

    }

    @Override
    @Transactional
    public Page<Distributor> findDistributorsByName(String query,int page,int size) {
        Pageable pageable=PageRequest.of(page,size);
        return distributorRepository.searchByDistributorName(query,pageable);
    }

    @Override
    @Transactional
    public Page<Distributor> findByDistributorGSTIN(String query,int page,int size) {
        Pageable pageable=PageRequest.of(page,size);
        return distributorRepository.findByDistributorGSTIN(query,pageable);
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

    @Override
    public Page<DistributorPurchasesDTO> getDistributorPurchasesByDistributorId(Long id, Pageable pageable) throws Exception {
        Distributor distributor=findById(id);

        List<Purchase> allPurchases = distributor.getPurchase();
        int totalPurchases = allPurchases.size();
        // Calculate the start and end indices for the pagination
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), totalPurchases);

        List<Purchase> paginatedPurchases = allPurchases.subList(start, end);

        DistributorPurchasesDTO distributorResponseDTO=new DistributorPurchasesDTO();
        distributorResponseDTO.setDistributorPurchases(paginatedPurchases);

        return new PageImpl<>(List.of(distributorResponseDTO), pageable, totalPurchases);
    }

    @Override
    @Transactional
    public Page<Distributor> findAllDistributorToPay(int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        List<Distributor> distributors = distributorRepository.findAll();

        // Filter distributors with positive account balance
        List<Distributor> toPay = distributors.stream()
                .filter(distributor -> distributor.getDistributorAccountBalance() > 0)
                .collect(Collectors.toList());

        // Convert list to a page with the requested pagination
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), toPay.size());
        List<Distributor> paginatedList = toPay.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, toPay.size());
    }
}
