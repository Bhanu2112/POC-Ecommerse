 package com.cart.api.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.cart.api.config.ProductServiceClient;
import com.cart.api.dto.CartAction;
import com.cart.api.dto.Product;
import com.cart.api.entity.Cart;
import com.cart.api.entity.CartItem;
import com.cart.api.repository.CartItemRepository;
import com.cart.api.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductServiceClient productServiceClient;
	
	
	@Autowired
	private AssemblyAI assemblyAI;
	
	
	/**
     * Get the cart for a specific user. If the cart doesn't exist, create a new one.
     */
	
	public Cart getOrCreateCart(Long userId) {
		
		return cartRepository.findByUserId(userId).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setUserId(userId);
			newCart.setCartItems(new ArrayList<>());
			return cartRepository.save(newCart);
		});
		
	}
	

	@CachePut(value = "cart",key = "#result.id")
	@Transactional
	public Cart addProductToCart(Long userId, Long productId, int quantity) {
	    Cart cart = getOrCreateCart(userId);
	    
	 
	    Product product = productServiceClient.getProductById(productId);
	    		//restTemplate.getForObject("http://localhost:8080/product/byid/" + productId, Product.class);
	    
	    
	    System.out.println("Product Retrieved: " + product);
	    
	    Optional<CartItem> existingItem = cart.getCartItems().stream()
	            .filter(item -> item.getProductId().equals(productId))
	            .findFirst();

	    if (existingItem.isPresent()) {
	        CartItem citem = existingItem.get();
	        citem.setQuantity(citem.getQuantity() + quantity);
	        cartItemRepository.save(citem);
	    } else {
	        CartItem cartItem = new CartItem();
	        cartItem.setProductId(productId);
	        cartItem.setQuantity(quantity);
	        cartItem.setCart(cart);
	        
	        cart.getCartItems().add(cartItem); 
	        
	        cartItemRepository.save(cartItem);
	    }
	    
	    cart = cartRepository.findById(cart.getId()).orElseThrow();
	    
	 
	    return cart;
	}
//	  @Caching(
//	            evict = {@CacheEvict(value = "user", allEntries = true), @CacheEvict(value="user", key="#id")
//	            })
	@Caching(evict = {@CacheEvict(value = "cart", allEntries = true),@CacheEvict(value="cart", key="#userId")})
	public Cart removeCartProduct(Long userId,Long productId) {
		
		Cart cart = getOrCreateCart(userId);
		Optional<CartItem> citem =	cart.getCartItems().stream().filter(item -> item.getProductId()==productId).findFirst();
		
		if(citem.isEmpty()) {
			throw new RuntimeException("Cart item not found");
		}else {
			System.out.println("entered detele method");
			CartItem ci = citem.get();
			ci.setCart(null);
			cartItemRepository.save(ci);
			
			cartItemRepository.deleteById(ci.getId());	
			cart.getCartItems().removeIf(i -> i.getId()==ci.getId());
			cartRepository.save(cart);
			
			
		}
		
		 cart = cartRepository.findById(cart.getId()).orElseThrow();
		
		return cart;
	}
	
	
	@Cacheable(value = "cart",key="#userId")
	public Cart getCart(Long userId) {
		return getOrCreateCart(userId);
	}
	
	
	 // Mapping of number words to digits
    private static final Map<String, Integer> numberWords = Map.of(
        "one", 1, "two", 2, "three", 3, "four", 4, "five", 5,
        "six", 6, "seven", 7, "eight", 8, "nine", 9, "ten", 10
    );

    public static CartAction parseCommand(String command) {
        // Fix missing spaces in commands like "Add2 iPhones"
        command = command.replaceAll("(?i)(add|remove)(\\d+)", "$1 $2");

        // Convert number words to digits (e.g., "two" → "2")
        for (Map.Entry<String, Integer> entry : numberWords.entrySet()) {
            command = command.replaceAll("(?i)\\b" + entry.getKey() + "\\b", entry.getValue().toString());
        }

        // Regex to extract action, quantity (optional), and product
        Pattern pattern = Pattern.compile("(?i)(add|remove)\\s*(\\d+)?\\s*(\\w+)");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            String action = matcher.group(1).toLowerCase(); // "add" or "remove"
            int quantity = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 1; // Default = 1
            String productName = matcher.group(3).toLowerCase(); // Extract product name

            return new CartAction(action, productName, quantity);
        }

        return null; // No match found
    }
	
	
	public String audioToText(MultipartFile file) throws IOException {
		
	
		File tempFile = saveFile(file);
		
		System.out.println(tempFile.getAbsolutePath());
		
		
		
		Transcript transcript = assemblyAI.transcripts().transcribe(new File("C:/Users/Lenovo/Downloads/audiod/audio4.mp3"));
		System.out.println(transcript.getText().get());
		return transcript.getText().get();
	}
	
	
//	 private File saveFile(MultipartFile file) throws IOException {
//	        File tempFile = File.createTempFile("uploaded_", ".mp3");
//	        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
//	            fos.write(file.getBytes());
//	        }
//	        return tempFile;
//	    }
	private File saveFile(MultipartFile file) throws IOException {
	    // Create a temporary file with the same name as the uploaded file
		File tempFile = File.createTempFile("uploaded_", ".mp3");

	    
	    // Save the uploaded content to the temp file
	    try (InputStream inputStream = file.getInputStream()) {
	        Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	    }
	    
	    return tempFile;
	}
	
	
	public Cart processAudio(Long userId, MultipartFile file) throws IOException {
		String commond = audioToText(file);
		return processVoiceCommand(commond, userId);
	}
	
	
	
	public  Cart processVoiceCommand(String command, Long userId) {
        CartAction action = parseCommand(command);

        if (action == null) {
            System.out.println("Invalid command format");
            
        }
        
        Product product = productServiceClient.getProductByName(action.getProductName());

        // Get Product ID from Product Service (assuming API exists)
     //   Long productId = getProductIdFromName(action.getProductName());

        if (product == null) {

            throw new RuntimeException("Product not found: " + action.getProductName());
        }

        if ("add".equals(action.getAction())) {
         
            System.out.println("Added " + action.getQuantity() + " " + action.getProductName() + " to cart.");
           return addProductToCart(userId,product.getId(),action.getQuantity());
        } 
        else if ("remove".equals(action.getAction())) {
          
            System.out.println("Removed " + action.getQuantity() + " " + action.getProductName() + " from cart.");
           return removeCartProduct(userId, product.getId());
        }
		return null;
    }

}
