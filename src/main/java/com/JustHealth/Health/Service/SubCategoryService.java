package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.SubCategoryRequestDTO;
import com.JustHealth.Health.Entity.SubCategory;

public interface SubCategoryService {
    SubCategory createSubCategory(SubCategoryRequestDTO subCategoryRequestDTO)throws Exception;
    SubCategory findSubCategoryById(Long id)throws Exception;
}
