package com.codework.shoppingcart.controller;

import com.codework.shoppingcart.exceptions.AlreadyExistsException;
import com.codework.shoppingcart.exceptions.ResourceNotFoundException;
import com.codework.shoppingcart.model.Category;
import com.codework.shoppingcart.response.ApiResponse;
import com.codework.shoppingcart.service.category.iCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController
{
    private final iCategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories()
    {
        try
        {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok().body(new ApiResponse("Found!", categories));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name)
    {
        try
        {
            Category theCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success!", theCategory));
        }
        catch (AlreadyExistsException e)
        {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id)
    {
        try
        {
            Category theCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok().body(new ApiResponse("Found!", theCategory));
        } catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name)
    {
        try
        {
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok().body(new ApiResponse("Found!", theCategory));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id)
    {
        try
        {
            categoryService.getCategoryById(id);
            return ResponseEntity.ok().body(new ApiResponse("Found!", null));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category)
    {
        try
        {
            Category updatedCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok().body(new ApiResponse("Update success", updatedCategory));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
