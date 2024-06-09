package com.petito.project.controller;

import com.petito.project.dto.view.ViewOrderDto;
import com.petito.project.entity.order.Order;
import com.petito.project.entity.product.Product;
import com.petito.project.entity.user.User;
import com.petito.project.repository.OrderRepository;
import com.petito.project.repository.ProductRepository;
import com.petito.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @PostMapping("/create")
    @Transactional
    public String createOrderPage(@RequestParam("id") Integer productId, Principal principal)
    {
        Product product = productRepository.findByProductId(productId);
        User user = userRepository.findByLogin(principal.getName());

        Order order = Order.builder()
                .status(Order.PayStatus.PAID)
                .arriveStatus(Order.ArriveStatus.IN_TRANSIT)
                .build();

        List<Order> productOrders = product.getOrders();
        productOrders.add(order);
        product.setOrders(productOrders);

        order.setUser(user);

        orderRepository.save(order);
        productRepository.save(product);

        return "item-purchase";
    }

    @GetMapping("/my")
    @Transactional(readOnly = true)
    public String viewOrdersPage(Principal principal, Model model)
    {
        User user = userRepository.findByLogin(principal.getName());

        if (Objects.nonNull(user.getOrders()))
        {
            if (user.getOrders().isEmpty())
            {
                model.addAttribute("errorMessage");
            }
        } else
        {
            model.addAttribute("errorMessage");
        }

        List<ViewOrderDto> orders = user.getOrders().stream()
                .map(ViewOrderDto::new)
                .collect(Collectors.toList());

        model.addAttribute("orders", orders);

        return "order-see";
    }
}
