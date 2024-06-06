package com.marcoscsouza.DDDTP3.domains.repositories;

import com.marcoscsouza.DDDTP3.domains.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
