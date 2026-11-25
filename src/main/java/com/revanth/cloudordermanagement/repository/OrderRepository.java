
package com.revanth.cloudordermanagement.repository;

import com.revanth.cloudordermanagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
