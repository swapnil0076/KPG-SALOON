package com.app.category.respository;

import com.app.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {

    List<Category> findBySalonId(Long salonId);

}
