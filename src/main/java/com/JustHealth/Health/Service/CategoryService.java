package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.CategoryRequestDTO;
import com.JustHealth.Health.DTO.CategoryResponseDTO;
import com.JustHealth.Health.Entity.Category;

public interface CategoryService {


    Category findCategoryById(Long id) throws Exception;
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) throws Exception;


}
