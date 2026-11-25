
package com.revanth.cloudordermanagement.service;

import com.revanth.cloudordermanagement.dto.CreateOrderRequest;
import com.revanth.cloudordermanagement.dto.OrderItemRequest;
import com.revanth.cloudordermanagement.entity.Order;
import com.revanth.cloudordermanagement.entity.OrderItem;
import com.revanth.cloudordermanagement.entity.Product;
import com.revanth.cloudordermanagement.exception.ResourceNotFoundException;
import com.revanth.cloudordermanagement.repository.OrderRepository;
import com.revanth.cloudordermanagement.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {

        Order order = new Order();
        order.setStatus("PLACED");

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with ID: " + itemRequest.getProductId()));

            int requestedQuantity = itemRequest.getQuantity();

            if (product.getQuantity() < requestedQuantity) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: " + product.getName());
            }

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(requestedQuantity));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(requestedQuantity);
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(itemTotal);

            // Reduce available inventory
            product.setQuantity(product.getQuantity() - requestedQuantity);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with ID: " + id));
    }
}
