package com.petito.project.controller;

import com.petito.project.dto.create.CreateProductDto;
import com.petito.project.entity.product.Brand;
import com.petito.project.entity.product.Category;
import com.petito.project.entity.product.Product;
import com.petito.project.entity.product.Subcategory;
import com.petito.project.repository.ProductRepository;
import com.petito.project.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController
{
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    @InitBinder("product")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(productValidator);
    }

    @GetMapping("/create")
    public String createProductPage(Model model)
    {
        model.addAttribute("product", new CreateProductDto());
        return "product-create";
    }

    @PostMapping("/create")
    @Transactional
    public String createProduct(@ModelAttribute("product") @Valid CreateProductDto product, BindingResult result)
    {
        if (result.hasErrors())
        {
            return "product-create";
        }

        Category solidCategory = Category.builder()
                .name(product.getCategory().getName())
                .build();

        Brand solidBrand = product.getBrand();

        Subcategory solidSubcategory = Subcategory.builder()
                .name(product.getSubcategory().getName())
                .build();

        Product solidProduct = Product.builder()
                .productType(product.getProductType())
                .code(UUID.randomUUID().hashCode())
                .price(product.getPrice())
                .amount(product.getAmount())
                .brand(solidBrand)
                .sizes(product.getSizes())
                .description(product.getDescription())
                .available(Product.ProductAvailable.NOT_AVAILABLE)
                .build();

        List<Category> categoryReference = new ArrayList<>();
        categoryReference.add(solidCategory);
        solidProduct.setCategories(categoryReference);

        List<Subcategory> subcategoryReference = new ArrayList<>();
        subcategoryReference.add(solidSubcategory);
        solidProduct.setSubcategories(subcategoryReference);

        solidProduct.setBrand(solidBrand);

        productRepository.save(solidProduct);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") String productId)
    {
        productRepository.deleteById(Integer.parseInt(productId));
        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String editProductPage(@PathVariable("id") String productId, Model model)
    {
        model.addAttribute("id", productId);
        model.addAttribute("updateProduct", new CreateProductDto());
        return "product-edit";
    }

    @PostMapping("/modify")
    @Transactional
    public String editProductById(@RequestParam("id") String productId,
                                  @ModelAttribute("updateProduct") @Valid CreateProductDto productDto,
                                  BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "product-edit";
        }

        Product product = productRepository.findByProductId(Integer.parseInt(productId));

        product.setProductType(productDto.getProductType());
        product.setPrice(productDto.getPrice());
        product.setSizes(productDto.getSizes());
        product.setAmount(productDto.getAmount());
        product.setBrand(productDto.getBrand());
        product.setDescription(productDto.getDescription());
        product.getCategories().set(0,
                Category.builder()
                .name(productDto.getCategory().getName())
                        .build()
        );
        product.getSubcategories().set(0,
                Subcategory.builder()
                        .name(productDto.getSubcategory().getName())
                        .build()
        );
        return "redirect:/";
    }
}
