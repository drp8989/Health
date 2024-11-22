package com.JustHealth.Health.Service;


import com.JustHealth.Health.DTO.SubCategoryRequestDTO;
import com.JustHealth.Health.Entity.SubCategory;
import com.JustHealth.Health.Repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubCategoryImpl implements SubCategoryService{

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    CategoryService categoryService;

    @Override
    public SubCategory createSubCategory(SubCategoryRequestDTO subCategoryRequestDTO) throws Exception {
        SubCategory subCategory=new SubCategory();
        subCategory.setSubCategoryName(subCategoryRequestDTO.getSubCategoryName());
        subCategory.setCategory(categoryService.findCategoryById(subCategoryRequestDTO.getCategoryId()));
        return subCategoryRepository.save(subCategory);

    }

    @Override
    public SubCategory findSubCategoryById(Long id) throws Exception {
        Optional<SubCategory> subCategory=subCategoryRepository.findById(id);
        if(subCategory.isEmpty()){
            throw new Exception("Sub Category Not Found");
        }else {
            return subCategory.get();
        }
    }
}
