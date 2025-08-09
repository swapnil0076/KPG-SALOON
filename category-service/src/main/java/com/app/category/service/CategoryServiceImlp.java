package com.app.category.service;

import com.app.category.PayloadDTO.SalonDTO;
import com.app.category.model.Category;
import com.app.category.respository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImlp implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {

        Category newCategory = new Category();

        newCategory.setName(category.getName());
        newCategory.setSalonId(salonDTO.getId());
        newCategory.setImage(category.getImage());

        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> getAllCategoryBySalon(Long id) {

        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(String id) throws Exception {

        Category category = categoryRepository.findById(id).orElse(null);

        if(category == null){
            throw new Exception("Category not exists with Id "+id);
        }

        return category;
    }

    @Override
    public Category deleteCategoryById(String id, Long salonId) throws Exception {

        Category category = getCategoryById(id);

        if(!category.getSalonId().equals(salonId)){
            throw new Exception("You Don't have Permission to delete this Id");
        }

        categoryRepository.deleteById(id);

        return category;
    }
}
