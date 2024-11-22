package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.*;
import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Exception.EmptyRequestBodyException;
import com.JustHealth.Health.Exception.InternalServerErrorException;
import com.JustHealth.Health.Exception.MedicineCompositionNotFoundException;
import com.JustHealth.Health.Service.MedicineCompositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Validated
@RestController
@RequestMapping("/api/v1/composition")
public class MedicineCompositionController {



    @Autowired
    MedicineCompositionService medicineCompositionService;



    @Operation(summary = "Create a MEDICINE Salt(composition)", description = "Creates a salt(compopsition) to be used by medicines which is also called as an active ingredient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createComposition(@Valid @RequestBody MedicineCompositionRequestDTO medicineCompositionRequestDTO, BindingResult result) throws Exception {


        //If the body is null
        if(medicineCompositionRequestDTO.isEmpty()){
            throw new EmptyRequestBodyException("Request Body is Empty");
        }


        //This will collect all the errors from @Valid annotation
        if (result.hasErrors()) {
            // Collect error messages from all errors
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(error -> {
                        if (error instanceof FieldError) {
                            return ((FieldError) error).getDefaultMessage(); // For field errors
                        } else {
                            return error.getDefaultMessage(); // For global errors
                        }
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        // Proceed with normal processing if everything is valid
        MedicineCompositionResponseDTO medicineComposition = medicineCompositionService.createMedicineComposition(medicineCompositionRequestDTO);
        return new ResponseEntity<>(medicineComposition, HttpStatus.CREATED);


    }



    @Operation(summary = "Getting a MEDICINE Salt(composition)", description = "Gives Data of salt(compopsition) to be used by medicines which is called active ingredient.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Error Not Found"),
            @ApiResponse(responseCode = "400",description = "Bad Request")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getMedicineComposition(@PathVariable Long id) throws Exception{
        try {
            MedicineCompositionResponseDTO responseDTO=medicineCompositionService.getMedicineComposition(id);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        }catch (MedicineCompositionNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }


    @Operation(summary = "Updating MEDICINE Salt(composition)", description = "Update all the field related to this entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400",description = "Bad Request")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCompostion(@PathVariable Long id, @RequestBody MedicineCompositionRequestDTO medicineCompositionRequestDTO) throws Exception {
        try {
            MedicineCompositionResponseDTO medicineComposition = medicineCompositionService.updateMedicineComposition(id, medicineCompositionRequestDTO);
            return new ResponseEntity<>(medicineComposition, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Bad request.",HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get all the compositions which are in the system", description = "Gets the composition data as paginated which then can be used in the system or get a brief info about. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/getAllComposition")
    public ResponseEntity<Page<MedicineCompositionResponseDTO>> getAllComposition(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "medicineCompositionName") String sortBy) {
        try {
            Page<MedicineCompositionResponseDTO> compositions = medicineCompositionService.getAllCompositions(page, size, sortBy);
            return new ResponseEntity<>(compositions, HttpStatus.OK);  // 200 OK
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred.", e);  // You can also return a ResponseEntity for errors if needed
        }
    }

    @Operation(summary = "Delete a composition by Id", description = "Deletes the composition from the system. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComposition(@PathVariable Long id) throws Exception {

            medicineCompositionService.deleteMedicineComposition(id);
            return new ResponseEntity<>("Delete Successfull",HttpStatus.OK);


    }

    @Operation(summary = "Retrives total composition in the system", description = "Retrives total composition in the system. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/count")
    public ResponseEntity<CountResponse> totalCompositoin()throws Exception{
        try {
            Long total=medicineCompositionService.totalCount();
            CountResponse countResponse=new CountResponse();
            countResponse.setResponseString("Total Compositions");
            countResponse.setTotalCount(total);
            return new ResponseEntity<>(countResponse,HttpStatus.OK);
        }catch (Exception e){
             throw new InternalServerErrorException("Internal Server Error",e);
        }

    }
}
