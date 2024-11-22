package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.MedicineFAQRequestDTO;
import com.JustHealth.Health.DTO.MedicineFAQResponseDTO;
import com.JustHealth.Health.Exception.InternalServerErrorException;
import com.JustHealth.Health.Service.MedicineFAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medicineFAQ")
public class MedicineFAQController {


    @Autowired
    MedicineFAQService medicineFAQService;

    @PostMapping("/update")
    private ResponseEntity<?> updateMedicineFAQ(@RequestBody MedicineFAQRequestDTO medicineFAQRequestDTO) throws Exception {
        try {
             MedicineFAQResponseDTO medicineFAQResponseDTO=medicineFAQService.updateMedicineFAQ(medicineFAQRequestDTO);
             return new ResponseEntity<>("Update Successfull", HttpStatus.OK);
        }catch (Exception e){
            throw new InternalServerErrorException("Internal Server Error Occured",e);
        }

    }
}
