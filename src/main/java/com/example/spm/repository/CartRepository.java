package com.example.spm.repository;

import com.example.spm.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart ,Long> {

}
