package org.csu.laomall.controller.controller;

import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.entity.Category;
import org.csu.laomall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories/level/{level}")
    public CommonResponse<List<Category>> getCategoriesByLevel(@PathVariable int level) {
        return CommonResponse.createForSuccess(categoryService.getCategoriesByLevel(level));
    }

    @GetMapping("/category/id/{id}")
    public CommonResponse<Category> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return CommonResponse.createForError("没有找到该分类");
        } else {
            return CommonResponse.createForSuccess(category);
        }
    }

}
