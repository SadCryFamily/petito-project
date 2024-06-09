package com.petito.project.dto.view;

import com.petito.project.entity.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ViewProductDto implements Serializable
{
    private Integer id;
    private String productType;
    private Integer price;
    private Integer amount;
    private String sizes;
    private ViewBrandDto brand;
    private String description;
    private ViewCategoryDto category;
    private ViewSubcategoryDto subcategory;

    public ViewProductDto(Product product)
    {
        this.id = product.getProductId();
        this.productType = product.getProductType();
        this.price = product.getPrice();
        this.amount = product.getAmount();
        this.sizes = product.getSizes();
        this.brand = new ViewBrandDto(product.getBrand());
        this.description = product.getDescription();
        this.category = new ViewCategoryDto(product.getCategories().get(0));
        this.subcategory = new ViewSubcategoryDto((product.getSubcategories().get(0)));
    }

    @Override
    public String toString() {
        return "ViewProductDto{" +
                "id=" + id +
                ", productType='" + productType + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", sizes='" + sizes + '\'' +
                ", brand=" + brand +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", subcategory=" + subcategory +
                '}';
    }
}
