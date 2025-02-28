package com.spring.jwt.SparePart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SparePartRepo extends JpaRepository<SparePart, Integer> {
}
