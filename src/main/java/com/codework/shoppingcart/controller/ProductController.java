package com.codework.shoppingcart.controller;

import com.codework.shoppingcart.dto.ProductDto;
import com.codework.shoppingcart.exceptions.ResourceNotFoundException;
import com.codework.shoppingcart.model.Product;
import com.codework.shoppingcart.request.AddProductRequest;
import com.codework.shoppingcart.request.ProductUpdateRequest;
import com.codework.shoppingcart.response.ApiResponse;
import com.codework.shoppingcart.service.product.iProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController
{
    private final iProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId)
    {
        try
        {
            Product product = productService.getProductById(productId);

            ProductDto productDto = productService.convertToDto(product);

            return ResponseEntity.ok(new ApiResponse("success", productDto));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product)
    {
        try
        {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add product success", theProduct));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, Long productId)
    {
        try
        {
            Product theProduct = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Update product success", theProduct));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId)
    {
        try
        {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", productId));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products/by/brand-and-name/")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName)
    {
        try
        {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            if (products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Products returned according to brand and name", convertedProducts));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by/category-and-brand/")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brandName)
    {
        try
        {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brandName);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            if (products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Products found!", convertedProducts));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products/{name}/product")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name)
    {
        try
        {
            List<Product> products = productService.getProductsByName(name);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            if (products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }

            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", null));
        }
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand)
    {
        try
        {
            List<Product> products = productService.getProductByBrand(brand);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            if (products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }

            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", null));
        }
    }

    @GetMapping("/products/{category}/all/product")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category)
    {
        try
        {
            List<Product> products = productService.getProductsByCategory(category);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            if (products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }

            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", null));
        }
    }

    @GetMapping("/product/count/by-brand/by-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name)
    {
        try
        {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count: ", productCount));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
}
