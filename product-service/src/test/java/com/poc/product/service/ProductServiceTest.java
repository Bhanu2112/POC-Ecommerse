package com.poc.product.service;
import com.poc.product.entity.Product;
import com.poc.product.exception.ProductNotFound;
import com.poc.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void addProductTest() {
        // Arrange
        Product product = new Product(1L, "Test Product", 10.99, "Test Category", false);

        when(productRepository.save(any())).thenReturn(product);
        // Act
        Product addedProduct = productService.addProduct(product);

        // Assert
        assertEquals(product, addedProduct);
        verify(productRepository).save(product);
    }

    @Test
    void softDeleteProductTest() {
        // Arrange
        Long productId = 1L;
        Product product = new Product(productId, "Test Product", 10.99, "Test Category", false);
        doReturn(Optional.of(product)).when(productRepository).findById(productId);

        // Act
        productService.softDeleteProduct(productId);

        // Assert
        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
    }

    @Test
    void updateProductPriceTest() {
        // Arrange
        Long productId = 1L;
        Double newPrice = 9.99;
        Product product = new Product(productId, "Test Product", 10.99, "Test Category", false);
        doReturn(Optional.of(product)).when(productRepository).findById(productId);

        when(productRepository.save(any())).thenReturn(product);
        // Act
        Product updatedProduct = productService.updateProductPrice(productId, newPrice);

        // Assert
        assertEquals(newPrice, updatedProduct.getPrice());
        verify(productRepository).findById(productId);
        verify(productRepository).save(updatedProduct);
    }

    @Test
    void getProductByNameTest() {
        // Arrange
        String productName = "Test Product";
        Product product = new Product(1L, productName, 10.99, "Test Category", false);
        doReturn(Optional.of(product)).when(productRepository).findByProductName(productName);

        // Act
        Product retrievedProduct = productService.getProductByName(productName);

        // Assert
        assertEquals(product, retrievedProduct);
        verify(productRepository).findByProductName(productName);
    }

    @Test
    void getProductByNameNotFoundTest() {
        // Arrange
        String productName = "Test Product";
        doReturn(Optional.empty()).when(productRepository).findByProductName(productName);

        // Act and Assert
        assertThrows(ProductNotFound.class, () -> productService.getProductByName(productName));
        verify(productRepository).findByProductName(productName);
    }

    @Test
    void getProductsByCategoryTest() {
        // Arrange
        String category = "Test Category";
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Test Product 1", 10.99, category, false));
        products.add(new Product(2L, "Test Product 2", 9.99, category, false));
        doReturn(products).when(productRepository).findByCategory(category);

        // Act
        List<Product> retrievedProducts = productService.getProductsByCategory(category);

        // Assert
        assertEquals(products, retrievedProducts);
        verify(productRepository).findByCategory(category);
    }

    @Test
    void getProductsSortedByPriceAscTest() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Test Product 1", 9.99, "Test Category", false));
        products.add(new Product(2L, "Test Product 2", 10.99, "Test Category", false));
        doReturn(products).when(productRepository).findByPriceAsc();

        // Act
        List<Product> retrievedProducts = productService.getProductsSortedByPriceAsc();

        // Assert
        assertEquals(products, retrievedProducts);
        verify(productRepository).findByPriceAsc();
    }

    @Test
    void getProductByIdTest() {
        // Arrange
        Long productId = 1L;
        Product product = new Product(productId, "Test Product", 10.99, "Test Category", false);
        doReturn(Optional.of(product)).when(productRepository).findById(productId);

        // Act
        Product retrievedProduct = productService.getProductById(productId);

        // Assert
        assertEquals(product, retrievedProduct);
        verify(productRepository).findById(productId);
    }
}