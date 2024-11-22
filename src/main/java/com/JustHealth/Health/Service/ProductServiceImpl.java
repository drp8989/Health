package com.JustHealth.Health.Service;


import com.JustHealth.Health.DTO.*;
import com.JustHealth.Health.Entity.*;
import com.JustHealth.Health.Exception.ProductNotFoundException;
import com.JustHealth.Health.Repository.MedicineProductRepository;
import com.JustHealth.Health.Repository.ProductRepository;
import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;


    @Autowired
    CategoryService categoryService;

    @Autowired
    SubCategoryService subCategoryService;

    @Autowired
    MedicineCompositionService medicineCompositionService;


    @Autowired
    MedicineProductRepository medicineProductRepository;

    @Override
    public Product findProductById(Long id) throws ProductNotFoundException {
        Optional<Product> product=productRepository.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Product not Found");
        }
        return product.get();
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) throws Exception {
        return null;
    }

    @Override
    @Transactional
    public MedicineProductResponseDTO createMedicineProduct(ProductRequestDTO productRequestDTO) throws Exception {

        MedicineProduct medicineProduct = new MedicineProduct();

        medicineProduct.setProductName(productRequestDTO.getProductName());
        medicineProduct.setProductManufacturer(productRequestDTO.getProductManufacturer());

        // Retrieve the Category and SubCategory
        Category category = categoryService.findCategoryById(productRequestDTO.getProductCategoryId());
        SubCategory subCategory = subCategoryService.findSubCategoryById(productRequestDTO.getProductSubCategoryId());

        // Set the category and subcategory in the product
        medicineProduct.setProductCategory(category);
        medicineProduct.setProductSubCategory(subCategory);

        // Add the product to the Category and SubCategory
//        category.addProduct(medicineProduct);
//        subCategory.addProduct(medicineProduct);

        // Set other product details
        medicineProduct.setMedicineRx(productRequestDTO.getMedicineRx());
        medicineProduct.setProductDosageForm(productRequestDTO.getProductDosageForm());
        medicineProduct.setMedicinePackSize(productRequestDTO.getMedicinePackSize());
        medicineProduct.setProductType(Product.productType.MEDICINE);

        MedicineComposition medicineComposition=medicineCompositionService.findMedicineCompositionById(productRequestDTO.getMedicineCompositionId());

        medicineProduct.setMedicineReturnPolicy(productRequestDTO.getMedicineReturnPolicy());
        medicineProduct.setMedicineComposition(medicineComposition);
        Slugify slugify=new Slugify();
        String slug=slugify.slugify(productRequestDTO.getProductName());
        medicineProduct.setSlug(slug);
        // Save the product to the database
        productRepository.save(medicineProduct);

        MedicineProductResponseDTO medicineProductResponseDTO=new MedicineProductResponseDTO();
        medicineProductResponseDTO.setProductName(medicineProduct.getProductName());
        medicineProductResponseDTO.setProductManufacturer(medicineProduct.getProductManufacturer());
        medicineProductResponseDTO.setProductType(medicineProduct.getProductType());

        //Create dto for category and subcategory and send object
        CategoryResponseDTO categoryResponseDTO=new CategoryResponseDTO();
        categoryResponseDTO.setCategoryName(category.getCategoryName());

        SubCategoryResponeDTO subCategoryResponeDTO=new SubCategoryResponeDTO();
        subCategoryResponeDTO.setSubCategoryName(subCategory.getSubCategoryName());

        medicineProductResponseDTO.setProductCategory(categoryResponseDTO);
        medicineProductResponseDTO.setProductSubCategory(subCategoryResponeDTO);

        medicineProductResponseDTO.setSlug(medicineProduct.getSlug());
        medicineProductResponseDTO.setMedicineRx(medicineProduct.getMedicineRx());
        medicineProductResponseDTO.setProductDosageForm(medicineProduct.getProductDosageForm());
        medicineProductResponseDTO.setMedicinePackSize(medicineProduct.getMedicinePackSize());
        medicineProductResponseDTO.setMedicineReturnPolicy(medicineProduct.getMedicineReturnPolicy());
        medicineProductResponseDTO.setMedicineComposition(medicineComposition);
        return medicineProductResponseDTO;



    }

    @Override
    @Transactional
    public OTCResonseDTO createOTCproduct(ProductRequestDTO productRequestDTO) throws Exception {
        OTCProduct otcProduct=new OTCProduct();
        otcProduct.setProductManufacturer(productRequestDTO.getProductManufacturer());
        otcProduct.setProductName(productRequestDTO.getProductName());

        Category category = categoryService.findCategoryById(productRequestDTO.getProductCategoryId());
        SubCategory subCategory = subCategoryService.findSubCategoryById(productRequestDTO.getProductSubCategoryId());

        otcProduct.setProductCategory(category);
        otcProduct.setProductSubCategory(subCategory);

        otcProduct.setUsageInstructions(productRequestDTO.getUsageInstructions());
        otcProduct.setWarnings(productRequestDTO.getWarnings());
        otcProduct.setStorageInstructions(productRequestDTO.getStorageInstructions());
        otcProduct.setWeight(productRequestDTO.getWeight());
        otcProduct.setProductType(Product.productType.OTC);
        Slugify slugify=new Slugify();
        String slug=slugify.slugify(productRequestDTO.getProductName());
        otcProduct.setSlug(slug);
        productRepository.save(otcProduct);


        OTCResonseDTO otcResonseDTO=new OTCResonseDTO();
        otcResonseDTO.setProductName(otcProduct.getProductName());
        otcResonseDTO.setProductManufacturer(otcProduct.getProductManufacturer());
        otcResonseDTO.setProductType(otcProduct.getProductType());

        CategoryResponseDTO categoryResponseDTO=new CategoryResponseDTO();
        categoryResponseDTO.setCategoryName(category.getCategoryName());

        SubCategoryResponeDTO subCategoryResponeDTO=new SubCategoryResponeDTO();
        subCategoryResponeDTO.setSubCategoryName(subCategory.getSubCategoryName());

        otcResonseDTO.setProductCategory(categoryResponseDTO);
        otcResonseDTO.setProductSubCategory(subCategoryResponeDTO);
        otcResonseDTO.setSlug(otcProduct.getSlug());
        otcResonseDTO.setUsageInstructions(otcProduct.getUsageInstructions());
        otcResonseDTO.setWarnings(otcProduct.getWarnings());
        otcResonseDTO.setStorageInstructions(otcProduct.getUsageInstructions());
        otcResonseDTO.setWeight(otcProduct.getWeight());


        return otcResonseDTO;



    }

    @Override
    @Transactional
    public Page<MedicineProductResponseDTO> getAllMedicineProduct(int page, int size, String sortBy) throws Exception {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> medicineProducts = productRepository.findByProductType(Product.productType.MEDICINE, pageable);

        // Map Product entities to MedicineProductResponseDTOs with type checking
        return medicineProducts.map(product -> {
            if (product instanceof MedicineProduct) {
                return convertToMedicineProductResponseDTOfromProduct((MedicineProduct) product);
            }
            return null; // or handle other types as needed
        });
    }

    @Override
    @Transactional
    public Page<OTCResonseDTO> getAllOTCProduct(int page, int size, String sortBy) throws Exception {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> otcProducts = productRepository.findByProductType(Product.productType.OTC, pageable);

        // Map Product entities to MedicineProductResponseDTOs with type checking
        return otcProducts.map(product -> {
            if (product instanceof OTCProduct) {
                return convertToOTCProductResponseDTOfromOTC((OTCProduct) product);
            }
            return null; // or handle other types as needed
        });
    }


    //Product to MedicineProductResponseDTO
    public MedicineProductResponseDTO convertToMedicineProductResponseDTOfromProduct(MedicineProduct product) {
        MedicineProductResponseDTO medicineProductResponseDTO=new MedicineProductResponseDTO();
        medicineProductResponseDTO.setProductName(product.getProductName());
        medicineProductResponseDTO.setProductManufacturer(product.getProductManufacturer());
        medicineProductResponseDTO.setProductType(product.getProductType());

        //Category and sub category
        medicineProductResponseDTO.setProductCategory(DTOHelper.convertCategorytoDTO(product.getProductCategory()));
        medicineProductResponseDTO.setProductSubCategory(DTOHelper.convertSubCategorytoDTO(product.getProductSubCategory()));


        medicineProductResponseDTO.setSlug(product.getSlug());
        medicineProductResponseDTO.setMedicineRx(product.getMedicineRx());
        medicineProductResponseDTO.setProductDosageForm(product.getProductDosageForm());
        medicineProductResponseDTO.setMedicinePackSize(product.getMedicinePackSize());
        medicineProductResponseDTO.setMedicineReturnPolicy(product.getMedicineReturnPolicy());

        medicineProductResponseDTO.setMedicineComposition(product.getMedicineComposition());


        return medicineProductResponseDTO;



    }

    public OTCResonseDTO convertToOTCProductResponseDTOfromOTC(OTCProduct otcProduct){
        OTCResonseDTO otcResonseDTO=new OTCResonseDTO();

        otcResonseDTO.setProductName(otcProduct.getProductName());
        otcResonseDTO.setProductManufacturer(otcProduct.getProductManufacturer());
        otcResonseDTO.setProductType(otcProduct.getProductType());

        //Category and sub category
        otcResonseDTO.setProductCategory(DTOHelper.convertCategorytoDTO(otcProduct.getProductCategory()));
        otcResonseDTO.setProductSubCategory(DTOHelper.convertSubCategorytoDTO(otcProduct.getProductSubCategory()));


        otcResonseDTO.setSlug(otcProduct.getSlug());

        otcResonseDTO.setUsageInstructions(otcProduct.getUsageInstructions());
        otcResonseDTO.setWarnings(otcProduct.getWarnings());
        otcResonseDTO.setStorageInstructions(otcProduct.getStorageInstructions());
        otcResonseDTO.setWeight(otcProduct.getWeight());
        return otcResonseDTO;

    }


}
