package com.codework.shoppingcart.service.product;

import com.codework.shoppingcart.dto.ProductDto;
import com.codework.shoppingcart.model.Product;
import com.codework.shoppingcart.request.AddProductRequest;
import com.codework.shoppingcart.request.ProductUpdateRequest;

import java.util.List;

public interface iProductService
{
    Product addProduct(AddProductRequest product);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductByBrand(String brandName);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);
    ProductDto convertToDto(Product product);
}
