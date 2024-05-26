package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.exceptions.OutOfStockException;
import com.js1802_team5.diamondShop.exceptions.ProductNotFoundException;
import com.js1802_team5.diamondShop.models.request_models.Product;
import com.js1802_team5.diamondShop.models.response_models.Cart;
import com.js1802_team5.diamondShop.models.response_models.CartItem;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import com.js1802_team5.diamondShop.repositories.DiamondShellRepo;
import com.js1802_team5.diamondShop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final DiamondShellRepo diamondShellRepo;
    private final DiamondRepo diamondRepo;
    private final Map<String, Cart> cartStorage = new HashMap<>();
    private static int cartCounter = 1;
    @Override
    public String generateCartId() {
        String cartId = String.format("CA%06d", cartCounter);
        cartCounter++;
        return cartId;
    }

    @Override
    public String addToCart(int productID, ProductType productType, int customerID) {
        Cart cart = null;
        if (cartStorage.isEmpty()) {
            String newCartID = generateCartId();
            cart = new Cart(newCartID, customerID, new ArrayList<>());
            cartStorage.put(newCartID, cart);
        } else {
            for (Map.Entry<String, Cart> entry : cartStorage.entrySet()) {
                Cart storedCart = entry.getValue();
                if (storedCart.getCustomerID().equals(customerID)) {
                    cart = storedCart;
                    break;
                } else {
                    String newCartID = generateCartId();
                    cart = new Cart(newCartID, customerID, new ArrayList<>());
                    cart.setCustomerID(customerID);
                    cartStorage.put(newCartID, cart);
                }
            }
        }

        Product product = findProductById(productID, productType);

        if (product.getQuantity() == 0) {
            throw new OutOfStockException("Product out of stock");
        }

        Optional<CartItem> existingItemOptional = cart.getItems().stream()
                .filter(item -> item.getProductId() == (productID) && item.getProductType() == productType)
                .findFirst();

        if (existingItemOptional.isPresent()) {
            CartItem existingItem = existingItemOptional.get();
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            existingItem.setAmount(existingItem.getAmount() + existingItem.getUnitPrice());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(productID);
            newItem.setProductType(productType);
            newItem.setQuantity(1);
            newItem.setUnitPrice(product.getPrice());
            newItem.setAmount(product.getPrice());
            cart.getItems().add(newItem);
        }
        cartStorage.put(cart.getCartId(), cart);
        return cart.getCartId();
    }

    @Override
    public Cart getCart(String cartID, int customerID) {
        return cartStorage.getOrDefault(cartID, new Cart(cartID, customerID ,new ArrayList<>()));
    }

    @Override
    public Product findProductById(int productID, ProductType productType) {
        if (productType == ProductType.DIAMOND) {
            return diamondRepo.findById(productID).orElseThrow(() -> new ProductNotFoundException("Diamond not found"));
        } else if (productType == ProductType.DIAMOND_SHELL) {
            return diamondShellRepo.findById(productID).orElseThrow(() -> new ProductNotFoundException("Diamond Shell not found"));
        } else {
            throw new IllegalArgumentException("Invalid product type");
        }
    }
}
