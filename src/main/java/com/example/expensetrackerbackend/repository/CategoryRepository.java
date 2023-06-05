package com.example.expensetrackerbackend.repository;
import com.example.expensetrackerbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
