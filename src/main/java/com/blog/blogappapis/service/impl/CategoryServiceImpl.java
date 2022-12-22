package com.blog.blogappapis.service.impl;

import com.blog.blogappapis.entities.Category;
import com.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.blog.blogappapis.payloads.CategoryDTO;
import com.blog.blogappapis.repository.CategoryRepo;
import com.blog.blogappapis.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=this.categoryRepo.save(this.dtoToCategory(categoryDTO));
        return this.categoryToDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," id",categoryId));
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        Category updatedCategory=this.categoryRepo.save(category);
        return  this.categoryToDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," id",categoryId));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDTO getCategoryById(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," id",categoryId));
        return this.categoryToDTO(category);
    }

    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> categoryList=this.categoryRepo.findAll();
        List<CategoryDTO> categoryDTOList=categoryList.stream().map(category->this.categoryToDTO(category)).collect(Collectors.toList());
        return categoryDTOList;
    }
    private CategoryDTO categoryToDTO(Category category){
        return this.modelMapper.map(category,CategoryDTO.class);
    }
    private Category dtoToCategory(CategoryDTO categoryDTO){
        return this.modelMapper.map(categoryDTO,Category.class);
    }
}
