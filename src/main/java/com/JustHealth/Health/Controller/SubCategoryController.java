package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.CategoryRequestDTO;
import com.JustHealth.Health.DTO.CategoryResponseDTO;
import com.JustHealth.Health.DTO.SubCategoryRequestDTO;
import com.JustHealth.Health.Entity.SubCategory;
import com.JustHealth.Health.Repository.SubCategoryRepository;
import com.JustHealth.Health.Service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subcategory")
public class SubCategoryController {

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    SubCategoryService subCategoryService;


    @PostMapping("/create")
    public ResponseEntity<?> createSubCategory(@RequestBody SubCategoryRequestDTO subCategoryRequestDTO)throws Exception{
        if(subCategoryRequestDTO==null){
            return new ResponseEntity<>("Request Body is null", HttpStatus.BAD_REQUEST);
        }else {
            try {
                SubCategory subCategory =subCategoryService.createSubCategory(subCategoryRequestDTO);
                return new ResponseEntity<>(subCategory,HttpStatus.CREATED);
            }catch (Exception e){
                return new ResponseEntity<>("Internal Server Error "+e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
