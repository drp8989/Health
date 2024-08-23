package com.JustHealth.Health.MedicineProductDao;


import com.JustHealth.Health.Entity.MedicineProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class InventorySearchQuery {


    @PersistenceContext
    EntityManager entityManager;

    public Page<MedicineProduct> searchByNameAndManufacturer(String medicineProductName, String manufacturer){

        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();

        CriteriaQuery<MedicineProduct> cq=criteriaBuilder.createQuery(MedicineProduct.class);

        Root<MedicineProduct> medicineProduct = cq.from(MedicineProduct.class);

        Predicate medicineProductNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(medicineProduct.get("productName")),"%"+medicineProductName+"%");
        Predicate medicineProductManufacturerPredicate=criteriaBuilder.like(medicineProduct.get("productManufacturer"),"%"+manufacturer+"%");
        Predicate or=criteriaBuilder.or(medicineProductNamePredicate,medicineProductManufacturerPredicate);

        cq.where(medicineProductNamePredicate,medicineProductManufacturerPredicate);
        TypedQuery<MedicineProduct> query=entityManager.createQuery(cq);
        return null;
//        return query.getResultList();
    }



}
