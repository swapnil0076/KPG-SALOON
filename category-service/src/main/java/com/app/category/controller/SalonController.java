package com.app.category.controller;

import com.app.category.model.Category;
import com.app.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class SalonController {

    private final CategoryService categoryService;

    @GetMapping("/salon/{id}")
    public ResponseEntity<List<Category>> getCategoryBySaloon(@PathVariable Long id){

        List<Category> categories = categoryService.getAllCategoryBySalon(id);

        return ResponseEntity.ok(categories);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id) throws Exception {

        Category categories = categoryService.getCategoryById(id);

        return ResponseEntity.ok(categories);

    }

}
