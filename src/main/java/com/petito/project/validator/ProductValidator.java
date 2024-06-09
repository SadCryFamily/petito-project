package com.petito.project.validator;

import com.petito.project.dto.create.CreateProductDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

@Component
public class ProductValidator implements Validator
{
    @Override
    public boolean supports(Class<?> clazz)
    {
        return CreateProductDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        CreateProductDto product = (CreateProductDto) target;
        rejectIfEmptyOrWhitespace(errors, "productType", "product.error.product-type");
        rejectIfEmptyOrWhitespace(errors, "price", "product.error.price");
        rejectIfEmptyOrWhitespace(errors, "amount", "product.error.amount");
        rejectIfEmptyOrWhitespace(errors, "sizes", "product.error.sized");
        rejectIfEmptyOrWhitespace(errors, "brand.name", "product.error.brand-name");
        rejectIfEmptyOrWhitespace(errors, "brand.country", "product.error.brand-country");
        rejectIfEmptyOrWhitespace(errors, "description", "product.error.description");
        rejectIfEmptyOrWhitespace(errors, "category.name", "product.error.category-name");
        rejectIfEmptyOrWhitespace(errors, "subcategory.name", "product.error.subcategory-name");
    }
}
