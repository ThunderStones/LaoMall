package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.laomall.entity.Category;
import org.csu.laomall.persistence.CategoryMapper;
import org.csu.laomall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoriesByLevel(int level) {
        return categoryMapper.selectList(new QueryWrapper<Category>().eq("level", level));
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return categoryMapper.selectById(categoryId);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryMapper.selectOne(new QueryWrapper<Category>().eq("name", name));
    }

    @Override
    public Category addCategory(Category category) {
        categoryMapper.insert(category);
        return category;
    }


}
