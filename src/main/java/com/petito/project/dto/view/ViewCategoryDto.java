package com.petito.project.dto.view;

import com.petito.project.entity.product.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewCategoryDto
{
    private Integer id;
    private String name;

    public ViewCategoryDto(Category category)
    {
        this.id = category.getCategoryId();
        this.name = category.getName();
    }
}
