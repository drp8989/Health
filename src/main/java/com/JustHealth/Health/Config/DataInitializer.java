package com.JustHealth.Health.Config;

import com.JustHealth.Health.Entity.*;
import com.JustHealth.Health.Repository.*;
import com.github.slugify.Slugify;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class DataInitializer {

    private final MedicineCompositionRepository medicineCompositionRepository;
    private final MedicineProductRepository medicineProductRepository;
    private final MedicineFAQRepository medicineFAQRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final Slugify slugify;  // Add Slugify dependency
    private boolean initialized = false;  // Flag to ensure initialization happens only once

    @PersistenceContext
    private EntityManager entityManager;

    public DataInitializer(MedicineCompositionRepository medicineCompositionRepository,
                           MedicineProductRepository medicineProductRepository,
                           MedicineFAQRepository medicineFAQRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.medicineCompositionRepository = medicineCompositionRepository;
        this.medicineProductRepository = medicineProductRepository;
        this.medicineFAQRepository = medicineFAQRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.slugify = new Slugify();  // Initialize Slugify
    }

   @EventListener(ApplicationReadyEvent.class)
    public void init() {
        if (!initialized) {
            initialized = true;

            // Create Categories and Subcategories
            Category vitaminsAndNutrition = createCategory("Vitamins and Nutrition");
            SubCategory multivitamins = createSubCategory("Multivitamins", vitaminsAndNutrition);
            SubCategory mineralSupplements = createSubCategory("Mineral Supplements", vitaminsAndNutrition);

            Category nutritionalDrinks = createCategory("Nutritional Drinks");
            SubCategory proteinShakes = createSubCategory("Protein Shakes", nutritionalDrinks);
            SubCategory energyDrinks = createSubCategory("Energy Drinks", nutritionalDrinks);

            Category stomachCare = createCategory("Stomach Care");
            SubCategory antacids = createSubCategory("Antacids", stomachCare);
            SubCategory laxatives = createSubCategory("Laxatives", stomachCare);

            Category personalCare = createCategory("Personal Care");
            SubCategory skinCare = createSubCategory("Skin Care", personalCare);
            SubCategory oralHygiene = createSubCategory("Oral Hygiene", personalCare);

            // Sample categories for medicines
            Category painRelief = createCategory("Pain Relief");
            SubCategory subCategory1 =  createSubCategory("Pain Relievers",painRelief);

            Category antibiotics =  createCategory("Antibiotics");
            SubCategory subCategory2 =  createSubCategory("Antibiotic Tablets",antibiotics);

            Category antacids1 =  createCategory("Antacids");
            SubCategory subCategory4 =  createSubCategory("Digestive Health",antacids1);

            Category allergy =  createCategory("Allergy & Sinus");
            SubCategory subCategory5 =  createSubCategory("Allergy Tablets",allergy);




            // Insert multiple Medicine Composition records with FAQs
            createMedicineComposition("Aspirin", "Pain Reliever",
                    "Used for pain relief, fever reduction, and inflammation.",
                    "Stomach upset, nausea, gastrointestinal bleeding.",
                    "Inhibits enzymes that cause pain and inflammation.",
                    "Take with food to reduce stomach upset.",
                    new String[][]{
                            {"How long does it take to work?", "It typically starts to work within 30 minutes."},
                            {"Is it safe for everyone?", "Consult your doctor if you have a history of stomach ulcers."}
                    });

            createMedicineComposition("Paracetamol", "Analgesic/Antipyretic",
                    "Used to relieve mild to moderate pain and reduce fever.",
                    "Liver damage with prolonged or excessive use.",
                    "Blocks chemical messengers that transmit pain signals and regulate body temperature.",
                    "Avoid alcohol while taking this medicine.",
                    new String[][]{
                            {"Can it be taken on an empty stomach?", "Yes, it can be taken with or without food."},
                            {"What should I do in case of overdose?", "Seek immediate medical attention."}
                    });

            createMedicineComposition("Ibuprofen", "Non-Steroidal Anti-Inflammatory Drug (NSAID)",
                    "Relieves pain, reduces inflammation, and lowers fever.",
                    "Stomach upset, heartburn, dizziness, or rash.",
                    "Reduces production of substances that cause inflammation and pain.",
                    "Do not exceed the recommended dose to avoid side effects.",
                    new String[][]{
                            {"Can it cause stomach problems?", "Yes, taking it with food may reduce the risk."},
                            {"Can I take it with other painkillers?", "Avoid combining with similar drugs like aspirin."}
                    });

            createMedicineComposition("Ciprofloxacin", "Antibiotic",
                    "Treats bacterial infections like urinary tract infections and respiratory infections.",
                    "Nausea, diarrhea, or sensitivity to sunlight.",
                    "Stops the growth of bacteria by interfering with their DNA replication.",
                    "Drink plenty of water and avoid sunlight exposure during treatment.",
                    new String[][]{
                            {"Can it be used for viral infections?", "No, it is ineffective against viruses."},
                            {"What should I avoid while taking this medicine?", "Avoid dairy products as they may reduce effectiveness."}
                    });

            createMedicineComposition("Cetirizine", "Antihistamine",
                    "Relieves allergy symptoms like runny nose, sneezing, and itching.",
                    "Drowsiness, dry mouth, or fatigue.",
                    "Blocks histamine, a substance that causes allergy symptoms.",
                    "Avoid driving or operating machinery if you feel drowsy.",
                    new String[][]{
                            {"Can I take it daily for allergies?", "Yes, but consult a doctor for long-term use."},
                            {"Does it cause drowsiness?", "It may cause drowsiness in some individuals."}
                    });

            createMedicineComposition("Metformin", "Anti-Diabetic",
                    "Helps control blood sugar levels in people with type 2 diabetes.",
                    "Nausea, stomach upset, or diarrhea.",
                    "Decreases glucose production in the liver and improves insulin sensitivity.",
                    "Take it with meals to reduce stomach upset.",
                    new String[][]{
                            {"Can it cause weight loss?", "It may lead to mild weight loss in some cases."},
                            {"Is it safe for people with kidney problems?", "Consult your doctor before use if you have kidney issues."}
                    });
            createMedicineProduct("Aspirin", "Bayer", "TABLET", "100 Tablets", true, "No returns after opening", 1L, 1L, 1L);
            createMedicineProduct("Paracetamol", "Panadol", "SYRUP", "200 ml", false, "Return within 30 days", 2L, 1L, 2L);
            createMedicineProduct("Ibuprofen", "Advil", "CAPSULE", "30 Capsules", true, "No returns after opening", 3L, 2L, 3L);
            createMedicineProduct("Amoxicillin", "Amoxil", "TABLET", "500 mg", true, "No returns after opening", 4L, 2L, 1L);
            createMedicineProduct("Vitamin C", "Nature's Bounty", "TABLET", "60 Tablets", false, "Return within 30 days", 5L, 3L, 2L);
            createMedicineProduct("Multivitamins", "Centrum", "CAPSULE", "30 Capsules", false, "Return within 30 days", 6L, 3L, 3L);
            createMedicineProduct("Antacid", "Tums", "CAPSULE", "150 Tablets", false, "Return within 30 days", 1L, 4L, 1L);
            createMedicineProduct("Omega-3", "Fish Oil", "CAPSULE", "100 Softgels", false, "No returns after opening", 2L, 4L, 2L);
            createMedicineProduct("Loperamide", "Imodium", "TABLET", "20 Tablets", true, "No returns after opening", 3L, 5L, 1L);
            createMedicineProduct("Loratadine", "Claritin", "TABLET", "10 Tablets", false, "Return within 30 days", 4L, 5L, 2L);
        }
    }

    private long getMedicineCompositionId(Long id){
        Optional<MedicineComposition> medicineComposition=medicineCompositionRepository.findById(id);
        if(medicineComposition.isEmpty()){
                throw new RuntimeException("Medicine Comppositoin Not Found");
        }
        return medicineComposition.get().getId();
    }

    private void createMedicineComposition(String name, String therapeuticClass, String use, String sideEffects,
                                           String working, String advice, String[][] faqs) {
        MedicineComposition composition = new MedicineComposition();
        composition.setMedicineCompositionName(name);
        composition.setCompositionTherapeuticClass(therapeuticClass);
        composition.setCompositionUse(use);
        composition.setCompositionSideEffects(sideEffects);
        composition.setCompositionWorking(working);
        composition.setCompositionExpertAdvice(advice);

        // Generate slug from name
        String slug = slugify.slugify(name);
        composition.setSlug(slug);

        // Add FAQs
        for (String[] faq : faqs) {
            MedicineFAQ faqEntry = new MedicineFAQ(faq[0], faq[1]);
            composition.addFAQ(faqEntry); // Add the FAQ to the composition
        }

        // Save the composition
        medicineCompositionRepository.save(composition);
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setCategoryName(name);
        categoryRepository.save(category);// Save category
        return category;
    }

    private SubCategory createSubCategory(String name, Category category) {
        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryName(name);
        subCategory.setCategory(category);
        subCategoryRepository.save(subCategory);  // Save subcategory
        return subCategory;
    }
    private void createMedicineProduct(String name, String manufacturer, String dosageForm, String packSize,
                                       Boolean isRx, String returnPolicy, Long compositionId,
                                       Long categoryId, Long subCategoryId) {
        MedicineProduct product = new MedicineProduct();
        product.setProductName(name);
        product.setProductManufacturer(manufacturer);
        product.setProductDosageForm(MedicineProduct.DosageForm.valueOf(dosageForm));
        product.setMedicinePackSize(packSize);
        product.setMedicineRx(isRx);
        product.setMedicineReturnPolicy(returnPolicy);

        // Retrieve and set MedicineComposition
        MedicineComposition composition = medicineCompositionRepository.findById(compositionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid MedicineComposition ID: " + compositionId));
        product.setMedicineComposition(composition);

        // Retrieve and set Category
        Category category = entityManager.find(Category.class, categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Invalid Category ID: " + categoryId);
        }
        product.setProductCategory(category);

        // Retrieve and set SubCategory
        SubCategory subCategory = entityManager.find(SubCategory.class, subCategoryId);
        if (subCategory == null) {
            throw new IllegalArgumentException("Invalid SubCategory ID: " + subCategoryId);
        }
        product.setProductSubCategory(subCategory);

        // Generate slug
        String slug = slugify.slugify(name);
        product.setSlug(slug);

        // Save product
        medicineProductRepository.save(product);
    }


//    private void createOTCProduct(String name, String manufacturer, String usageInstructions, String warnings,
//                                  String storageInstructions, Double weight, SubCategory subCategory, Category category) {
//        OTCProduct product = new OTCProduct();
//        product.setProductName(name);
//        product.setProductManufacturer(manufacturer);
//        product.setUsageInstructions(usageInstructions);
//        product.setWarnings(warnings);
//        product.setStorageInstructions(storageInstructions);
//        product.setWeight(weight);
//        product.setProductCategory(category);
//        product.setProductSubCategory(subCategory);
//
//        // Generate slug
//        String slug = slugify.slugify(name);
//        product.setSlug(slug);
//
//        // Save product
//        medicineProductRepository.save(product);
//    }
}

