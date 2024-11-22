package com.JustHealth.Health.DTO;


import com.JustHealth.Health.Entity.Category;
import com.JustHealth.Health.Entity.Product;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class SubCategoryRequestDTO {

    private String subCategoryName;

    private Long categoryId;

    @Nullable
    private Long productId;
}
