package com.codework.shoppingcart.service.order;

import com.codework.shoppingcart.dto.OrderDto;
import com.codework.shoppingcart.model.Order;

import java.util.List;

public interface IOrderService
{
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
