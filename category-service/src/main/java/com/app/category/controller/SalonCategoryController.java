package com.app.category.controller;


import com.app.category.PayloadDTO.SalonDTO;
import com.app.category.model.Category;
import com.app.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories/salon-owner")
@AllArgsConstructor
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> getCategoryBySaloon(@RequestBody Category category  ){

        SalonDTO salonDTO = new SalonDTO();

        salonDTO.setId(1L);
        Category categories = categoryService.saveCategory(category,salonDTO);

        return ResponseEntity.ok(categories);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id) throws Exception {

        SalonDTO salonDTO = new SalonDTO();

        salonDTO.setId(1L);

        Category categories = categoryService.deleteCategoryById(id,salonDTO.getId());

        return ResponseEntity.ok("Category Deleted Successfully");


    }

}
