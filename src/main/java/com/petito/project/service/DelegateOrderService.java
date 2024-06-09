package com.petito.project.service;

import com.petito.project.entity.order.Order;
import com.petito.project.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

@Service
@Slf4j
@RequiredArgsConstructor
public class DelegateOrderService
{
    private final OrderRepository orderRepository;
    private final SmtpService smtpService;

    @Transactional
    @Scheduled(
            initialDelayString = "${order.simulation.arrive-timeout-ms}",
            fixedDelayString = "${order.simulation.arrive-timeout-ms}"
    )
    public void nextArrive()
    {
        Iterator<Order> rootIterator = orderRepository.findAll().iterator();
        while (rootIterator.hasNext())
        {
            Order currentOrder = rootIterator.next();
            if (currentOrder.getArriveStatus() == Order.ArriveStatus.IN_TRANSIT)
            {
                currentOrder.setArriveStatus(Order.ArriveStatus.ARRIVED);
                smtpService.orderArrivedNotify(
                        currentOrder.getUser().getEmail(),
                        currentOrder.getUser().getDetails().getFirstName(),
                        currentOrder
                );
            }
        }
    }
}
