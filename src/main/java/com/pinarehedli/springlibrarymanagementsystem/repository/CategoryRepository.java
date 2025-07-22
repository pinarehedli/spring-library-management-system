package com.pinarehedli.springlibrarymanagementsystem.repository;

import com.pinarehedli.springlibrarymanagementsystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
