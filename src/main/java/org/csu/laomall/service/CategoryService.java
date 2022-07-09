package org.csu.laomall.service;

import org.csu.laomall.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategoriesByLevel(int level);

    Category getCategoryById(int categoryId);

    Category getCategoryByName(String name);

    Category addCategory(Category category);
}
