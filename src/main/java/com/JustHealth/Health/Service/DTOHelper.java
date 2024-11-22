package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.BatchResponseDTO;
import com.JustHealth.Health.DTO.CategoryResponseDTO;
import com.JustHealth.Health.DTO.InventoryResponseDTO;
import com.JustHealth.Health.DTO.SubCategoryResponeDTO;
import com.JustHealth.Health.Entity.Batch;
import com.JustHealth.Health.Entity.Category;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.SubCategory;

import java.util.List;
import java.util.stream.Collectors;

public class DTOHelper {


    //Category to category response DTO

    public static CategoryResponseDTO convertCategorytoDTO(Category category){
        CategoryResponseDTO categoryResponseDTO=new CategoryResponseDTO();
        categoryResponseDTO.setCategoryName(category.getCategoryName());
        return categoryResponseDTO;
    }

    public static SubCategoryResponeDTO convertSubCategorytoDTO(SubCategory subCategory){

        SubCategoryResponeDTO subCategoryResponeDTO=new SubCategoryResponeDTO();
        subCategoryResponeDTO.setSubCategoryName(subCategory.getSubCategoryName());
        return subCategoryResponeDTO;

    }

    public static InventoryResponseDTO convertToInventoryResponseDTO(Inventory inventory) {
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setInventoryId(inventory.getId());
        dto.setReorderLevel(inventory.getReorderLevel());
        dto.setReorderQuantity(inventory.getReorderQuantity());
        dto.setLocation(inventory.getLocation());
        dto.setMinQTY(inventory.getMinQTY());
        dto.setMaxQTY(inventory.getMaxQTY());
        dto.setGST(inventory.getGST());
        dto.setCurrentStock(inventory.getCurrentStock());
//        dto.setInventoryBatches(inventory.getInventoryBatch());
        List<BatchResponseDTO> batchResponseDTOs = inventory.getInventoryBatch().stream()
                .map(DTOHelper::convertToBatchResponseDTO)
                .collect(Collectors.toList());

        dto.setInventoryBatches(batchResponseDTOs);
        // Map additional fields as needed
        return dto;
    }

    public static BatchResponseDTO convertToBatchResponseDTO(Batch batch) {

        BatchResponseDTO batchDto = new BatchResponseDTO();
        batchDto.setBatch(batch.getBatch());
        batchDto.setBatchPTR(batch.getBatchPTR());
        batchDto.setBatchMRP(batch.getBatchMRP());
        batchDto.setBatchLP(batch.getBatchLP());
        batchDto.setQuantityInStock(batch.getQuantityInStock());
        batchDto.setExpiryDate(batch.getExpiryDate());

        return batchDto;
    }


    public static List<BatchResponseDTO> convertToBatchResponseDTO(List<Batch> batches) {
        return batches.stream()
                .map(DTOHelper::convertToBatchResponseDTO)  // Map each Batch to BatchResponseDTO
                .collect(Collectors.toList());  // Collect results into a list
    }


}
