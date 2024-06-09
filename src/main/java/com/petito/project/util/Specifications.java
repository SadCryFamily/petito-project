package com.petito.project.util;

import com.petito.project.entity.product.Brand;
import com.petito.project.entity.product.Category;
import com.petito.project.entity.product.Product;
import com.petito.project.entity.product.Subcategory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Specifications
{
    public static Specification<Product> filterByPriceAndCategory(String minPrice,
                                                                  String maxPrice,
                                                                  String category,
                                                                  String subcategory,
                                                                  String brandName)
    {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minPrice != null)
            {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null)
            {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (checkFilterParam(category))
            {
                Join<Product, Category> categoriesJoin = root.join("categories", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(categoriesJoin.get("name"), category));
            }

            if (checkFilterParam(subcategory))
            {
                Join<Product, Subcategory> subcategoriesJoin = root.join("subcategories", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(subcategoriesJoin.get("name"), subcategory));
            }

            if (checkFilterParam(brandName))
            {
                Join<Product, Brand> brandJoin = root.join("brand", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(brandJoin.get("name"), brandName));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean checkFilterParam(String value)
    {
        return (value != null && !value.equals("None") && !Objects.equals(value, "Немає"));
    }
}
