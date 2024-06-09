package com.petito.project.repository;

import com.petito.project.entity.product.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product>
{
    Product findByProductId(Integer productId);
    List<Product> findAllByPriceGreaterThan(Integer minPrice);
    List<Product> findAll(Specification<Product> specification);
}
