package com.petito.project.dto.view;

import com.petito.project.entity.product.Brand;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewBrandDto
{
    private String name;
    private String country;

    public ViewBrandDto(Brand brand)
    {
        this.name = brand.getName();
        this.country = brand.getCountry();
    }
}
