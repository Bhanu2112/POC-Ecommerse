//package com.cart.api.service;
//
//import com.assemblyai.api.AssemblyAI;
//import com.cart.api.config.ProductServiceClient;
//import com.cart.api.dto.Product;
//import com.cart.api.entity.Cart;
//import com.cart.api.entity.CartItem;
//import com.cart.api.repository.CartItemRepository;
//import com.cart.api.repository.CartRepository;
//import com.cart.api.service.CartService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CartServiceTest {
//
//    @Mock
//    private CartRepository cartRepository;
//
//    @Mock
//    private CartItemRepository cartItemRepository;
//
//    @Mock
//    private ProductServiceClient productServiceClient;
//
//    @Mock
//    private AssemblyAI assemblyAI;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private CartService cartService;
//
//    @Test
//    public void testGetOrCreateCart_UserExists_ReturnsCart() {
//        // Arrange
//        Long userId = 1L;
//        Cart cart = new Cart();
//        cart.setUserId(userId);
//        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
//
//        // Act
//        Cart result = cartService.getOrCreateCart(userId);
//
//        // Assert
//        assertEquals(cart, result);
//        verify(cartRepository, times(1)).findByUserId(userId);
//    }
//
//    @Test
//    public void testGetOrCreateCart_UserDoesNotExist_ReturnsNewCart() {
//        // Arrange
//        Long userId = 1L;
//        Cart cart = new Cart();
//        cart.setUserId(userId);
//        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
//
//        // Act
//        Cart result = cartService.getOrCreateCart(userId);
//
//        // Assert
//        assertEquals(cart.getUserId(), result.getUserId());
//        verify(cartRepository, times(1)).findByUserId(userId);
//        verify(cartRepository, times(1)).save(any(Cart.class));
//    }
//
//    @Test
//    public void testAddProductToCart_ProductExists_IncrementsQuantity() {
//        // Arrange
//        Long userId = 1L;
//        Long productId = 1L;
//        int quantity = 2;
//        Cart cart = new Cart();
//        cart.setUserId(userId);
//        CartItem cartItem = new CartItem();
//        cartItem.setProductId(productId);
//        cartItem.setQuantity(1);
//        cart.setCartItems(new ArrayList<>());
//        cart.getCartItems().add(cartItem);
//
//        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
//        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
//        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
//        when(productServiceClient.getProductById(productId)).thenReturn(new Product());
//
//        // Act
//        Cart result = cartService.addProductToCart(userId, productId, quantity);
//
//        // Assert
//        assertEquals(3, result.getCartItems().get(0).getQuantity());
//        verify(cartItemRepository, times(1)).save(any(CartItem.class));
//    }
//
//    @Test
//    public void testAddProductToCart_ProductDoesNotExist_AddsNewCartItem() {
//        // Arrange
//        Long userId = 1L;
//        Long productId = 1L;
//        int quantity = 2;
//        Cart cart = new Cart();
//        cart.setUserId(userId);
//        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
//        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
//        when(productServiceClient.getProductById(productId)).thenReturn(new Product());
//
//        // Act
//        Cart result = cartService.addProductToCart(userId, productId, quantity);
//
//        // Assert
//        assertEquals(1, result.getCartItems().size());
//        assertEquals(productId, result.getCartItems().get(0).getProductId());
//        assertEquals(quantity, result.getCartItems().get(0).getQuantity());
//        verify(cartItemRepository, times(1)).save(any(CartItem.class));
//    }
//
//    @Test
//    public void testRemoveCartProduct_ProductExists_RemovesCartItem() {
//        // Arrange
//        Long userId = 1L;
//        Long productId = 1L;
//        Cart cart = new Cart();
//        cart.setUserId(userId);
//        CartItem cartItem = new CartItem();
//        cartItem.setProductId(productId);
//        cart.getCartItems().add(cartItem);
//        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
//
//        // Act
//        Cart result = cartService.removeCartProduct(userId, productId);
//
//        // Assert
//        assertEquals(0, result.getCartItems().size());
//        verify(cartItemRepository, times(1)).deleteById(any(Long.class));
//    }
//
//    @Test
//    public void testRemoveCartProduct_ProductDoesNotExist_ThrowsRuntimeException() {
//        // Arrange
//        Long userId = 1L;
//        Long productId = 1L;
//        Cart cart = new Cart();
//        cart.setUserId(userId);
//        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
//
//        // Act and Assert
//        assertThrows(RuntimeException.class, () -> cartService.removeCartProduct(userId, productId));
//    }
//
//
//
//
//}
