package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.DistributorDTO;
import com.JustHealth.Health.DTO.DistributorPurchasesDTO;
import com.JustHealth.Health.DTO.DistributorResponseDTO;
import com.JustHealth.Health.Entity.Distributor;
import com.JustHealth.Health.Service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/distributor")
public class DistributorController {

    @Autowired
    DistributorService distributorService;

    @PostMapping("/create")
    private DistributorResponseDTO createDistributor(@RequestBody DistributorDTO distributorDTO)throws Exception{

        try {
            DistributorResponseDTO distributorResponseDTO=distributorService.createDistributor(distributorDTO);
            return distributorResponseDTO;
        }catch (Exception e){
            throw new Exception("Error"+e);
        }

    }


    @DeleteMapping("/delete/{id}")
    private ResponseEntity<?> deleteDistribubtorById(@PathVariable Long id)throws Exception{

        try {
            distributorService.deleteByDistributorId(id);
            return new ResponseEntity<>("Delete Successfull", HttpStatus.OK);
        }catch (Exception e){
            throw new Exception("Distributor with ID " + id + " not found: " + e.getMessage(), e);
        }

    }



    @GetMapping("/getAll")
    private List<Distributor> getAll() throws Exception {
        List<Distributor> distributors=distributorService.getAllDistributor();
        return distributors;
    }

    @GetMapping("/getByName")
    private Page<Distributor> searchByName(@RequestParam(name = "name",required = false)String query,@RequestParam(name = "page",defaultValue = "0") int page,@RequestParam(name = "size",defaultValue = "5") int size) throws Exception{
        Page<Distributor> distributors=distributorService.findDistributorsByName(query,page,size);
        return distributors;

    }

    @GetMapping("/getByGSTIN")
    private Page<Distributor> searchByGSTIN(@RequestParam String query,@RequestParam(name = "page",defaultValue = "0") int page,@RequestParam(name = "size",defaultValue = "5") int size)throws Exception{
        try {
            Page<Distributor> distributors=distributorService.findByDistributorGSTIN(query,page,size);
            return distributors;
        }catch (Exception e){
            throw new Exception("Error" +e.getMessage());
        }

    }

    @GetMapping("/getToPayDistributors")
    private Page<Distributor> findToPayDistributors(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size) throws Exception{
        return distributorService.findAllDistributorToPay(page, size);
    }

    @GetMapping("/getPurchases/{id}")
    private Page<DistributorPurchasesDTO> getPurchasesByDistributorId(@PathVariable() Long id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) throws Exception{
        Pageable pageable= PageRequest.of(page,size);
        Page<DistributorPurchasesDTO> distributorResponse=distributorService.getDistributorPurchasesByDistributorId(id,pageable);
        return distributorResponse;

    }



}
