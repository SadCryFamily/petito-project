package com.petito.project.controller;

import com.petito.project.dto.view.ViewCommentDto;
import com.petito.project.entity.comment.Comment;
import com.petito.project.entity.product.Product;
import com.petito.project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CommentController
{
    private final ProductRepository productRepository;

    @GetMapping("/product/{id}/comments")
    @Transactional(readOnly = true)
    public String viewCommentsPage(@PathVariable("id") String productId, Model model)
    {
        List<ViewCommentDto> comments = Collections.emptyList();
        Product product = productRepository.findByProductId(Integer.parseInt(productId));

        if (Objects.nonNull(product.getComments()))
        {
            comments = product.getComments().stream()
                    .map(ViewCommentDto::new)
                    .collect(Collectors.toList());
        }

        model.addAttribute("productId", productId);
        model.addAttribute("comments", comments);

        return "post-comments";
    }

    @PostMapping("/product/{id}/comment")
    @Transactional
    public String addNewComment(@PathVariable("id") String productId,
                                @RequestParam("commentText") String commentText, Principal principal)
    {
        Product product = productRepository.findByProductId(Integer.parseInt(productId));

        Comment comment = Comment.builder()
                .author(principal.getName())
                .product(product)
                .message(commentText)
                .creationDateTime(new Date())
                .build();

        product.getComments().add(comment);

        return "redirect:/product/{id}/comments";
    }
}
