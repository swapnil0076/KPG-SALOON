package com.app.category.service;

import com.app.category.PayloadDTO.SalonDTO;
import com.app.category.model.Category;

import java.util.*;

public interface CategoryService {

Category saveCategory(Category category, SalonDTO salonDTO);
List<Category> getAllCategoryBySalon(Long id);
Category getCategoryById(String id) throws Exception;
Category deleteCategoryById(String id, Long salonId) throws Exception;

}
