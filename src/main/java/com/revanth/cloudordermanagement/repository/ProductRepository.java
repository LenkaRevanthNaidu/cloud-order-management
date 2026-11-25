
package com.revanth.cloudordermanagement.repository;

import com.revanth.cloudordermanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

