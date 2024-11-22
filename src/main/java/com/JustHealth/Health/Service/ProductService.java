package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.MedicineProductResponseDTO;
import com.JustHealth.Health.DTO.OTCResonseDTO;
import com.JustHealth.Health.DTO.ProductRequestDTO;
import com.JustHealth.Health.DTO.ProductResponseDTO;
import com.JustHealth.Health.Entity.Product;
import com.JustHealth.Health.Exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService {

    public Product findProductById(Long id) throws ProductNotFoundException;

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO)throws Exception;

    public MedicineProductResponseDTO createMedicineProduct(ProductRequestDTO productRequestDTO) throws Exception;

    public OTCResonseDTO createOTCproduct(ProductRequestDTO productRequestDTO) throws Exception;

    public Page<MedicineProductResponseDTO> getAllMedicineProduct(int page, int size, String sortBy) throws Exception;

    public Page<OTCResonseDTO> getAllOTCProduct(int page, int size, String sortBy) throws Exception;






}
