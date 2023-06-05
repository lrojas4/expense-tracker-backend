package com.example.expensetrackerbackend.controller;
import com.example.expensetrackerbackend.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/")
public class CategoryController {

    private CategoryService categoryService;
}
