package com.petito.project.entity.order;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.petito.project.entity.product.Product;
import com.petito.project.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PayStatus status;

    @Column(name = "arrive_status")
    @Enumerated(EnumType.STRING)
    private ArriveStatus arriveStatus;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_login")
    private User user;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_order",
            joinColumns = @JoinColumn(name = "orders_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public enum PayStatus
    {
        PAID, NOT_PAID;
    }

    public enum ArriveStatus
    {
        IN_TRANSIT,
        ARRIVED;
    }
}
