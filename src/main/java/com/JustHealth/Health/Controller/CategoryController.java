package com.JustHealth.Health.Controller;


import com.JustHealth.Health.DTO.CategoryRequestDTO;
import com.JustHealth.Health.DTO.CategoryResponseDTO;
import com.JustHealth.Health.DTO.DistributorResponseDTO;
import com.JustHealth.Health.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {


    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO)throws Exception{
        if(categoryRequestDTO==null){
            return new ResponseEntity<>("Request Body is null", HttpStatus.BAD_REQUEST);
        }else {
            try {
                CategoryResponseDTO categoryResponseDTO=categoryService.createCategory(categoryRequestDTO);
                return new ResponseEntity<>(categoryResponseDTO,HttpStatus.CREATED);
            }catch (Exception e){
                return new ResponseEntity<>("Internal Server Error "+e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
