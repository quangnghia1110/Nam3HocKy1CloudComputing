package hcmute.service;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import hcmute.dto.CategoryForm;
import hcmute.model.Category;

public interface ICategoryService {

	List<Category> getAllCategories();

	void insert(@Valid CategoryForm category) throws IOException;

    List<CategoryForm> getCategoryShows();

    CategoryForm getById(long parseLong);

    void updateCategory(CategoryForm categoryForm) throws IOException;
}
