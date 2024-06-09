package com.petito.project.dto.view;

import com.petito.project.entity.product.Subcategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewSubcategoryDto
{
    private Integer id;
    private String name;

    public ViewSubcategoryDto(Subcategory subcategory)
    {
        this.id = subcategory.getSubcategoryId();
        this.name = subcategory.getName();
    }
}
