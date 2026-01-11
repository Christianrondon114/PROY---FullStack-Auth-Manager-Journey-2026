package com.attemp3.sc.repository;

import com.attemp3.sc.entities.Product;
import com.attemp3.sc.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
