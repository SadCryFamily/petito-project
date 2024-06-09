package com.petito.project.controller;

import com.petito.project.util.Specifications;
import com.petito.project.dto.view.ViewProductDto;
import com.petito.project.entity.product.Product;
import com.petito.project.entity.user.User;
import com.petito.project.repository.ProductRepository;
import com.petito.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class IndexController
{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final LocaleResolver localeResolver;

    @GetMapping("/")
    @Transactional
    public String indexPage(Model model,
                            @RequestParam(value = "minPrice", required = false) String minPrice,
                            @RequestParam(value = "maxPrice", required = false) String maxPrice,
                            @RequestParam(value = "category", required = false) String category,
                            @RequestParam(value = "subcategory", required = false) String subcategory,
                            @RequestParam(value = "brandName", required = false) String brandName,
                            Principal principal)
    {
        Specification<Product> specification = Specifications.filterByPriceAndCategory(
                minPrice,
                maxPrice,
                category,
                subcategory,
                brandName
        );
        List<ViewProductDto> products = productRepository.findAll(specification)
            .stream()
            .map(ViewProductDto::new).toList();

        model.addAttribute("products", products);

        validateAdmin(model, principal);

        return "index";
    }

    @GetMapping("/change-locale")
    public String changeLanguage(HttpServletRequest request, HttpServletResponse response,
                                 String lang)
    {
        localeResolver.setLocale(request, response, new Locale(lang));
        return "redirect:/";
    }

    private void validateAdmin(Model model, Principal principal)
    {
        if (Objects.nonNull(principal))
        {
            User user = userRepository.findByLogin(principal.getName());
            if (user.getRole().equals(User.Role.ADMIN))
            {
                model.addAttribute("isAdmin", true);
            }
        }
    }
}
