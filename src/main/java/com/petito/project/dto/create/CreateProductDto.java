package com.petito.project.dto.create;

import com.petito.project.entity.product.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductDto
{
    private String productType;
    private Integer price;
    private Integer amount;
    private String sizes;
    private Brand brand;
    private String description;
    private CreateCategoryDto category;
    private CreateSubcategoryDto subcategory;
}
