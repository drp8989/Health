package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.MedicineFAQResponseDTO;
import com.JustHealth.Health.DTO.MedicineProductResponseDTO;
import com.JustHealth.Health.DTO.OTCResonseDTO;
import com.JustHealth.Health.DTO.ProductRequestDTO;
import com.JustHealth.Health.Entity.Product;
import com.JustHealth.Health.Exception.ProductNotFoundException;
import com.JustHealth.Health.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping("/{id}")
    private Product getProductById(@PathVariable Long id) throws ProductNotFoundException {

        return productService.findProductById(id);

    }



    @PostMapping("/createMedicine")
    @Operation(summary = "Create a MEDICINE Product", description = "Creates a MedicineProducts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error")
    })
    private ResponseEntity<?> createMedicineProduct(@RequestBody ProductRequestDTO productRequestDTO) throws Exception{
        try {
            MedicineProductResponseDTO medicineProductResponseDTO = productService.createMedicineProduct(productRequestDTO);
            return new ResponseEntity<>(medicineProductResponseDTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/createOTC")
    @Operation(summary = "Create's a OTC Product", description = "Creates OTCProducts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error")
    })
    private ResponseEntity<?> createOTCProduct(@RequestBody ProductRequestDTO productRequestDTO) throws Exception{
        try {
            OTCResonseDTO otcResonseDTO = productService.createOTCproduct(productRequestDTO);
            return new ResponseEntity<>(otcResonseDTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAllMedicines")
    @Operation(summary = "Gives a list of all medicines inside the product catalouge", description = "Gives a list of all medicines inside the product catalouge")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error")
    })
    private ResponseEntity<?> getAllMedicines(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "productName") String sortBy){
        try {
            Page<MedicineProductResponseDTO> medicineProducts=productService.getAllMedicineProduct(page,size,sortBy);
            return new ResponseEntity<>(medicineProducts,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAllOTC")
    @Operation(summary = "Gives a list of all otc products inside the product catalouge", description = "Gives a list of all otc inside the product catalouge")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error")
    })
    private ResponseEntity<?> getAllOTCProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "productName") String sortBy){
        try {
            Page<OTCResonseDTO> medicineProducts=productService.getAllOTCProduct(page,size,sortBy);
            return new ResponseEntity<>(medicineProducts,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
