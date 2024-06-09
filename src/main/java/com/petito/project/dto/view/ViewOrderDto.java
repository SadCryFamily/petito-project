package com.petito.project.dto.view;

import com.petito.project.entity.order.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewOrderDto
{
    private Integer orderId;
    private Order.PayStatus status;
    private Order.ArriveStatus arriveStatus;
    private ViewProductDto product;

    public ViewOrderDto(Order order)
    {
        this.orderId = order.getOrderId();
        this.status = order.getStatus();
        this.arriveStatus = order.getArriveStatus();
        if (order.getProducts().size() != 0)
        {
            this.product = new ViewProductDto(order.getProducts().get(0));
        }
    }
}
