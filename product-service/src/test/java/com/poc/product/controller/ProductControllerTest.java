package com.poc.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.product.entity.Product;
import com.poc.product.jwt.JwtService;
import com.poc.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)  // Disable Security Filters for testing
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;
    @MockBean
    private JwtService jwtService;



    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setProductName("Laptop");
        sampleProduct.setCategory("Electronics");
        sampleProduct.setPrice(1500.0);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addProduct_ShouldReturnProduct() throws Exception {
        when(productService.addProduct(any(Product.class))).thenReturn(sampleProduct);

        mockMvc.perform(post("/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.category").value("Electronics"))
                .andExpect(jsonPath("$.price").value(1500.0));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProduct_ShouldReturnMessage() throws Exception {
        Mockito.doNothing().when(productService).softDeleteProduct(anyLong());

        mockMvc.perform(delete("/product/remove/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product soft deleted"));
    }

    @Test
    void getProductByName_ShouldReturnProduct() throws Exception {
        when(productService.getProductByName("Laptop")).thenReturn(sampleProduct);

        mockMvc.perform(get("/product/name/Laptop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.category").value("Electronics"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProductPrice_ShouldReturnUpdatedProduct() throws Exception {
        sampleProduct.setPrice(1200.0);
        when(productService.updateProductPrice(anyLong(), any(Double.class))).thenReturn(sampleProduct);

        mockMvc.perform(put("/product/update-price/1?newPrice=1200.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(1200.0));
    }

    @Test
    void getProductsByCategory_ShouldReturnProductList() throws Exception {
        List<Product> products = Arrays.asList(sampleProduct);
        when(productService.getProductsByCategory("Electronics")).thenReturn(products);

        mockMvc.perform(get("/product/category/Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].category").value("Electronics"));
    }

    @Test
    void getProductsSortedByAsc_ShouldReturnSortedList() throws Exception {
        List<Product> products = Arrays.asList(sampleProduct);
        when(productService.getProductsSortedByPriceAsc()).thenReturn(products);

        mockMvc.perform(get("/product/price/asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getProductsSortedByDesc_ShouldReturnSortedList() throws Exception {
        List<Product> products = Arrays.asList(sampleProduct);
        when(productService.getProductsSortedByPriceDesc("desc")).thenReturn(products);

        mockMvc.perform(get("/product/price/orderby?orderBy=desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getProductById_ShouldReturnProduct() throws Exception {
        when(productService.getProductById(1L)).thenReturn(sampleProduct);

        mockMvc.perform(get("/product/byid/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
