package com.editor;

import com.model.Category;
import com.service.CategoryService;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryEditor extends PropertyEditorSupport {

    @Autowired
    private CategoryService categoryService;

    // Converts a String role id to a Roll (when submitting form)
    @Override
    public void setAsText(String text) {
       Category c = this.categoryService.getCategory(Integer.valueOf(text));

        this.setValue(c);
    }
}


