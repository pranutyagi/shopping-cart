package com.codework.shoppingcart.repository;

import com.codework.shoppingcart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>
{
    Cart findByUserId(Long userId);
}
