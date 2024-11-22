package com.JustHealth.Health.Service;


import com.JustHealth.Health.DTO.CategoryRequestDTO;
import com.JustHealth.Health.DTO.CategoryResponseDTO;
import com.JustHealth.Health.Entity.Category;
import com.JustHealth.Health.Exception.CategoryNotFoundException;
import com.JustHealth.Health.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> category=categoryRepository.findById(id);
        if(category.isEmpty()){
            throw new CategoryNotFoundException("Cannot get Category Id"+id);
        }else{
         return category.get();
        }
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) throws Exception {
        Category category=new Category();
        category.setCategoryName(categoryRequestDTO.getCategoryName());
        categoryRepository.save(category);
        return objToDTO(category);
    }
    CategoryResponseDTO objToDTO(Category category){
        CategoryResponseDTO categoryResponseDTO= new CategoryResponseDTO();
        categoryResponseDTO.setCategoryName(category.getCategoryName());
        return categoryResponseDTO;
    }
}
