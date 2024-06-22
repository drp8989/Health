package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.DistributorDTO;
import com.JustHealth.Health.Entity.Distributor;
import com.JustHealth.Health.Service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/distributor")
public class DistributorController {

    @Autowired
    DistributorService distributorService;

    @PostMapping("/create")
    private Distributor createDistributor(@RequestBody DistributorDTO distributorDTO)throws Exception{

        try {
            Distributor distributor=distributorService.createDistributor(distributorDTO);
            return distributor;
        }catch (Exception e){
            throw new Exception("Error"+e);
        }

    }

    @GetMapping("/getAll")
    private List<Distributor> getAll() throws Exception {
        List<Distributor> distributors=distributorService.getAllDistributor();
        return distributors;
    }

    @GetMapping("/getByQuery")
    private List<Distributor> search() throws Exception{
        List<Distributor> distributors=distributorService.findDistributorsByName("R");
        return distributors;

    }

    @GetMapping("/getByGSTIN")
    private List<Distributor> searchByGSTIN()throws Exception{
        try {
            List<Distributor> distributors=distributorService.findByDistributorGSTIN("A");
            return distributors;
        }catch (Exception e){
            throw new Exception("Error" +e.getMessage());
        }

    }



}
